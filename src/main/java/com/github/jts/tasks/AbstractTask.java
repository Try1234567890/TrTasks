package com.github.jts.tasks;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.imlps.RepeatingTask;
import com.github.jts.tasks.imlps.time.AsyncTimeTask;
import com.github.jts.tasks.imlps.time.SyncTimeTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

public abstract class AbstractTask implements Task {
    public static final Logger LOGGER = Logger.getLogger(AbstractTask.class.getName());
    private final String id;
    private final Executor executor;
    private final Timer timer;
    private final Time initialDelay;
    private final List<TaskListener> listeners = new CopyOnWriteArrayList<>();
    private final TaskConfig config;
    private TaskAction action;

    private volatile TaskState state = TaskState.PENDING;

    public AbstractTask(String id, Executor executor, Timer timer,
                        Time initialDelay, TaskConfig config, TaskAction action) {
        this.id = Preconditions.parameterNotNull(id, "id");
        this.executor = Preconditions.simpleParameterNotNull(executor, "executor");
        this.timer = Preconditions.simpleParameterNotNull(timer, "timer");
        this.initialDelay = Time.ensure(initialDelay);
        this.config = Preconditions.simpleNotNull(config, new TaskConfig());
        this.action = Preconditions.simpleParameterNotNull(action, "action");
    }

    protected void setAction(TaskAction action) {
        if (state != TaskState.PENDING && state != TaskState.PAUSED)
            throw new IllegalStateException("Cannot set action for task in state: " + state + ". Only PENDING and PAUSED states are allowed.");
        this.action = Preconditions.parameterNotNull(action, "action");
    }

    public TaskConfig config() {
        return config;
    }

    public Timer getTimer() {
        return timer;
    }

    /**
     * Retrieves the identifier of this task.
     * Useful for logging or to search a task.
     *
     * @return the identifier of this task.
     */
    @Override
    public String getID() {
        return id;
    }

    /**
     * Retrieves the executor associated with this task.
     *
     * @return the executor associated with this task.
     */
    @Override
    public Executor getExecutor() {
        return executor;
    }

    /**
     * Retrieves the {@link TaskState} of this task.
     *
     * @return the {@link TaskState} of this task.
     */
    @Override
    public TaskState getState() {
        return state;
    }

    @Override
    public TaskAction getAction() {
        return action;
    }


    public Time getInitialDelay() {
        return initialDelay;
    }

    public TaskConfig getConfig() {
        return config;
    }

    /**
     * Adds a listener to this task.
     *
     * @param listener the listener to add
     */
    @Override
    public void newListener(TaskListener listener) {
        listeners.add(Preconditions.parameterNotNull(listener, "listener"));
    }

    /**
     * Remove a listener to this task.
     *
     * @param listener the listener to add
     */
    @Override
    public void remListener(TaskListener listener) {
        listeners.remove(Preconditions.parameterNotNull(listener, "listener"));
    }

    /**
     * Create a new {@link RepeatingTask} of this task with the given {@code amount} of repetitions.
     *
     * @param amount The amount of repetitions.
     * @return A new {@link RepeatingTask} of this task with the given {@code amount} of repetitions.
     */
    public RepeatingTask repeat(int amount) {
        return new RepeatingTask(this, amount);
    }

    /**
     * Execute the business logic of this task.
     * <p>
     * The execution can throw any exception, them are all caught and {@code not thrown}
     * to avoid the task to crash the executor. Instead, the {@link TaskListener#onFailure(Task, Exception)}
     * will be called on each registered listener.
     */
    @Override
    public final void run() {
        if (!canRun()) return;
        state = TaskState.RUNNING;
        listeners.forEach(l -> safeNotify(() -> l.onStart(this), "An error occurs while running onStart on " + l.getClass().getName()));
        try {
            getExecutor().execute(this);
            state = TaskState.COMPLETED;
            listeners.forEach(l -> safeNotify(() -> l.onSuccess(this), "An error occurs while running onSuccess on " + l.getClass().getName()));
        } catch (Exception e) {
            unscheduled(TaskState.FAILED);
            listeners.forEach(l -> safeNotify(() -> l.onFailure(this, e), "An error occurs while running onFailure on " + l.getClass().getName()));

            if (config.RESCHEDULE_ON_FAIL.get()) schedule();
        }
    }

    /**
     * Unschedule of this task <b>permanently</b>.
     * <p>
     * The task will be removed from the {@link Timer} and this action cannot be undone.
     * If the task is currently running, it will continue to run until completion.
     */
    @Override
    public final void cancel() {
        getExecutor().shutdown();
        unscheduled(TaskState.CANCELLED);
        listeners.forEach(l -> safeNotify(() -> l.onCancel(this), "An error occurs while running onCancel on " + l.getClass().getName()));
    }

    /**
     * Unschedule of this task <b>temporarily</b>.
     * <p>
     * The task will be removed from the {@link Timer} and this action can be undone.
     * If the task is currently running, it will continue to run until completion.
     *
     * @see #resume()
     */
    @Override
    public final void pause() {
        unscheduled(TaskState.PAUSED);
        listeners.forEach(l -> safeNotify(() -> l.onPause(this), "An error occurs while running onPause on " + l.getClass().getName()));
    }

    /**
     * Reschedule this task. The task will be added back to the {@link Timer}.
     *
     * @see #pause()
     */
    @Override
    public final void resume() {
        schedule();
        listeners.forEach(l -> safeNotify(() -> l.onResume(this), "An error occurs while running onResume on " + l.getClass().getName()));
    }

    @Override
    public boolean schedule() {
        if (state.isScheduled) return false;

        state = TaskState.SCHEDULED;
        return getScheduler().schedule(getTimer());
    }

    @Override
    public void unschedule() {
        unscheduled(TaskState.PENDING);
        getScheduler().unschedule(getTimer());
    }

    private void unscheduled(TaskState newState) {
        if (state.isScheduled)
            state = newState;
        unschedule();
    }

    private void safeNotify(Runnable notification, String msgError) {
        try {
            notification.run();
        } catch (RuntimeException e) {
            LOGGER.severe(msgError + " --> " + e.getMessage());
        }
    }

    private boolean canRun() {
        return state != TaskState.CANCELLED
                && state != TaskState.PAUSED
                && state != TaskState.FAILED;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Task task)) return false;
        return this == task || getID().equals(task.getID());
    }

    @Override
    public String toString() {
        return "Task#'" + getID() + "'(state=" + state + ")";
    }
}
