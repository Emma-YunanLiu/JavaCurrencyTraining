package kingland.task009;

import kingland.task004.PerformanceTestingUsingJMH;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.*;
import java.util.stream.IntStream;

@Fork(value = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
@BenchmarkMode(Mode.Throughput)
public class PerformanceTesting {
    private static final int THREAD_COUNT = 10;
    private static final int LIST_SIZE = 1000;

    @Benchmark
    public static void testSynchronizedGrocery() throws InterruptedException {
        putValuesWithMultipleThread(new SynchronizedGrocery());
    }

    @Benchmark
    public static void testImprovedSynchronizedGrocery() throws InterruptedException {
        putValuesWithMultipleThread(new ImprovedSynchronizedGrocery());
    }

    public static void main(String[] args) throws IOException, RunnerException {
//        org.openjdk.jmh.Main.main(args);
        Options opts = new OptionsBuilder()
                .include(PerformanceTesting.class.getSimpleName())
                .exclude(PerformanceTestingUsingJMH.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opts).run();
    }

    private static void putValuesWithMultipleThread(Grocery grocery) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        IntStream.range(0, THREAD_COUNT).forEach(i -> executorService.submit(() -> {
            IntStream.range(0, LIST_SIZE).forEach(v -> grocery.addFruit(v, String.valueOf(v)));
            IntStream.range(0, LIST_SIZE).forEach(v -> grocery.addVegetable(v, String.valueOf(v)));
            countDownLatch.countDown();
        }));
        countDownLatch.await();
        executorService.shutdown();
    }
}
