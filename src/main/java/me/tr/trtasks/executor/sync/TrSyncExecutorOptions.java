package me.tr.trtasks.executor.sync;

import me.tr.trtasks.executor.TrExecutor;
import me.tr.trtasks.executor.TrExecutorOptions;

public class TrSyncExecutorOptions extends TrExecutorOptions {

    public TrSyncExecutorOptions(TrSyncExecutor executor) {
        super(executor);
    }

    @Override
    public TrSyncExecutor getExecutor() {
        return (TrSyncExecutor) super.getExecutor();
    }
}
