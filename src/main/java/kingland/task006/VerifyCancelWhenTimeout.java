package kingland.task006;

import kingland.task005.ImprovedTestHarness;

import java.util.concurrent.TimeUnit;

public class VerifyCancelWhenTimeout {
    private static final int TIMEOUT_THRESHOLD = 4;
    private static final int THREAD_COUNT = 2;
    private static final int MIN_EXECUTION_TIME = 6;
    public static void main(String[] args) {
        // The task will be cancelled when the execution time exceeds the period we provided.
        ImprovedTestHarness improvedTestHarness = new ImprovedTestHarness();
        improvedTestHarness.timeTasks(THREAD_COUNT, TIMEOUT_THRESHOLD, ()->{
            try {
                TimeUnit.SECONDS.sleep(MIN_EXECUTION_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}


