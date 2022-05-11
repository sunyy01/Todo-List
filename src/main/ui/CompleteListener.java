package ui;

import model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// change status of Tasks in ToDoList
public class CompleteListener implements ActionListener {
    private GuiMain gui;

    // EFFECTS : creates an completeListener
    public CompleteListener(GuiMain gui) {
        this.gui = gui;
    }

    // MODIFIES: this, task, ToDoList
    // EFFECTS : set the status of selected task as complete, change description
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = gui.list.getSelectedIndex();
        String taskDescribe = gui.listModel.getElementAt(index);
        String sub = taskDescribe.substring(4);

        for (Task task: gui.tasks.getTasks()) {
            if (sub.contains(task.getName())) {
                task.changeStatus(true);
                taskDescribe = "DONE: " + task.getName() + " is due on: " + task.getDeadline();
                gui.listModel.remove(index);
                gui.listModel.insertElementAt(taskDescribe,index);
            }

        }

        int size = gui.listModel.getSize();

        if (size == 0) { //Nobody's left, disable firing.
            gui.completeButton.setEnabled(false);

            gui.list.setSelectedIndex(index);
            gui.list.ensureIndexIsVisible(index);
        }

    }

}
