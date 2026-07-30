package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Months.
 */
public class Months implements TimeUnit {
    private Months() {
    }

    private static final class Holder {
        private static final Months INSTANCE = new Months();
    }

    public static Months getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 2_629_746_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("mo", "month", "months");
    }
}