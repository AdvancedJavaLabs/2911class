import java.util.concurrent.Semaphore;

public class Checker extends Thread {
    private final Homework homework;
    private final Semaphore semaphore;

    public Checker(Homework homework, Semaphore semaphore) {
        this.homework = homework;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Checking homework '" + homework.getName() + "' from ID: " + homework.getId());

            long currentTime = System.currentTimeMillis();
            if (currentTime > homework.getDeadline()) {
                System.out.println("Homework '" + homework.getName() + "' ID " + homework.getId() + " not completed by deadline. Grade: 2");
            } else {
                Thread.sleep(1000);

                int grade = (int) (Math.random() * 4 + 2);
                System.out.println("Homework grade '" + homework.getName() + "' to ID " + homework.getId() + ": " + grade);
            }

            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
