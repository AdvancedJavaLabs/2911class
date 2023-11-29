package org.example;


import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.printf("start sim");
        TasksRepository tr = new TasksRepository();
        List<Checker> clst = new ArrayList<>();
        List<Student> slst = new ArrayList<>();

        for (int i = 0; i < 3; ++i) {
            var worker = new Checker(tr);
            worker.start();
            clst.add(worker);
        }

        for (int i = 0; i < 50; ++i) {
            System.out.println("add student");
            var worker = new Student(i, tr, i);
            worker.start();
            slst.add(worker);
        }
        System.out.println("Join");
        for (var worker : slst) {
            worker.join();
        }
        System.out.println("ended");
    }
}