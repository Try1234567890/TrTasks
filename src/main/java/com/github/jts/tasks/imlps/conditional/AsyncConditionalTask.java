package com.github.jts.tasks.imlps.conditional;

import com.github.jts.executor.async.AsyncExecutor;
import com.github.jts.scheuduler.ConditionalScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.builders.conditional.AsyncConditionalTaskBuilder;
import com.github.jts.tasks.builders.conditional.ConditionalTaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.function.BooleanSupplier;

public class AsyncConditionalTask extends AbstractTask implements ConditionalTask {
    private final Scheduler scheduler;
    private final BooleanSupplier condition;

    public AsyncConditionalTask(String id, Timer timer, Time initialDelay, TaskConfig config,
                                BooleanSupplier condition, TaskAction action) {
        super(id, new AsyncExecutor(), timer, initialDelay, config, action);
        this.scheduler = new ConditionalScheduler(this);
        this.condition = Preconditions.simpleParameterNotNull(condition, "condition");
    }


    public static AsyncConditionalTask create(String id, Time initDelay, TaskConfig config,
                                              BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static AsyncConditionalTask create(String id, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static AsyncConditionalTask create(String id, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static AsyncConditionalTask create(BooleanSupplier condition, TaskAction action) {
        return builder()
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    private static AsyncConditionalTaskBuilder builder() {
        return ConditionalTaskBuilder.async();
    }

    @Override
    public BooleanSupplier condition() {
        return condition;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
