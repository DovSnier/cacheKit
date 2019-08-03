package com.dvsnier.cache.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * TimeUnitTest
 * Created by dovsnier on 2019-07-23.
 */
public class TimeUnitTest {

    public TimeUnit timeUnit;

    @Test
    public void years() {
        timeUnit = TimeUnit.YEARS;
        Assert.assertEquals(timeUnit.toYears(1L), 1L);
        Assert.assertEquals(timeUnit.toMonths(1L), 12L);
        Assert.assertEquals(timeUnit.toWeeks(1L), 365 / 7L);
        Assert.assertEquals(timeUnit.toDays(1L), 365L);
        Assert.assertEquals(timeUnit.toHours(1L), 365 * 24L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 365 * 24 * 60L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 365 * 24 * 60 * 60L);
        Assert.assertEquals(timeUnit.toMillis(1L), 365 * 24 * 60 * 60 * 1000L);
    }

    @Test
    public void months() {
        timeUnit = TimeUnit.MONTHS;
        Assert.assertEquals(timeUnit.toYears(1L), 1 / 12L);
        Assert.assertEquals(timeUnit.toMonths(1L), 1L);
        Assert.assertEquals(timeUnit.toWeeks(1L), 30 / 7L);
        Assert.assertEquals(timeUnit.toDays(1L), 30L);
        Assert.assertEquals(timeUnit.toHours(1L), 30 * 24L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 30 * 24 * 60L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 30 * 24 * 60 * 60L);
        Assert.assertEquals(timeUnit.toMillis(1L), 30 * 24 * 60 * 60 * 1000L);
    }

    @Test
    public void weeks() {
        timeUnit = TimeUnit.WEEKS;
        Assert.assertEquals(timeUnit.toYears(1L), 7 / 365L);
        Assert.assertEquals(timeUnit.toMonths(1L), 7 / 30L);
        Assert.assertEquals(timeUnit.toWeeks(1L), 1L);
        Assert.assertEquals(timeUnit.toDays(1L), 7L);
        Assert.assertEquals(timeUnit.toHours(1L), 7 * 24L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 7 * 24 * 60L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 7 * 24 * 60 * 60L);
        Assert.assertEquals(timeUnit.toMillis(1L), 7 * 24 * 60 * 60 * 1000L);
    }

    @Test
    public void days() {
        timeUnit = TimeUnit.DAYS;
        Assert.assertEquals(timeUnit.toYears(1L), 1 / 365L);
        Assert.assertEquals(timeUnit.toMonths(1L), 1 / 30L);
        Assert.assertEquals(timeUnit.toWeeks(1L), 1 / 7L);
        Assert.assertEquals(timeUnit.toDays(1L), 1L);
        Assert.assertEquals(timeUnit.toHours(1L), 1 * 24L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 1 * 24 * 60L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 1 * 24 * 60 * 60L);
        Assert.assertEquals(timeUnit.toMillis(1L), 1 * 24 * 60 * 60 * 1000L);
    }

    @Test
    public void minutes() {
        timeUnit = TimeUnit.MINUTES;
        Assert.assertEquals(timeUnit.toYears(1L), 1 / (365 * 24 * 60L));
        Assert.assertEquals(timeUnit.toMonths(1L), 1 / (30 * 24 * 60L));
        Assert.assertEquals(timeUnit.toWeeks(1L), 1 / (7 * 24 * 60L));
        Assert.assertEquals(timeUnit.toDays(1L), 1 / (24 * 60L));
        Assert.assertEquals(timeUnit.toHours(1L), 1 / 60L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 1L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 1 * 60L);
        Assert.assertEquals(timeUnit.toMillis(1L), 1 * 60 * 1000L);
    }

    @Test
    public void seconds() {
        timeUnit = TimeUnit.SECONDS;
        Assert.assertEquals(timeUnit.toYears(1L), 1 / (365 * 24 * 60 * 60L));
        Assert.assertEquals(timeUnit.toMonths(1L), 1 / (30 * 24 * 60 * 60L));
        Assert.assertEquals(timeUnit.toWeeks(1L), 1 / (7 * 24 * 60 * 60L));
        Assert.assertEquals(timeUnit.toDays(1L), 1 / (24 * 60 * 60L));
        Assert.assertEquals(timeUnit.toHours(1L), 1 / 60 * 60L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 1 / 60L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 1L);
        Assert.assertEquals(timeUnit.toMillis(1L), 1 * 1000L);
    }

    @Test
    public void milliseconds() {
        timeUnit = TimeUnit.MILLISECONDS;
        Assert.assertEquals(timeUnit.toYears(1L), 1 / (365 * 24 * 60 * 60 * 1000L));
        Assert.assertEquals(timeUnit.toMonths(1L), 1 / (30 * 24 * 60 * 60 * 1000L));
        Assert.assertEquals(timeUnit.toWeeks(1L), 1 / (7 * 24 * 60 * 60 * 1000L));
        Assert.assertEquals(timeUnit.toDays(1L), 1 / (24 * 60 * 60 * 1000L));
        Assert.assertEquals(timeUnit.toHours(1L), 1 / 60 * 60 * 1000L);
        Assert.assertEquals(timeUnit.toMinutes(1L), 1 / 60 * 1000L);
        Assert.assertEquals(timeUnit.toSeconds(1L), 1 / 1000L);
        Assert.assertEquals(timeUnit.toMillis(1L), 1L);
    }
}