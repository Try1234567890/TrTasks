package com.github.jts.scheuduler;

import com.github.jts.tasks.Task;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;

public interface Scheduler {

    Task task();

    boolean schedule(Timer timer);

    void shutdown();

    default boolean schedule() {
        return schedule(StaticTimer.get());
    }

    default boolean unschedule(Timer timer) {
        return timer.remTask(task());
    }

    default boolean unschedule() {
        return unschedule(StaticTimer.get());
    }



}
