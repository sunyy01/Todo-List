package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.GuiMain.createImageIcon;

// display a picture when click completeButton
public class CompleteDisplay implements ActionListener {
    private GuiMain gui;

    // EFFECTS: creates a completeDisplay ActionListener
    public CompleteDisplay(GuiMain gui) {
        this.gui = gui;
    }

    // EFFECTS : pop up a gif picture and messages
    @Override
    public void actionPerformed(ActionEvent e) {
        ImageIcon icon = createImageIcon("/ui/IMG_4773.GIF");
        JOptionPane.showMessageDialog(null,"Yohooo~","Congratulations!",
                JOptionPane.PLAIN_MESSAGE,icon);

    }
}
