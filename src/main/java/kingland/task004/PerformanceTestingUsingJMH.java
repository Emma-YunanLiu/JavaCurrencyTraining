package kingland.task004;


import kingland.task003.ImprovedMap;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Fork(value = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
public class PerformanceTestingUsingJMH {

    private static final int THREAD_COUNT = 10;

    private static final int MAP_SIZE_SMALL = 10000;
    private static final int MAP_SIZE_MEDIUM = 50000;
    private static final int MAP_SIZE_LARGE = 100000;

    @Benchmark
    public static void testSynchronizedMap() throws InterruptedException {
        putValuesWithMultipleThread(Collections.synchronizedMap(new HashMap<>()));
    }

    @Benchmark
    public static void testConcurrentHashMap() throws InterruptedException {
        putValuesWithMultipleThread(new ConcurrentHashMap<>());
    }

    @Benchmark
    public static void testImprovedMap() throws InterruptedException {
        putValuesWithMultipleThread(new ImprovedMap<>(new HashMap<>()));
    }

    public static void main(String[] args) throws IOException, RunnerException {
//        org.openjdk.jmh.Main.main(args);
        Options opts = new OptionsBuilder()
                .include(PerformanceTestingUsingJMH.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
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
