package org.example;

public class Student extends Thread{
    private final int id;
    private final int taskAmount;
    private final TasksRepository tr;

    public Student(int id, TasksRepository tr, int taskAmount) {
        this.id = id;
        this.tr = tr;
        this.taskAmount = taskAmount;
    }

    @Override
    public void run() {
        for (int i = 0; i < taskAmount; ++i) {
            System.out.println("add task id = " + id);
            Task t = new Task(id);
            try {
                tr.addTaskToReview(t);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
