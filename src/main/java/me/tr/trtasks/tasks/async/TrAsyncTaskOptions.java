package me.tr.trtasks.tasks.async;

import me.tr.trtasks.tasks.TrTaskOptions;

public class TrAsyncTaskOptions extends TrTaskOptions {

    public TrAsyncTaskOptions(TrAsyncTask task) {
        super(task);
    }

    @Override
    public TrAsyncTaskOptions copy(TrTaskOptions options) {
        super.copy(options);
        return this;
    }
}
