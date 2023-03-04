package kingland.task002;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VerifyEscapeFix {
    public static void main(String[] args) {
        SafeStates safeStates = new SafeStates(new String[]{"AK", "AL"});
        System.out.println("Original Value: " + Arrays.toString(safeStates.getStates()));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            safeStates.getStates()[0] = "AK_UPD";
        });

        executor.shutdown();
        try {
            executor.awaitTermination( 5l, TimeUnit.SECONDS );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Current Value: " + Arrays.toString(safeStates.getStates()));
    }
}

class SafeStates {
    // Refer to 3.12
    private final String[] states;

    public SafeStates(String[] states) {
        this.states = Arrays.copyOf(states, states.length);
    }

    public String[] getStates() {
        return Arrays.copyOf(states, states.length);
    }
}
