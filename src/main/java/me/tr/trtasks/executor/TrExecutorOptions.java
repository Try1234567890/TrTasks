package me.tr.trtasks.executor;

public class TrExecutorOptions {
    private final TrExecutor executor;


    public TrExecutorOptions(TrExecutor executor) {
        this.executor = executor;
    }

    public TrExecutor getExecutor() {
        return executor;
    }
}
