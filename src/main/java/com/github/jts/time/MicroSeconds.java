package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a MicroSeconds.
 */
public class MicroSeconds implements TimeUnit {
    private MicroSeconds() {
    }

    private static final class Holder {
        private static final MicroSeconds INSTANCE = new MicroSeconds();
    }

    public static MicroSeconds getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 1000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("us", "micro", "microsecond", "microseconds");
    }

}
