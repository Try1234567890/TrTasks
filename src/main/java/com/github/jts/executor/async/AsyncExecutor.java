package com.github.jts.executor.async;

import com.github.jts.executor.Executor;
import com.github.jts.tasks.Task;
import com.github.jts.time.Time;
import com.github.jts.time.TimeUnit;
import com.github.utilities.validators.Preconditions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AsyncExecutor implements Executor {
    public static final Logger LOGGER = Logger.getLogger(AsyncExecutor.class.getName());
    private final ExecutorService executorService;
    private final AsyncExecutorConfig config;

    public AsyncExecutor(AsyncExecutorConfig config) {
        this.config = Preconditions.simpleNotNull(config, new AsyncExecutorConfig());
        this.executorService = Executors.newSingleThreadExecutor(r -> Thread.ofVirtual().name("async-task-executor").unstarted(r));
    }

    public AsyncExecutor() {
        this(new AsyncExecutorConfig());
    }

    public AsyncExecutorConfig config() {
        return config;
    }

    @Override
    public void execute(Task task) {
        Preconditions.parameterNotNull(task, "task");
        executorService.execute(() -> {
            try {
                task.getAction().execute();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An unexpected exception is thrown while executing " + task, e);
            }
        });
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
        try {
            Time await = config.TERMINATION_TIMEOUT.get();
            if (!executorService.awaitTermination(await.to(TimeUnit.MILLISECONDS), java.util.concurrent.TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            LOGGER.severe("The async task executor thread was interrupted while waiting for termination. Shutting down now.");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}





