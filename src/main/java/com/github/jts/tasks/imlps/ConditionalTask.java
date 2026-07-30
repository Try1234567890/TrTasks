package com.github.jts.tasks.imlps;

import com.github.jts.executor.Executor;
import com.github.jts.scheuduler.ConditionalScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.tasks.TaskConfig;
import com.github.jts.tasks.TaskListener;
import com.github.jts.tasks.builders.ConditionalTaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;
import com.github.utilities.validators.Preconditions;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BooleanSupplier;

public class ConditionalTask extends AbstractTask {
    private final Scheduler scheduler;
    private final BooleanSupplier condition;

    public ConditionalTask(String id, Executor executor, Timer timer, Time initialDelay, TaskConfig config,
                           List<TaskListener> listeners, BooleanSupplier condition, TaskAction action) {
        super(id, executor, timer, initialDelay, config, listeners, action);
        this.scheduler = new ConditionalScheduler(this);
        this.condition = Preconditions.simpleParameterNotNull(condition, "condition");
    }

    public ConditionalTask(String id, Executor executor, Timer timer, Time initialDelay, TaskConfig config,
                           BooleanSupplier condition, TaskAction action) {
        super(id, executor, timer, initialDelay, config, new CopyOnWriteArrayList<>(), action);
        this.scheduler = new ConditionalScheduler(this);
        this.condition = Preconditions.simpleParameterNotNull(condition, "condition");
    }

    public static ConditionalTask sync(String id, Time initDelay, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static ConditionalTask sync(String id, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static ConditionalTask sync(String id, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withCondition(condition)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static ConditionalTask sync(BooleanSupplier condition, TaskAction action) {
        return builder()
                .withCondition(condition)
                .withAction(action)
                .synchronous()
                .build();
    }

    public static ConditionalTask async(String id, Time initDelay, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withInitialDelay(initDelay)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static ConditionalTask async(String id, TaskConfig config, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withConfig(config)
                .withCondition(condition)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static ConditionalTask async(String id, BooleanSupplier condition, TaskAction action) {
        return builder()
                .withID(id)
                .withCondition(condition)
                .withAction(action)
                .asynchronous()
                .build();
    }

    public static ConditionalTask async(BooleanSupplier condition, TaskAction action) {
        return builder()
                .withCondition(condition)
                .withAction(action)
                .asynchronous()
                .build();
    }

    private static ConditionalTaskBuilder builder() {
        return new ConditionalTaskBuilder();
    }

    public BooleanSupplier condition() {
        return condition;
    }

    @Override
    public Scheduler getScheduler() {
        return scheduler;
    }
}
