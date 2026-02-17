package me.tr.trtasks.scheduler;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.timer.TrTaskTimer;
import me.tr.trtasks.timer.TrTimer;

public class TrTaskScheduler implements TrScheduler {
    private final TrTimer timer;

    public TrTaskScheduler(TrTimer timer) {
        this.timer = timer == null ? new TrTaskTimer() : timer;
    }

    public TrTaskScheduler() {
        this(null);
    }

    public TrTimer getTimer() {
        return timer;
    }

    public void syncTimer(Runnable task, TrTime period) {
        syncTimer(getTimer(), task, period);
    }

    public void sync(Runnable task) {
        sync(getTimer(), task);
    }

    public void syncDelayedTimer(Runnable task, TrTime period, TrTime delay) {
        syncDelayedTimer(getTimer(), task, period, delay);
    }

    public void asyncTimer(Runnable task, TrTime period) {
        asyncTimer(getTimer(), task, period);
    }

    public void async(Runnable task) {
        async(getTimer(), task);
    }

    public void asyncDelayedTimer(Runnable task, TrTime period, TrTime delay) {
        asyncDelayedTimer(getTimer(), task, period, delay);
    }

    public void syncDelay(Runnable task, TrTime delay) {
        syncDelay(getTimer(), task, delay);
    }

    public void asyncDelay(Runnable task, TrTime delay) {
        asyncDelay(getTimer(), task, delay);
    }

    public void task(TrTask task) {
        task(getTimer(), task);
    }
}
