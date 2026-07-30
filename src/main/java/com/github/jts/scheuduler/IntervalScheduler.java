package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.IntervalTask;
import com.github.jts.timer.Timer;

import java.util.function.Predicate;

public class IntervalScheduler extends GenericTaskScheduler {
    private static final Predicate<IntervalTask> CAN_RUN_TIME_PREDICATE =
            t -> (System.currentTimeMillis() >= (t.getLastTimeExecuted() + t.getInterval().toMillis()));


    public IntervalScheduler(IntervalTask task) {
        super(task);
    }

    @Override
    public IntervalTask getTask() {
        return (IntervalTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.register(CAN_RUN_TIME_PREDICATE, getTask());
    }
}


















