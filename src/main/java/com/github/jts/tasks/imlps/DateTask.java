package com.github.jts.tasks.imlps;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.DateScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.time.LocalDate;
import java.util.List;

public class DateTask extends AbstractTask {
    private final Scheduler scheduler;
    private final LocalDate date;

    public DateTask(String id, Executor executor, Timer timer, Time initialDelay,
                    LocalDate date, TaskConfig config, List<TaskListener> listeners,
                    TaskAction action) {
        super(id, executor, timer, initialDelay, config, listeners, action);
        this.scheduler = new DateScheduler(this);
        this.date = Preconditions.simpleNotNull(date, "date");
    }

    public DateTask(String id, Executor executor, Timer timer, Time initialDelay,
                    LocalDate date, TaskConfig config, TaskAction action) {
        super(id, executor, timer, initialDelay, config, action);
        this.scheduler = new DateScheduler(this);
        this.date = Preconditions.simpleNotNull(date, "date");
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


    public LocalDate getDate() {
        return date;
    }
}








