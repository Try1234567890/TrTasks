package com.github.jts.tasks.builders.time;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.TaskBuilder;
import com.github.jts.tasks.imlps.time.TimeTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

import java.util.List;

public abstract class TimeTaskBuilder extends TaskBuilder {
    protected Time interval;

    public static SyncTimeTaskBuilder sync() {
        return new SyncTimeTaskBuilder();
    }

    public static AsyncTimeTaskBuilder async() {
        return new AsyncTimeTaskBuilder();
    }

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
    public SyncTimeTaskBuilder synchronous() {
        return new SyncTimeTaskBuilder()
                .withID(identifier)
                .withAction(action)
                .withListeners(listeners)
                .withTimer(timer)
                .withInitialDelay(initialDelay)
                .withConfig(config)
                .withInterval(interval);
    }

    @Override
    public AsyncTimeTaskBuilder asynchronous() {
        return new AsyncTimeTaskBuilder()
                .withID(identifier)
                .withAction(action)
                .withListeners(listeners)
                .withTimer(timer)
                .withInitialDelay(initialDelay)
                .withConfig(config)
                .withInterval(interval);
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
    public abstract TimeTask build();
}
