package com.github.jts.time;

import com.github.utilities.registries.CollectionRegistry;

public class TimeUnits extends CollectionRegistry<TimeUnit> {
    private TimeUnits() {
        register(TimeUnit.NANOSECONDS);
        register(TimeUnit.MICROSECONDS);
        register(TimeUnit.MILLISECONDS);
        register(TimeUnit.SECONDS);
        register(TimeUnit.MINUTES);
        register(TimeUnit.HOURS);
        register(TimeUnit.DAYS);
        register(TimeUnit.WEEKS);
        register(TimeUnit.MONTHS);
        register(TimeUnit.YEARS);
        register(TimeUnit.DECADES);
        register(TimeUnit.CENTURIES);
    }

    private static final class Holder {
        private static final TimeUnits INSTANCE = new TimeUnits();
    }

    public static TimeUnits getInstance() {
        return Holder.INSTANCE;
    }

}
