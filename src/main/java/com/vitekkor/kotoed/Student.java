package com.vitekkor.kotoed;

import java.time.Instant;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Student extends Thread {

    private final Instant startInclusive = Instant.now().minusSeconds(10000L);
    private final Instant endExclusive = startInclusive.plusSeconds(10000L * 2L);

    private final Kotoed kotoed;
    private final int id;

    public Student(Kotoed kotoed, int id) {
        this.kotoed = kotoed;
        this.id = id;
    }

    @Override
    public void run() {
        var newHomeWork = new HomeWork(new Random().nextInt(), randomDate(startInclusive, endExclusive));
        kotoed.submitHomeWork(id, newHomeWork);
        try {
            kotoed.getHomeWorkResult(id, newHomeWork.name());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static Instant randomDate(Instant startInclusive, Instant endExclusive) {
        long startSeconds = startInclusive.getEpochSecond();
        long endSeconds = endExclusive.getEpochSecond();
        long random = ThreadLocalRandom
                .current()
                .nextLong(startSeconds, endSeconds);

        return Instant.ofEpochSecond(random);
    }
}
