# Class Diagram

quick sketch of how the classes relate. nothing fancy, just an outline so I
can keep track while I am building this.

```
Main
 |
 |-- talks to --> Settlement -- holds --> Survivor (many)
 |
 |-- talks to --> Inventory  -- holds --> Item (many)
 |
 |-- talks to --> EventLog (Stack of events, Queue of tasks)
 |
 |-- uses --> SortingUtils  (static)
 |-- uses --> SearchUtils   (static)
 |-- uses --> FileManager   (static, also reads from Settlement and Inventory)


Survivor:
   name, health, level, role

Item:
   name, type, value, quantity

Settlement:
   name
   survivors  (ArrayList<Survivor>)

Inventory:
   items       (ArrayList<Item>)
   typeCounts  (HashMap<String, Integer>)

EventLog:
   recentEvents  (Stack<String>)
   upcomingTasks (Queue<String>)
```

## responsibilities

- Main - menu loop, glues all the other classes together
- Settlement - holds survivors, computes average health and role counts
- Survivor - record for one person
- Inventory - holds items, tracks counts by type
- Item - record for one item type
- EventLog - stack of recent events, queue of upcoming tasks
- SortingUtils - static sort methods
- SearchUtils - static search methods including a recursive binary search
- FileManager - static save and load methods
