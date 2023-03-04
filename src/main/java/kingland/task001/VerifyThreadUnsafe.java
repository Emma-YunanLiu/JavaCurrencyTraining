package kingland.task001;

import lombok.Getter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VerifyThreadUnsafe {
    private static final int THREAD_COUNT = 500;
    private static final int COUNT_DOWN_LATCH_TIMEOUT = 5;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        UnsafeSequence unsafeSequence = new UnsafeSequence();
        //Create thread pool
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                cachedThreadPool.execute(() ->{
                    unsafeSequence.getNext();
                    countDownLatch.countDown();
                });
            }
            countDownLatch.await(COUNT_DOWN_LATCH_TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Exception: await interrupted exception");
        } finally {
            System.out.println("countDownLatch: " + countDownLatch.toString());
        }
//        System.out.println(unsafeSequence.getValue());
        cachedThreadPool.shutdown();
    }
}

@Getter
class UnsafeSequence {
    private int value;

    public int getNext() {
        return value++;
    }
}

