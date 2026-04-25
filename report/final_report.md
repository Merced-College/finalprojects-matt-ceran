# Wasteland Survival Manager - Final Report

Matt Ceran
CPSC-39
April 2026

## Program Overview

For my final project I made a text-based survival manager called Wasteland Survival Manager. It is set in a post-apocalyptic wasteland kind of like Fallout. The user is the overseer of a small settlement and they have to keep their people alive and their supplies stocked. Everything happens through a console menu, no graphics, just text in and out.

The user can do things like add and remove survivors, give them roles like Guard, Scavenger, or Medic, manage an inventory of weapons, food, medicine, and junk, sort the survivors and items different ways, search for them, see settlement stats, and save and load the whole thing to a text file.

I picked this idea because it lets me actually use a lot of the stuff we covered in class, sorting, searching, hashmaps, stacks, and queues, but in a way that I thought would be fun rather than just doing exercises. It also fit pretty well with the proposal classes I had already planned.

## Algorithms

I ended up writing more than three algorithms for this but the report only needs three so here are the ones I think are the most interesting.

### Selection Sort by Health

This sorts the survivor list so the lowest health is first. The point of it is when the user wants to see who needs medical attention. The medic in the settlement should probably look at whoever is at the top of this list.

Pseudocode:

```
selectionSortByHealth(list):
    for i from 0 to size - 2:
        minIndex = i
        for j from i + 1 to size - 1:
            if list[j].health < list[minIndex].health:
                minIndex = j
        swap list[i] and list[minIndex]
```

The way it works is the outer loop walks every position. For each position it scans the rest of the list to find the smallest health value, then swaps that survivor into the current position. Once the loop is done, the list is sorted from lowest health to highest. It is not the fastest sort but it is easy to write and understand.

The Big-O is O(n^2) because there are two nested loops that both go through the list.

This lives in SortingUtils.selectionSortByHealth and there is a sister method selectionSortByLevel that does the same thing but with a max instead of a min so the highest level survivors come first.

### Recursive Binary Search

This is the search I am most proud of since it is recursive. Once the survivor list is sorted by name, binary search finds someone way faster than scanning the whole list.

Pseudocode:

```
binarySearch(list, name):
    return helper(list, name, 0, size - 1)

helper(list, name, low, high):
    if low > high:
        return -1
    mid = (low + high) / 2
    if list[mid].name == name:
        return mid
    else if list[mid].name < name:
        return helper(list, name, mid + 1, high)
    else:
        return helper(list, name, low, mid - 1)
```

It checks the middle of the range first. If that is the name we wanted, we are done. Otherwise the name has to be either smaller or bigger than the middle, so it calls itself on whichever half it has to be in. Each call cuts the range in half, so even with a thousand survivors it would only take about ten checks instead of a thousand.

The Big-O is O(log n) for time and O(log n) for the recursion depth on the call stack.

This lives in SearchUtils.binarySearch and the private binarySearchHelper. The menu calls insertionSortByName right before binary search to make sure the list is actually sorted, since binary search does not work on an unsorted list.

### Recursive Count of Survivors at a Given Level

This counts how many survivors are at or above a level the user picks. I wrote it recursively on purpose so I would have a second clear example of recursion in the project.

Pseudocode:

```
countAtLeastLevel(list, level, idx):
    if idx >= size:
        return 0
    rest = countAtLeastLevel(list, level, idx + 1)
    if list[idx].level >= level:
        return rest + 1
    else:
        return rest
```

The base case is when the index has gone past the end of the list, at which point it returns 0. Otherwise it recurses into the rest of the list and either adds 1 to whatever the rest returned, if the current survivor counts, or just passes that number back if not.

The Big-O is O(n) for time and O(n) for recursion depth.

This lives in SearchUtils.countAtLeastLevel.

### Other Algorithms

The project also uses insertion sort by name, insertion sort items by value, linear search, a partial keyword item search, a role count tally, and a stats calculation for average health and total caps. They are not as interesting as the three above but they are in the code and the docs/algorithms.md file.

## Data Structures

### ArrayList

I used ArrayList for the list of survivors in Settlement and the list of items in Inventory. It was the right choice because the program iterates these lists a lot, looks them up by index, and adds and removes occasionally. ArrayList is backed by an array under the hood so iterating and indexed access are fast.

### HashMap

