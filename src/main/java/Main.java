import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        TaskSystem taskSystem = new TaskSystem();

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            executorService.submit(new TaskChecker(taskSystem));
        }

        Random random = new Random();
        for (int i = 1; i <= 50; i++) {
            Task task = new Task(i, LocalDateTime.now().plusMinutes(random.nextInt() % 100), "Task " + i);
            taskSystem.add(task);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }

        System.out.println("All tasks are finished");
        System.out.println(taskSystem.results);
    }
}
