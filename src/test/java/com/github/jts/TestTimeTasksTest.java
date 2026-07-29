package com.github.jts;

import com.github.jts.tasks.utils.TaskBuilder;
import com.github.jts.time.Time;
import com.github.jts.time.TimeUnit;
import org.junit.jupiter.api.Test;

class TestTimeTasksTest {

    @Test
    void time_task_synchronous() {
        long nanos = System.nanoTime();

        boolean scheduled = TaskBuilder.time()
                .withInterval(Time.seconds(1))
                .withDelay(Time.seconds(5))
                .withAction(() -> System.out.println("Task executed after " + (Time.nanoseconds(System.nanoTime()).minus(nanos).asString(TimeUnit.SECONDS)) + " in " + Thread.currentThread().getName()))
                .asynchronous()
                .build()
                .schedule();

        System.out.println("Task scheduled: " + scheduled);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}