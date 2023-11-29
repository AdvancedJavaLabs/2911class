package com.vitekkor.kotoed;

import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Kotoed {
    private final PriorityBlockingQueue<HomeWork> queue
            = new PriorityBlockingQueue<>(10, Comparator.comparing(HomeWork::deadline));

    private final ConcurrentHashMap<Integer, HomeWorkResult> results = new ConcurrentHashMap<>();

    public void checkHomeWork(int id) throws InterruptedException {
        while (true) {
            System.out.println("Worker #" + id + " waits for new homework");
            var homeWork = queue.poll();
            if (homeWork == null) {
                continue;
            }

            System.out.println("Worker #" + id + " start checking homework: " + homeWork.name() + "due to " + homeWork.deadline());
            Thread.sleep(2000);
            System.out.println("Worker #" + id + " finish checking homework: " + homeWork.name());
            var mark = ThreadLocalRandom.current().nextInt(0, 11);
            results.put(homeWork.name(), new HomeWorkResult(mark, homeWork));
        }
    }

    public void submitHomeWork(int id, HomeWork homeWork) {
        System.out.println("Student #" + id + " submit new homework: " + homeWork.name());
        queue.add(homeWork);
    }

    public HomeWorkResult getHomeWorkResult(int id, int name) throws InterruptedException {
        while (results.get(name) == null) {
            System.out.println("Student #" + id + " waits for homework result");
            Thread.sleep(1000);
        }
        var result = results.get(name);
        System.out.println("Student #" + id + " received the result: " + result);
        return result;
    }
}
