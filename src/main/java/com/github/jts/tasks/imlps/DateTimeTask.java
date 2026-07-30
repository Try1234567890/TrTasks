package com.github.jts.tasks.imlps;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.DateTimeScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.time.ZonedDateTime;
import java.util.List;

public class DateTimeTask extends AbstractTask {
    private final Scheduler scheduler;
    private final ZonedDateTime datetime;

    public DateTimeTask(String id, Executor executor, Timer timer, Time initialDelay,
                        ZonedDateTime datetime, TaskConfig config, List<TaskListener> listeners,
                        TaskAction action) {
        super(id, executor, timer, initialDelay, config, listeners, action);
        this.scheduler = new DateTimeScheduler(this);
        this.datetime = Preconditions.simpleNotNull(datetime, "time");
    }

    public DateTimeTask(String id, Executor executor, Timer timer, Time initialDelay,
                        ZonedDateTime datetime, TaskConfig config, TaskAction action) {
        super(id, executor, timer, initialDelay, config, action);
        this.scheduler = new DateTimeScheduler(this);
        this.datetime = Preconditions.simpleNotNull(datetime, "time");
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


    public ZonedDateTime getDateTime() {
        return datetime;
    }
}








