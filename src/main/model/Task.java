package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents a task with its name, deadline and status of completion
 */

public class Task implements Writable {
    private int deadline;                 // the deadline of the task
    private String name;                   // the name of the task
    private Boolean status;                 // the status of the task

    /*
     * REQUIRES: taskName has a non-zero length
     * EFFECTS: name of task is set by String name;
     *          deadline is int follow DateFormat pattern that assigned to the task
     *          status of task is present by the boolean isComplete( or not);
     */
    public Task(String name, int deadline, Boolean isComplete) {
        this.name = name;
        this.deadline = deadline;
        this.status = isComplete;
    }

    // REQUIRE: task name already exist
    // MODIFIES: this
    // EFFECTS : rename the task
    public void setName(String setTaskName) {
        name = setTaskName;
        EventLog.getInstance().logEvent(new Event("Task name is set"));
    }

    // REQUIRES: task deadline already exist
    // MODIFIES: this
    // EFFECTS : changes the deadline of the task
    public void setDeadline(int setDeadline) {
        deadline = setDeadline;
        EventLog.getInstance().logEvent(new Event("Task deadline is set"));
    }

    // MODIFIES: this
    // EFFECTS: change the status of the task to complete or not complete
    public void changeStatus(Boolean newStatus) {
        status = newStatus;
        EventLog.getInstance().logEvent(new Event("Task: " + name + " status is changed"));
    }

    // EFFECTS: get the name of the task
    public String getName() {
        return name;
    }

    // EFFECTS: get the name of the deadline
    public int getDeadline() {
        return deadline;
    }

    // EFFECTS: get the status of the task
    public Boolean getStatus() {
        return status;
    }

    // EFFECTS: Returns true if Task is called
    // and false otherwise
    public boolean isCalled(String call) {
        return getName().equals(call);
    }

    // EFFECTS: return task as JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("deadline", deadline);
        json.put("status", status);
        return json;
    }

}