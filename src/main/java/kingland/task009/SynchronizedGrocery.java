package kingland.task009;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedGrocery implements Grocery {
    private final List<String> fruits = new ArrayList<>();
    private final List<String> vegetables = new ArrayList<>();
    public synchronized void addFruit(int index, String fruit) {
        fruits.add(index, fruit);
    }
    public synchronized void addVegetable(int index, String vegetable) {
        vegetables.add(index, vegetable);
    }
}
