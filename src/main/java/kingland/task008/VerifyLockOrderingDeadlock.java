package kingland.task008;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class VerifyLockOrderingDeadlock {
    public static void main(String[] args) throws InterruptedException {
        SimpleOrderingDeadlock simpleOrderingDeadlock = new SimpleOrderingDeadlock();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Object>> tasks = new ArrayList<>(2);
        tasks.add(simpleOrderingDeadlock::leftRight);
        tasks.add(simpleOrderingDeadlock::rightLeft);
        List<Future<Object>> taskFutures = executorService.invokeAll(tasks);

        taskFutures.forEach(res -> {
            try {
                System.out.println("Result - " + res.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
        executorService.shutdown();
    }
}
