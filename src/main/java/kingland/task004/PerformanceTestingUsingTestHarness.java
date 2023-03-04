package kingland.task004;

import kingland.task003.ImprovedMap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PerformanceTestingUsingTestHarness {

    private static final int THREAD_COUNT_LOW = 5;
    private static final int THREAD_COUNT_MEDIUM = 50;
    private static final int THREAD_COUNT_HIGH = 500;
    private static final List<Integer> THREAD_COUNT_LIST = List.of(THREAD_COUNT_LOW, THREAD_COUNT_MEDIUM, THREAD_COUNT_HIGH);
    private static final int MAP_SIZE = 10000;

    public static void main(String[] args) {
        Map<String, Long> resultMap = new HashMap<>();
        Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ImprovedMap improvedMap = new ImprovedMap(new HashMap<>());
        Map<String, Map> measuredMaps = Map.of("synchronizedMap", synchronizedMap, "ConcurrentHashMap", concurrentHashMap, "ImprovedMap", improvedMap);
        List<Map> allMaps = List.of(resultMap, synchronizedMap, concurrentHashMap, improvedMap);
        THREAD_COUNT_LIST.stream().forEach(thread_count -> {
            measuredMaps.forEach((key, value) ->
                    resultMap.put(key, measureMapPerformance(synchronizedMap, thread_count, MAP_SIZE))
            );
            resultMap.forEach((key, value) ->
                    System.out.printf("MAP_TYPE:[%s], THREAD_COUNT:[%d], MAP_SIZE: [%d], EXECUTION_TIME:[%d]%n", key, thread_count, MAP_SIZE, value)
            );
            Optional<Map.Entry<String, Long>> maxEntry = resultMap.entrySet().stream().min(Map.Entry.comparingByValue());
            System.out.printf("Testing Result: When THREAD_COUNT is [%d], [%s] has the best performance, EXECUTION_TIME:[%d]%n", thread_count, maxEntry.get().getKey(), maxEntry.get().getValue());
            clearMaps(allMaps);
        });
    }
    public static long measureMapPerformance(Map<Integer, Integer> map, int threadCount, int mapSize) {
        TestHarness testHarness = new TestHarness();
        // Anonymous Runnable can be replaced with Lambda.
        try {
            return testHarness.timeTasks(threadCount, () -> {
                for (int i = 0; i < mapSize; i++) {
                    map.put(i, i);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void clearMaps(List<Map> maps) {
        maps.stream().forEach(map -> map.clear());
    }
}
