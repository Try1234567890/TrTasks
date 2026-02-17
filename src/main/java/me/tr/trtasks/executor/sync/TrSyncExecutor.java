package me.tr.trtasks.executor.sync;

import me.tr.trtasks.executor.TrExecutor;
import me.tr.trtasks.executor.TrExecutorEntry;
import me.tr.trtasks.tasks.TrTask;

public class TrSyncExecutor implements TrExecutor {
    public static final TrExecutorEntry ENTRY = new TrExecutorEntry() {
        @Override
        public TrExecutor newInstance(TrTask task) {
            return new TrSyncExecutor();
        }

        @Override
        public boolean isExecutorFor(TrTask task) {
            return task.isSync();
        }
    };
    private final TrSyncExecutorOptions options;

    public TrSyncExecutor() {
        this.options = new TrSyncExecutorOptions(this);
    }

    @Override
    public void execute(TrTask task) {
        task.run();
    }

    @Override
    public TrSyncExecutorOptions getOptions() {
        return options;
    }
}
