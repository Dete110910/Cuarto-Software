package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenu extends JPanel {

    private JLabel titleMenu;
    private Button addProcess, modifyProcess, deleteProcess, partitions, reports, sendProcess, viewManual, exit;

    public PanelMenu(ActionListener actionListener){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode("#4a4e69"));
        this.setPreferredSize(new Dimension(350,80));
        this.setMaximumSize(new Dimension(350,80));
        this.setMinimumSize(new Dimension(350,80));
        this.initComponents(actionListener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener actionListener){
        this.titleMenu = new JLabel("Procesos");
        this.titleMenu.setForeground(Color.WHITE);
        this.titleMenu.setFont(ConstantsGUI.FONT_MENU_TITLE);
        Utilities.addComponent(this, titleMenu, 0, 1);

        this.addProcess = new Button("Crear");
        this.addProcess.addActionListener(actionListener);
        this.addProcess.setActionCommand("CrearProceso");
        Utilities.addComponent(this, this.addProcess, 0, 2);

        this.modifyProcess = new Button("Modificar");
        this.modifyProcess.addActionListener(actionListener);
        this.modifyProcess.setActionCommand("ModificarProceso");
        Utilities.addComponent(this, this.modifyProcess, 0, 3);

        this.deleteProcess = new Button("Eliminar");
        this.deleteProcess.addActionListener(actionListener);
        this.deleteProcess.setActionCommand("EliminarProceso");
        Utilities.addComponent(this, this.deleteProcess, 0, 4);

        this.partitions = new Button("Particiones");
        this.partitions.addActionListener(actionListener);
        this.partitions.setActionCommand("MenuParticiones");
        Utilities.addComponent(this, this.partitions, 0, 5);

        this.reports = new Button("Reportes");
        this.reports.addActionListener(actionListener);
        this.reports.setActionCommand("Reportes");
        Utilities.addComponent(this, this.reports, 0, 6);

        this.sendProcess = new Button("Iniciar Simulación");
        this.sendProcess.addActionListener(actionListener);
        this.sendProcess.setActionCommand("Enviar");
        Utilities.addComponent(this, this.sendProcess, 0, 7);

        this.viewManual = new Button("Manual de usuario");
        this.viewManual.addActionListener(actionListener);
        this.viewManual.setActionCommand("ManualUsuario");
        Utilities.addComponent(this, this.viewManual, 0, 8);

        this.exit = new Button("Salir");
        this.exit.addActionListener(actionListener);
        this.exit.setActionCommand("Salir");
        Utilities.addComponent(this, exit, 0, 9);
    }


}
