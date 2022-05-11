package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Represents the ToDoList application
public class ToDoListApp {
    private static final String JSON_STORE = "./data/todolist.json";
    private ToDoList userList;
    private Scanner input;
    private EventLog eventLog;
    private Event event;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the ToDoList Application
    public ToDoListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        userList = new ToDoList("Iris's TodoList");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runToDoList();
    }

    //MODIFIES: this
    //EFFECTS: process user input
    private void runToDoList() {
        System.out.println("Welcome to the TodoList application ~\n");
        boolean keepGoing = true;
        String command = null;

        init();
        
        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nSee you next time!");
        printLogEvnet();


    }

    private void printLogEvnet() {
        eventLog = EventLog.getInstance();
        for (Event event: eventLog) {
            System.out.println(event.toString());
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command)  {
        if (command.equals("t")) {
            createTask();
        } else if (command.equals("r")) {
            removeTask();
        } else if (command.equals("d")) {
            changeTaskToComplete();
        } else if (command.equals("c")) {
            showCompleteToDoList();
        } else if (command.equals("i")) {
            showIncompleteToDoList();
        } else if (command.equals("a")) {
            getNumberOfComplete();
        } else if (command.equals("b")) {
            getNumberOfIncomplete();
        } else if (command.equals("n")) {
            getNumberOfAllTask();
        } else if (command.equals("l")) {
            loadTodoList();
        } else if (command.equals("s")) {
            saveTodoList();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS : initializes todolist.json
    private void init() {
        userList = new ToDoList("MyList");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tt -> create task");
        System.out.println("\tr -> remove task");
        System.out.println("\td -> change task status");
        System.out.println("\ti -> show incomplete tasks");
        System.out.println("\tc -> show complete tasks");
        System.out.println("\ta -> get number of complete tasks");
        System.out.println("\tb -> get number of incomplete tasks");
        System.out.println("\tn -> get the number of tasks");
        System.out.println("\tl -> load tasks from the file");
        System.out.println("\ts -> save tasks to the file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: Return the number of all tasks in the list
    private void getNumberOfAllTask() {
        System.out.println("This is the number of tasks in the list");
        System.out.println(userList.getTasks().size() + "\n");
    }

    // EFFECTS: Return the number of incomplete tasks in the list
    private void getNumberOfIncomplete() {
        System.out.println("This is the number of incomplete tasks in the list");
        System.out.println(userList.incompleteTask().size() + "\n");
    }

    // EFFECTS: Return the number of complete tasks in the list
    private void getNumberOfComplete() {
        System.out.println("This is the number complete of tasks in the list");
        System.out.println(userList.completeTask().size() + "\n");
    }

    // REQUIRES: Task is already exist as an element of the todolist.json,
    //           todolist.json is not empty
    // MODIFIES: this
    // EFFECTS: Change the status of the task to complete with given name
    private void changeTaskToComplete() {
        System.out.println("Enter the name of the task you want to mark as complete");
        String changeStatusTask = input.next();

        for (Task task: userList.getTasks()) {
            if (task.isCalled(changeStatusTask)) {
                task.changeStatus(true);
                System.out.println("Task is marked as complete");
            }
        }
    }


    // EFFECTS: show all the completed tasks from the list
    private void showCompleteToDoList() {
        System.out.println("The showing tasks are completed");

        for (Task task : userList.completeTask()) {
            System.out.println(task.getName());
        }
    }

    // EFFECTS: show all the incomplete tasks from the list
    private void showIncompleteToDoList() {
        System.out.println("The showing tasks are incomplete");

        for (Task task: userList.incompleteTask()) {
            System.out.println(task.getName());
        }
    }


    // REQUIRES: Task is already exist as an element of the todolist.json,
    //           todolist.json is not empty
    // MODIFIES: this,todolist.json
    // EFFECTS: remove the task from the todolist.json
    private void removeTask() {
        System.out.println("Enter the name of the task you want to remove");
        String removeTaskName = input.next();

        userList.removeTask(removeTaskName);
        System.out.println("Task is removed from the todolist.json");
    }

    // MODIFIES: this,todolist.json
    // EFFECTS: create a new task and add it to the todolist.json
    private void createTask() {
        System.out.println("Create your task and start by name");
        String name = input.next();

        System.out.println("What is the deadline in yyyy/MM/dd format");
        int deadline = input.nextInt();

        Task task = new Task(name, deadline, false);
        userList.addTask(task);
        System.out.println("Task is added to the todolist.json");
    }

    // code modelled from JsonSerializationDemo-Thingy
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the todolist to file
    private void saveTodoList() {
        try {
            jsonWriter.open();
            jsonWriter.write(userList);
            jsonWriter.close();
            System.out.println("Saved " + userList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads todolist from file
    private void loadTodoList() {
        try {
            userList = jsonReader.read();
            System.out.println("Loaded " + userList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}
