# Wasteland Survival Manager

CPSC-39 Final Project
Matt Ceran

## description

text-based survival manager set in a post-apocalyptic wasteland kind of like Fallout.
the user is the overseer of a small settlement and they manage survivors, keep track
of supplies, and try to keep everyone alive. its all through console menus so theres
no graphics, just text input and output.

## how to run

needs Java (JDK 11 or newer should be fine).

```
cd src
javac *.java
java Main
```

a save file called `wasteland_save.txt` gets created next to where you run it. if
you delete it the program will reseed with some default survivors and items.

## features

- add and remove survivors
- assign survivors to roles (Guard, Scavenger, Medic, Unassigned)
- add and remove inventory items (Weapon, Food, Medicine, Junk)
- sort survivors by name, health, or level
- sort items by value
- linear search and recursive binary search for survivors by name
- recursive count of survivors at or above a given level
- partial keyword search through items
- settlement stats (total survivors, average health, role counts, total caps)
- type breakdown of inventory using a hashmap
- recent event log (stack) and upcoming task queue
- save and load to a text file

## data structures used

- **ArrayList** - the survivor list in `Settlement` and the item list in `Inventory`
- **HashMap** - role counts in `Settlement.getRoleCounts()` and inventory type counts in `Inventory`
- **Stack** - recent events in `EventLog`
- **Queue** (LinkedList) - upcoming tasks in `EventLog`
- **String** - everywhere, names, roles, types, save file format
- **Array** - String.split returns arrays in `FileManager.load`

## algorithms used

- **selection sort** - sort survivors by health (ascending) or level (descending)
- **insertion sort** - sort survivors by name and items by value
- **linear search** - find a survivor by name in O(n)
- **binary search (recursive)** - find a survivor by name in O(log n) after sort
- **recursive count** - count survivors at or above a given level
- **stats calculation** - average health, total caps, role/type breakdowns

## file structure

```
/src
    Main.java          - menu loop
    Settlement.java    - holds survivors, role counts
    Survivor.java      - one survivor record
    Inventory.java     - holds items, type counts
    Item.java          - one item record
    SortingUtils.java  - selection and insertion sort
    SearchUtils.java   - linear search, binary search, recursive count
    EventLog.java      - stack of events, queue of tasks
    FileManager.java   - save and load to text file
/docs
    class_diagram.md
    algorithms.md
/report
    final_report.md
README.md
```

## contributors

Matt Ceran (individual project)

## citations

- selection sort, insertion sort, and binary search follow standard textbook
  implementations from class notes
- HashMap, Stack, and LinkedList usage from the Java API docs
  (https://docs.oracle.com/en/java/javase/17/docs/api/)
