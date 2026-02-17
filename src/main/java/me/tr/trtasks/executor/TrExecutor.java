package me.tr.trtasks.executor;

import me.tr.trtasks.tasks.TrTask;

public interface TrExecutor {

    void execute(TrTask task);

    TrExecutorOptions getOptions();
}
