package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.DateTimeTask;
import com.github.jts.timer.Timer;

import java.time.ZonedDateTime;

public class DateTimeScheduler extends GenericTaskScheduler {

    public DateTimeScheduler(DateTimeTask task) {
        super(task);
    }

    @Override
    public DateTimeTask getTask() {
        return (DateTimeTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.register(_ -> canExecute(), getTask());
    }

    private boolean canExecute() {
        ZonedDateTime dateTime = getTask().getDateTime();
        ZonedDateTime now = ZonedDateTime.now(dateTime.getZone());
        return dateTime.isBefore(now) || dateTime.isEqual(now);
    }
}


















