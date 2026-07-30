package com.github.jts.scheuduler;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

public class ConditionalScheduler extends GenericTaskScheduler {

    public ConditionalScheduler(ConditionalTask task) {
        super(task);
    }

    @Override
    public ConditionalTask getTask() {
        return (ConditionalTask) super.getTask();
    }

    @Override
    public void push(Timer timer) {
        timer.newTask((_) -> getTask().condition().getAsBoolean(), getTask());
    }
}


















