package com.github.jts.tasks;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.time.Time;
import com.github.jts.timer.TimerImpl;

public interface Task {

    /**
     * Retrieves the identifier of this task.
     * Useful for logging or to search a task.
     *
     * @return the identifier of this task.
     */
    String getID();

    /**
     * Retrieves the executor associated with this task.
     *
     * @return the executor associated with this task.
     */
    Executor getExecutor();

    /**
     * Retrieves the scheduler associated with this task.
     *
     * @return the scheduler associated with this task.
     */
    Scheduler getScheduler();

    /**
     * Retrieves the action that this task executes
     *
     * @return the action of this task;
     */
    TaskAction getAction();

    /**
     * Retrieves the configuration of this task.
     *
     * @return the configuration of this task.
     */
    TaskConfig getConfig();

    /**
     * Retrieves the initial delay before the first execution of this task.
     *
     * @return The initial delay.
     */
    Time getInitialDelay();

    /**
     * Schedule this task to its timer.
     *
     * @return {@code true} if, and only if, the task was scheduled, {@code false} otherwise
     */
    boolean schedule();

    /**
     * Unschedule this task from its timer.
     *
     * @return {@code true} if, and only if, the task was unscheduled, {@code false} otherwise
     */
    boolean unschedule();

    /**
     * Retrieves the {@link TaskState} of this task.
     *
     * @return the {@link TaskState} of this task.
     */
    TaskState getState();

    /**
     * Adds a listener to this task.
     *
     * @param listener the listener to add
     */
    void newListener(TaskListener listener);

    /**
     * Execute the business logic of this task.
     * <p>
     * The execution can throw any exception, them are all caught and {@code not thrown}
     * to avoid the task to crash the executor. Instead, the {@link TaskListener#onFailure(Task, Exception)}
     * will be called on each registered listener.
     */
    void run();

    /**
     * Unschedule of this task <b>permanently</b>.
     * <p>
     * The task will be removed from the {@link TimerImpl} and this action cannot be undone.
     * If the task is currently running, it will continue to run until completion.
     */
    void cancel();

    /**
     * Unschedule of this task <b>temporarily</b>.
     * <p>
     * The task will be removed from the {@link TimerImpl} and this action can be undone.
     * If the task is currently running, it will continue to run until completion.
     *
     * @see #resume()
     */
    void pause();

    /**
     * Reschedule this task. The task will be added back to the {@link TimerImpl}.
     *
     * @see #pause()
     */
    void resume();

}
