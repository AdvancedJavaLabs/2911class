import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskSystem {
    public final PriorityBlockingQueue<Task> priorityBlockingQueue = new PriorityBlockingQueue<>();
    public final ConcurrentHashMap<Integer, Integer> results = new ConcurrentHashMap<>();

    public void add(Task task) {
        priorityBlockingQueue.add(task);
    }

    public Task take() throws InterruptedException {
        return priorityBlockingQueue.take();
    }

    public void saveResult(Task task) {
        results.put(task.getId(), task.getPoints());
    }
}
