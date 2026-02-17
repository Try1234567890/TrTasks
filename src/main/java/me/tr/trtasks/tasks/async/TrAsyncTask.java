package me.tr.trtasks.tasks.async;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.TrTaskOptions;
import me.tr.trtasks.time.TrTime;

import java.util.UUID;

public abstract class TrAsyncTask extends TrTask {

    public TrAsyncTask(String id, TrTime period, TrTime delay, TrAsyncTaskOptions options) {
        super(id, period, delay, true, options);
        setNextRun(delay.toMillis());
    }

    public TrAsyncTask(TrAsyncTaskOptions options) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, options);
    }

    public TrAsyncTask(String id, TrTime period, TrTime delay) {
        this(id, period, delay, null);
    }

    public TrAsyncTask(TrTime period, TrTime delay) {
        this(UUID.randomUUID().toString(), period, delay, null);
    }

    public TrAsyncTask(String id, TrTime period) {
        this(id, period, TrTime.EMPTY);
    }

    public TrAsyncTask(TrTime period) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY);
    }

    public TrAsyncTask(String id) {
        this(id, TrTime.EMPTY);
    }

    public TrAsyncTask() {
        this(UUID.randomUUID().toString());
    }

    @Override
    public TrAsyncTaskOptions getOptions() {
        TrTaskOptions options = super.getOptions();
        if (!(options instanceof TrAsyncTaskOptions)) {
            return setOptions(new TrAsyncTaskOptions(this).copy(options));
        }
        return (TrAsyncTaskOptions) super.getOptions();
    }
}
