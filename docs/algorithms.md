# Algorithm Pseudocode

pseudocode for the main algorithms in the project.

## 1. selection sort by health

sorts the survivor list so the lowest health is first. used for the medic
to see who needs help most urgently.

```
selectionSortByHealth(list):
    for i from 0 to size - 2:
        minIndex = i
        for j from i + 1 to size - 1:
            if list[j].health < list[minIndex].health:
                minIndex = j
        swap list[i] and list[minIndex]
```

big-O: O(n^2)

## 2. insertion sort by name

sorts the survivor list alphabetically. also a prereq for binary search.

```
insertionSortByName(list):
    for i from 1 to size - 1:
        key = list[i]
        j = i - 1
        while j >= 0 and list[j].name > key.name:
            list[j + 1] = list[j]
            j = j - 1
        list[j + 1] = key
```

big-O: O(n^2) worst case, O(n) on already sorted input

## 3. binary search (recursive)

assumes the list is sorted alphabetically by name. cuts the search range in
half on each call.

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

big-O: O(log n) time, O(log n) recursion depth

## 4. linear search

backup search that doesnt require a sorted list.

```
linearSearch(list, name):
    for i from 0 to size - 1:
        if list[i].name == name:
            return i
    return -1
```

big-O: O(n)

## 5. recursive count of survivors at or above a level

a recursive way to count without using a loop. mostly here as a clear
example of recursion for the SLO requirement.

```
countAtLeastLevel(list, level, idx):
    if idx >= size:
        return 0                       (base case)
    rest = countAtLeastLevel(list, level, idx + 1)
    if list[idx].level >= level:
        return rest + 1
    else:
        return rest
```

big-O: O(n) time, O(n) recursion depth

## 6. role count using a hashmap

walks the survivor list and tallies roles into a hashmap. used to print the
role breakdown in the stats screen.

```
getRoleCounts():
    counts = empty HashMap
    for each survivor s:
        if counts contains s.role:
            counts[s.role] = counts[s.role] + 1
        else:
            counts[s.role] = 1
    return counts
```

big-O: O(n) since hashmap put/get is O(1) average
