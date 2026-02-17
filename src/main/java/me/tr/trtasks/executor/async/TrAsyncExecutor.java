package me.tr.trtasks.executor.async;

import me.tr.trtasks.executor.TrExecutor;
import me.tr.trtasks.executor.TrExecutorEntry;
import me.tr.trtasks.tasks.TrTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrAsyncExecutor implements TrExecutor {
    public static final TrExecutorEntry ENTRY = new TrExecutorEntry() {
        @Override
        public TrExecutor newInstance(TrTask task) {
            return new TrAsyncExecutor();
        }

        @Override
        public boolean isExecutorFor(TrTask task) {
            return task.isAsync();
        }
    };
    private ExecutorService executor;
    private final TrAsyncExecutorOptions options;

    public TrAsyncExecutor() {
        this.options = new TrAsyncExecutorOptions(this);
        this.executor = Executors.newFixedThreadPool(options.getNThreads());
    }

    @Override
    public void execute(TrTask task) {
        executor.submit(task::run);
    }

    void changeExecutor(int nThreads) {
        this.executor.shutdown();
        this.executor = Executors.newFixedThreadPool(nThreads);
    }

    @Override
    public TrAsyncExecutorOptions getOptions() {
        return options;
    }
}
