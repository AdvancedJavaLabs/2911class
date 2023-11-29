package dev.timatifey.homesystem;

import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class VerifySystem {

    private final int CAPACITY = 11;
    private final BlockingQueue<HomeTask> tasks = new PriorityBlockingQueue<>(CAPACITY, new TasksComparator());
    private final Map<Integer, HomeTask> verifiedTasks = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public void addTask(int studentId, HomeTask task, long sendTimestamp) {
        System.out.println("Got task " + task.id + " from student " + studentId + " at time " + sendTimestamp);
        tasks.add(task);
    }

    public void verifyTask(int inspectorId) throws InterruptedException {
        HomeTask task = tasks.take();

        Thread.sleep(random.nextInt(5000));
        task.status = HomeTask.Status.VERIFIED;
        task.inspectorId = inspectorId;
        task.score = random.nextInt(100);

        System.out.printf("Inspector verified task. Inspector = %d Task = %d Score = %d%n", inspectorId, task.id, task.score);

        verifiedTasks.put(task.studentId, task);
    }

    public HomeTask checkTask(int studentId) {
        HomeTask task = verifiedTasks.get(studentId);
        return task;
    }

    private class TasksComparator implements Comparator<HomeTask> {

        @Override
        public int compare(HomeTask o1, HomeTask o2) {
            boolean firstMorePriority = (o1.deadlineTimestamp > o2.deadlineTimestamp) || (o1.id < o2.id);
            return firstMorePriority ? 1 : 0;
        }
    }

}
