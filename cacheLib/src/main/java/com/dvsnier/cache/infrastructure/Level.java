package com.dvsnier.cache.infrastructure;

/**
 * Level - the log mode in debug running environment
 * Created by dovsnier on 2019-07-10.
 */
public enum Level {
    ERROR(0), WARN(1), INFO(2), DEBUG(3), VERBOSE(4);

    private int value;

    Level(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return super.toString();
    }}
