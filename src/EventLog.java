import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

// event log uses two different data structures
// a Stack for recent events (most recent on top, like undo style)
// a Queue for upcoming tasks (first in first out, like a to do list)
public class EventLog {

    private Stack<String> recentEvents;
    private Queue<String> upcomingTasks;

    public EventLog() {
        recentEvents = new Stack<>();
        upcomingTasks = new LinkedList<>(); // LinkedList works as a Queue
    }

    // push a new event onto the stack
    public void log(String event) {
        recentEvents.push(event);
    }

    // peek the most recent event without removing it
    public String peekRecent() {
        if (recentEvents.isEmpty()) return null;
        return recentEvents.peek();
    }

    // pop the most recent event off the top of the stack
    public String popRecent() {
        if (recentEvents.isEmpty()) return null;
        return recentEvents.pop();
    }

    // add a task to the back of the queue
    public void queueTask(String task) {
        upcomingTasks.offer(task);
    }

    // pull the next task off the front of the queue
    public String processNextTask() {
        return upcomingTasks.poll();
    }

    public int recentCount() { return recentEvents.size(); }
    public int taskCount() { return upcomingTasks.size(); }

    // print the most recent N events without messing up the stack
    public void printRecent(int howMany) {
        if (recentEvents.isEmpty()) {
            System.out.println("no events yet");
            return;
        }
        int count = 0;
        for (int i = recentEvents.size() - 1; i >= 0 && count < howMany; i--) {
            System.out.println(" - " + recentEvents.get(i));
            count++;
        }
    }

    public void printTasks() {
        if (upcomingTasks.isEmpty()) {
            System.out.println("no upcoming tasks");
            return;
        }
        for (String t : upcomingTasks) {
            System.out.println(" - " + t);
        }
    }
}
