package kingland.task002;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VerifyEscapeAddFinal {
    public static void main(String[] args) {
//        UnsafeStatesWithFinal unsafeStatesWithFinal = new UnsafeStatesWithFinal();
//        System.out.println("Original Value: " + Arrays.toString(unsafeStatesWithFinal.getStates()));
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.submit(() -> {
//            unsafeStatesWithFinal.getStates()[0] = "AK_UPD";
//        });
//
//        executor.shutdown();
//        try {
//            executor.awaitTermination( 5l, TimeUnit.SECONDS );
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Current Value: " + Arrays.toString(unsafeStatesWithFinal.getStates()));
    }
}

@Getter
class UnsafeStatesWithFinal {
    private final String[] states = new String[] {
            "AK", "AL"
    };
}
