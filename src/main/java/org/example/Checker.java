package org.example;

public class Checker extends Thread{
    private final TasksRepository tr;

    public Checker(TasksRepository tr) {
        this.tr = tr;
    }

    @Override
    public void run() throws RuntimeException{
        while (true) {
            Task t;
            try {
                t = tr.getTaskToReview();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (t == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            try {
                t = checkTask(t);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                tr.addCheckedTask(t.id, t);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    private Task checkTask(Task t) throws InterruptedException {
        t.setMark(5);
        t.setReviewed(true);
        System.out.println("Reviewing task id = " + t.id);
        Thread.sleep(1000);
        return t;
    }
}
