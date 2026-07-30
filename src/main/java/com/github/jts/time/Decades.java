package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Decades.
 */
public class Decades implements TimeUnit {
    private Decades() {
    }

    private static final class Holder {
        private static final Decades INSTANCE = new Decades();
    }

    public static Decades getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 315_569_520_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("dec", "decade", "decades");
    }
}