package kingland.task003;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FixHashMapUnsafe {
    private static final int THREAD_COUNT = 500;
    private static final int COUNT_DOWN_LATCH_TIMEOUT = 5;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        final ImprovedMap<String, Integer> mapTest = new ImprovedMap<String, Integer>(new HashMap<>());
        //Create thread pool
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                cachedThreadPool.execute(() ->{
                    IntStream intStream = IntStream.range(0, 10);
                    intStream.forEach(index -> mapTest.put(String.valueOf(UUID.randomUUID()), index));
                });
            }
            countDownLatch.await(COUNT_DOWN_LATCH_TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Exception: await interrupted exception");
        } finally {
            System.out.println("countDownLatch: " + countDownLatch.toString());
        }
        System.out.println("Map Size: " + mapTest.size());
        cachedThreadPool.shutdown();
    }
}
