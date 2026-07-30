package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.ConditionalTask;
import com.github.jts.timer.Timer;

public class ConditionalScheduler extends GenericTaskScheduler {

    public ConditionalScheduler(ConditionalTask task) {
        super(task);
    }

    @Override
    public ConditionalTask getTask() {
        return (ConditionalTask) super.getTask();
    }

    @Override
    protected void push(Timer timer) {
        timer.register((_) -> getTask().getCondition().getAsBoolean(), getTask());
    }
}


















