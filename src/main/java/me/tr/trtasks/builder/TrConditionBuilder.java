package me.tr.trtasks.builder;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.condition.TrConditionTask;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.time.TrTimeUnit;

import java.util.UUID;
import java.util.function.Supplier;

public class TrConditionBuilder {
    private String id;
    private TrTime period;
    private TrTime delay;
    private boolean sync;
    private Runnable task;
    private Supplier<Boolean> canRun;

    public TrConditionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TrConditionBuilder sync() {
        this.sync = true;
        return this;
    }

    public TrConditionBuilder async() {
        this.sync = false;
        return this;
    }

    public TrConditionBuilder delay(TrTime delay) {
        this.delay = delay;
        return this;
    }

    public TrConditionBuilder period(TrTime period) {
        this.period = period;
        return this;
    }

    public TrConditionBuilder task(Runnable task) {
        this.task = task;
        return this;
    }

    public TrConditionBuilder canRun(Supplier<Boolean> canRun) {
        this.canRun = canRun;
        return this;
    }

    private String getID() {
        return (id == null || id.isEmpty()) ? this.id = UUID.randomUUID().toString() : id;
    }

    private void validate() {
        if (task == null)
            throw new NullPointerException("runnable of " + getID() + " is null");

        if (period == null)
            period = new TrTime(1, TrTimeUnit.SECONDS);

        if (delay == null)
            delay = TrTime.EMPTY;


    }

    public TrConditionTask build() {
        if (sync)
            return buildSync();
        else
            return buildAsync();
    }

    public TrConditionTask buildAsync() {
        validate();
        return new TrConditionTask(getID(), period, delay, true) {
            @Override
            protected void execute() {
                task.run();
            }

            @Override
            public boolean canRun() {
                return canRun.get();
            }
        };
    }

    public TrConditionTask buildSync() {
        validate();
        return new TrConditionTask(getID(), period, delay, false) {
            @Override
            protected void execute() {
                task.run();
            }

            @Override
            public boolean canRun() {
                return canRun.get();
            }
        };
    }
}
