package me.tr.trtasks.tasks.sync;

import me.tr.trtasks.tasks.TrTaskOptions;

public class TrSyncTaskOptions extends TrTaskOptions {

    public TrSyncTaskOptions(TrSyncTask task) {
        super(task);
    }

    @Override
    public TrSyncTaskOptions copy(TrTaskOptions options) {
        super.copy(options);
        return this;
    }
}
