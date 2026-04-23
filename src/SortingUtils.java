import java.util.ArrayList;

// static methods for sorting survivor and item lists
// got the basic idea for selection and insertion sort from class notes
public class SortingUtils {

    // selection sort survivors by health, lowest first
    // useful when you want to see who needs help in the medbay
    public static void selectionSortByHealth(ArrayList<Survivor> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getHealth() < list.get(minIndex).getHealth()) {
                    minIndex = j;
                }
            }
            // swap minimum with current spot
            Survivor tmp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, tmp);
        }
    }

    // selection sort survivors by level, highest first
    public static void selectionSortByLevel(ArrayList<Survivor> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int maxIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).getLevel() > list.get(maxIndex).getLevel()) {
                    maxIndex = j;
                }
            }
            Survivor tmp = list.get(i);
            list.set(i, list.get(maxIndex));
            list.set(maxIndex, tmp);
        }
    }

    // insertion sort survivors alphabetically by name
    // also needed for binary search to work
    public static void insertionSortByName(ArrayList<Survivor> list) {
        for (int i = 1; i < list.size(); i++) {
            Survivor key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getName().compareToIgnoreCase(key.getName()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    // insertion sort items by value per unit, highest first
    public static void insertionSortItemsByValue(ArrayList<Item> list) {
        for (int i = 1; i < list.size(); i++) {
            Item key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getValue() < key.getValue()) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}
