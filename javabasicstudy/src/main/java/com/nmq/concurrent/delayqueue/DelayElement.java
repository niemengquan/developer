package com.nmq.concurrent.delayqueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by niemengquan on 2018/1/10.
 */
public class DelayElement implements Delayed {
    private long expired;
    private long delay;
    private String name;

    public DelayElement(long delay, String name) {
        this.delay = delay;
        this.name = name;
        expired=delay+System.currentTimeMillis();
    }

    public long getDelay(TimeUnit unit) {
        return expired-System.currentTimeMillis();
    }

    public int compareTo(Delayed o) {
        DelayElement cached=(DelayElement) o;
        return cached.getExpired()>this.expired?1:-1;
    }

    public long getExpired() {
        return expired;
    }
    @Override
    public String toString() {
        return "DelayedElement [delay=" + delay + ", name=" + name + "]";
    }
}
