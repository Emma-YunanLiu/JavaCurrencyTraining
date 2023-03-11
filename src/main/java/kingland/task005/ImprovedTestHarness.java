package kingland.task004;


public class ImprovedTestHarness {
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CyclicBarrier startGate = new CyclicBarrier(1);
        final CyclicBarrier endGate = new CyclicBarrier(nThreads);

        for(int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {}
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }
}
