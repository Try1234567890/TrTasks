package com.github.jts.tasks.builders;

import com.github.jts.executor.Executor;
import com.github.jts.executor.SyncExecutor;
import com.github.jts.executor.async.AsyncExecutor;
import com.github.jts.tasks.*;
import com.github.jts.time.Time;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskBuilder {
    protected String identifier;
    protected TaskAction action;
    protected Timer timer;
    protected Time initialDelay;
    protected TaskConfig config;
    protected List<TaskListener> listeners = new ArrayList<>();
    protected Executor executor;

    public static IntervalTaskBuilder time() {
        return new IntervalTaskBuilder();
    }

    public static ConditionalTaskBuilder conditional() {
        return new ConditionalTaskBuilder();
    }

    private String newID() {
        return TaskIDGenerator.getInstance().newID();
    }

    public TaskBuilder withID(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public TaskBuilder withAction(TaskAction action) {
        this.action = action;
        return this;
    }

    public TaskBuilder withListeners(List<TaskListener> listeners) {
        this.listeners.addAll(listeners);
        return this;
    }

    public TaskBuilder withListener(TaskListener listener) {
        if (listener == null) return this;
        this.listeners.add(listener);
        return this;
    }

    public TaskBuilder synchronous() {
        this.executor = new SyncExecutor();
        return this;
    }

    public TaskBuilder asynchronous() {
        this.executor = new AsyncExecutor();
        return this;
    }

    public TaskBuilder withTimer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public TaskBuilder withInitialDelay(Time initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public TaskBuilder withConfig(TaskConfig config) {
        this.config = config;
        return this;
    }

    public abstract Task build();

    protected Timer getTimer() {
        return timer != null ? timer : StaticTimer.get();
    }

    protected String getID() {
        return (identifier == null || identifier.isEmpty()) ? newID() : identifier;
    }

    protected Time getInitialDelay() {
        return (initialDelay == null || initialDelay.isInvalid()) ? Time.EMPTY : initialDelay;
    }

    protected TaskConfig getConfig() {
        return config != null ? config : new TaskConfig();
    }

    protected Executor getExecutor() {
        return executor != null ? executor : new SyncExecutor();
    }

    protected List<TaskListener> getListeners() {
        return listeners == null ? new ArrayList<>() : listeners;
    }

    protected TaskAction getAction() {
        return Preconditions.simpleNotNull(action, "the action cannot be null");
    }
}
