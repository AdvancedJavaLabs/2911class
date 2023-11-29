package org.example;

public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public void submitHomework(Homework hw, CheckingSystem system) {
        system.addToQueue(hw, this);
    }

    public String getName() {
        return name;
    }
}
