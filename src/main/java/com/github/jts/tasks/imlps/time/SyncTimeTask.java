package com.github.jts.tasks.imlps.time;

import com.github.jts.executor.SyncExecutor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.builders.time.SyncTimeTaskBuilder;
import com.github.jts.tasks.builders.time.TimeTaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

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

    public static SyncTimeTask create(String id, Time initDelay, Time interval, TaskConfig config, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withAction(action)
                .withInterval(interval)
                .build();
    }

    public static SyncTimeTask create(String id, Time interval, TaskConfig config, TaskAction action) {
        return builder()
                .withID(id)
                .withInterval(interval)
                .withConfig(config)
                .withAction(action)
                .build();
    }

    public static SyncTimeTask create(String id, Time interval, TaskAction action) {
        return builder()
                .withID(id)
                .withInterval(interval)
                .withAction(action)
                .build();
    }

    public static SyncTimeTask create(Time interval, TaskAction action) {
        return builder()
                .withInterval(interval)
                .withAction(action)
                .build();
    }

    public static SyncTimeTaskBuilder builder() {
        return TimeTaskBuilder.sync();
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
