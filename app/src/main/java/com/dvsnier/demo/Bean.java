package com.dvsnier.demo;

import java.io.Serializable;

/**
 * Created by dovsnier on 2018/6/12.
 */
public class Bean implements Serializable {

    private static final long serialVersionUID = 8219150308909916068L;
    private String name;
    private String version;

    public Bean() {
    }

    public Bean(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
