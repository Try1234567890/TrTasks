package com.github.jts.time;

import com.github.utilities.validators.Preconditions;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;

/**
 * Represents the time unit for a specific duration.
 */
public interface TimeUnit {

    NanoSeconds NANOSECONDS = NanoSeconds.getInstance();
    MicroSeconds MICROSECONDS = MicroSeconds.getInstance();
    MilliSeconds MILLISECONDS = MilliSeconds.getInstance();
    Seconds SECONDS = Seconds.getInstance();
    Minutes MINUTES = Minutes.getInstance();
    Hours HOURS = Hours.getInstance();
    Days DAYS = Days.getInstance();
    Weeks WEEKS = Weeks.getInstance();
    Months MONTHS = Months.getInstance();
    Years YEARS = Years.getInstance();
    Decades DECADES = Decades.getInstance();
    Centuries CENTURIES = Centuries.getInstance();

    /**
     * Try to identify a {@link TimeUnit} from the provided {@code symbol}.
     *
     * @param symbol The symbol to identify
     * @return The {@link TimeUnit} if found, otherwise {@link Optional#empty()}
     * @throws IllegalArgumentException if the not {@link TimeUnit} is found
     */
    static TimeUnit parse(String symbol) throws IllegalArgumentException {
        Preconditions.parameterNotNull(symbol, "symbol", "symbol cannot be null or empty.");
        String normalizedText = symbol.toLowerCase(Locale.ROOT);

        for (TimeUnit unit : TimeUnits.getInstance().asCollection()) {
            if (unit.getSymbols().contains(normalizedText)) {
                // We have found the TimeUnit
                return unit;
            }
        }

        throw new IllegalArgumentException("No TimeUnit found for symbol: " + symbol);
    }

    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Converts 1 unit of this time unit into nanoseconds.
     * <p>
     * The nanoseconds are the smallest unit that is supported so these
     * are used for time conversion.
     *
     * @return the nanoseconds value for 1 unit of this time unit.
     */
    long toNanos();

    /**
     * Retrieve the symbols that are associated with this time unit.
     * <p>
     * The symbols are <b>case-insensitive</b> so the symbols of a time units must be {@code lower-case},
     * all symbols {@code upper-case} are ignored.
     *
     * <p><b>Implementation Note:</b> All symbols must be lower-case.
     * @return the lower-case symbols that are associated with this time unit.
     */
    Set<String> getSymbols();

}
