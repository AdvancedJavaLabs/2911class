package org.example;

import java.util.*;
import java.util.concurrent.*;

class Main {
    public static void main(String[] args) throws InterruptedException {
        var random = new Random();
        var gs = new GradingSystem();
        var students = new ArrayList<Student>(5);
        for (var i = 0; i < 5; i++) {
            students.add(new Student(gs, "student" + i));
        }

        var gsTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    gs.startGrading();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        gsTread.start();

        ExecutorService studentExec = Executors.newFixedThreadPool(4);

        while (true) {
            var nextStudent = students.get(random.nextInt(0, 5));
            studentExec.execute(nextStudent::doHW);
        }

    }
}


class Student {
    public String name = "noname";
    private final GradingSystem gs;

    private Semaphore hwDoing = new Semaphore(1);

    public Student(GradingSystem gs, String name) {
        this.gs = gs;
        this.name = name;
    }

    private static HomeWork getNewHW() {
        var random = new Random();
        return new HomeWork(random.nextInt(10000, 15000), random.nextInt(0, 150), random.nextInt(2000, 3000));
    }

    public void doHW() {
        try {
            hwDoing.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var newHW = getNewHW();
        try {
            System.out.println(this.name + " is doing hw" + newHW.hwId());
            Thread.sleep(newHW.msWorkTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        var hwResult = new HomeWorkResult(newHW, this);
        hwDoing.release();
        gs.getHWFromStudent(hwResult);
    }
}

class GradingSystem {
    private final Stack<Grader> graders = new Stack<>();
    private final PriorityBlockingQueue<HomeWorkResult> hwToGrade = new PriorityBlockingQueue<>(1000, Comparator.comparingInt(hw -> hw.hw().deadline()));

    private final ExecutorService gradingExecutor = Executors.newFixedThreadPool(2);
    public final List<HomeWorkGraded> gradedHWs = new ArrayList<>();

    public GradingSystem() {
        for (int i = 0; i < 14; i++) {
            this.graders.push(
                    new Grader(this, "grader" + 1)
            );
        }

    }

    public void getHWFromStudent(HomeWorkResult hw) {
        hwToGrade.add(hw);
    }


    public void startGrading() throws InterruptedException {
        while (true) {
            if (this.hwToGrade.isEmpty()) continue;
            Grader grader;
            try {
                grader = this.graders.pop();
            } catch (EmptyStackException e) {
                Thread.sleep(100);
                System.out.println("Пока что нет свободных проверяющих, домашек в очереди на оценку:" + this.hwToGrade.size());
                continue;
            }
            var hwToCheck = this.hwToGrade.poll();
            if (hwToCheck == null) {
                Thread.sleep(100);
                System.out.println("Nothing to check");
                continue;
            }
            gradingExecutor.execute(() -> {
                try {
                    grader.grade(hwToCheck);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            this.graders.addLast(grader);
        }
    }
}

class Grader {
    private static GradingSystem gs;
    private String name = "noname";

    public Grader(GradingSystem gs, String name) {
        this.name = name;
        this.gs = gs;
    }

    private final Semaphore gradingSemaphore = new Semaphore(1);
    private final Random random = new Random();
    final int timeToGrade = 1500;

    public void grade(HomeWorkResult hw) throws InterruptedException {
        this.gradingSemaphore.acquire();
        System.out.println(this.name + " is grading hw" + hw.hw().hwId() + " by " + hw.student().name);
        Thread.sleep(timeToGrade);
        this.gradingSemaphore.release();
        gs.gradedHWs.add(
                new HomeWorkGraded(
                        hw,
                        random.nextInt(0, 5)
                )
        );
    }

}

record HomeWork(int deadline, int hwId, int msWorkTime) {
}

record HomeWorkResult(HomeWork hw, Student student) {
}

record HomeWorkGraded(HomeWorkResult hw, int grade) {
}
