package me.tr.trtasks.scheduler;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.async.TrAsyncTask;
import me.tr.trtasks.tasks.sync.TrSyncTask;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.timer.TrTimer;

public interface TrScheduler {

    default void syncTimer(TrTimer timer, Runnable task, TrTime period) {
        timer.register(new TrSyncTask(period) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void sync(TrTimer timer, Runnable task) {
        timer.register(new TrSyncTask(TrTime.EMPTY) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void syncDelayedTimer(TrTimer timer, Runnable task, TrTime period, TrTime delay) {
        timer.register(new TrSyncTask(period, delay) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void asyncTimer(TrTimer timer, Runnable task, TrTime period) {
        timer.register(new TrAsyncTask(period) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void async(TrTimer timer, Runnable task) {
        timer.register(new TrAsyncTask(TrTime.EMPTY) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void asyncDelayedTimer(TrTimer timer, Runnable task, TrTime period, TrTime delay) {
        timer.register(new TrAsyncTask(period, delay) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void syncDelay(TrTimer timer, Runnable task, TrTime delay) {
        timer.register(new TrSyncTask(TrTime.EMPTY, delay) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void asyncDelay(TrTimer timer, Runnable task, TrTime delay) {
        timer.register(new TrAsyncTask(TrTime.EMPTY, delay) {
            @Override
            protected void task() {
                task.run();
            }
        });
    }

    default void task(TrTimer timer, TrTask task) {
        timer.register(task);
    }
}
