package com.github.jts.tasks.imlps.time;

import com.github.jts.scheuduler.Scheduler;
import com.github.jts.scheuduler.TimeScheduler;
import com.github.jts.tasks.Task;
import com.github.jts.time.Time;
import com.github.jts.timer.Timer;

public interface TimeTask extends Task {

    /**
     * Retrieves the last time that this task has been executed in milliseconds.
     *
     * @return The last execution time in milliseconds.
     */
    long getLastTimeExecuted();

    /**
     * Retrieves the interval to wait between each execution of this task.
     *
     * @return The interval between executions.
     */
    Time getInterval();

}
