package kingland.task002;

import lombok.Getter;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VerifyEscape {
    public static void main(String[] args) {
//        UnsafeStates unsafeStates = new UnsafeStates();
//        System.out.println("Original Value: " + Arrays.toString(unsafeStates.getStates()));
//        ExecutorService executor =Executors.newSingleThreadExecutor();
//        executor.submit(() -> {
//            unsafeStates.getStates()[0] = "AK_UPD";
//        });
//
//        executor.shutdown();
//        try {
//            executor.awaitTermination( 5l, TimeUnit.SECONDS );
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Current Value: " + Arrays.toString(unsafeStates.getStates()));
    }
}

@Getter
class UnsafeStates {
    private String[] states = new String[] {
        "AK", "AL"
    };
}
