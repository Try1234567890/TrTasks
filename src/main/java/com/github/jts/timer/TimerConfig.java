package com.github.jts.timer;

import com.github.jts.time.Time;
import com.github.jts.time.TimeUnit;
import com.github.utilities.options.BooleanOption;
import com.github.utilities.options.IntegerOption;
import com.github.utilities.options.Option;
import com.github.utilities.validators.Preconditions;

public class TimerConfig {
    public final Option<Time> CYCLE_INTERVAL = new Option<>(new Time(1, TimeUnit.MILLISECONDS));
    public final IntegerOption MAX_TASKS = new IntegerOption(1000);
    public final BooleanOption START_ON_CREATE = new BooleanOption(true);

    public TimerConfig setCycleInterval(Time newValue) {
        CYCLE_INTERVAL.set(Preconditions.simpleParameterNotNull(newValue, "newValue"));
        return this;
    }

    public Time getCycleInterval() {
        return CYCLE_INTERVAL.get();
    }

    public TimerConfig setMaxTasks(int newValue) {
        MAX_TASKS.set(Preconditions.simpleParameterNotNull(newValue, "newValue"));
        return this;
    }

    public int getMaxTasks() {
        return MAX_TASKS.get();
    }

    public TimerConfig setStartOnFirstTask(boolean state) {
        START_ON_CREATE.set(state);
        return this;
    }

    public TimerConfig startOnFirstTask() {
        return setStartOnFirstTask(true);
    }

    public TimerConfig notStartOnFirstTask() {
        return setStartOnFirstTask(false);
    }

    public boolean shouldStartOnFirstTask() {
        return START_ON_CREATE.get();
    }
}