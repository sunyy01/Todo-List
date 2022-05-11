package persistence;

import model.Task;
import model.ToDoList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;
// code modelled from JsonSerializationDemo-Thingy
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads todolist.json from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ToDoList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTodoList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses todolist.json from JSON object and returns it
    private ToDoList parseTodoList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ToDoList toDoList = new ToDoList(name);
        addTasks(toDoList, jsonObject);
        return toDoList;
    }

    // MODIFIES: todolist
    // EFFECTS: parses tasks from JSON object and adds them to workroom
    private void addTasks(ToDoList toDoList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("tasks");
        for (Object json : jsonArray) {
            JSONObject nextTask = (JSONObject) json;
            addTask(toDoList,nextTask);
        }
    }

    // MODIFIES: todolist
    // EFFECTS: parses task from JSON object and adds it to todolist
    private void addTask(ToDoList toDoList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int deadline = jsonObject.getInt("deadline");
        boolean status = jsonObject.getBoolean("status");
        Task task = new Task(name,deadline,status);
        toDoList.addTask(task);
    }
}
