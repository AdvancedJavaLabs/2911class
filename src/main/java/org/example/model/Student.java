package org.example.model;

import java.util.concurrent.CopyOnWriteArrayList;

public class Student {
    private final String name;
    private final CopyOnWriteArrayList<Homework> homeworks;

    public Student(String name) {
        this.name = name;
        this.homeworks = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void sendResult(Homework homework) {
        homeworks.removeIf(hw -> hw.getContent().equals(homework.getContent()));
        homeworks.add(homework);
    }
}
