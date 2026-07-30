package com.github.jts.tasks;


import com.github.jts.timer.Timer;

/**
 * A TaskListener is an object that wait for any event of a task and execute some logic.
 * An event is notification of a state change inside the task that owns it.
 */
public interface TaskListener {

    /**
     * Called when a task is scheduled inside a {@link Timer}.
     *
     * @param task the task that was scheduled
     */
    default void onSchedule(Task task) {

    }

    /**
     * Called when a task is paused.
     *
     * @param task the task that was paused
     */
    default void onPause(Task task) {

    }

    /**
     * Called when a task is resumed from a pause.
     *
     * @param task the task that was resumed
     */
    default void onResume(Task task) {

    }

    /**
     * Called when a task is started.
     *
     * @param task the task that was started
     */
    default void onStart(Task task) {

    }

    /**
     * Called when a task ends successful.
     *
     * @param task the task that ends successful
     */
    default void onSuccess(Task task) {

    }

    /**
     * Called when a task finishes, independent of the result (if is a success or a fail).
     *
     * @param task the task that finishes
     */
    default void onFinish(Task task) {

    }

    /**
     * Called when a task ends with an error.
     *
     * @param task the task that ends with an error
     */
    default void onFailure(Task task, Exception e) {

    }

    /**
     * Called when a task is canceled.
     *
     * @param task the task that was canceled
     */
    default void onCancel(Task task) {

    }
}
