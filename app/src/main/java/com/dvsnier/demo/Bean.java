package com.dvsnier.demo;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by dovsnier on 2018/6/12.
 */
public class Bean implements Serializable {

    private static final long serialVersionUID = 8219150308909916068L;
    @NonNull
    private String name;
    @NonNull
    private String version;

    public Bean() {
    }

    public Bean(@NonNull String name, @NonNull String version) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bean bean = (Bean) o;

        if (!name.equals(bean.name)) return false;
        return version.equals(bean.version);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + version.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
