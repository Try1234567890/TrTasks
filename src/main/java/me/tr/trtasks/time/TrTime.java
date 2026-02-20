package me.tr.trtasks.time;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrTime {
    public static final TrTime EMPTY = new TrTime(0);
    public static final TrTime NULL = new TrTime(-1);
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)\\s*([a-zA-Z]{1,12})");

    private long amount;
    private final TrTimeUnit unit;

    public TrTime(long amount, TrTimeUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public TrTime(long amount) {
        this(amount, TrTimeUnit.MILLISECONDS);
    }

    public long to(TrTimeUnit to) {
        return amount * (unit.toNano() / to.toNano());
    }

    public long toNano() {
        return to(TrTimeUnit.NANOSECONDS);
    }

    public long toMicro() {
        return to(TrTimeUnit.MICROSECONDS);
    }

    public long toMillis() {
        return to(TrTimeUnit.MILLISECONDS);
    }

    public long toTicks() {
        return to(TrTimeUnit.TICKS);
    }

    public long toSeconds() {
        return to(TrTimeUnit.SECONDS);
    }

    public long toMinutes() {
        return to(TrTimeUnit.MINUTES);
    }

    public long toHours() {
        return to(TrTimeUnit.HOURS);
    }

    public long toDays() {
        return to(TrTimeUnit.DAYS);
    }

    public long toWeeks() {
        return to(TrTimeUnit.WEEKS);
    }

    public long toMonths() {
        return to(TrTimeUnit.MONTHS);
    }

    public long toYears() {
        return to(TrTimeUnit.YEARS);
    }

    public long toDecades() {
        return to(TrTimeUnit.DECADES);
    }

    public long toCenturies() {
        return to(TrTimeUnit.CENTURIES);
    }

    public boolean isMajor(TrTime time) {
        return toNano() > time.toNano();
    }

    public boolean isMajorOrEquals(TrTime time) {
        return toNano() >= time.toNano();
    }

    public boolean isEquals(TrTime time) {
        return toNano() == time.toNano();
    }

    public boolean isMinor(TrTime time) {
        return toNano() < time.toNano();
    }

    public boolean isMinorOrEquals(TrTime time) {
        return toNano() <= time.toNano();
    }

    public synchronized TrTime add(TrTime time) {
        this.amount += time.to(unit);
        return this;
    }

    public synchronized TrTime remove(TrTime time) {
        this.amount -= time.to(unit);
        return this;
    }

    public synchronized TrTime add(long time) {
        this.amount += time;
        return this;
    }

    public synchronized TrTime remove(long time) {
        this.amount -= time;
        return this;
    }

    public boolean isNull() {
        return this == NULL || amount < 0;
    }

    public boolean isEmpty() {
        return this == EMPTY || amount == 0;
    }

    public static TrTime parse(String str) {
        Matcher matcher = TIME_PATTERN.matcher(str);

        if (matcher.matches()) {
            long amount = Long.parseLong(matcher.group(1));
            TrTimeUnit unit = TrTimeUnit.parse(matcher.group(2));
            if (unit == TrTimeUnit.NONE) {
                return new TrTime(amount);
            }

            return new TrTime(amount, unit);
        }

        return NULL;
    }

    @Override
    public String toString() {
        return "Time{Amount=" + amount + ", Unit=" + unit + '}';
    }

    public String asString() {
        return amount + " " + unit;
    }
}
