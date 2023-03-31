package kingland.task007;

import kingland.task005.ImprovedTestHarness;

import java.util.concurrent.TimeUnit;

public class VerifyPerformanceOfPreStartAllCoreThreads {
    private static final int TIMEOUT_THRESHOLD = 10;
    private static final int THREAD_COUNT = 10;

    public static void main(String[] args) {
        Runnable runnableLastFiveSeconds = () -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        System.out.println("Task time when start all core threads in advance: " + improvedTestHarness.timeTasks(VerifyPerformanceOfPreStartAllCoreThreads.THREAD_COUNT, VerifyPerformanceOfPreStartAllCoreThreads.TIMEOUT_THRESHOLD, runnableLastFiveSeconds, true, TimeUnit.MILLISECONDS));
        System.out.println("Task time when not start all core threads in advance: " + improvedTestHarness.timeTasks(VerifyPerformanceOfPreStartAllCoreThreads.THREAD_COUNT, VerifyPerformanceOfPreStartAllCoreThreads.TIMEOUT_THRESHOLD, runnableLastFiveSeconds, false, TimeUnit.MILLISECONDS));
    }
}
