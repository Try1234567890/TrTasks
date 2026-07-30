package com.github.jts.tasks;

import com.github.utilities.options.BooleanOption;

/**
 * This class represent a configuration for any {@link AbstractTask}.
 * The TaskConfig contains some options to configure the behavior of the task.
 */
public class TaskConfig {
    /**
     * When a task fail to execute, if this options is {@code true} it will be rescheduled,
     * otherwise when a task fails it will be canceled.
     */
    public final BooleanOption RESCHEDULE_ON_FAIL = new BooleanOption(true);

    public static TaskConfig newConfiguration() {
        return new TaskConfig();
    }

    public TaskConfig setRescheduleOnFail(boolean state) {
        RESCHEDULE_ON_FAIL.set(state);
        return this;
    }

    public TaskConfig rescheduleOnFail() {
        return setRescheduleOnFail(true);
    }

    public TaskConfig notRescheduleOnFail() {
        return setRescheduleOnFail(false);
    }
}
