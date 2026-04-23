import java.util.ArrayList;

// search algorithms - linear, binary (recursive), and a recursive count
public class SearchUtils {

    // linear search through the survivor list, O(n)
    public static int linearSearch(ArrayList<Survivor> list, String name) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    // public binary search, list has to be sorted by name first
    // O(log n) which is way faster than linear once the list gets big
    public static int binarySearch(ArrayList<Survivor> sortedList, String name) {
        return binarySearchHelper(sortedList, name, 0, sortedList.size() - 1);
    }

    // recursive helper that splits the list in half each call
    private static int binarySearchHelper(ArrayList<Survivor> list, String name, int low, int high) {
        if (low > high) {
            return -1; // not found
        }
        int mid = (low + high) / 2;
        int cmp = list.get(mid).getName().compareToIgnoreCase(name);
        if (cmp == 0) {
            return mid;
        } else if (cmp < 0) {
            // name we want is to the right
            return binarySearchHelper(list, name, mid + 1, high);
        } else {
            // name we want is to the left
            return binarySearchHelper(list, name, low, mid - 1);
        }
    }

    // recursive function that counts how many survivors are at or above a given level
    // not the most efficient but its a good example of recursion
    public static int countAtLeastLevel(ArrayList<Survivor> list, int level, int idx) {
        if (idx >= list.size()) {
            return 0; // base case, end of list
        }
        int rest = countAtLeastLevel(list, level, idx + 1);
        if (list.get(idx).getLevel() >= level) {
            return rest + 1;
        }
        return rest;
    }

    // partial keyword match for items, returns every match
    public static ArrayList<Item> findItemsByKeyword(ArrayList<Item> items, String keyword) {
        ArrayList<Item> matches = new ArrayList<>();
        String key = keyword.toLowerCase();
        for (Item it : items) {
            if (it.getName().toLowerCase().contains(key)) {
                matches.add(it);
            }
        }
        return matches;
    }
}
