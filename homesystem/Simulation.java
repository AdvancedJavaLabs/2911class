package dev.timatifey.homesystem;

import java.util.Random;

public class Simulation {

    public static void main(String[] args) throws InterruptedException {
        VerifySystem verifySystem = new VerifySystem();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Inspector inspector = new Inspector(i, verifySystem);
            inspector.start();
        }

        for (int i = 0; i < 100; i++) {
            Student student = new Student(i, verifySystem);

            long currentTime = System.currentTimeMillis();
            long taskTime = random.nextLong(1000000);
            HomeTask task = new HomeTask(i, student.id, currentTime + taskTime);

            student.sendTaskOnVerify(task);
            student.start();
        }

    }
}
