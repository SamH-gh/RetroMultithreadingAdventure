package main;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameStats {
    private final List<String> events = new CopyOnWriteArrayList<>();
    private final List<String> itemEvents = new CopyOnWriteArrayList<>();

    public void recordEvent(String event) {
        events.add(event);
    }

    public void recordItemCollected(String playerName, Item item) {
        itemEvents.add(playerName + ":" + item.getName());
    }

    public void printSummary() {
        System.out.println("\n--- Round Summary ---");
        System.out.println("Events: " + events.size());
        events.forEach(e -> System.out.println(" - " + e));

        System.out.println("\nItems collected:");
        Map<String, Long> counts = itemEvents.stream()
            .map(s -> s.split(":"))
            .filter(arr -> arr.length == 2)
            .map(arr -> arr[0])
            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        counts.forEach((k,v) -> System.out.println(" - " + k + ": " + v));
    }
}
