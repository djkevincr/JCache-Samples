package com.hazelcast.examples.application;

import javax.cache.configuration.Factory;
import java.io.Serializable;

/**
 * Created by djkevincr on 3/22/16.
 */
public class factory <T> implements Factory<T>, Serializable {
    public static final long serialVersionUID = 201305101626L;
    private String className;

    public factory(Class<T> clazz) {
        this.className = clazz.getName();
    }

    public factory(String className) {
        this.className = className;
    }

    public T create() {
        try {
            ClassLoader e = Thread.currentThread().getContextClassLoader();
            Class clazz = e.loadClass(this.className);
            return (T)clazz.newInstance();
        } catch (Exception var3) {
            throw new RuntimeException("Failed to create an instance of " + this.className, var3);
        }
    }

    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(other != null && this.getClass() == other.getClass()) {

            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.className.hashCode();
    }
}