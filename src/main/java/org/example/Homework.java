package org.example;

import java.util.Objects;

public class Homework {

    private int id;

    private String student;
    private String task;
    private int deadline;

    public Homework(int id, String task, int deadline, String student) {
        this.id = id;
        this.task = task;
        this.deadline = deadline;
        this.student = student;
    }

    public String getStudentName() {
        return student;
    }

    public String getTask() {
        return task;
    }

    public int getDeadline() {
        return deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Homework homework = (Homework) o;
        return id == homework.id &&
                deadline == homework.deadline &&
                Objects.equals(student, homework.student) &&
                Objects.equals(task, homework.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, task,deadline);
    }
}
