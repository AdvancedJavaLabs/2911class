import java.time.LocalDateTime;
import java.util.Random;

public class HomeworkChecker implements Runnable {
    private final HomeworkSystem system;
    private final Random random;

    public HomeworkChecker(HomeworkSystem system) {
        this.system = system;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Homework homework = system.getNextHomework();
                LocalDateTime deadline = system.getDeadline().get();

                int grade = getGradeForHomework(homework, deadline);
                System.out.printf("%s checker result for homework of student %s: %s%n",
                        this, homework.authorName(), grade);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getGradeForHomework(Homework homework, LocalDateTime deadline) {
        if (homework.sendDateTime().isAfter(deadline)) {
            return 0;
        }

        return random.nextInt(6);
    }
}
