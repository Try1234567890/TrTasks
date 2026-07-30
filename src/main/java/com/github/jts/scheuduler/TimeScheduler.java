package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.time.TimeTask;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.logging.Logger;

public record TimeScheduler(TimeTask task) implements Scheduler {
    public static final Logger LOGGER = Logger.getLogger(TimeScheduler.class.getName());
    private static final Predicate<TimeTask> CAN_RUN_TIME_PREDICATE =
            t -> (System.currentTimeMillis() >= (t.getLastTimeExecuted() + t.getInterval().toMillis()));
    private static final ExecutorService waiter = Executors.newThreadPerTaskExecutor(r -> Thread.ofVirtual().name("task-time-scheduler")
            .uncaughtExceptionHandler((t, e) -> LOGGER.severe("Uncaught exception in time-task scheduler: " + e.getMessage()))
            .unstarted(r));

    public TimeScheduler(TimeTask task) {
        this.task = Preconditions.simpleParameterNotNull(task, "task");
    }

    @Override
    public boolean schedule(Timer timer) {
        long delay = task().getDelay().toMillis();
        if (delay <= 0) {
            timer.newTask(CAN_RUN_TIME_PREDICATE, task);
            return true;
        } else {
            waiter.submit(() -> {
                try {
                    Thread.sleep(delay);
                    timer.newTask(CAN_RUN_TIME_PREDICATE, task);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Cannot schedule task " + task + " due to thread interruption.", e);
                }
            });
        }
        return true;
    }

    @Override
    public void shutdown() {
        try {
            if (waiter.awaitTermination(30, TimeUnit.SECONDS)) {
                waiter.shutdown();
            } else {
                LOGGER.severe("Scheduler completing tasks before shutdown process timed out (30 seconds). Shutting down now.");
                waiter.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Cannot shutdown time scheduler due to thread interruption while waiting for tasks to complete.", e);
        }

    }
}


















