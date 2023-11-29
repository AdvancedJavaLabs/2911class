import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class Student extends Thread {

    private final Integer id;

    private final HomeWorkCheckSystem checkSystem;

    public Student(HomeWorkCheckSystem checkSystem, Integer id) {
        this.checkSystem = checkSystem;
        this.id = id;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                sleep(2000);
                if (Math.random() < 0.25) {
                    sleep(3000);
                    continue;
                }
                HomeWork hw = processHomeWork();
                System.out.println("Student " + id + " sending homework: " + hw.id() + " with deadline: " + hw.deadLine());
                Consumer<HomeWorkResult> callback = homeWorkResult -> System.out.println("Homework " + hw.id() + " result, with score: " + homeWorkResult.score());
                checkSystem.sendHomeWorkForCheck(hw, callback);
            }
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HomeWorkResult getResult(HomeWork hw) {
        HomeWorkResult hwResult = checkSystem.getHomeWorkResult(hw.id());
        System.out.println("Received result of homework " + hw.id() + " with score: " + hwResult.score());
        return hwResult;
    }

    private HomeWork processHomeWork() {
        Random ran = new Random();
        int homeworkId = ran.nextInt();

        LocalDate startDate = LocalDate.of(2023, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 5, 31);
        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        long randomNumberOfDays = ThreadLocalRandom.current().nextLong(daysBetween + 1);
        LocalDate deadline = startDate.plusDays(randomNumberOfDays);

        return new HomeWork("SomeData", homeworkId, deadline);
    }
}
