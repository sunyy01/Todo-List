package ui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadListener implements ActionListener {
    private GuiMain gui;

    // EFFECTS : creates an loadTaskListener
    public LoadListener(GuiMain gui) {
        this.gui = gui;
    }

    // EFFECTS: load tasks from the file
    @Override
    public void actionPerformed(ActionEvent e) {
        gui.loadTasks();
    }
}
