package com.dvsnier.cache.base;

/**
 * TimeUnit
 * Created by dovsnier on 2019-07-23.
 */
public enum TimeUnit {

    /**
     * Time unit representing one thousandth of a second.
     */
    MILLISECONDS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toMillis(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return duration;
        }

        @Override
        public long toSeconds(long duration) {
            return duration / (C1 / C0);
        }

        @Override
        public long toMinutes(long duration) {
            return duration / (C2 / C0);
        }

        @Override
        public long toHours(long duration) {
            return duration / (C3 / C0);
        }

        @Override
        public long toDays(long duration) {
            return duration / (C4 / C0);
        }

        @Override
        public long toWeeks(long duration) {
            return duration / (C5 / C0);
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C0);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C0);
        }
    },

    /**
     * Time unit representing one second.
     */
    SECONDS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toSeconds(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C1 / C0, MAX / (C1 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return duration;
        }

        @Override
        public long toMinutes(long duration) {
            return duration / (C2 / C1);
        }

        @Override
        public long toHours(long duration) {
            return duration / (C3 / C1);
        }

        @Override
        public long toDays(long duration) {
            return duration / (C4 / C1);
        }

        @Override
        public long toWeeks(long duration) {
            return duration / (C5 / C1);
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C1);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C1);
        }
    },

    /**
     * Time unit representing sixty seconds.
     */
    MINUTES {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return super.convert(sourceDuration, sourceUnit);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C2 / C0, MAX / (C2 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C2 / C1, MAX / (C2 / C1));
        }

        @Override
        public long toMinutes(long duration) {
            return duration;
        }

        @Override
        public long toHours(long duration) {
            return duration / (C3 / C2);
        }

        @Override
        public long toDays(long duration) {
            return duration / (C4 / C2);
        }

        @Override
        public long toWeeks(long duration) {
            return duration / (C5 / C2);
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C2);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C2);
        }
    },

    /**
     * Time unit representing sixty minutes.
     */
    HOURS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toHours(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C3 / C0, MAX / (C3 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C3 / C1, MAX / (C3 / C1));
        }

        @Override
        public long toMinutes(long duration) {
            return x(duration, C3 / C2, MAX / (C3 / C2));
        }

        @Override
        public long toHours(long duration) {
            return duration;
        }

        @Override
        public long toDays(long duration) {
            return duration / (C4 / C3);
        }

        @Override
        public long toWeeks(long duration) {
            return duration / (C5 / C3);
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C3);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C3);
        }
    },

    /**
     * Time unit representing twenty four hours.
     */
    DAYS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toDays(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C4 / C0, MAX / (C4 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C4 / C1, MAX / (C4 / C1));
        }

        @Override
        public long toMinutes(long duration) {
            return x(duration, C4 / C2, MAX / (C4 / C2));
        }

        @Override
        public long toHours(long duration) {
            return x(duration, C4 / C3, MAX / (C4 / C3));
        }

        @Override
        public long toDays(long duration) {
            return duration;
        }

        @Override
        public long toWeeks(long duration) {
            return duration / (C5 / C4);
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C4);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C4);
        }
    },

    /**
     * Time unit representing seven days.
     */
    WEEKS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toWeeks(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C5 / C0, MAX / (C5 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C5 / C1, MAX / (C5 / C1));
        }

        @Override
        public long toMinutes(long duration) {
            return x(duration, C5 / C2, MAX / (C5 / C2));
        }

        @Override
        public long toHours(long duration) {
            return x(duration, C5 / C3, MAX / (C5 / C3));
        }

        @Override
        public long toDays(long duration) {
            return x(duration, C5 / C4, MAX / (C5 / C4));
        }

        @Override
        public long toWeeks(long duration) {
            return duration;
        }

        @Override
        public long toMonths(long duration) {
            return duration / (C6 / C5);
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C5);
        }
    },

    /**
     * Time unit representing four weeks and two days.
     */
    MONTHS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toMonths(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C6 / C0, MAX / (C6 / C0));
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C6 / C1, MAX / (C6 / C1));
        }

        @Override
        public long toMinutes(long duration) {
            return x(duration, C6 / C2, MAX / (C6 / C2));
        }

        @Override
        public long toHours(long duration) {
            return x(duration, C6 / C3, MAX / (C6 / C3));
        }

        @Override
        public long toDays(long duration) {
            return x(duration, C6 / C4, MAX / (C6 / C4));
        }

        @Override
        public long toWeeks(long duration) {
            return x(duration, C6 / C5, MAX / (C6 / C5));
        }

        @Override
        public long toMonths(long duration) {
            return duration;
        }

        @Override
        public long toYears(long duration) {
            return duration / (C7 / C6);
        }
    },

    /**
     * Time unit representing twelve months.
     */
    YEARS {
        @Override
        public long convert(long sourceDuration, TimeUnit sourceUnit) {
            return sourceUnit.toYears(sourceDuration);
        }

        @Override
        public long toMillis(long duration) {
            return x(duration, C7 / C0, MAX / (C7 / C0)) + U5L * U24L * U60L * U60L * U1000L;
        }

        @Override
        public long toSeconds(long duration) {
            return x(duration, C7 / C1, MAX / (C7 / C1)) + U5L * U24L * U60L * U60L;
        }

        @Override
        public long toMinutes(long duration) {
            return x(duration, C7 / C2, MAX / (C7 / C2)) + U5L * U24L * U60L;
        }

        @Override
        public long toHours(long duration) {
            return x(duration, C7 / C3, MAX / (C7 / C3)) + U5L * U24L;
        }

        @Override
        public long toDays(long duration) {
            return x(duration, C7 / C4, MAX / (C7 / C4)) + U5L;
        }

        @Override
        public long toWeeks(long duration) {
            return x(duration, (C7 + C4 * U5L) / C5, MAX / ((C7 + C4 * U5L) / C5));
        }

        @Override
        public long toMonths(long duration) {
            return x(duration, C7 / C6, MAX / (C7 / C6));
        }

        @Override
        public long toYears(long duration) {
            return duration;
        }
    };

    // Handy constants for conversion methods
    static final long U1L = 1L;
    static final long U5L = 5L;
    static final long U7L = 7L;
    static final long U12L = 12L;
    static final long U24L = 24L;
    static final long U30L = 30L;
    static final long U60L = 60L;
    static final long U1000L = 1000L;
    static final long C0 = U1L; // millisecond
    static final long C1 = C0 * U1000L; // second
    static final long C2 = C1 * U60L; // min
    static final long C3 = C2 * U60L; // hour
    static final long C4 = C3 * U24L; // day
    static final long C5 = C4 * U7L; // week
    static final long C6 = C4 * U30L; // month
    static final long C7 = C6 * U12L; // year

    static final long MAX = Long.MAX_VALUE;

    /**
     * Scale d by m, checking for overflow.
     * This has a short name to make above code more readable.
     */
    static long x(long d, long m, long over) {
        if (d > +over) return Long.MAX_VALUE;
        if (d < -over) return Long.MIN_VALUE;
        return d * m;
    }

    /**
     * Converts the given time duration in the given unit to this unit.
     * Conversions from finer to coarser granularities truncate, so
     * lose precision. For example, converting {@code 999} milliseconds
     * to seconds results in {@code 0}. Conversions from coarser to
     * finer granularities with arguments that would numerically
     * overflow saturate to {@code Long.MIN_VALUE} if negative or
     * {@code Long.MAX_VALUE} if positive.
     *
     * <p>For example, to convert 10 minutes to milliseconds, use:
     * {@code TimeUnit.MILLISECONDS.convert(10L, TimeUnit.MINUTES)}
     *
     * @param sourceDuration the time duration in the given {@code sourceUnit}
     * @param sourceUnit     the unit of the {@code sourceDuration} argument
     * @return the converted duration in this unit,
     * or {@code Long.MIN_VALUE} if conversion would negatively
     * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
     */
    public long convert(long sourceDuration, TimeUnit sourceUnit) {
        throw new AbstractMethodError();
    }


    /**
     * @param duration the duration
     * @return the converted duration,
     * or {@code Long.MIN_VALUE} if conversion would negatively
     * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
     */
    public long toMillis(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) SECONDS.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration,
     * or {@code Long.MIN_VALUE} if conversion would negatively
     * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
     */
    public long toSeconds(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) MINUTES.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration,
     * or {@code Long.MIN_VALUE} if conversion would negatively
     * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
     */
    public long toMinutes(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) HOURS.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration,
     * or {@code Long.MIN_VALUE} if conversion would negatively
     * overflow, or {@code Long.MAX_VALUE} if it would positively overflow.
     */
    public long toHours(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) DAYS.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration
     */
    public long toDays(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) Weeks.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration
     */
    public long toWeeks(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) Months.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration
     */
    public long toMonths(long duration) {
        throw new AbstractMethodError();
    }

    /**
     * Equivalent to
     * {@link #convert(long, TimeUnit) Years.convert(duration, this)}.
     *
     * @param duration the duration
     * @return the converted duration
     */
    public long toYears(long duration) {
        throw new AbstractMethodError();
    }
}

