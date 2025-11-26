package main;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TreasureChest {
    private final ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<>();

    public TreasureChest() {
        // default contents
        addItems(Arrays.asList(
            new Item("Gold Coin", 10),
            new Item("Silver Ring", 25),
            new Item("Potion", 15),
            new Item("Ancient Rune", 50),
            new Item("Dagger", 20)
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
