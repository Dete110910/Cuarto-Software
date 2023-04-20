package views;

import javax.swing.*;
import java.awt.*;

public class Utilities {

    public static void addComponent(Container parent ,JComponent component, int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        parent.add(component, gbc);
    }

    public static void showErrorDialog(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static int showConfirmationWarning(){
        int response = JOptionPane.showOptionDialog(null, "¿Está seguro?", "Advertencia",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[] {"Sí","No"}, "Sí");
        return response;
    }

    public static void showDoneCPUProcess(){
        JOptionPane.showMessageDialog(null, "Los procesos han sido ejecutados con éxito", "Acción realizada", JOptionPane.INFORMATION_MESSAGE);
    }
}