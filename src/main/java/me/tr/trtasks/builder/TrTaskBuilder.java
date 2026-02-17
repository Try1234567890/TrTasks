package me.tr.trtasks.builder;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.async.TrAsyncTask;
import me.tr.trtasks.tasks.sync.TrSyncTask;
import me.tr.trtasks.time.TrTime;
import me.tr.trtasks.time.TrTimeUnit;

import java.util.UUID;

public class TrTaskBuilder {
    private String id;
    private TrTime period;
    private TrTime delay;
    private boolean sync;
    private Runnable task;

    public TrTaskBuilder id(String id) {
        this.id = id;
        return this;
    }

    public TrTaskBuilder sync() {
        this.sync = true;
        return this;
    }

    public TrTaskBuilder async() {
        this.sync = false;
        return this;
    }

    public TrTaskBuilder delay(TrTime delay) {
        this.delay = delay;
        return this;
    }

    public TrTaskBuilder period(TrTime period) {
        this.period = period;
        return this;
    }

    public TrTaskBuilder task(Runnable task) {
        this.task = task;
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

    public TrTask build() {
        if (sync)
            return buildSync();
        else
            return buildAsync();
    }

    public TrAsyncTask buildAsync() {
        validate();
        return new TrAsyncTask(getID(), period, delay) {
            @Override
            protected void task() {
                task.run();
            }
        };
    }

    public TrSyncTask buildSync() {
        validate();
        return new TrSyncTask(getID(), period, delay) {
            @Override
            protected void task() {
                task.run();
            }
        };
    }
}
