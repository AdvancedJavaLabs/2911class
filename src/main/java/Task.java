import lombok.Data;

import java.time.LocalDateTime;
import java.util.Random;

@Data
public class Task implements Comparable<Task>, Runnable {
    private int id;
    private LocalDateTime date;
    private String name;
    private int points = 0;

    public Task(int i, LocalDateTime localDateTime, String s) {
        this.id = i;
        this.date = localDateTime;
        this.name = s;
    }

    @Override
    public int compareTo(Task o) {
        return this.date.compareTo(o.date);
    }

    @Override
    public void run() {
        System.out.println("Task " + this.id + " is running");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        this.points = random.nextInt(100);
    }
}
