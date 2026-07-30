package com.github.jts.tasks.builders;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.imlps.IntervalTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

import java.util.List;

public class IntervalTaskBuilder extends TaskBuilder {
    protected Time interval;

    @Override
    public IntervalTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public IntervalTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public IntervalTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public IntervalTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public IntervalTaskBuilder synchronous() {
        super.synchronous();
        return this;
    }

    @Override
    public IntervalTaskBuilder asynchronous() {
        super.asynchronous();
        return this;
    }

    @Override
    public IntervalTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public IntervalTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public IntervalTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public IntervalTaskBuilder withInterval(Time interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public IntervalTask build() {
        return new IntervalTask(
                getID(),
                getExecutor(),
                getTimer(),
                getInitialDelay(),
                Time.ensure(interval),
                getConfig(),
                getListeners(),
                getAction()
        );
    }
}
