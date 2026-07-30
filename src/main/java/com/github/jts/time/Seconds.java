package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Seconds.
 */
public class Seconds implements TimeUnit {
    private Seconds() {
    }

    private static final class Holder {
        private static final Seconds INSTANCE = new Seconds();
    }

    public static Seconds getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 1_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("s", "sec", "second", "seconds");
    }
}