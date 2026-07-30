package com.github.jts.tasks.imlps;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.time.LocalTime;
import java.util.List;

public class TimeTask extends AbstractTask {
    private final Scheduler scheduler;
    private final LocalTime time;

    public TimeTask(String id, Executor executor, Timer timer, Time initialDelay,
                    LocalTime time, TaskConfig config, List<TaskListener> listeners,
                    TaskAction action) {
        super(id, executor, timer, initialDelay, config, listeners, action);
        this.scheduler = new TimeScheduler(this);
        this.time = Preconditions.simpleNotNull(time, "time");
    }

    public TimeTask(String id, Executor executor, Timer timer, Time initialDelay,
                    LocalTime time, TaskConfig config, TaskAction action) {
        super(id, executor, timer, initialDelay, config, action);
        this.scheduler = new TimeScheduler(this);
        this.time = Preconditions.simpleNotNull(time, "time");
    }

    /**
     * Retrieves the scheduler associated with this task.
     *
     * @return the scheduler associated with this task.
     */
    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }


    public LocalTime getTime() {
        return time;
    }
}








