
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckSystem {
    private static final int NUMBER_OF_TEACHERS = 2;
    private static final int NUMBER_OF_STUDENTS = 5;
    private final PriorityBlockingQueue<Hometask> tasks;
    private final ConcurrentMap<Integer, Result> results;


    public CheckSystem() {
        Comparator<Hometask> comparator = Comparator.comparing(Hometask::getDate);
        tasks = new PriorityBlockingQueue<>(1, comparator);
        results = new ConcurrentHashMap<>();
    }


    public static void main(String[] args) {
        CheckSystem cs = new CheckSystem();

        Teacher[] teachers = new Teacher[NUMBER_OF_TEACHERS];
        Student[] students = new Student[NUMBER_OF_STUDENTS];


        for (int i = 0; i < NUMBER_OF_STUDENTS; i++) {
            students[i] = new Student(i, cs);
            new Thread(students[i]).start();
        }

        for (int i = 0; i < NUMBER_OF_TEACHERS; i++) {
            teachers[i] = new Teacher(i, cs);
            new Thread(teachers[i]).start();
        }

    }

    public PriorityBlockingQueue<Hometask> getTasks() {
        return tasks;
    }

    public ConcurrentMap<Integer, Result> getResults() {
        return results;
    }

    public Result getResult(Student student) {
        return results.get(student);
    }
}

record Result(Hometask task, int mark) {
}

class Student extends Thread {
    private final int studentId;
    private final CheckSystem cs;

    public Student(int id, CheckSystem cs) {
        this.studentId = id;
        this.cs = cs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                doHometask();
                relax();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void doHometask() throws InterruptedException {
        System.out.println("Student" + studentId + " doing hometask");
        Thread.sleep(2000);
        Hometask hometask = new Hometask(new Date(), studentId);
        cs.getTasks().put(hometask);
    }

    private void relax() throws InterruptedException {
        System.out.println("Student" + studentId + " relaxing");
        Thread.sleep(1000);
    }

}

class Teacher extends Thread {
    private final int teacherId;
    private final CheckSystem cs;

    public Teacher(int id, CheckSystem cs) {
        this.teacherId = id;
        this.cs = cs;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for tasks to check");
                Hometask task = cs.getTasks().take();
                check(task);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    public void check(Hometask task) throws InterruptedException {
        System.out.println("Teacher" + teacherId + " checking task " + "Hometask" + task.getHometaskId());
        Thread.sleep(3000);

        Random rand = new Random();
        Result result = new Result(task, rand.nextInt(100));
        cs.getResults().put(task.getStudentId(), result);
    }
}

class Hometask {
    private static AtomicInteger count = new AtomicInteger(0);
    private final AtomicInteger hometaskId = new AtomicInteger(count.getAndIncrement());

    private Date date;
    private final int studentId;

    public Hometask(Date date, int studentId) {
        this.date = date;
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public int getStudentId() {
        return studentId;
    }

    public static AtomicInteger getCount() {
        return count;
    }

    public AtomicInteger getHometaskId() {
        return hometaskId;
    }
}
