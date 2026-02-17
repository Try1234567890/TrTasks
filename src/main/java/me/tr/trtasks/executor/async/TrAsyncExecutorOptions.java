package me.tr.trtasks.executor.async;

import me.tr.trtasks.executor.TrExecutor;
import me.tr.trtasks.executor.TrExecutorOptions;

public class TrAsyncExecutorOptions extends TrExecutorOptions {
    private int nThreads;

    public TrAsyncExecutorOptions(TrAsyncExecutor executor) {
        super(executor);
        this.nThreads = Math.min(6, Runtime.getRuntime().availableProcessors());
    }

    public TrAsyncExecutorOptions setNThreads(int nThreads) {
        this.nThreads = nThreads;
        getExecutor().changeExecutor(nThreads);
        return this;
    }

    public int getNThreads() {
        return nThreads;
    }

    @Override
    public TrAsyncExecutor getExecutor() {
        return (TrAsyncExecutor) super.getExecutor();
    }
}
