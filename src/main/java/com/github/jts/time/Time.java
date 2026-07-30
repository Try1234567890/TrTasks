package com.github.jts.time;

import com.github.utilities.validators.Preconditions;
import com.github.utilities.validators.ValidationUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an amount of time with a specific unit.
 * The {@code amount} tells how many {@code unit} of time this instance represents.
 * <p>
 * <b>The Time objects are immutable</b> to prevent any data corruptions, for this reason all methods that
 * would modify the instance will return a new instance instead.
 */
public class Time {
    public static final Time EMPTY = Time.nanoseconds(0);
    public static final Time NULL = Time.nanoseconds(0);
    private static final Pattern TIME_PATTERN = Pattern.compile("(\\d+)\\s*([a-zA-Z]{1,12})");

    private final long amount;
    private final TimeUnit unit;

    /**
     * Create a new {@link Time} instance.
     *
     * @param amount The amount of time.
     * @param unit   The unit of time.
     * @throws InvalidTimeException if the {@code amount} is negative.
     * @throws NullPointerException if the {@code unit} is null.
     */
    public Time(long amount, TimeUnit unit) {
        this.amount = Preconditions.<Long, InvalidTimeException>ensure(
                (amt) -> amt >= 0, amount, () -> new InvalidTimeException("Amount must be non-negative"));
        this.unit = Preconditions.parameterNotNull(unit, "unit");
    }

    /**
     * Ensure that the time is valid and throw a {@link InvalidTimeException} if is not.
     * Throws {@link NullPointerException} if the {@code time} is null.
     *
     * @param time The time to validate.
     * @return The validated time.
     */
    public static Time ensure(Time time) {
        return Preconditions.<Time, InvalidTimeException>ensure(
                Time::isValid,
                Preconditions.simpleNotNull(time, "time"),
                () -> new InvalidTimeException("the time cannot be invalid (nanoseconds < 0)")
        );
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} nanoseconds.
     *
     * @param amount The amount of nanoseconds
     * @return A new Time instance of {@code amount} nanoseconds.
     */
    public static Time nanoseconds(long amount) {
        return new Time(amount, TimeUnit.NANOSECONDS);
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} microseconds.
     *
     * @param amount The amount of microseconds
     * @return A new Time instance of {@code amount} microseconds.
     */
    public static Time microseconds(long amount) {
        return new Time(amount, TimeUnit.MICROSECONDS);
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} milliseconds.
     *
     * @param amount The amount of milliseconds
     * @return A new Time instance of {@code amount} milliseconds.
     */
    public static Time milliseconds(long amount) {
        return new Time(amount, TimeUnit.MILLISECONDS);
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} seconds.
     *
     * @param amount The amount of seconds
     * @return A new Time instance of {@code amount} seconds.
     */
    public static Time seconds(long amount) {
        return new Time(amount, TimeUnit.SECONDS);
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} minutes.
     *
     * @param amount The amount of minutes
     * @return The amount of minutes
     */
    public static Time minutes(long amount) {
        return new Time(amount, TimeUnit.MINUTES);
    }


    /**
     * Creates a new {@link Time} instance with {@code amount} hours.
     *
     * @param amount The amount of hours
     * @return The amount of hours
     */
    public static Time hours(long amount) {
        return new Time(amount, TimeUnit.HOURS);
    }


    /**
     * Creates a new {@link Time} instance with {@code amount} days.
     *
     * @param amount The amount of days
     * @return The amount of days
     */
    public static Time days(long amount) {
        return new Time(amount, TimeUnit.DAYS);
    }


    /**
     * Creates a new {@link Time} instance with {@code amount} weeks.
     *
     * @param amount The amount of weeks
     * @return The amount of weeks
     */
    public static Time weeks(long amount) {
        return new Time(amount, TimeUnit.WEEKS);
    }

    /**
     * Creates a new {@link Time} instance with {@code amount} months.
     *
     * @param amount The amount of months
     * @return The amount of months
     */
    public static Time months(long amount) {
        return new Time(amount, TimeUnit.MONTHS);
    }


    /**
     * Creates a new {@link Time} instance with {@code amount} years.
     *
     * @param amount The amount of years
     * @return The amount of years
     */
    public static Time years(long amount) {
        return new Time(amount, TimeUnit.YEARS);
    }


    /**
     * Creates a new {@link Time} instance with {@code amount} decades.
     *
     * @param amount The amount of decades
     * @return The amount of decades
     */
    public static Time decades(long amount) {
        return new Time(amount, TimeUnit.DECADES);
    }

