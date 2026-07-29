package com.github.jts.executor.async;

import com.github.jts.time.Time;
import com.github.utilities.options.Option;

public class AsyncExecutorConfig {
    public final Option<Time> TERMINATION_TIMEOUT = new Option<>(Time.seconds(5));

    public AsyncExecutorConfig setTerminationTimeout(Time newValue) {
        TERMINATION_TIMEOUT.set(Time.ensure(newValue));
        return this;
    }

    public Time getTerminationTimeout() {
        return TERMINATION_TIMEOUT.get();
    }
}