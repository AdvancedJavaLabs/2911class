import lombok.AllArgsConstructor;

@AllArgsConstructor
class TaskChecker implements Runnable {
    private final TaskSystem taskSystem;
    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskSystem.take();
                task.run();
                taskSystem.saveResult(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (taskSystem.priorityBlockingQueue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (taskSystem.priorityBlockingQueue.isEmpty()) {
                    break;
                }
            }
        }
    }
}
