package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenuPartitions extends JPanel {

    private JLabel titleMenu;
    private JButton addProcess, modifyProcess, deleteProcess, back;

    public PanelMenuPartitions(ActionListener actionListener){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode("#4a4e69"));
        this.setPreferredSize(new Dimension(350,80));
        this.setMaximumSize(new Dimension(350,80));
        this.setMinimumSize(new Dimension(350,80));
        this.initComponents(actionListener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener actionListener) {
        this.titleMenu = new JLabel("Particiones");
        this.titleMenu.setForeground(Color.WHITE);
        this.titleMenu.setFont(ConstantsGUI.FONT_MENU_TITLE);
        Utilities.addComponent(this, titleMenu, 0, 0);

        this.addProcess = new Button("Crear");
        this.addProcess.addActionListener(actionListener);
        this.addProcess.setActionCommand("CrearParticion");
        Utilities.addComponent(this, this.addProcess, 0, 1);

        this.modifyProcess = new Button("Modificar");
        this.modifyProcess.addActionListener(actionListener);
        this.modifyProcess.setActionCommand("ModificarParticion");
        Utilities.addComponent(this, this.modifyProcess, 0, 2);

        this.deleteProcess = new Button("Eliminar");
        this.deleteProcess.addActionListener(actionListener);
        this.deleteProcess.setActionCommand("EliminarParticion");
        Utilities.addComponent(this, this.deleteProcess, 0, 3);

        this.back = new Button("Atrás");
        this.back.addActionListener(actionListener);
        this.back.setActionCommand("Atras");
        Utilities.addComponent(this, this.back, 0, 4);
    }


}
