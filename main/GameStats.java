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
        // Use streams and method reference to print events
        events.stream()
              .map(e -> " - " + e)
              .forEach(System.out::println);

        System.out.println("\nItems collected:");
        Map<String, Long> counts = itemEvents.stream()
            .map(s -> s.split(":"))
            .filter(arr -> arr.length == 2)
            .map(arr -> arr[0])
            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

        // Print counts using stream mapping for a consistent functional style
        counts.entrySet().stream()
              .map(en -> " - " + en.getKey() + ": " + en.getValue())
              .forEach(System.out::println);
    }
}
