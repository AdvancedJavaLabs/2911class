package org.example;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.Semaphore;

public class TasksRepository {
    private final Queue<Task> tasks = new LinkedList<>();
    private final ConcurrentMap<Integer, List<Task>> checkedTasks = new ConcurrentSkipListMap<>();
    private final Semaphore tasksSemaphore = new Semaphore(1);
    private final Semaphore checkedTaskSemaphore = new Semaphore(1);

    public TasksRepository () {

    }

    public Task getTaskToReview() throws InterruptedException {
        tasksSemaphore.acquire();
        Task resultTask = tasks.poll();
        tasksSemaphore.release();
        return resultTask;
    }
    public void addTaskToReview(Task t) throws InterruptedException {
        tasksSemaphore.acquire();
        tasks.offer(t);
        tasksSemaphore.release();
    }

    public List<Task> getCheckedTasks(int id) {
        return checkedTasks.get(id);
    }
    public void addCheckedTask(int id, Task t) throws InterruptedException {
        checkedTaskSemaphore.acquire();
        if (checkedTasks.containsKey(id)) {
            checkedTasks.get(id).add(t);
        } else {
            var lst = new ArrayList<Task>();
            lst.add(t);
            checkedTasks.put(id, lst);
        }
        checkedTaskSemaphore.release();
    }
}
