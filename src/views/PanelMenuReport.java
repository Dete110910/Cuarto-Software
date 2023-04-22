package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanelMenuReport extends JPanel {

    private JLabel titleMenuReports;

    private Button existingProcesses, readyReport, dispatchedReport, executionReport, expirationReport, blockReport, wakeReport, finishedReport, back;

    public PanelMenuReport(ActionListener listener){
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.decode("#4a4e69"));
        this.setPreferredSize(new Dimension(380,80));
        this.setMaximumSize(new Dimension(380,80));
        this.setMinimumSize(new Dimension(380,80));
        this.initComponents(listener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener listener) {
        titleMenuReports = new JLabel("Reportes");
        titleMenuReports.setForeground(Color.WHITE);
        titleMenuReports.setFont(ConstantsGUI.FONT_MENU_TITLE);
        Utilities.addComponent(this, this.titleMenuReports, 0, 1);

        existingProcesses = new Button("Procesos actuales");
        existingProcesses.addActionListener(listener);
        existingProcesses.setActionCommand("Actuales");
        Utilities.addComponent(this, this.existingProcesses, 0, 2);

        readyReport = new Button("Listos");
        readyReport.addActionListener(listener);
        readyReport.setActionCommand("Listos");
        Utilities.addComponent(this, this.readyReport, 0, 3);

        dispatchedReport = new Button("Despachados");
        dispatchedReport.addActionListener(listener);
        dispatchedReport.setActionCommand("Despachados");
        Utilities.addComponent(this, this.dispatchedReport, 0, 4);

        executionReport = new Button("Ejecución");
        executionReport.addActionListener(listener);
        executionReport.setActionCommand("Ejecucion");
        Utilities.addComponent(this, this.executionReport, 0, 5);

        expirationReport = new Button("Expirados");
        expirationReport.addActionListener(listener);
        expirationReport.setActionCommand("Expirados");
        Utilities.addComponent(this, this.expirationReport, 0, 6);

        blockReport = new Button("Bloqueados");
        blockReport.addActionListener(listener);
        blockReport.setActionCommand("Bloqueados");
        Utilities.addComponent(this, this.blockReport, 0, 7);

        wakeReport = new Button("Despertar");
        wakeReport.addActionListener(listener);
        wakeReport.setActionCommand("Despertar");
        Utilities.addComponent(this, this.wakeReport, 0, 8);

        finishedReport = new Button("Finalizados");
        finishedReport.addActionListener(listener);
        finishedReport.setActionCommand("Finalizados");
        Utilities.addComponent(this, this.finishedReport, 0, 9);

        finishedReport = new Button("No Ejecutados");
        finishedReport.addActionListener(listener);
        finishedReport.setActionCommand("NoEjecutados");
        Utilities.addComponent(this, this.finishedReport, 0, 10);

        back = new Button("Atrás");
        back.addActionListener(listener);
        back.setActionCommand("Atras");
        Utilities.addComponent(this, this.back, 0, 14);
        }
    }
