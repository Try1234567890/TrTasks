package me.tr.trtasks.tasks.sync;

import me.tr.trtasks.tasks.TrTask;
import me.tr.trtasks.tasks.TrTaskOptions;
import me.tr.trtasks.time.TrTime;

import java.util.UUID;

public abstract class TrSyncTask extends TrTask {

    public TrSyncTask(String id, TrTime period, TrTime delay, TrSyncTaskOptions options) {
        super(id, period, delay, false, options);
        setNextRun(delay.toMillis());
    }

    public TrSyncTask(TrSyncTaskOptions options) {
        this(UUID.randomUUID().toString(), TrTime.EMPTY, TrTime.EMPTY, options);
    }

    public TrSyncTask(String id, TrTime period, TrTime delay) {
        this(id, period, delay, null);
    }


    public TrSyncTask(TrTime period, TrTime delay) {
        this(UUID.randomUUID().toString(), period, delay, null);
    }

    public TrSyncTask(String id, TrTime period) {
        this(id, period, TrTime.EMPTY);
    }


    public TrSyncTask(TrTime period) {
        this(UUID.randomUUID().toString(), period, TrTime.EMPTY);
    }

    public TrSyncTask(String id) {
        this(id, TrTime.EMPTY);
    }

    public TrSyncTask() {
        this(UUID.randomUUID().toString());
    }

    @Override
    public TrSyncTaskOptions getOptions() {
        TrTaskOptions options = super.getOptions();
        if (!(options instanceof TrSyncTaskOptions)) {
            return setOptions(new TrSyncTaskOptions(this).copy(options));
        }
        return (TrSyncTaskOptions) super.getOptions();
    }
}
