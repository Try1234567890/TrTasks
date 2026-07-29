package com.github.jts.tasks.imlps.time;

import com.github.jts.executor.async.AsyncExecutor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.time.Time;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;

import java.util.concurrent.atomic.AtomicLong;

public class AsyncTimeTask extends AbstractTask implements TimeTask {
    private final Scheduler scheduler;
    private final Time interval;
    private final Time delay;
    private final AtomicLong lastExecutionTime = new AtomicLong(0);

    public AsyncTimeTask(String id, TaskAction action, Timer timer, Time interval, Time delay) {
        super(id, new AsyncExecutor(), timer, action);
        this.scheduler = new TimeScheduler(this);
        this.interval = interval;
        this.delay = delay;
        setAction(() -> {
            action.execute();
            lastExecutionTime.set(System.currentTimeMillis());
        });
    }

    public AsyncTimeTask(String id, TaskAction action, Time interval, Time delay) {
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

    /**
     * Retrieves the initial delay before the first execution of this task.
     *
     * @return The initial delay.
     */
    @Override
    public Time getDelay() {
        return delay;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
