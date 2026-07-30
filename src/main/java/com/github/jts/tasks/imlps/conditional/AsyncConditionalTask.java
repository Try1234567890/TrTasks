package com.github.jts.tasks.imlps.conditional;

import com.github.jts.executor.async.AsyncExecutor;
import com.github.jts.scheuduler.ConditionalScheduler;
import com.github.jts.scheuduler.Scheduler;
import com.github.jts.tasks.AbstractTask;
import com.github.jts.tasks.TaskAction;
import com.github.jts.timer.StaticTimer;
import com.github.jts.timer.Timer;

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

    public AsyncConditionalTask(String id, BooleanSupplier condition, TaskAction action) {
        this(id, StaticTimer.get(), condition, action);
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
