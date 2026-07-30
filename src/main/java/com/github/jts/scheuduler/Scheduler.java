package com.github.jts.scheuduler;

import com.github.jts.tasks.Task;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface Scheduler {

    Task getTask();

    boolean schedule(Timer timer);


    default Optional<? extends Task> unschedule(Timer timer) {
        return timer.unregister(getTask());
    }

    void shutdown();
}
