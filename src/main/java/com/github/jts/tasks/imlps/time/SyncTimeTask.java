package com.github.jts.tasks.imlps.time;

import com.github.jts.executor.SyncExecutor;
import com.github.jts.executor.async.AsyncExecutor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskAction;
import com.github.jts.time.InvalidTimeException;
import com.github.jts.time.Time;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.concurrent.atomic.AtomicLong;

public class SyncTimeTask extends AbstractTask implements TimeTask {
    private final Scheduler scheduler;
    private final Time interval;
    private final AtomicLong lastExecutionTime = new AtomicLong(0);

    public SyncTimeTask(String id, Timer timer, Time initialDelay, Time interval,
                        TaskConfig config, TaskAction action) {
        super(id, new SyncExecutor(), timer, initialDelay, config, action);

        this.interval = Time.ensure(interval);
        this.scheduler = new TimeScheduler(this);
        setAction(() -> {
            action.execute();
            lastExecutionTime.set(System.currentTimeMillis());
        });
    }

    public SyncTimeTask(String id, TaskAction action, Time interval, Time delay) {
        this(id, action, StaticTimer.get(), interval, delay);
    }

    /**
     * Retrieves the last time that this task has been executed in milliseconds.
     *
     * @return The last execution time in milliseconds.
     */
    @Override
    public long getLastTimeExecuted() {
        return lastExecutionTime.get();
    }

    /**
     * Retrieves the interval to wait between each execution of this task.
     *
     * @return The interval between executions.
     */
    @Override
    public Time getInterval() {
        return interval;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
