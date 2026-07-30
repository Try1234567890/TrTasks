package com.github.jts.tasks;

import java.util.concurrent.atomic.AtomicLong;

public class TaskIDGenerator {
    private final AtomicLong COUNTER = new AtomicLong(0);

    private TaskIDGenerator() {}

    private static final class Holder {
        private static final TaskIDGenerator INSTANCE = new TaskIDGenerator();
    }

    public static TaskIDGenerator getInstance() {
        return Holder.INSTANCE;
    }

    public String newID(String prefix) {
        return prefix + COUNTER.getAndIncrement();
    }

    public String newID() {
        return String.valueOf(COUNTER.getAndIncrement());
    }

}
