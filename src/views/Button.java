package views;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String text) {
        super(text);
        this.setBackground(Color.decode("#9a8c98"));
        this.setForeground(Color.WHITE);
        this.setFont(ConstantsGUI.FONT_MENU_ITEMS);
        this.setPreferredSize(new Dimension(280,40));
    }

    public void changePreferredSize(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
    }
}
