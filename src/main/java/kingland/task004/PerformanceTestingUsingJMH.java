package kingland.task004;


import kingland.task003.ImprovedMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class PerformanceTestingUsingJMH {

    private static final int THREAD_COUNT = 10;

    private static final int MAP_SIZE_SMALL = 10000;
    private static final int MAP_SIZE_MEDIUM = 50000;
    private static final int MAP_SIZE_LARGE = 100000;

    @Fork(value = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public static void testSynchronizedMap() throws InterruptedException {
        putValuesWithMultipleThread(Collections.synchronizedMap(new HashMap<>()));
    }

    @Fork(value = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public static void testConcurrentHashMap() throws InterruptedException {
        putValuesWithMultipleThread(new ConcurrentHashMap<>());
    }

    @Fork(value = 2)
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @BenchmarkMode(Mode.Throughput)
    public static void testImprovedMap() throws InterruptedException {
        putValuesWithMultipleThread(new ImprovedMap<>(new HashMap<>()));
    }

    public static void main(String[] args) throws IOException, RunnerException {
        org.openjdk.jmh.Main.main(args);
    }

    private static void putValuesWithMultipleThread(Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        IntStream.range(0, THREAD_COUNT).forEach(i -> executorService.submit(() -> {
            IntStream.range(0, MAP_SIZE_MEDIUM).forEach(v -> map.put(v, v));
            countDownLatch.countDown();
        }));
        countDownLatch.await();
        executorService.shutdown();
    }
}
