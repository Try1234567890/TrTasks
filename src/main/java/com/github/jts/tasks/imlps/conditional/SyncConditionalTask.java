package com.github.jts.tasks.imlps.conditional;

import com.github.jts.executor.SyncExecutor;
import com.github.jts.scheuduler.ConditionalScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.builders.conditional.ConditionalTaskBuilder;
import com.github.jts.tasks.builders.conditional.SyncConditionalTaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.function.BooleanSupplier;

public class SyncConditionalTask extends AbstractTask implements ConditionalTask {
    private final Scheduler scheduler;
    private final BooleanSupplier condition;

    public SyncConditionalTask(String id, Timer timer, Time initialDelay, TaskConfig config,
                               BooleanSupplier condition, TaskAction action) {
        super(id, new SyncExecutor(), timer, initialDelay, config, action);
        this.scheduler = new ConditionalScheduler(this);
        this.condition = Preconditions.simpleParameterNotNull(condition, "condition");
    }

    public static SyncConditionalTask create(String id, Time initDelay, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static SyncConditionalTask create(String id, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static SyncConditionalTask create(String id, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    public static SyncConditionalTask create(BooleanSupplier condition, TaskAction action) {
        return builder()
                .withCondition(condition)
                .withAction(action)
                .build();
    }

    private static SyncConditionalTaskBuilder builder() {
        return ConditionalTaskBuilder.sync();
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
