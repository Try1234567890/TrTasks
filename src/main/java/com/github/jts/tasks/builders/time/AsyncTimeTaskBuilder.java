package com.github.jts.tasks.builders.time;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.TaskBuilder;
import com.github.jts.tasks.imlps.time.AsyncTimeTask;
import com.github.jts.tasks.imlps.time.SyncTimeTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;

public class AsyncTimeTaskBuilder extends TimeTaskBuilder {

    @Override
    public AsyncTimeTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }


    @Override
    public AsyncTimeTaskBuilder asynchronous() {
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public AsyncTimeTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public AsyncTimeTaskBuilder withInterval(Time interval) {
        super.withInterval(interval);
        return this;
    }

    @Override
    public AsyncTimeTask build() {
        AsyncTimeTask task = new AsyncTimeTask(
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
