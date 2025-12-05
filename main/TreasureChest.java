package main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

// Thread-safe treasure chest for sharing items among characters
public class TreasureChest {
    private final ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<>();

    public TreasureChest() {
        // default contents
        addItems(Arrays.asList(
            new Item("Prize Tokens", 10),
            new Item("Silver Ring", 25),
            new Item("Life Elixir", 15),
            new Item("Crystal Ball", 50),
            new Item("Ruby Dice", 20)
        ));
    }

    public TreasureChest(List<Item> initial) {
        addItems(initial);
    }

    public void addItems(List<Item> list) {
        if (list != null) list.forEach(items::offer);
    }

    public Item takeItem() {
        return items.poll();
    }

    public int remaining() { return items.size(); }
}