I used HashMap in two places. The first is in Settlement.getRoleCounts where it maps each role name to how many survivors have that role. The second is in Inventory where it tracks how much of each item type is in stock. The reason for HashMap is that lookups and updates are O(1) on average, which beats scanning the whole list every time you want a count for a single role or type. It also means I do not have to declare the role or type names ahead of time, the map just grows as new ones show up.

### Stack

The Stack lives in EventLog.recentEvents. Every time the user does something the program logs an event onto the stack. The reason for using a Stack is that the most recent action should be the easiest to see, and a stack is naturally last-in-first-out so the top is always the most recent. The menu can peek the top, pop it off, or print the top N items.

### Queue (LinkedList)

The Queue lives in EventLog.upcomingTasks. When the user adds a new survivor, the program automatically queues an "orient new survivor" task. When the user picks Handle Next Task in the menu, it pulls the front of the queue. The reason for a Queue is that tasks should be handled in the order they came in, which is first-in-first-out. I used LinkedList because in Java that class implements the Queue interface.

### String and Array

Strings are everywhere in this program, names, roles, types, save file lines, menu prompts. There is also a plain String[] array in FileManager.load where String.split(",") returns a String array for each line of the save file.

## Development Process

I started with the proposal idea and broke it into the classes I had planned. The order I built them in was Survivor and Settlement first because they are the core, then Item and Inventory, then SortingUtils and SearchUtils so the lists could be sorted and searched, then EventLog when I realized I needed somewhere clean to use a Stack and a Queue. After that came FileManager for save and load and then finally Main to wire all the menus together.

I ran into a couple of problems along the way. Here are three of them.

The first one was that my binary search was returning -1 even when the survivor I was looking for definitely existed. After some printing I figured out it was because my list was not sorted yet. Binary search assumes the input is sorted, and I was searching the unsorted list. The fix was to make the menu call insertionSortByName right before binary search every time. That way the list is always in the right state when binary search runs.

The second problem was that my inventory type counts kept getting out of sync with the actual items. The first version of addItem and removeItem only changed the items list, it never updated the typeCounts hashmap, so the type breakdown screen showed wrong numbers. I fixed it by adding a private bumpType helper method that updates the count any time items go in or out of the inventory. Now both methods call it so the counts stay correct.

The third problem was with my save file loader. The first version was reading the settlement name from the file and trying to set it on a settlement that already had a name from the constructor, which left the name as some weird string. I added a nameRead flag so the loader skips that line on the assumption the settlement has already been created with a name. Not perfect but it works for this project since the user does not change the settlement name from the menu.

One design improvement I found while writing this report was that I had originally pasted the role count code into Main.showStats. That was kind of gross because Main was doing work that should belong on Settlement, since Settlement is the class that actually owns the survivor list. I moved it into Settlement.getRoleCounts and now Main just calls that method. Cleaner separation.

If I were to do a version 2 of this I would add a real combat and event system where survivors take damage and the medic can actually heal them. I would add random scavenging events that pull from a deck of cards (which would be a perfect use case for a Queue). I would also like to add multiple settlements that you can move survivors between, and a real GUI in JavaFX so it is not just text.

## Object Oriented vs Structured Design

This is one of the SLOs for the class so I want to talk about it briefly. I designed the project mostly using OOP. Each thing in the world is its own class with its own data and methods. Survivor, Item, Settlement, Inventory are all classes with private fields and getters. The static utility classes like SortingUtils, SearchUtils, and FileManager are more structured or procedural since they are just collections of functions that take data in and operate on it without having any state of their own.

The OOP parts make it easier to add new behavior to a survivor or item, like a new field or a new method, without having to touch the menu code. The structured parts make sense for things that do not really need state, like a sort method. Wrapping a sort method in an instance object would just be ceremony for no real benefit. I think real programs end up doing a mix like this and it does not have to be one or the other.

## Lifetime Management

This is also an SLO. Java uses garbage collection so I do not have to free any memory by hand. Every time I write new Survivor or new Item, the object gets cleaned up automatically once nothing is pointing to it anymore. If I had written this in C++ I would have had to either call delete myself or use smart pointers like shared_ptr to handle reference counting. Garbage collection makes the code shorter and safer because you cannot forget to free something, but it has some overhead and you cannot predict exactly when the cleanup runs. For a small program like this the tradeoff is worth it.
