package me.tr.trtasks.time;


public enum TrTimeUnit {
    NANOSECONDS(1L, "ns", "nano", "nanoseconds"),
    MICROSECONDS(1_000L, "us", "micro", "microseconds"),

    MILLISECONDS(1_000_000L, "ms", "millis", "milliseconds"),
    TICKS(50_000_000L, "tick", "t", "ticks"),
    SECONDS(1_000_000_000L, "s", "sec", "seconds"),
    MINUTES(60_000_000_000L, "m", "min", "minutes"),
    HOURS(3_600_000_000_000L, "h", "hour", "hours"),
    DAYS(86_400_000_000_000L, "d", "day", "days"),
    WEEKS(604_800_000_000_000L, "w", "week", "weeks"),

    MONTHS(2_629_746_000_000_000L, "mo", "month", "months"),
    YEARS(31_556_952_000_000_000L, "y", "year", "years"),
    DECADES(315_569_520_000_000_000L, "dec", "decade", "decades"),
    CENTURIES(3_155_695_200_000_000_000L, "c", "century", "centuries"),

    NONE(0L);

    private final String[] symbols;
    private final long toNano;

    TrTimeUnit(long toNano, String... symbols) {
        this.symbols = symbols;
        this.toNano = toNano;
    }

    public String[] getSymbols() {
        return symbols;
    }

    public long toNano() {
        return toNano;
    }

    public static TrTimeUnit parse(String str) {
        if (str == null || str.isEmpty()) return NONE;

        for (TrTimeUnit timeUnit : TrTimeUnit.values()) {
            for (String symbol : timeUnit.getSymbols()) {
                if (symbol.equalsIgnoreCase(str)) {
                    return timeUnit;
                }
            }
        }
        return NONE;
    }
}
