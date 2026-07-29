package com.github.jts.scheuduler;

import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

public record ConditionalScheduler(ConditionalTask task) implements Scheduler {

    public ConditionalScheduler(ConditionalTask task) {
        this.task = Preconditions.simpleParameterNotNull(task, "task");
    }

    @Override
    public boolean schedule(Timer timer) {
        timer.newTask((_) -> task.condition().getAsBoolean(), task);
        return false;
    }

}


















