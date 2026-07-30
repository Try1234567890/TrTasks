package com.github.jts.time;

import java.util.Set;

/**
 * Represents the time unit for a Centuries.
 */
public class Centuries implements TimeUnit {
    private Centuries() {
    }

    private static final class Holder {
        private static final Centuries INSTANCE = new Centuries();
    }

    public static Centuries getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public long toNanos() {
        return 3_155_695_200_000_000_000L;
    }

    @Override
    public Set<String> getSymbols() {
        return Set.of("c", "century", "centuries");
    }
}