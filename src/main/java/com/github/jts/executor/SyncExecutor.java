package com.github.jts.executor;

import com.github.jts.tasks.Task;
import com.github.utilities.validators.Preconditions;

public class SyncExecutor implements Executor {

    /**
     * Execute the {@code task} synchronously.
     *
     * @param task the task to execute
     */
    @Override
    public void execute(Task task) throws Exception {
        Preconditions.simpleParameterNotNull(task, "task");
        task.getAction().execute();
    }

    @Override
    public void shutdown() {
        // Nothing to-do
    }
}
