package org.example;

import org.example.model.Homework;
import org.example.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    private static final Random random = new Random();

    private Utils() {
        // utils class
    }

    public static List<Homework> generateAndSubmitHomework(int numberOfHomeworks, long minDeadline, long maxDeadline) {
        List<Homework> homeworkList = new ArrayList<>();
        for (int i = 0; i < numberOfHomeworks; i++) {
            String studentName = "Student " + (i + 1);
            String content = "Homework " + (i + 1);
            long deadline = System.currentTimeMillis() + random.nextLong(maxDeadline - minDeadline) + minDeadline;

            homeworkList.add(
                    new Homework(
                            new Student(studentName),
                            content,
                            deadline
                    )
            );
        }
        return homeworkList;
    }
}
