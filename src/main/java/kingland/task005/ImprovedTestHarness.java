package kingland.task005;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ImprovedTestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CyclicBarrier startGate = new CyclicBarrier(nThreads + 1);
        final CyclicBarrier endGate = new CyclicBarrier(nThreads + 1);
        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for(int i = 0; i < nThreads; i++) {
            executorService.execute(new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.await();
                    }
                } catch (BrokenBarrierException | InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }));

        }
        try {
            startGate.await();
            long start = System.nanoTime();
            endGate.await();
            long end = System.nanoTime();
            return end-start;
        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    public long timeTasks(int nThreads, int timeoutInSeconds, final Runnable task) {
        final CyclicBarrier startGate = new CyclicBarrier(nThreads + 1);
        final CyclicBarrier endGate = new CyclicBarrier(nThreads + 1);
        final ExecutorService executorService = Executors.newFixedThreadPool(nThreads);

        for(int i = 0; i < nThreads; i++) {
            executorService.execute(new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        runTaskWithinPeriod(task, timeoutInSeconds);
                    } finally {
                        endGate.await();
                    }
                } catch (BrokenBarrierException | InterruptedException ignored) {
                    Thread.currentThread().interrupt();
                }
            }));

        }
        try {
            startGate.await();
            long start = System.nanoTime();
            endGate.await();
            long end = System.nanoTime();
            return end-start;
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    private void runTaskWithinPeriod(final Runnable task, int timeoutInSeconds) {

        final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        Future<?> submittedJob = singleThreadExecutor.submit(task);
        try {
            submittedJob.get(timeoutInSeconds, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            System.out.println("Task canceled due to timeout. Thread: " + Thread.currentThread().getName());
        } finally {
            submittedJob.cancel(true);
            singleThreadExecutor.shutdown();
        }
    }
}