package ui;

import model.Task;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// unshown all complete tasks, only show incomplete tasks
public class ClearListener implements ActionListener {
    private GuiMain gui;

    // EFFECTS : creates an clearListener
    public ClearListener(GuiMain gui) {
        this.gui = gui;
    }

    // MODIFIES: this
    // EFFECTS : clear the list, only show UNDO tasks
    @Override
    public void actionPerformed(ActionEvent e) {
        gui.listModel.removeAllElements();

        for (Task task: gui.tasks.getTasks()) {
            if (!task.getStatus()) {
                String taskAll = "UNDO: " + task.getName() + " is due on: " + task.getDeadline();
                gui.listModel.insertElementAt(taskAll,0);
            }
        }

    }
}