    /**
     * Converts this code to the given {@code newUnit}.
     * <p>
     * The precision of this conversion is {@code Nanoseconds}.
     *
     * @param newUnit The unit to convert this time to.
     * @return this time converted to the given {@code newUnit}.
     */
    public long to(TimeUnit newUnit) {
        return (amount * unit.toNanos()) / newUnit.toNanos();
    }

    /**
     * Converts this time to {@code Nanoseconds}.
     *
     * @return this time to {@code code}
     */
    public long toNanos() {
        return to(TimeUnit.NANOSECONDS);
    }

    /**
     * Converts this time to {@code Microseconds}.
     *
     * @return this time to {@code code}
     */
    public long toMicros() {
        return to(TimeUnit.MICROSECONDS);
    }

    /**
     * Converts this time to {@code Milliseconds}.
     *
     * @return this time to {@code code}
     */
    public long toMillis() {
        return to(TimeUnit.MILLISECONDS);
    }

    /**
     * Converts this time to {@code Seconds}.
     *
     * @return this time to {@code code}
     */
    public long toSeconds() {
        return to(TimeUnit.SECONDS);
    }

    /**
     * Converts this time to {@code Minutes}.
     *
     * @return this time to {@code code}
     */
    public long toMinutes() {
        return to(TimeUnit.MINUTES);
    }

    /**
     * Converts this time to {@code Hours}.
     *
     * @return this time to {@code code}
     */
    public long toHours() {
        return to(TimeUnit.HOURS);
    }

    /**
     * Converts this time to {@code Days}.
     *
     * @return this time to {@code code}
     */
    public long toDays() {
        return to(TimeUnit.DAYS);
    }

    /**
     * Converts this time to {@code Weeks}.
     *
     * @return this time to {@code code}
     */
    public long toWeeks() {
        return to(TimeUnit.WEEKS);
    }

    /**
     * Converts this time to {code Months}.
     *
     * @return this time to {@code Months}
     */
    public long toMonths() {
        return to(TimeUnit.MONTHS);
    }

    /**
     * Converts this time to {code Years}.
     *
     * @return this time to {@code Years}
     */
    public long toYears() {
        return to(TimeUnit.YEARS);
    }

    /**
     * Converts this time to {code Decades}.
     *
     * @return this time to {@code Decades}
     */
    public long toDecades() {
        return to(TimeUnit.DECADES);
    }

    /**
     * Converts this time to {code Centuries}.
     *
     * @return this time to {@code Centuries}
     */
    public long toCenturies() {
        return to(TimeUnit.CENTURIES);
    }

    /**
     * Checks if this time is major ({@code >}) to the given {@code time}.
     * <p>
     * The precision of this method is {@code Nanoseconds}.
     *
     * @param time the time to compare with.
     * @return {@code true} if this time is major to the given {@code time}, {@code false} otherwise.
     */
    public boolean isMajor(Time time) {
        return toNanos() > time.toNanos();
    }

    /**
     * Checks if this time is major ({@code >}) to the given {@code time}.
     * <p>
     * The precision of this method is {@code Nanoseconds}.
     *
     * @param time the time to compare with.
     * @return {@code true} if this time is major to the given {@code time}, {@code false} otherwise.
     */
    public boolean isMajorOrEquals(Time time) {
        return toNanos() >= time.toNanos();
    }

    /**
     * Checks if this time is equals ({@code ==}) to the given {@code time}.
     * <p>
     * The precision of this method is {@code Nanoseconds}.
     *
     * @param time the time to compare with.
     * @return {@code true} if this time is equals to the given {@code time}, {@code false} otherwise.
     */
    public boolean isEquals(Time time) {
        return toNanos() == time.toNanos();
    }

    /**
     * Checks if this time is minor ({@code <}) to the given {@code time}.
     * <p>
     * The precision of this method is {@code Nanoseconds}.
     *
     * @param time the time to compare with.
     * @return {@code true} if this time is minor to the given {@code time}, {@code false} otherwise.
     */
    public boolean isMinor(Time time) {
        return toNanos() < time.toNanos();
    }

    /**
     * Checks if this time is minor or equals ({@code <=}) to the given {@code time}.
     * <p>
     * The precision of this method is {@code Nanoseconds}.
     *
     * @param time the time to compare with.
     * @return {@code true} if this time is minor or equals to the given {@code time}, {@code false} otherwise.
     */
    public boolean isMinorOrEquals(Time time) {
        return toNanos() <= time.toNanos();
    }

    /**
     * Adds the given {@code time} to this time.
     *
     * @param time The time to add.
     * @return A new time instance with the result of the addition.
     * @throws InvalidTimeException If the time is negative.
     */
    public Time add(Time time) {
        return add(Preconditions.parameterNotNull(time, "time").to(unit));
    }


