import java.util.ArrayList;
import java.util.HashMap;

// inventory holds all the items in the settlement
// uses an ArrayList for the items and a HashMap to keep track
// of how many of each type we have
public class Inventory {

    private ArrayList<Item> items;
    private HashMap<String, Integer> typeCounts;

    public Inventory() {
        items = new ArrayList<>();
        typeCounts = new HashMap<>();
    }

    public ArrayList<Item> getItems() { return items; }

    public HashMap<String, Integer> getTypeCounts() { return typeCounts; }

    // add an item, if we already have one with the same name just bump the quantity
    public void addItem(Item it) {
        for (Item existing : items) {
            if (existing.getName().equalsIgnoreCase(it.getName())) {
                existing.setQuantity(existing.getQuantity() + it.getQuantity());
                bumpType(it.getType(), it.getQuantity());
                return;
            }
        }
        items.add(it);
        bumpType(it.getType(), it.getQuantity());
    }

    // helper that updates the hashmap count for a type
    private void bumpType(String type, int amount) {
        if (typeCounts.containsKey(type)) {
            typeCounts.put(type, typeCounts.get(type) + amount);
        } else {
            typeCounts.put(type, amount);
        }
    }

    // remove an item by name, returns true if it was found
    public boolean removeItem(String name) {
        for (int i = 0; i < items.size(); i++) {
            Item it = items.get(i);
            if (it.getName().equalsIgnoreCase(name)) {
                bumpType(it.getType(), -it.getQuantity());
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    // sum up the total caps value of every item
    public int totalCaps() {
        int total = 0;
        for (Item it : items) {
            total += it.totalValue();
        }
        return total;
    }

    public void printAll() {
        if (items.isEmpty()) {
            System.out.println("inventory is empty");
            return;
        }
        for (Item it : items) {
            System.out.println(" - " + it);
        }
    }
}
