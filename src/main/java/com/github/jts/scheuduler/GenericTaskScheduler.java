package com.github.jts.scheuduler;

import com.github.jts.tasks.Task;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class GenericTaskScheduler implements Scheduler {
    public static final Logger LOGGER = Logger.getLogger(GenericTaskScheduler.class.getName());
    private static final ExecutorService waiter = Executors.newThreadPerTaskExecutor(r -> Thread.ofVirtual().name("task-time-scheduler")
            .uncaughtExceptionHandler((t, e) -> LOGGER.severe("Uncaught exception in time-task scheduler: " + e.getMessage()))
            .unstarted(r));
    private final Task task;

    public GenericTaskScheduler(Task task) {
        this.task = Preconditions.parameterNotNull(task, "task");
    }

    @Override
    public Task getTask() {
        return task;
    }

    protected abstract void push(Timer timer);

    @Override
    public boolean schedule(Timer timer) {
        long delay = getTask().getInitialDelay().toMillis();
        if (delay <= 0) {
            push(timer);
            return true;
        } else {
            waiter.submit(() -> {
                try {
                    Thread.sleep(delay);
                    push(timer);
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
