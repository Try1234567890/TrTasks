package com.github.jts.tasks.utils;

import com.github.jts.tasks.imlps.conditional.AsyncConditionalTask;
import com.github.jts.tasks.imlps.conditional.ConditionalTask;
import com.github.jts.tasks.imlps.conditional.SyncConditionalTask;
import com.github.utilities.validators.Preconditions;

import java.util.function.BooleanSupplier;

public class ConditionalTaskBuilder extends TaskBuilder {
    private BooleanSupplier condition;

    public ConditionalTaskBuilder withCondition(BooleanSupplier condition) {
        this.condition = condition;
        return this;
    }

    @Override
    public ConditionalTask build() {
        if (synchronous) {
            SyncConditionalTask task = new SyncConditionalTask(getID(), getTimer(),
                    Preconditions.simpleNotNull(condition, "condition cannot be null"),
                    Preconditions.simpleNotNull(action, "action cannot be null"));
            listeners.forEach(task::newListener);
            return task;
        } else {
            AsyncConditionalTask task = new AsyncConditionalTask(getID(), getTimer(),
                    Preconditions.simpleNotNull(condition, "condition cannot be null"),
                    Preconditions.simpleNotNull(action, "action cannot be null"));
            listeners.forEach(task::newListener);
            return task;
        }
    }
}
