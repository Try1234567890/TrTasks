package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for an Hours.
 */
public class Hours implements TimeUnit {
    private Hours() {
    }

    private static final class Holder {
        private static final Hours INSTANCE = new Hours();
    }

    public static Hours getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 3_600_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("h", "hour", "hours");
    }
}