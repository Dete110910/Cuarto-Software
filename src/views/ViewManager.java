package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelTable panelTable;
    private DialogCreatePartition dialogCreatePartition;
    public ViewManager(ActionListener actionListener, KeyListener keyListener){
        this.setLayout(new BorderLayout());
        this.setTitle("Tercer Software");
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(700, 500);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.decode("#f2e9e4"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initComponents(actionListener, keyListener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener){
        this.panelMenu = new PanelMenu(actionListener);
        this.add(this.panelMenu, BorderLayout.WEST);

        this.panelTable = new PanelTable();
        this.add(this.panelTable, BorderLayout.CENTER);


        this.dialogCreatePartition = new DialogCreatePartition(actionListener, keyListener);
    }

    public void showCreatePartitionDialog(){
        this.dialogCreatePartition.setVisible(true);
    }

    public void hideCreatePartitionsDialog(){
        this.dialogCreatePartition.setVisible(false);
    }

    public String getPartitionName(){
        return this.dialogCreatePartition.getPartitionName();
    }

    public BigInteger getPartitionSize(){
        return this.dialogCreatePartition.getPartitionSize();
    }

    public void cleanFieldsPartitionDialog(){
        this.dialogCreatePartition.cleanAllFields();
    }
}
