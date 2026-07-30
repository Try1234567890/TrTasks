package com.github.jts.timer;

/**
 * This enum represents the state of a timer at a specific point in time.
 */
public enum TimerState {
    /**
     * The timer has been created but not started yet.
     */
    CREATED,
    /**
     * The timer has started and is not running at the moment.
     */
    STARTED,
    /**
     * The timer is currently running and executing tasks.
     */
    RUNNING,
    /**
     * The timer is in the process of stopping.
     */
    STOPPING,
    /**
     * The timer has been stopped successfully. Cannot be re-started.
     */
    STOPPED,
    /**
     * The timer is currently paused. Can be re-started with resume method.
     */
    PAUSED,

}
