package ui;

import model.Task;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//code modelled from Java Tutorials Code Sample – ListDemo.java - FireListener
//https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/exampl
//es/components/ListDemoProject/src/components/ListDemo.java

// remove selected task
public class RemoveListener implements ActionListener {
    private GuiMain gui;

    // EFFECTS : creates an removeTaskListener
    public RemoveListener(GuiMain gui) {
        this.gui = gui;
    }
    //This method can be called only if
    //there's a valid selection
    //so go ahead and remove whatever's selected.

    // MODIFIES: this, Task, ToDoList
    // EFFECTS : remove a task to a To-Do list
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = gui.list.getSelectedIndex();
        String taskDescribe = gui.listModel.getElementAt(index);


        gui.tasks.getTasks().removeIf(task -> taskDescribe.contains(task.getName()));

        gui.listModel.remove(index);
        for (Task task: gui.tasks.getTasks()) {
            if (taskDescribe.contains(task.getName())) {
                gui.tasks.remove(task);
            }
        }

        int size = gui.listModel.getSize();
        if (size == 0) { //Nobody's left, disable firing.
            gui.removeButton.setEnabled(false);

        } else { //Select an index.
            if (index == gui.listModel.getSize()) {
                //removed item in last position
                index--;
            }

            gui.list.setSelectedIndex(index);
            gui.list.ensureIndexIsVisible(index);
        }
    }
}