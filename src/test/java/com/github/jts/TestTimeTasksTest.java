package com.github.jts;

import com.github.jts.executor.SyncExecutor;
import com.github.jts.tasks.builders.TaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.time.TimeUnit;
import org.junit.jupiter.api.Test;

class TestTimeTasksTest {

    @Test
    void time_task_synchronous() {
        long nanos = System.nanoTime();

        TaskBuilder.time()
                .withInitialDelay(Time.seconds(5))
                .withInterval(Time.seconds(1))
                .withAction(() -> System.out.println(
                        "Task async-testing-task executed after " + (Time.nanoseconds(System.nanoTime())
                                .minus(nanos)
                                .asString(TimeUnit.SECONDS)) + " in " + Thread.currentThread().getName()
                ))
                .asynchronous()
                .withID("async-testing-task")
                .build()
                .schedule();

        TaskBuilder.time()
                .withInitialDelay(Time.seconds(5))
                .withInterval(Time.seconds(1))
                .withAction(() -> System.out.println(
                        "Task sync-testing-task executed after " + (Time.nanoseconds(System.nanoTime())
                                .minus(nanos)
                                .asString(TimeUnit.SECONDS)) + " in " + Thread.currentThread().getName()
                ))
                .synchronous()
                .withID("sync-testing-task")
                .build()
                .schedule();

        SyncExecutor.runLoop();
    }
}