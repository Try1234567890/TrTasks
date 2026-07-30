package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a MilliSeconds.
 */
public class MilliSeconds implements TimeUnit {
    private MilliSeconds() {
    }

    private static final class Holder {
        private static final MilliSeconds INSTANCE = new MilliSeconds();
    }

    public static MilliSeconds getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 1_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("ms", "millis", "millisecond", "milliseconds");
    }

}
