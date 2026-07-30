package com.github.jts.scheuduler;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.imlps.time.TimeTask;
import com.github.jts.timer.Timer;

import java.util.function.Predicate;
import java.util.logging.Logger;

public class TimeScheduler extends GenericTaskScheduler {
    public static final Logger LOGGER = Logger.getLogger(TimeScheduler.class.getName());
    private static final Predicate<TimeTask> CAN_RUN_TIME_PREDICATE =
            t -> (System.currentTimeMillis() >= (t.getLastTimeExecuted() + t.getInterval().toMillis()));


    public TimeScheduler(TimeTask task) {
        super(task);
    }

    @Override
    public TimeTask getTask() {
        return (TimeTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.newTask(CAN_RUN_TIME_PREDICATE, getTask());
    }
}


















