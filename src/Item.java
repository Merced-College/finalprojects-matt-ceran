// item in the inventory, has a name, type, value and quantity
public class Item {

    // type constants so we dont have random strings everywhere
    public static final String WEAPON = "Weapon";
    public static final String FOOD = "Food";
    public static final String MEDICINE = "Medicine";
    public static final String JUNK = "Junk";

    private String name;
    private String type;
    private int value;
    private int quantity;

    public Item(String name, String type, int value, int quantity) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getValue() { return value; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int q) { this.quantity = q; }

    // total caps for the whole stack of this item
    public int totalValue() {
        return value * quantity;
    }

    public String toString() {
        return name + " (" + type + ") x" + quantity + " - " + value + " caps each";
    }
}
