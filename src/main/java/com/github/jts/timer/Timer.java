package com.github.jts.timer;

import com.github.jts.tasks.Task;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * The timer is the process that runs it its own thread and cycle through all registered
 * tasks checking if they can run and executing them if they can.
 *
 * <h2>Timer Error Handling</h2>
 * TODO
 */
public interface Timer {

    /**
     * Remove the {@code task} from the running tasks and mark it as <b>task in pause</b>
     *
     * @param task the task to work on
     */
    void pause(Task task);

    /**
     * Re-add the {@code task} to the running tasks and unmark it from <b>task in pause</b>
     *
     * @param task the task to work on
     * @return the optional container of the removed task if any has removed, otherwise {@link Optional#empty()}
     */
    Optional<? extends Task> unpause(Task task);

    /**
     * Register the {@code task} with the predicate {@code canRun} to check if it can run at each iteration.
     *
     * @param canRun the predicate to check if it can run; This is called at each iteration.
     * @param task   the task to execute.
     */
    void register(Predicate<? extends Task> canRun, Task task);

    /**
     * Unregister the {@code task} from the timer.
     *
     * @param task the task to remove.
     * @return the optional container of the removed task if any has removed, otherwise {@link Optional#empty()}
     */
    Optional<? extends Task> unregister(Task task);

    /**
     * Get the current state of the timer.
     *
     * @return the current state of the timer.
     */
    TimerState getState();

    /**
     * Checks if this timer is currently in the process of stopping or already stopped.
     * <p>
     * This is equivalent to {@code getState() == TimerState.STOPPED || getState() == TimerState.STOPPING}.
     *
     * @return {@code true} if the timer is in the process of stopping or already stopped, otherwise {@code false}.
     */
    default boolean isStopping() {
        return getState() == TimerState.STOPPED || getState() == TimerState.STOPPING;
    }

    /**
     * Checks if this timer is currently stopped.
     * <p>
     * This is equivalent to {@code getState() == TimerState.STOPPED}.
     *
     * @return {@code true} if the timer is stopped, otherwise {@code false}.
     */
    default boolean isStopped() {
        return getState() == TimerState.STOPPED;
    }

    /**
     * Checks if this timer is currently stopped.
     * <p>
     * This is equivalent to {@code getState() == TimerState.PAUSED}.
     *
     * @return {@code true} if the timer is paused, otherwise {@code false}.
     */
    default boolean isPaused() {
        return getState() == TimerState.PAUSED;
    }

    /**
     * Checks if this timer is currently started.
     * <p>
     * This is equivalent to {@code getState() == TimerState.STARTED || getState() == TimerState.RUNNING}.
     *
     * @return {@code true} if the timer is started, otherwise {@code false}.
     */
    default boolean isStarted() {
        return getState() == TimerState.STARTED || getState() == TimerState.RUNNING;
    }

    /**
     * Checks if this timer is currently running.
     * <p>
     * This is equivalent to {@code getState() == TimerState.RUNNING}.
     *
     * @return {@code true} if the timer is running, otherwise {@code false}.
     */
    default boolean isRunningNow() {
        return getState() == TimerState.RUNNING;
    }

    /**
     * Starts the current timer if it is not already started.
     */
    void start();

    /**
     * Stops the current timer immediately. This method doesn't wait for the current tasks to finish.
     */
    void shutdownNow();

    /**
     * Stops the current timer. This method waits for the current tasks to finish.
     */
    void shutdown();

    /**
     * Pauses the current timer. This method doesn't wait for the current tasks to finish.
     */
    void pause();

    /**
     * Resumes the current timer.
     */
    void resume();

}











