package me.tr.trtasks.scheduler;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.time.TrTime;

public class TrStaticTaskScheduler {
    private static final TrTaskScheduler SCHEDULER = new TrTaskScheduler();

    public static void syncTimer(Runnable task, TrTime period) {
        SCHEDULER.syncTimer(task, period);
    }

    public static void sync(Runnable task) {
        SCHEDULER.sync(task);
    }

    public static void syncDelayedTimer(Runnable task, TrTime period, TrTime delay) {
        SCHEDULER.syncDelayedTimer(task, period, delay);
    }

    public static void asyncTimer(Runnable task, TrTime period) {
        SCHEDULER.asyncTimer(task, period);
    }

    public static void async(Runnable task) {
        SCHEDULER.async(task);
    }

    public static void asyncDelayedTimer(Runnable task, TrTime period, TrTime delay) {
        SCHEDULER.asyncDelayedTimer(task, period, delay);
    }

    public static void syncDelay(Runnable task, TrTime delay) {
        SCHEDULER.syncDelay(task, delay);
    }

    public static void asyncDelay(Runnable task, TrTime delay) {
        SCHEDULER.asyncDelay(task, delay);
    }

    public static void task(TrTask task) {
        SCHEDULER.task(task);
    }
}
