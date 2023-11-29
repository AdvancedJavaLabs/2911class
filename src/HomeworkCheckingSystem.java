import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;

public class HomeworkCheckingSystem {
    private final Semaphore semaphore;
    private final PriorityBlockingQueue<Homework> homeworkQueue = new PriorityBlockingQueue<>();

    public HomeworkCheckingSystem(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public void receiveHomework(Homework homework) {
        homeworkQueue.add(homework);
        processHomework();
    }

    private void processHomework() {
        while (!homeworkQueue.isEmpty()) {
            try {
                Homework homework = homeworkQueue.take();
                Checker checker = new Checker(homework, semaphore);
                checker.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
