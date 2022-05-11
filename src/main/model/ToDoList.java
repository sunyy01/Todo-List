package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a list of tasks
public class ToDoList implements Writable {
    private String name;
    private final ArrayList<Task> tasks;

    // EFFECTS: initialize an empty todolist.json
    public ToDoList(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }

    // EFFECTS: return the name of the todolist.json
    public String getName() {
        return name;
    }

    // EFFECTS: return the list of tasks in the todolist.json
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    // MODIFIES: this
    // EFFECTS: new task is added to the todolist.json
    public void addTask(Task task) {
        tasks.add(task);
        EventLog.getInstance().logEvent(new Event("Task: " + task.getName() + " is added"));
    }

    // REQUIRES: Task is already exist as an element of the todolist.json
    // MODIFIES: this
    // EFFECTS: search the task with given name and remove it from the todolist.json

    public void remove(Task task) {
        tasks.remove(task);
        EventLog.getInstance().logEvent(new Event("Task: " + task.getName() + " is removed"));
    }

    public void removeTask(String searchName) {
        ArrayList<Task> removeTask = new ArrayList<>();
        for (Task task: tasks) {
            if (task.isCalled(searchName)) {
                removeTask.add(task);
            }
        }
        tasks.removeAll(removeTask);
        EventLog.getInstance().logEvent(new Event("Task: " + searchName + "is removed"));
    }

    // EFFECTS: Return the list of tasks that incomplete
    public ArrayList<Task> incompleteTask() {
        ArrayList<Task> incompleteTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (!task.getStatus()) {
                incompleteTasks.add(task);
            }
        }
        return incompleteTasks;
    }


    // EFFECTS: Return the list of tasks that complete
    public ArrayList<Task> completeTask() {
        ArrayList<Task> completeTasks = new ArrayList<>();
        for (Task task: tasks) {
            if (task.getStatus()) {
                completeTasks.add(task);
            }
        }
        return completeTasks;
    }


    // code modelled from JsonSerializationDemo-Thingy
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    @Override
    // EFFECTS: return the todolist name and all tasks on the list as JSON Object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("tasks", tasksToJson());
        return json;
    }

    // EFFECTS: returns tasks on this todolist as a JSON array
    private JSONArray tasksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Task t: tasks) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }

}