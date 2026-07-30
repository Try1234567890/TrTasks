package com.github.jts.timer;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskListener;
import com.github.utilities.validators.Preconditions;

import java.util.function.Predicate;

/**
 * This record represent a {@link TaskListener} assigned to a {@link Timer}.
 * This is used to pause and resume the tasks inside the timer when the task is paused or resumed.
 * <p>
 * This listener is registered to the task when {@link Timer#register(Predicate, Task)} finishes its execution.
 *
 * @param timer The timer assigned to this listener.
 */
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
