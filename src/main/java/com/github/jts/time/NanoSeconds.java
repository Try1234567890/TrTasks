package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a NanoSeconds.
 */

public class NanoSeconds implements TimeUnit {
    private NanoSeconds() {
    }

    private static final class Holder {
        private static final NanoSeconds INSTANCE = new NanoSeconds();
    }

    public static NanoSeconds getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 1;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("ns", "nano", "nanosecond", "nanoseconds");
    }

}
