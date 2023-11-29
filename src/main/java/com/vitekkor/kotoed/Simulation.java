package com.vitekkor.kotoed;

import java.util.Arrays;

public class Simulation {
    public static void main(String[] args) throws InterruptedException {
        var kotoed = new Kotoed();
        Worker[] workers = new Worker[10];
        for (int i = 0; i < 10; i++) {
            workers[i] = new Worker(kotoed, i);
            workers[i].start();
        }

        for (int i = 0; i < 100; i++) {
            var student = new Student(kotoed, i);
            student.start();
        }

        while (Arrays.stream(workers).anyMatch(Worker::isWorking)) {
            System.out.println("waits for completion");
            Thread.sleep(1000);
        }
        for (Worker worker : workers) {
            worker.interrupt();
        }
        System.out.println("Done.");
    }
}
