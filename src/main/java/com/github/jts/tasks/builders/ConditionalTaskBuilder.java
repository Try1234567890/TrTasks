package com.github.jts.tasks.builders;

import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.imlps.ConditionalTask;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.function.BooleanSupplier;

public class ConditionalTaskBuilder extends TaskBuilder {
    protected BooleanSupplier condition;

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

    public ConditionalTaskBuilder synchronous() {
        super.synchronous();
        return this;
    }

    public ConditionalTaskBuilder asynchronous() {
        super.asynchronous();
        return this;
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
    public ConditionalTask build() {
        return new ConditionalTask(
                getID(),
                getExecutor(),
                getTimer(),
                getInitialDelay(),
                getConfig(),
                getListeners(),
                Preconditions.simpleNotNull(condition, "the condition cannot be null"),
                getAction()
        );
    }
}
