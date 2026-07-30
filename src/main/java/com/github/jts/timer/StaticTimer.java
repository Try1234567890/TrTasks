package com.github.jts.timer;

/**
 * Static timer that can be used to schedule tasks.
 */
public final class StaticTimer extends TimerImpl {
    private StaticTimer() {
        super(new TimerConfig().setMaxTasks(5000));
    }

    private static final class Holder {
        private static final StaticTimer INSTANCE = new StaticTimer();
    }

    public static StaticTimer get() {
        return Holder.INSTANCE;
    }
}
