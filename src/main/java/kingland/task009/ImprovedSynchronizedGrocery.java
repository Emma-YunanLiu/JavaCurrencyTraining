package kingland.task009;

import java.util.ArrayList;
import java.util.List;


public class ImprovedSynchronizedGrocery implements Grocery {
    private final List<String> fruits = new ArrayList<>();
    private final List<String> vegetables = new ArrayList<>();
    public void addFruit(int index, String fruit) {
        synchronized (fruits) {
            fruits.add(index, fruit);
        }
    }
    public void addVegetable(int index, String vegetable) {
        synchronized (vegetables) {
            vegetables.add(index, vegetable);
        }
    }
}
