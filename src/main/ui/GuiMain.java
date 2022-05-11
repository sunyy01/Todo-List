package ui;

import model.Event;
import model.EventLog;
import model.Task;
import model.ToDoList;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;


// part code modelled from Java Tutorials Code Sample – ListDemo.java
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/exampl
// es/components/ListDemoProject/src/components/ListDemo.java

// ToDoList Application GUI Display
public class GuiMain extends JPanel implements ListSelectionListener {
    protected ToDoList tasks;
    protected JList<String> list;
    protected DefaultListModel<String> listModel;

    private AddListener addListener;

    protected ImageIcon icon = new ImageIcon("IMG_4773.GIF");

    private JButton addButton;
    protected JButton removeButton;
    private JButton saveButton;
    private JButton loadButton;
    protected JButton clearButton;
    protected JButton completeButton;
    private JPanel filePane;
    private JPanel taskEdit;
    private JScrollPane listScrollPane;
    private JSplitPane splitPane;
    protected  JFrame mainFrame;

    protected JTextField taskName;
    protected JTextField taskDate;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/todolist.json";


    // EFFECTS: constructs the ToDoList Application GUI
    public GuiMain() {
        super(new BorderLayout());

        listModel = new DefaultListModel<>();

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        tasks = new ToDoList("List");

        listModel.addElement("");
        setTaskJList();
        setTaskListPane();
        setAddButton();
        setRemoveButton();
        setClearButton();
        setCompleteButton();
        setSaveButton();
        setLoadButton();
        setTaskField();
        setFilePane();
        setupSplitPane();
        setupFrame();
    }

    // MODIFIES: this
    // EFFECTS: Create the list
    public void setTaskJList() {
        list = new JList<String>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(8);
    }

    // MODIFIES: this
    // EFFECTS: create the scroll pane of task list
    public void setTaskListPane() {
        listScrollPane = new JScrollPane(list);
        listScrollPane.createVerticalScrollBar();
    }

    // MODIFIES: this
    // EFFECTS: creates addButton
    public void setAddButton() {
        addButton = new JButton("Add");
        addListener = new AddListener(this, addButton);
        addButton.addActionListener(addListener);
    }

    // MODIFIES: this
    // EFFECTS: creates removeButton
    public void setRemoveButton() {
        removeButton = new JButton("Remove");
        removeButton.setActionCommand("Remove");
        RemoveListener removeListener = new RemoveListener(this);
        removeButton.addActionListener(removeListener);
        removeButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: creates ImageIcon
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = CompleteListener.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates completeButton
    public void setCompleteButton() {
        completeButton = new JButton("DONE!");
        completeButton.setActionCommand("Complete");
        CompleteListener completeListener = new CompleteListener(this);
        CompleteDisplay completeDisplayListener = new CompleteDisplay(this);
        completeButton.addActionListener(completeDisplayListener);
        completeButton.addActionListener(completeListener);
        completeButton.setEnabled(false);
    }

    // MODIFIES: this
    // EFFECTS: creates cleareButton
    public void setClearButton() {
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("See");
        ClearListener clearListener = new ClearListener(this);
        clearButton.addActionListener(clearListener);
        clearButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: creates saveButton
    public void setSaveButton() {
        saveButton = new JButton("Save Tasks");
        saveButton.setActionCommand("Save");
        SaveListener saveListener = new SaveListener(this);
        saveButton.addActionListener(saveListener);
        saveButton.setEnabled(true);
    }

    // MODIFIES: this
    // EFFECTS: creates loadButton
    public void setLoadButton() {
        loadButton = new JButton("Load Tasks");
        loadButton.setActionCommand("Load");
        LoadListener loadListener = new LoadListener(this);
        loadButton.addActionListener(loadListener);
        loadButton.setEnabled(true);
    }

    // REQUIRES: saveButton and loadButton constructed
    // MODIFIES: this
    // EFFECTS: creates the Pane for save/load buttons
    public void setFilePane() {
        filePane = new JPanel();
        filePane.setLayout(new BoxLayout(filePane, BoxLayout.LINE_AXIS));
        filePane.add(saveButton);
        filePane.add(Box.createHorizontalStrut(5));
        filePane.add(new JSeparator(SwingConstants.VERTICAL));
        filePane.add(Box.createHorizontalStrut(5));
        filePane.add(loadButton);

        filePane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    }

    // REQUIRES: addButton, removeButton,clearButton and completeButton constructed
    // MODIFIES: this
    // EFFECTS: creates the Pane for add,remove,clear,complete buttons,
    // taskName and taskDate text-fields
    public void setTaskField() {
        taskEdit = new JPanel();
        taskEdit.setLayout(new GridLayout(6,6));
        JLabel name = new JLabel("TaskName:");
        JLabel date = new JLabel("Deadline:");
        taskEdit.add(name);

        taskName = new JTextField(8);
        taskName.addActionListener(addListener);
        taskName.getDocument().addDocumentListener(addListener);
        taskEdit.add(taskName);

        taskEdit.add(date);

        taskDate = new JTextField(8);
        taskDate.addActionListener(addListener);
        taskDate.getDocument().addDocumentListener(addListener);
        taskEdit.add(taskDate);



        taskEdit.add(addButton);
        taskEdit.add(removeButton);
        taskEdit.add(clearButton);
        taskEdit.add(completeButton);

    }

    //code modelled from Java Tutorials Code Sample – SplitPaneDemo.java
    //https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/
    //examples/components/SplitPaneDemoProject/src/components/SplitPaneDemo.java

    // MODIFIES: this
    // EFFECTS: creates the SplitPane
    private void setupSplitPane() {
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, taskEdit, listScrollPane);
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(150);

        //Provide minimum sizes for the two components in the split pane.
        Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
        taskEdit.setMinimumSize(minimumSize);

        //Provide a preferred size for the split pane.
        splitPane.setPreferredSize(new Dimension(400, 200));
    }


    // REQUIRES: splitPane,filePane constructed
    // MODIFIES: this
    // EFFECTS: sets up the ToDoList Application Frame
    public void setupFrame() {
        //Create and set up the window.
        mainFrame = new JFrame("ToDoList Application");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        mainFrame.add(splitPane,BorderLayout.CENTER);
        mainFrame.add(filePane,BorderLayout.PAGE_END);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                EventLog eventLog = EventLog.getInstance();
                for (Event event : eventLog) {
                    System.out.println(event.toString());
                }
            }
        });
//        enableEvents(java.awt.AWTEvent.WINDOW_EVENT_MASK);

    }


    // EFFECTS: if ListSelectionEvent's value is not adjusting and or no selection, set
    // disable removeButton and completeButton, else enable both buttons
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);
                completeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
                completeButton.setEnabled(true);
            }


        }
    }

    // EFFECTS : saves tasks to a destination file
    public void saveTasks() {
        try {
            jsonWriter.open();
            jsonWriter.write(tasks);
            jsonWriter.close();
            System.out.println("Saved " + tasks.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES : this
    // EFFECTS : loads tasks from a source file
    public void loadTasks() {
        try {
            tasks = jsonReader.read();
            for (Task task : tasks.getTasks()) {
                String taskName = task.getName();
                int dateString = task.getDeadline();
                String status = "UNDO: ";
                if (task.getStatus()) {
                    status = "DONE: ";
                }
                String taskAll = status + taskName + " is due on: " + dateString;
                listModel.addElement(taskAll);
                System.out.println("Loaded " + tasks.getName() + " from " + JSON_STORE);
            }
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: run the ToDoList Application
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        new GuiMain();
    }
}