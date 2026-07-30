package com.github.jts.tasks.imlps.time;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.time.Time;

public interface TimeTask extends Task {

    static SyncTimeTask sync(String id, Time initDelay, Time interval, TaskConfig config, TaskAction action) {
        return SyncTimeTask.create(id, initDelay, interval, config, action);
    }

    static SyncTimeTask sync(String id, Time interval, TaskConfig config, TaskAction action) {
        return SyncTimeTask.create(id, interval, config, action);
    }

    static SyncTimeTask sync(String id, Time interval, TaskAction action) {
        return SyncTimeTask.create(id, interval, action);
    }

    static SyncTimeTask sync(Time interval, TaskAction action) {
        return SyncTimeTask.create(interval, action);
    }

    static AsyncTimeTask async(String id, Time initDelay, Time interval, TaskConfig config, TaskAction action) {
        return AsyncTimeTask.create(id, initDelay, interval, config, action);
    }

    static AsyncTimeTask async(String id, Time interval, TaskConfig config, TaskAction action) {
        return AsyncTimeTask.create(id, interval, config, action);
    }

    static AsyncTimeTask async(String id, Time interval, TaskAction action) {
        return AsyncTimeTask.create(id, interval, action);
    }

    static AsyncTimeTask async(Time interval, TaskAction action) {
        return AsyncTimeTask.create(interval, action);
    }

    /**
     * Retrieves the last time that this task has been executed in milliseconds.
     *
     * @return The last execution time in milliseconds.
     */
    long getLastTimeExecuted();

    /**
     * Retrieves the interval to wait between each execution of this task.
     *
     * @return The interval between executions.
     */
    Time getInterval();

}
