package pl.ldworski.virtualthreads;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    private final AtomicInteger counter = new AtomicInteger(0);

    public int increment() {
        return counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }

}
