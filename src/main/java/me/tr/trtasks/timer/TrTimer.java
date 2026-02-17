package me.tr.trtasks.timer;

import me.tr.trtasks.tasks.TrTask;

public interface TrTimer {

    /**
     * Register a new {@code task} to the timer cycle.
     *
     * @param task The task to add
     */
    void register(TrTask task);

    /**
     * Start the timer cycle.
     */
    void start();

    /**
     * Checks if the timer is started.
     *
     * @return {@code true} if timer is started, otherwise {@code false}.
     */
    boolean isStarted();

    /**
     * Retrieve the timer options.
     *
     * @return {@link TrTimerOptions} of this instance.
     */
    TrTimerOptions getOptions();
}
