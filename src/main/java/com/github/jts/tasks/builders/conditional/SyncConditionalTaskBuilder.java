package com.github.jts.tasks.builders.conditional;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.TaskBuilder;
import com.github.jts.tasks.imlps.conditional.AsyncConditionalTask;
import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.tasks.imlps.conditional.SyncConditionalTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.function.BooleanSupplier;

public class SyncConditionalTaskBuilder extends ConditionalTaskBuilder {

    @Override
    public SyncConditionalTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public SyncConditionalTaskBuilder withCondition(BooleanSupplier condition) {
        super.withCondition(condition);
        return this;
    }

    @Override
    public SyncConditionalTaskBuilder synchronous() {
        return this;
    }

    @Override
    public SyncConditionalTask build() {
        SyncConditionalTask task = new SyncConditionalTask(getID(), getTimer(),
                getInitialDelay(),
                getConfig(),
                Preconditions.simpleNotNull(condition, "condition cannot be null"),
                Preconditions.simpleNotNull(action, "action cannot be null"));
        listeners.forEach(task::newListener);
        return task;
    }
}
