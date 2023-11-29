import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class HomeworkSystem {
    private final Semaphore semaphore;
    private final BlockingQueue<Homework> queue;
    private final AtomicReference<LocalDateTime> deadline;

    public HomeworkSystem(int maxConcurrentCheckers) {
        this.semaphore = new Semaphore(maxConcurrentCheckers);
        this.queue = new PriorityBlockingQueue<>();
        this.deadline = new AtomicReference<>();
    }

    public void setDeadlineForCurrentHomework(LocalDateTime deadline) {
        this.deadline.set(deadline);
    }

    public AtomicReference<LocalDateTime> getDeadline() {
        return deadline;
    }

    public void receiveHomework(Homework homework) {
        queue.add(homework);
    }

    public Homework getNextHomework() throws InterruptedException {
        semaphore.acquire();
        try {
            Homework homework = queue.take();
            // Проверка дедлайна
            return homework;
        } finally {
            semaphore.release();
        }
    }
}
