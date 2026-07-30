package com.github.jts.tasks.builders.conditional;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.imlps.conditional.AsyncConditionalTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.function.BooleanSupplier;

public class AsyncConditionalTaskBuilder extends ConditionalTaskBuilder {
    private BooleanSupplier condition;

    @Override
    public AsyncConditionalTaskBuilder withID(String identifier) {
        super.withID(identifier);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withAction(TaskAction action) {
        super.withAction(action);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withListeners(List<TaskListener> listeners) {
        super.withListeners(listeners);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withListener(TaskListener listener) {
        super.withListener(listener);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withTimer(Timer timer) {
        super.withTimer(timer);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withInitialDelay(Time initialDelay) {
        super.withInitialDelay(initialDelay);
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder withConfig(TaskConfig config) {
        super.withConfig(config);
        return this;
    }

    public AsyncConditionalTaskBuilder withCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public AsyncConditionalTaskBuilder asynchronous() {
        return this;
    }

    @Override
    public AsyncConditionalTask build() {
        AsyncConditionalTask task = new AsyncConditionalTask(getID(), getTimer(),
                getInitialDelay(),
                getConfig(),
                Preconditions.simpleNotNull(condition, "condition cannot be null"),
                Preconditions.simpleNotNull(action, "action cannot be null"));
        listeners.forEach(task::newListener);
        return task;
    }
}
