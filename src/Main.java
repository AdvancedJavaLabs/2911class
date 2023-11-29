import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        HomeworkSystem system = new HomeworkSystem(5);
        system.setDeadlineForCurrentHomework(LocalDateTime.now().plusDays(7));
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            executor.execute(new HomeworkChecker(system));
        }

        Student student1 = new Student("Alex");
        student1.submitHomework(system, new Homework("Math Homework", student1.name(), LocalDateTime.now()));
    }
}