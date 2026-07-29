package com.github.jts.tasks.utils;

import com.github.jts.tasks.imlps.time.AsyncTimeTask;
import com.github.jts.tasks.imlps.time.SyncTimeTask;
import com.github.jts.tasks.imlps.time.TimeTask;
import com.github.jts.time.Time;
import com.github.utilities.validators.Preconditions;

public class TimeTaskBuilder extends TaskBuilder {
    private Time interval;
    private Time delay;

    public TimeTaskBuilder withInterval(Time interval) {
        this.interval = interval;
        return this;
    }

    public TimeTaskBuilder withDelay(Time delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public TimeTask build() {
        if (synchronous) {
            SyncTimeTask task = new SyncTimeTask(
                    getID(),
                    Preconditions.simpleNotNull(action, "action cannot be null."),
                    getTimer(),
                    Preconditions.simpleNotNull(interval, "interval cannot be null."),
                    Preconditions.simpleNotNull(delay, "delay cannot be null."));
            listeners.forEach(task::newListener);
            return task;
        } else {
            AsyncTimeTask task = new AsyncTimeTask(
                    getID(),
                    Preconditions.simpleNotNull(action, "action cannot be null."),
                    getTimer(),
                    Preconditions.simpleNotNull(interval, "interval cannot be null."),
                    Preconditions.simpleNotNull(delay, "delay cannot be null."));
            listeners.forEach(task::newListener);
            return task;
        }
    }
}
