package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.TimeTask;
import com.github.jts.timer.Timer;

import java.time.LocalTime;

public class TimeScheduler extends GenericTaskScheduler {

    public TimeScheduler(TimeTask task) {
        super(task);
    }

    @Override
    public TimeTask getTask() {
        return (TimeTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.register(_ -> canExecute(), getTask());
    }

    private boolean canExecute() {
        LocalTime time = getTask().getTime();
        LocalTime now = LocalTime.now();
        return time.isBefore(now) || time.equals(now);
    }
}


