    /**
     * Adds the given {@code time} to this time.
     *
     * @param time The time to add.
     * @return A new time instance with the result of the addition.
     * @throws InvalidTimeException If the time is negative.
     */
    public Time minus(Time time) {
        return minus(Preconditions.parameterNotNull(time, "time").to(unit));
    }

    /**
     * Adds the given {@code time} to this time.
     *
     * @param time The time to add.
     * @return A new time instance with the result of the addition.
     * @throws InvalidTimeException If the time is negative.
     */
    public Time add(long time) {
        return new Time((amount + Preconditions.<Long, InvalidTimeException>ensure(
                t -> t >= 0, time,
                () -> new InvalidTimeException("The time cannot be negative.")
        )), unit);
    }

    /**
     * Subtracts the given {@code time} from this time.
     *
     * @param time The time to subtract.
     * @return A new time instance with the result of the subtraction.
     * @throws InvalidTimeException If the time is negative.
     */
    public Time minus(long time) {
        return new Time((amount - Preconditions.<Long, InvalidTimeException>ensure(
                t -> t >= 0, time,
                () -> new InvalidTimeException("The time cannot be negative.")
        )), unit);
    }

    /**
     * Checks if this instance of Time is {@link #NULL} time.
     *
     * @return {@code true} if this instance of Time is {@link #NULL} time, {@code false} otherwise.
     */
    public boolean isNull() {
        return this == NULL;
    }

    /**
     * Checks if this instance of Time is {@link #EMPTY} time.
     *
     * @return {@code true} if this instance of Time is {@link #EMPTY} time, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this == EMPTY;
    }

    /**
     * Checks if this time is invalid in any way.
     * <p>
     * This is equals to: {@code isNull() || isEmpty();}
     *
     * @return {@code true} if this time is invalid, {@code false} otherwise.
     */
    public boolean isInvalid() {
        return isNull() || isEmpty();
    }

    /**
     * Checks if this time is not null and is not empty.
     * <p>
     * This is equals to: {@code !isNull() && !isEmpty();}
     *
     * @return {@code true} if this time is not null and is not empty, {@code false} otherwise.
     */
    public boolean isValid() {
        return !isNull() && !isEmpty();
    }

    /**
     * Try to parse a time instance from the given {@code text}.
     * The parsing process is done with the regex {@link #TIME_PATTERN} that searches for a
     * number followed by a word of length in range 1-12, ignoring all spaces between them.
     * So, for example a valid matches for time is: {@code 1000 Milliseconds}, {@code 24 Hours} or {@code 1 Year}.
     * <p>
     * If the text is null, empty or not valid time pattern are found inside the text, {@link #NULL} is returned.
     * If the text matches a valid pattern but some error occurs while parsing it, an {@link InvalidTimeException} is thrown.
     *
     * @param text The text to parse the time from.
     * @return A new time instance parsed from the {@code text}, or {@link #NULL} if not times pattern are found.
     * @throws InvalidTimeException If an error occurs while parsing a time pattern.
     */
    public static Time parse(String text) throws InvalidTimeException {
        if (ValidationUtils.isNotBlank(text)) return NULL;
        Matcher matcher = TIME_PATTERN.matcher(text);

        if (matcher.matches()) {
            try {
                long amount = Long.parseLong(Preconditions.completeNotNull(matcher.group(1), "The amount cannot be null."));
                TimeUnit unit = TimeUnit.parse(Preconditions.completeNotNull(matcher.group(2), "The unit cannot be null."));
                return new Time(amount, unit);
            } catch (Exception e) {
                throw new InvalidTimeException("An invalid time is found inside " + text.substring(matcher.start(), matcher.end()), e);
            }
        }

        return NULL;
    }

    @Override
    public String toString() {
        return "Time{Amount=" + amount + ", Unit=" + unit + '}';
    }

    /**
     * Returns it as a representation of string following this pattern: {@code <amount> <unit name>} (e.g. "5 Seconds").
     *
     * @return The string representation of this time with the {@code unit}
     */
    public String asString() {
        return amount + " " + unit;
    }

    /**
     * Converts this time to the specified unit and returns it as a representation of string
     * following this pattern: {@code <amount> <unit name>} (e.g. "5 Seconds").
     *
     * @param unit The unit to convert the time to
     * @return The string representation of this time with the {@code unit}
     */
    public String asString(TimeUnit unit) {
        Preconditions.parameterNotNull(unit, "unit");
        long amt = to(unit);
        return amt + " " + unit.getName();
    }
}




