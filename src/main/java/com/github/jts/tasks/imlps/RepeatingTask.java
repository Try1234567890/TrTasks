package com.github.jts.tasks.imlps;

import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.timer.Timer;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This task is a wrapper of any {@link AbstractTask} to limit
 * how many times it executes.<p>
 * <p>
 * This task will execute the original task until the repeat limit is reached.
 * The limit is decremented each time the task effectively executes, if the task
 * not runs for any reason (e.g. {@link ConditionalTask} predicate returns {@code false}) the limit is not decremented.
 */
public class RepeatingTask extends AbstractTask {
    private final AbstractTask task;
    private final AtomicLong repeatingRemaining;

    public RepeatingTask(AbstractTask task, Timer timer, int repeat) {
        super(task.getID(), task.getExecutor(), timer, task.getAction());
        this.task = task;
        this.repeatingRemaining = new AtomicLong(repeat);

        setAction(() -> {
            if (repeatingRemaining.decrementAndGet() <= 0) {
                // Task is done, remove it from scheduler
                task.unschedule();
            }
            // Execute the original action
            getAction().execute();
        });
    }

    public RepeatingTask(AbstractTask task, int repeat) {
        this(task, task.getTimer(), repeat);
    }


    @Override
    public Scheduler getScheduler() {
        return task.getScheduler();
    }
}
