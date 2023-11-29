package dev.timatifey.homesystem;

public class Student extends Thread {

    int id;
    private final VerifySystem verifySystem;

    public Student(int id, VerifySystem verifySystem) {
        this.id = id;
        this.verifySystem = verifySystem;
    }

    public void sendTaskOnVerify(HomeTask task) throws InterruptedException {
        Thread.sleep(2000);
        long currentTime = System.currentTimeMillis();
        verifySystem.addTask(id, task, currentTime);
    }

    @Override
    public void run() {
        while (true) {
            HomeTask task = verifySystem.checkTask(id);
            if (task != null) {
                System.out.printf("Student checked task. Student = %d Task = %d Score = %d%n", id, task.id, task.score);
                break;
            }
        }
    }
}
