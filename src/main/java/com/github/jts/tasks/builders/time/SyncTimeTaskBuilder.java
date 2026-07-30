package com.github.jts.tasks.builders.time;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.imlps.time.SyncTimeTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;

public class SyncTimeTaskBuilder extends TimeTaskBuilder {

    @Override
    public SyncTimeTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder synchronous() {
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public SyncTimeTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public SyncTimeTaskBuilder withInterval(Time interval) {
        super.withInterval(interval);
        return this;
    }

    @Override
    public SyncTimeTask build() {
        SyncTimeTask task = new SyncTimeTask(
                getID(),
                getTimer(),
                getInitialDelay(),
                Preconditions.simpleNotNull(interval, "interval cannot be null."),
                getConfig(),
                Preconditions.simpleNotNull(action, "action cannot be null."));
        listeners.forEach(task::newListener);
        return task;
    }
}
