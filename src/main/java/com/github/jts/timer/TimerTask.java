package com.github.jts.timer;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.*;
import com.github.jts.time.Time;

import java.util.function.Predicate;

/**
 * The scope of this record is to hold a predicate and a task
 * while is running inside a Timer to simply the execution logic.
 * <p>
 * If fact, without this object, the timer needs to store the task
 * with the predicate as key inside a Map, but when we need to remove
 * a task from the timer (unschedule), we cannot use the predicate to check equality.
 *
 * @param predicate The predicate to check if the task should run.
 * @param task      The task to execute.
 */
public record TimerTask(Predicate<? extends Task> predicate, Task task) implements Task {

    /**
     * Tests the task against the predicate.
     *
     * @param task The task to test.
     * @return true if the task should run, false otherwise.
     */
    @SuppressWarnings("unchecked")
    public boolean test(Task task) {
        return ((Predicate<Task>) predicate()).test(task);
    }

    /**
     * Checks if the task is equal to the given object.
     *
     * @param obj The object to check.
     * @return true if the task is equal to the given object, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task simpletask)
            return task.equals(simpletask);
        else return false;
    }


    /**
     * Retrieves the identifier of this task.
     * Useful for logging or to search a task.
     *
     * @return the identifier of this task.
     */
    @Override
    public String getID() {
        return task.getID();
    }

    /**
     * Retrieves the executor associated with this task.
     *
     * @return the executor associated with this task.
     */
    @Override
    public Executor getExecutor() {
        return task.getExecutor();
    }

    /**
     * Retrieves the scheduler associated with this task.
     *
     * @return the scheduler associated with this task.
     */
    @Override
    public Scheduler getScheduler() {
        return task.getScheduler();
    }

    @Override
    public TaskAction getAction() {
        return task.getAction();
    }

    /**
     * Retrieves the configuration of this task.
     *
     * @return the configuration of this task.
     */
    @Override
    public TaskConfig getConfig() {
        return task.getConfig();
    }

    /**
     * Retrieves the initial delay before the first execution of this task.
     *
     * @return The initial delay.
     */
    @Override
    public Time getInitialDelay() {
        return task.getInitialDelay();
    }

    @Override
    public boolean schedule() {
        return task.schedule();
    }

    @Override
    public void unschedule() {
        task.unschedule();
    }

    /**
     * Retrieves the {@link TaskState} of this task.
     *
     * @return the {@link TaskState} of this task.
     */
    @Override
    public TaskState getState() {
        return task.getState();
    }

    /**
     * Adds a listener to this task.
     *
     * @param listener the listener to add
     */
    @Override
    public void newListener(TaskListener listener) {
        task.newListener(listener);
    }

    /**
     * Remove a listener to this task.
     *
     * @param listener the listener to add
     */
    @Override
    public void remListener(TaskListener listener) {
        task.remListener(listener);
    }

    /**
     * Execute the business logic of this task.
     * <p>
     * The execution can throw any exception, them are all caught and {@code not thrown}
     * to avoid the task to crash the executor. Instead, the {@link TaskListener#onFailure(Task, Exception)}
     * will be called on each registered listener.
     */
    @Override
    public void run() {
        task.run();
    }

    /**
     * Unschedule of this task <b>permanently</b>.
     * <p>
     * The task will be removed from the {@link TimerImpl} and this action cannot be undone.
     * If the task is currently running, it will continue to run until completion.
     */
    @Override
    public void cancel() {
        task.cancel();
    }

    /**
     * Unschedule of this task <b>temporarily</b>.
     * <p>
     * The task will be removed from the {@link TimerImpl} and this action can be undone.
     * If the task is currently running, it will continue to run until completion.
     *
     * @see #resume()
     */
    @Override
    public void pause() {
        task.pause();
    }

    /**
     * Reschedule this task. The task will be added back to the {@link TimerImpl}.
     *
     * @see #pause()
     */
    @Override
    public void resume() {
        task.resume();
    }
}
