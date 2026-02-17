package me.tr.trtasks.executor;

import me.tr.trtasks.registry.TrExecutorRegistry;
import me.tr.trtasks.tasks.TrTask;

public class TrTaskExecutor {

    public void execute(TrTask task) {
        TrExecutorRegistry.getExecutorFor(task).
                ifPresent(exe -> exe.execute(task));
    }

}
