package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Week.
 */
public class Weeks implements TimeUnit {
    private Weeks() {
    }

    private static final class Holder {
        private static final Weeks INSTANCE = new Weeks();
    }

    public static Weeks getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 604_800_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("w", "week", "weeks");
    }
}