package com.github.jts.tasks.imlps;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.TimeTaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class TimeTask extends AbstractTask {
    private final Scheduler scheduler;
    private final Time interval;
    private final AtomicLong lastExecutionTime = new AtomicLong(0);

    public TimeTask(String id, Executor executor, Timer timer,
                    Time initialDelay, Time interval, TaskConfig config,
                    List<TaskListener> listeners, TaskAction action) {
        super(id, executor, timer, initialDelay, config, action);

        this.interval = Time.ensure(interval);
        this.scheduler = new TimeScheduler(this);
        setAction(() -> {
            action.execute();
            lastExecutionTime.set(System.currentTimeMillis());
        });
    }

    public TimeTask(String id, Executor executor, Timer timer,
                    Time initialDelay, Time interval, TaskConfig config,
                    TaskAction action) {
        this(id, executor, timer, initialDelay, interval, config, new CopyOnWriteArrayList<>(), action);
    }

    public static TimeTask sync(String id, Time initDelay, Time interval, TaskConfig config, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withInterval(interval)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static TimeTask sync(String id, Time initDelay, Time interval, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withInterval(interval)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static TimeTask sync(String id, Time interval, TaskAction action) {
        return builder()
                .withID(id)
                .withInterval(interval)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static TimeTask sync(Time interval, TaskAction action) {
        return builder()
                .withInterval(interval)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static TimeTask async(String id, Time initDelay, Time interval, TaskConfig config, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withInterval(interval)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static TimeTask async(String id, Time initDelay, Time interval, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withInterval(interval)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static TimeTask async(String id, Time interval, TaskAction action) {
        return builder()
                .withID(id)
                .withInterval(interval)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static TimeTask async(Time interval, TaskAction action) {
        return builder()
                .withInterval(interval)
                .withAction(action)
                .asynchronous()
                .build();
    }

    private static TimeTaskBuilder builder() {
        return new TimeTaskBuilder();
    }

    /**
     * Retrieves the last time that this task has been executed in milliseconds.
     *
     * @return The last execution time in milliseconds.
     */
    public long getLastTimeExecuted() {
        return lastExecutionTime.get();
    }

    /**
     * Retrieves the interval to wait between each execution of this task.
     *
     * @return The interval between executions.
     */
    public Time getInterval() {
        return interval;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
