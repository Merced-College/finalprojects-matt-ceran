import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// main entry point with the menu loop
// connects all the other classes together
public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static Settlement settlement;
    private static Inventory inventory;
    private static EventLog log;
    private static final String SAVE_FILE = "wasteland_save.txt";

    public static void main(String[] args) {
        settlement = new Settlement("Sanctuary Hills");
        inventory = new Inventory();
        log = new EventLog();

        // try to load an existing save first, otherwise seed some default stuff
        if (FileManager.load(settlement, inventory, SAVE_FILE)) {
            System.out.println("loaded save from " + SAVE_FILE);
            log.log("loaded save file");
        } else {
            seedDefaults();
            log.log("started new settlement");
        }

        boolean running = true;
        while (running) {
            printMainMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": survivorMenu(); break;
                case "2": inventoryMenu(); break;
                case "3": showStats(); break;
                case "4": log.printRecent(5); break;
                case "5": handleNextTask(); break;
                case "6":
                    FileManager.save(settlement, inventory, SAVE_FILE);
                    log.log("saved game");
                    break;
                case "0":
                    FileManager.save(settlement, inventory, SAVE_FILE);
                    System.out.println("see you in the wasteland");
                    running = false;
                    break;
                default:
                    System.out.println("not a valid option");
            }
        }
    }

    // populate a fresh settlement with a few starting survivors and items
    private static void seedDefaults() {
        Survivor preston = new Survivor("Preston");
        preston.setRole("Guard");
        preston.setLevel(5);

        Survivor sturges = new Survivor("Sturges");
        sturges.setRole("Scavenger");
        sturges.setLevel(3);

        Survivor curie = new Survivor("Curie");
        curie.setRole("Medic");
        curie.setLevel(4);
        curie.setHealth(85);

        settlement.addSurvivor(preston);
        settlement.addSurvivor(sturges);
        settlement.addSurvivor(curie);

        inventory.addItem(new Item("Stimpak", Item.MEDICINE, 30, 5));
        inventory.addItem(new Item("Laser Rifle", Item.WEAPON, 250, 1));
        inventory.addItem(new Item("Purified Water", Item.FOOD, 15, 10));
        inventory.addItem(new Item("Tin Can", Item.JUNK, 1, 20));
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("=== " + settlement.getName() + " ===");
        System.out.println("1. survivors");
        System.out.println("2. inventory");
        System.out.println("3. settlement stats");
        System.out.println("4. recent events");
        System.out.println("5. handle next task");
        System.out.println("6. save");
        System.out.println("0. quit");
        System.out.print("> ");
    }

    private static void survivorMenu() {
        System.out.println();
        System.out.println("-- survivors --");
        System.out.println("1. list all");
        System.out.println("2. add new");
        System.out.println("3. remove");
        System.out.println("4. assign role");
        System.out.println("5. sort by name");
        System.out.println("6. sort by health");
        System.out.println("7. sort by level");
        System.out.println("8. find by name (binary search)");
        System.out.println("9. count survivors at level X+");
        System.out.print("> ");
        String c = sc.nextLine().trim();

        switch (c) {
            case "1":
                if (settlement.getSurvivors().isEmpty()) {
                    System.out.println("no survivors yet");
                } else {
                    for (Survivor s : settlement.getSurvivors()) {
                        System.out.println(" - " + s);
                    }
                }
                break;
            case "2":
                System.out.print("name: ");
                String n = sc.nextLine().trim();
                if (!n.isEmpty()) {
                    settlement.addSurvivor(new Survivor(n));
                    log.log("added survivor " + n);
                    log.queueTask("orient new survivor: " + n);
                    System.out.println("welcomed " + n + " to the settlement");
                }
                break;
            case "3":
                System.out.print("name to remove: ");
                String rm = sc.nextLine().trim();
                if (settlement.removeSurvivor(rm)) {
                    log.log("removed survivor " + rm);
                    System.out.println(rm + " has left the settlement");
                } else {
                    System.out.println("nobody by that name");
                }
                break;
            case "4":
                System.out.print("survivor name: ");
                String sn = sc.nextLine().trim();
                int idx = SearchUtils.linearSearch(settlement.getSurvivors(), sn);
                if (idx == -1) {
                    System.out.println("not found");
                    break;
                }
                System.out.print("new role (Guard/Scavenger/Medic/Unassigned): ");
                String r = sc.nextLine().trim();
                settlement.getSurvivors().get(idx).setRole(r);
                log.log("assigned " + sn + " to " + r);
                break;
            case "5":
                SortingUtils.insertionSortByName(settlement.getSurvivors());
                System.out.println("sorted by name:");
                for (Survivor s : settlement.getSurvivors()) System.out.println(" - " + s);
                break;
            case "6":
                SortingUtils.selectionSortByHealth(settlement.getSurvivors());
                System.out.println("sorted by health:");
                for (Survivor s : settlement.getSurvivors()) System.out.println(" - " + s);
                break;
            case "7":
                SortingUtils.selectionSortByLevel(settlement.getSurvivors());
                System.out.println("sorted by level:");
                for (Survivor s : settlement.getSurvivors()) System.out.println(" - " + s);
                break;
            case "8":
                // need to be sorted by name first for binary search
                SortingUtils.insertionSortByName(settlement.getSurvivors());
                System.out.print("name to find: ");
                String find = sc.nextLine().trim();
                int found = SearchUtils.binarySearch(settlement.getSurvivors(), find);
                if (found == -1) {
                    System.out.println("not found");
                } else {
                    System.out.println("found: " + settlement.getSurvivors().get(found));
                }
                break;
            case "9":
                System.out.print("minimum level: ");
                try {
                    int lvl = Integer.parseInt(sc.nextLine().trim());
                    int count = SearchUtils.countAtLeastLevel(settlement.getSurvivors(), lvl, 0);
                    System.out.println(count + " survivor(s) at level " + lvl + " or higher");
                } catch (NumberFormatException e) {
                    System.out.println("that wasnt a number");
                }
                break;
            default:
                System.out.println("nope");
        }
    }

    private static void inventoryMenu() {
        System.out.println();
        System.out.println("-- inventory --");
        System.out.println("1. list all");
        System.out.println("2. add item");
        System.out.println("3. remove item");
        System.out.println("4. sort by value");
        System.out.println("5. search by keyword");
        System.out.println("6. type breakdown");
        System.out.print("> ");
        String c = sc.nextLine().trim();

        switch (c) {
            case "1":
                inventory.printAll();
                break;
            case "2":
                System.out.print("item name: ");
                String name = sc.nextLine().trim();
                System.out.print("type (Weapon/Food/Medicine/Junk): ");
                String type = sc.nextLine().trim();
                try {
                    System.out.print("value per unit: ");
                    int val = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("quantity: ");
                    int q = Integer.parseInt(sc.nextLine().trim());
                    inventory.addItem(new Item(name, type, val, q));
                    log.log("added " + q + "x " + name);
                } catch (NumberFormatException e) {
                    System.out.println("invalid number, item not added");
                }
                break;
            case "3":
                System.out.print("name to remove: ");
                String rm = sc.nextLine().trim();
                if (inventory.removeItem(rm)) {
                    log.log("removed " + rm);
                } else {
                    System.out.println("not in inventory");
                }
                break;
            case "4":
                SortingUtils.insertionSortItemsByValue(inventory.getItems());
                System.out.println("sorted by value:");
                inventory.printAll();
                break;
            case "5":
                System.out.print("keyword: ");
                String kw = sc.nextLine().trim();
                ArrayList<Item> hits = SearchUtils.findItemsByKeyword(inventory.getItems(), kw);
                if (hits.isEmpty()) {
                    System.out.println("no matches");
                } else {
                    for (Item it : hits) System.out.println(" - " + it);
                }
                break;
            case "6":
                HashMap<String, Integer> counts = inventory.getTypeCounts();
                if (counts.isEmpty()) {
                    System.out.println("no items");
                } else {
                    for (String k : counts.keySet()) {
                        System.out.println(" " + k + ": " + counts.get(k));
                    }
                }
                break;
            default:
                System.out.println("nope");
        }
    }

    // print overall settlement stats including a role breakdown
    private static void showStats() {
        System.out.println();
        System.out.println("-- " + settlement.getName() + " stats --");
        System.out.println("total survivors: " + settlement.getSurvivors().size());
        System.out.printf("average health: %.1f%n", settlement.getAverageHealth());
        System.out.println("total caps in inventory: " + inventory.totalCaps());

        HashMap<String, Integer> roleCounts = settlement.getRoleCounts();
        System.out.println("roles:");
        if (roleCounts.isEmpty()) {
            System.out.println(" (no survivors)");
        } else {
            for (String role : roleCounts.keySet()) {
                System.out.println(" - " + role + ": " + roleCounts.get(role));
            }
        }
    }

    // pull the next task off the queue and handle it
    private static void handleNextTask() {
        String t = log.processNextTask();
        if (t == null) {
            System.out.println("no tasks queued");
        } else {
            System.out.println("handled task: " + t);
            log.log("handled: " + t);
        }
    }
}
