package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class DialogContainerCreatePartitionAndTable extends JDialog {

    private PanelCreatePartition panelCreatePartition;
    private PanelTable panelTable;


    public DialogContainerCreatePartitionAndTable(ActionListener actionListener, KeyListener keyListener){
        this.setModal(true);
        this.setTitle("Crear particiones");
        this.setLayout(new BorderLayout());
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(460, 420);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode("#C9ADA7"));
        this.initComponents(actionListener, keyListener);
    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener){
        this.panelCreatePartition = new PanelCreatePartition(actionListener, keyListener);
        this.panelTable = new PanelTable();

        this.add(panelCreatePartition, BorderLayout.NORTH);
        Object[][] tableModel = new Object[0][0];
        String [] headers = {"Nombre", "Tamaño"};
        this.panelTable.changeTitle("Particiones");
        this.panelTable.setTableProcess(new DefaultTableModel(tableModel, headers));
        this.add(panelTable,BorderLayout.CENTER);


    }

    public void setTableProcess(DefaultTableModel defaultTableModel){
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public String getPartitionName(){
       return this.panelCreatePartition.getPartitionName();
    }

    public BigInteger getPartitionSize(){
        return this.panelCreatePartition.getPartitionSize();
    }

    public void cleanAllFields(){
        this.panelCreatePartition.cleanAllFields();
    }

}
