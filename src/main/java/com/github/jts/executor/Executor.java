package com.github.jts.executor;

import com.github.jts.tasks.Task;

public interface Executor {

    /**
     * Execute the {@code task} following the rules of this executor.
     *
     * @param task the task to execute
     */
    void execute(Task task) throws Exception;

    void shutdown();

}
