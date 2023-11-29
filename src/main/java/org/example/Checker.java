package org.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Checker implements Runnable {
    private BlockingQueue<Homework> queue;
    public ConcurrentHashMap<Homework, String> results;

    public Checker(BlockingQueue<Homework> queue, ConcurrentHashMap<Homework, String> results) {
        this.queue = queue;
        this.results = results;
    }

    @Override
    public void run() {
        while (true) {
            Homework hw = queue.poll();
            if (hw == null) {
                break;
            }
            try {
                // 1 секунда на проверку
                Thread.sleep(1000);
                System.out.println("Lab " + hw.getTask() + " has been checked for student " + hw.getStudentName());
                if (hw.getDeadline() <= 1000) {
                    results.put(hw, "Lab " + hw.getTask() + " delivered");
                } else {
                    results.put(hw, "Lab " + hw.getTask() + " was not delivered on time");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
