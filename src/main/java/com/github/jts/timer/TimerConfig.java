package com.github.jts.timer;

import com.github.jts.tasks.Task;
import com.github.jts.time.Time;
import com.github.jts.time.TimeUnit;
import com.github.utilities.options.BooleanOption;
import com.github.utilities.options.IntegerOption;
import com.github.utilities.options.Option;
import com.github.utilities.validators.Preconditions;

import java.util.function.Predicate;

/**
 * This class represent a configuration for a timer.
 * It contains some options that can be configured to manipulate the timer
 * behavior.
 *
 * @see TimerImpl
 */
public class TimerConfig {
    /**
     * This option define how much time should pass between each timer iteration?
     * Consequentially, the value of this option implicit define the minimum interval
     * of the registered task too.
     * <p>
     * This value also affect the precision of the timer.
     * The precision of the timer is how near to the actual task-interval the task will be executed when
     * the timer effectively runs the task.
     * In fact, if this value is too low: probably the timer will consume too much CPU resources or
     * the timer will not be able to keep up with the requested interval, else if the value is too high
     * and some task require to run between short-period of times, the timer will not be able to execute
     * the task with the required precision.
     * <p>
     * Default value: {@code 1 millisecond}
     */
    public final Option<Time> CYCLE_INTERVAL = new Option<>(new Time(1, TimeUnit.MILLISECONDS));

    /**
     * This option define how many tasks the timer can handle.
     * This option is useful to avoid attacks or to set a max limit that cannot be overflowed, but if
     * you don't need to set a max limit, you can set this option to {@code -1} to disable it.
     * Anyway is recommended to set a max limit to avoid memory issues.
     * <p>
     * By default, if the number of tasks overflow the positive number of this option, a {@link IndexOutOfBoundsException}
     * is thrown by the {@link TimerImpl#register(Predicate, Task)} method.
     * <p>
     * Default value: {@code 1000}
     */
    public final IntegerOption MAX_TASKS = new IntegerOption(1000);

    /**
     * If this option is {@code true} when the timer is successfully created, it will start automatically,
     * even if it does not have any tasks registered yet.
     * <p>
     * Default value: {@code true}
     */
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