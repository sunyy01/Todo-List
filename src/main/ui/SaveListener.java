package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// save the tasks to todolist
public class SaveListener implements ActionListener {
    private GuiMain gui;

    // EFFECTS : creates an saveTaskListener
    public SaveListener(GuiMain gui) {
        this.gui = gui;
    }

    // EFFECTS: save the tasks to the file
    @Override
    public void actionPerformed(ActionEvent e) {
        gui.saveTasks();
    }
}