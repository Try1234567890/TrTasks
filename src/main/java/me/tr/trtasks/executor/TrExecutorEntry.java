package me.tr.trtasks.executor;

import me.tr.trtasks.tasks.TrTask;

public interface TrExecutorEntry {

    TrExecutor newInstance(TrTask task);

    boolean isExecutorFor(TrTask task);

}
