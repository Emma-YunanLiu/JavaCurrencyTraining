package kingland.task008;

public class SimpleOrderingDeadlock {
    private final Object left = new Object();
    private final Object right = new Object();

    public Object leftRight() throws InterruptedException {
        synchronized (left) {
//            Thread.sleep(5000);
            System.out.println("leftRight - left locked.");
            synchronized (right) {
                System.out.println("leftRight - right locked.");
                return right;
            }
        }
    }

    public Object rightLeft() throws InterruptedException {
        synchronized (right) {
//            Thread.sleep(5000);
            System.out.println("rightLeft - right locked.");
            synchronized (left) {
                System.out.println("rightLeft - left locked.");
                return left;
            }
        }
    }

}
