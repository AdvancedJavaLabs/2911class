package com.vitekkor.kotoed;

public class Simulation {
    public static void main(String[] args) {
        var kotoed = new Kotoed();
        for (int i = 0; i < 10; i++) {
            var worker = new Worker(kotoed, i);
            worker.start();
        }

        for (int i = 0; i < 100; i++) {
            var student = new Student(kotoed, i);
            student.start();
        }
    }
}
