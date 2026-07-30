package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.DateTask;
import com.github.jts.timer.Timer;

import java.time.LocalDate;

public class DateScheduler extends GenericTaskScheduler {

    public DateScheduler(DateTask task) {
        super(task);
    }

    @Override
    public DateTask getTask() {
        return (DateTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.register(_ -> canExecute(), getTask());
    }

    private boolean canExecute() {
        LocalDate date = getTask().getDate();
        LocalDate now = LocalDate.now();
        return date.isBefore(now) || date.isEqual(now);
    }
}


















