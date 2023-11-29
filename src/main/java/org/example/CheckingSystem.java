package org.example;

import java.util.concurrent.*;

public class CheckingSystem {
    private BlockingQueue<Homework> queue = new LinkedBlockingQueue<>();
    private ConcurrentHashMap<Homework, String> results = new ConcurrentHashMap<>();

    public void addToQueue(Homework hw, Student student) {
        queue.add(hw);
        System.out.println(student.getName() + " sending " + hw.getTask() + " for checking.");
    }

    public void startChecking() {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executor.execute(new Checker(queue, results));
        }
        executor.shutdown();
    }

    public String getResult(Homework hw) {
        return results.get(hw);
    }

    public ConcurrentHashMap<Homework, String> getAllResults() {
        return results;
    }
}
