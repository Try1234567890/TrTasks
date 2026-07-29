package com.github.jts.timer;

import com.github.jts.tasks.Task;

import java.util.function.Predicate;

record TimerTask(Predicate<? extends Task> predicate, Task task) {

    @SuppressWarnings("unchecked")
    public boolean test(Task task) {
        return ((Predicate<Task>) predicate()).test(task);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task simpletask)
            return task.equals(simpletask);
        else if (obj instanceof TimerTask timerTask) {
            return task.equals(timerTask.task);
        } else return false;
    }
}
