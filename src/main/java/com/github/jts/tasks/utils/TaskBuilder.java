package com.github.jts.tasks.utils;

import com.github.jts.tasks.Task;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskListener;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskBuilder {
    protected String identifier;
    protected TaskAction action;
    protected Timer timer;
    protected List<TaskListener> listeners = new ArrayList<>();
    protected boolean synchronous;

    public TaskBuilder() {
        this.identifier = newID();
    }

    public static TimeTaskBuilder time() {
        return new TimeTaskBuilder();
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
        this.synchronous = true;
        return this;
    }

    public TaskBuilder asynchronous() {
        this.synchronous = false;
        return this;
    }

    public TaskBuilder withTimer(Timer timer) {
        this.timer = timer;
        return this;
    }

    public abstract Task build();

    protected Timer getTimer() {
        return timer != null ? timer : StaticTimer.get();
    }

    protected String getID() {
        return (identifier == null || identifier.isEmpty()) ? newID() : identifier;
    }


}
