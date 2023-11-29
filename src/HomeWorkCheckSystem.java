import java.util.concurrent.*;
import java.util.function.Consumer;

public class HomeWorkCheckSystem extends Thread {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final PriorityBlockingQueue<HomeWork> hwQueue = new PriorityBlockingQueue<>(10, new HomeWorkComparator());
    private final ConcurrentHashMap<Integer, Consumer<HomeWorkResult>> hwCheckCallbacks = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, HomeWorkResult> results = new ConcurrentHashMap<>();

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                HomeWork needToCheck = hwQueue.poll();
                if (needToCheck == null) {
                    System.out.println("Waiting for new homeworks");
                    sleep(1000);
                    continue;
                }
                System.out.println("Pushing hw " + needToCheck.id() + " with deadline: " + needToCheck.deadLine() + " to check" );
                processHomeWorkCheck(needToCheck);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHomeWorkForCheck(HomeWork hw, Consumer<HomeWorkResult> callback) {
        System.out.println("New homework: " + hw.id() + " with deadline: " + hw.deadLine());
        hwQueue.add(hw);
        hwCheckCallbacks.put(hw.id(), callback);
    }

    public HomeWorkResult getHomeWorkResult(Integer id) {
        return results.get(id);
    }

    private void processHomeWorkCheck(HomeWork hw) throws InterruptedException, ExecutionException {
        var future = executorService.submit(() -> {
            try {
                System.out.println("Processing hw check: " + hw.id());
                sleep(1000);
                double randNumber = Math.random();
                double score = randNumber * 100;
                HomeWorkResult hwResult = new HomeWorkResult(hw, score, "Some description...");
                results.put(hw.id(), hwResult);
                hwCheckCallbacks.get(hw.id()).accept(hwResult);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
