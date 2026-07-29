package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.time.TimeTask;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.time.Duration;
import java.util.function.Predicate;

public record TimeScheduler(TimeTask task) implements Scheduler {
    private static final Predicate<TimeTask> CAN_RUN_TIME_PREDICATE =
            t -> (System.currentTimeMillis() >= (t.getLastTimeExecuted() + t.getInterval().toMillis()));

    public TimeScheduler(TimeTask task) {
        this.task = Preconditions.simpleParameterNotNull(task, "task");
    }

    @Override
    public boolean schedule(Timer timer) {
        try {
            long delay = task().getDelay().toNano();
            Thread.sleep(Duration.ofNanos(delay));

            timer.newTask(CAN_RUN_TIME_PREDICATE, task);
            return true;
        } catch (InterruptedException e) {
            throw new IllegalStateException("Cannot schedule " + task + " due to thread interruption.", e);
        }
    }
}


















