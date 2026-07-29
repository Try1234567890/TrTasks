package com.github.jts.time;

import com.github.utilities.validators.Preconditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time {
    public static final Time EMPTY = Time.microseconds(0);
    public static final Time NULL = Time.microseconds(0);
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)\\s*([a-zA-Z]{1,12})");

    private final long amount;
    private final TimeUnit unit;

    public Time(long amount, TimeUnit unit) {
        this.amount = Preconditions.<Long, IllegalArgumentException>ensure(
                (amt) -> amt >= 0, amount,  () -> new IllegalArgumentException("Amount must be non-negative"));
        this.unit = TimeUnit.ensure(unit);
    }

    public static Time ensure(Time time) {
        return Preconditions.<Time, InvalidTimeException>ensure(
                Time::isValid,
                Preconditions.simpleNotNull(time, "time"),
                () -> new InvalidTimeException("the time cannot be invalid (nanoseconds < 0)")
        );
    }
    public static Time ensure(Time time, Time alternative) {
        return Preconditions.ensure(
                Time::isValid,
                Preconditions.simpleNotNull(time, "time"),
                alternative
        );
    }

    public static Time nanoseconds(long amount) {
        return new Time(amount, TimeUnit.NANOSECONDS);
    }
    public static Time microseconds(long amount) {
        return new Time(amount, TimeUnit.MICROSECONDS);
    }
    public static Time milliseconds(long amount) {
        return new Time(amount, TimeUnit.MILLISECONDS);
    }
    public static Time seconds(long amount) {
        return new Time(amount, TimeUnit.SECONDS);
    }
    public static Time minutes(long amount) {
        return new Time(amount, TimeUnit.MINUTES);
    }
    public static Time hours(long amount) {
        return new Time(amount, TimeUnit.HOURS);
    }
    public static Time days(long amount) {
        return new Time(amount, TimeUnit.DAYS);
    }
    public static Time weeks(long amount) {
        return new Time(amount, TimeUnit.WEEKS);
    }
    public static Time months(long amount) {
        return new Time(amount, TimeUnit.MONTHS);
    }
    public static Time years(long amount) {
        return new Time(amount, TimeUnit.YEARS);
    }
    public static Time decades(long amount) {
        return new Time(amount, TimeUnit.DECADES);
    }

    public long to(TimeUnit to) {
        return (amount * unit.toNano()) / to.toNano();
    }
    public long toNano() {
        return to(TimeUnit.NANOSECONDS);
    }
    public long toMicro() {
        return to(TimeUnit.MICROSECONDS);
    }
    public long toMillis() {
        return to(TimeUnit.MILLISECONDS);
    }
    public long toSeconds() {
        return to(TimeUnit.SECONDS);
    }
    public long toMinutes() {
        return to(TimeUnit.MINUTES);
    }
    public long toHours() {
        return to(TimeUnit.HOURS);
    }
    public long toDays() {
        return to(TimeUnit.DAYS);
    }
    public long toWeeks() {
        return to(TimeUnit.WEEKS);
    }
    public long toMonths() {
        return to(TimeUnit.MONTHS);
    }
    public long toYears() {
        return to(TimeUnit.YEARS);
    }
    public long toDecades() {
        return to(TimeUnit.DECADES);
    }
    public long toCenturies() {
        return to(TimeUnit.CENTURIES);
    }

    public boolean isMajor(Time time) {
        return toNano() > time.toNano();
    }

    public boolean isMajorOrEquals(Time time) {
        return toNano() >= time.toNano();
    }

    public boolean isEquals(Time time) {
        return toNano() == time.toNano();
    }

    public boolean isMinor(Time time) {
        return toNano() < time.toNano();
    }

    public boolean isMinorOrEquals(Time time) {
        return toNano() <= time.toNano();
    }

    public Time add(Time time) {
        return add(Preconditions.parameterNotNull(time, "time").to(unit));
    }

    public Time minus(Time time) {
        return minus(Preconditions.parameterNotNull(time, "time").to(unit));
    }

    public Time add(long time) {
        return new Time((amount + Preconditions.<Long, InvalidTimeException>ensure(
                t -> t >= 0, time,
                () -> new InvalidTimeException("The time cannot be negative.")
        )), unit);
    }

    public Time minus(long time) {
        return new Time((amount - Preconditions.<Long, InvalidTimeException>ensure(
                t -> t >= 0, time,
                () -> new InvalidTimeException("The time cannot be negative.")
        )), unit);
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public boolean isInvalid() {
        return !isValid();
    }

    public boolean isValid() {
        return !isNull() && !isEmpty();
    }

    public static Time parse(String str) {
        Matcher matcher = TIME_PATTERN.matcher(str);

        if (matcher.matches()) {
            long amount = Preconditions.<Long, IllegalArgumentException>ensure((amt) -> amt >= 0,
                    Long.parseLong(Preconditions.completeNotNull(matcher.group(1), "The amount cannot be null.")),
                    () -> new IllegalArgumentException("Amount must be non-negative"));

            TimeUnit unit = Preconditions.<TimeUnit, IllegalArgumentException>ensure((u) -> u != TimeUnit.NONE,
                    TimeUnit.parse(Preconditions.completeNotNull(matcher.group(2), "The amount cannot be null.")),
                    () -> new IllegalArgumentException("Unit must be valid"));

            return new Time(amount, unit);
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
    public String asString(TimeUnit unit) {
        long amt = to(unit);
        return amt + " " + unit;
    }
}
