package org.example.service;

import org.example.model.Homework;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class HomeworkSystem {
    private final BlockingQueue<Homework> homeworkQueue = new PriorityBlockingQueue<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public void submitHomework(Homework homework) throws InterruptedException {
        homeworkQueue.put(homework);
    }

    public void startGrading() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Homework homework = homeworkQueue.take();
                long currentTime = System.currentTimeMillis();
                if (currentTime < homework.getDeadline()) {
                    executor.execute(new Grader(homework, this));
                } else {
                    homework.setResult("Deadline has expired");
                    postResult(homework);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void postResult(Homework homework) {
        System.out.println("Student: " + homework.getStudent().getName() + ", result: " + homework.getResult() + ", deadline: " + homework.getDeadline());
        homework.getStudent().sendResult(homework); // O_o
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
    }
}
