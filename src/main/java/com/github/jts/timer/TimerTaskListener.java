package com.github.jts.timer;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskListener;
import com.github.utilities.validators.Preconditions;

public record TimerTaskListener(Timer timer) implements TaskListener {

    public TimerTaskListener(Timer timer) {
        this.timer = Preconditions.parameterNotNull(timer, "timer");
    }

    @Override
    public void onPause(Task task) {
        timer.pause(task);
    }

    @Override
    public void onResume(Task task) {
        timer.unpause(task);
    }
}
