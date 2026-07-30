package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Year.
 */
public class Years implements TimeUnit {
    private Years() {
    }

    private static final class Holder {
        private static final Years INSTANCE = new Years();
    }

    public static Years getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 31_536_000_000_000_000L; // 365 days in nanoseconds
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("y", "year", "years");
    }

    public static class Leap extends Years {
        private Leap() {
        }

        private static final class Holder {
            private static final Leap INSTANCE = new Leap();
        }

        public static Leap getInstance() {
            return Holder.INSTANCE;
        }

        @Override
        public long toNanos() {
            return 31_556_952_000_000_000L; // 366 days / average Gregorian year in nanoseconds
        }
    }
}