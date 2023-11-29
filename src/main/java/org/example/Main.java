package org.example;

import org.example.model.Homework;
import org.example.service.HomeworkSystem;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        HomeworkSystem homeworkSystem = new HomeworkSystem();

        new Thread(homeworkSystem::startGrading).start();

        List<Homework> homeworkList = Utils.generateAndSubmitHomework(
                100,
                System.currentTimeMillis(),
                System.currentTimeMillis() + 1000000
        );

        homeworkList.forEach(homework -> {
            try {
                homeworkSystem.submitHomework(homework);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread.sleep(500000); // можно ещё сделать либо while(working), но зачем))
        homeworkSystem.shutdown();
    }
}
