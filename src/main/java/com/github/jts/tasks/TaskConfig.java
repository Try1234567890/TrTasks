package com.github.jts.tasks;

import com.github.utilities.options.BooleanOption;

public class TaskConfig {
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
