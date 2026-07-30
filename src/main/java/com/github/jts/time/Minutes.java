package com.github.jts.time;

import java.util.Set;


/**
 * Represents the time unit for a Minutes.
 */
public class Minutes implements TimeUnit {
    private Minutes() {
    }

    private static final class Holder {
        private static final Minutes INSTANCE = new Minutes();
    }

    public static Minutes getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 60_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("m", "min", "minute", "minutes");
    }
}