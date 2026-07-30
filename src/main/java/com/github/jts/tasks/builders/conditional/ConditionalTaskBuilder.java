package com.github.jts.tasks.builders.conditional;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.TaskBuilder;
import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

import java.util.List;
import java.util.function.BooleanSupplier;

public abstract class ConditionalTaskBuilder extends TaskBuilder {
    protected BooleanSupplier condition;

    public static SyncConditionalTaskBuilder sync() {
        return new SyncConditionalTaskBuilder();
    }

    public static AsyncConditionalTaskBuilder async() {
        return new AsyncConditionalTaskBuilder();
    }
    @Override
    public ConditionalTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public ConditionalTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public ConditionalTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public ConditionalTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public ConditionalTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    public SyncConditionalTaskBuilder synchronous() {
        return new SyncConditionalTaskBuilder()
                .withID(identifier)
                .withAction(action)
                .withListeners(listeners)
                .withTimer(timer)
                .withInitialDelay(initialDelay)
                .withConfig(config)
                .withCondition(condition);
    }

    public AsyncConditionalTaskBuilder asynchronous() {
        return new AsyncConditionalTaskBuilder()
                .withID(identifier)
                .withAction(action)
                .withListeners(listeners)
                .withTimer(timer)
                .withInitialDelay(initialDelay)
                .withConfig(config)
                .withCondition(condition);
    }

    @Override
    public ConditionalTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public ConditionalTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public ConditionalTaskBuilder withCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public abstract ConditionalTask build();
}
