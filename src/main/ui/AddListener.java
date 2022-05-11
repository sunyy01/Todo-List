package ui;

import model.Task;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//code modelled from Java Tutorials Code Sample – ListDemo.java - HireListener
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/exampl
//es/components/ListDemoProject/src/components/ListDemo.java

//  adds a task with taskName and taskDate to the ToDoList
class AddListener implements ActionListener,DocumentListener {

    private GuiMain gui;
    private boolean alreadyEnabled = false;
    private JButton button;

    // EFFECTS : creates an addTaskListener
    public AddListener(GuiMain gui, JButton clickedAddButton) {
        this.gui = gui;
        this.button = clickedAddButton;
    }

    // REQUIRES: addButton pressed and taskName/taskDate bar is not empty
    // MODIFIES: this, Task, ToDoList
    // EFFECTS : adds a task to a To-Do list
    @Override
    public void actionPerformed(ActionEvent e) {
        String name = gui.taskName.getText();
        String dateString = gui.taskDate.getText();
        int date = Integer.parseInt(dateString);
        String taskAll = "UNDO: " + name + " is due on: " + date;

        //User didn't type in a unique name...
        if (name.equals("") || alreadyInList(name)) {
            Toolkit.getDefaultToolkit().beep();
            gui.taskName.requestFocusInWindow();
            gui.taskName.selectAll();
            return;
        }

        int index = gui.list.getSelectedIndex(); //get selected index
        if (index == -1) { //no selection, so insert at beginning
            index = 0;
        } else {           //add after the selected item
            index++;
        }

        Task task = new Task(name, date,false);
        gui.tasks.addTask(task);
        gui.listModel.insertElementAt(taskAll, index);
        //If we just wanted to add to the end, we'd do this:
        //listModel.addElement(employeeName.getText());

        //Reset the text field.
        reset(gui.taskName);
        reset(gui.taskDate);

        //Select the new item and make it visible.
        gui.list.setSelectedIndex(index);
        gui.list.ensureIndexIsVisible(index);
    }

    // helper
    // MODIFIES: this
    // EFFECTS: reset the text field
    private void reset(JTextField taskName) {
        taskName.requestFocusInWindow();
        taskName.setText("");
    }

    //This method tests for string equality. You could certainly
    //get more sophisticated about the algorithm.  For example,
    //you might want to ignore white space and capitalization.
    protected boolean alreadyInList(String name) {
        return gui.listModel.contains(name);
    }

    //Required by DocumentListener.
    public void insertUpdate(DocumentEvent e) {
        enableButton();
    }

    //Required by DocumentListener.
    public void removeUpdate(DocumentEvent e) {
        handleEmptyTextField(e);
    }

    //Required by DocumentListener.
    public void changedUpdate(DocumentEvent e) {
        if (!handleEmptyTextField(e)) {
            enableButton();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the button is not enabled, set it as enabled
    private void enableButton() {
        if (!alreadyEnabled) {
            button.setEnabled(true);
        }
    }

    // MODIFIES: this
    // EFFECTS: when portion of document is removed and length is less than 0, the buttons
    // are disabled and returns true
    // otherwise returns false
    private boolean handleEmptyTextField(DocumentEvent e) {
        if (e.getDocument().getLength() <= 0) {
            button.setEnabled(false);
            alreadyEnabled = false;
            return true;
        }
        return false;
    }
}
