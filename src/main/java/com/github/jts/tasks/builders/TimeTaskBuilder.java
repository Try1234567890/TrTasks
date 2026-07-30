package com.github.jts.tasks.builders;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.imlps.TimeTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;

public class TimeTaskBuilder extends TaskBuilder {
    protected Time interval;

    @Override
    public TimeTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public TimeTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public TimeTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public TimeTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public TimeTaskBuilder synchronous() {
        super.synchronous();
        return this;
    }

    @Override
    public TimeTaskBuilder asynchronous() {
        super.asynchronous();
        return this;
    }

    @Override
    public TimeTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public TimeTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public TimeTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public TimeTaskBuilder withInterval(Time interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public TimeTask build() {
        return new TimeTask(
                getID(),
                getExecutor(),
                getTimer(),
                getInitialDelay(),
                Preconditions.simpleNotNull(interval, "interval"),
                getConfig(),
                getListeners(),
                getAction()
        );
    }
}
