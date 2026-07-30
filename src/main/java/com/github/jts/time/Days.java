package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Days.
 */
public class Days implements TimeUnit {
    private Days() {
    }

    private static final class Holder {
        private static final Days INSTANCE = new Days();
    }

    public static Days getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 86_400_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("d", "day", "days");
    }
}