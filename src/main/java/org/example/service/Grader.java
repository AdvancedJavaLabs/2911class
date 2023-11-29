package org.example.service;

import org.example.model.Homework;

import java.util.Random;

class Grader implements Runnable {
    private final Homework homework;
    private final HomeworkSystem system;
    private final Random random = new Random();


    public Grader(Homework homework, HomeworkSystem system) {
        this.homework = homework;
        this.system = system;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(random.nextInt(5000));

            int grade = random.nextInt(5) + 1;
            homework.setResult("Grade: " + grade);
            system.postResult(homework);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
