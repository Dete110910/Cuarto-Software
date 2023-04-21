package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelMenuReport panelMenuReport;
    private PanelTable panelTable;
    private DialogCreateProcess dialogCreateProcess;
    private DialogContainerCreatePartitionAndTable dialogContainerCreatePartitionAndTable;
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

        this.panelMenuReport = new PanelMenuReport(actionListener);

        this.dialogCreateProcess = new DialogCreateProcess(actionListener, keyListener);

        this.dialogContainerCreatePartitionAndTable = new DialogContainerCreatePartitionAndTable(actionListener, keyListener, panelTable);
    }

    public void showCreatePartitionDialog(){
        this.dialogContainerCreatePartitionAndTable.setVisible(true);
    }

    public void hideCreatePartitionsDialog(){
        this.dialogContainerCreatePartitionAndTable.setVisible(false);
    }

    public String getPartitionName(){
        return this.dialogContainerCreatePartitionAndTable.getPartitionName();
    }

    public BigInteger getPartitionSize(){
        return this.dialogContainerCreatePartitionAndTable.getPartitionSize();
    }

    public void cleanFieldsPartitionDialog(){
        this.dialogContainerCreatePartitionAndTable.cleanAllFields();
    }

    public void showCreateProcessDialog(){
        this.dialogCreateProcess.setVisible(true);
    }

    public void setComboPartitions(String[] comboPartitions){
        this.dialogCreateProcess.setValuesCombo(comboPartitions);
    }

    public String getProcessName(){
        return this.dialogCreateProcess.getNameProcess();
    }

    public BigInteger getProcessTime(){
        return this.dialogCreateProcess.getTimeProcess();
    }

    public BigInteger getProcessSize(){
        return this.dialogCreateProcess.getProcessSize();
    }

    public boolean isBlock(){
        return this.dialogCreateProcess.getIsBlock();
    }

    public String getSelectedPartition(){
        return this.dialogCreateProcess.getSelectedElementFirstCombo();
    }

    public void setValuesToTable(Object[][] list, String title) {
        Object[][] newQueueList =  this.parseValuesIsBlockAndIsSuspended(list);
        DefaultTableModel defaultTableModel = new DefaultTableModel(newQueueList, ConstantsGUI.HEADERS);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    private Object[][] parseValuesIsBlockAndIsSuspended(Object[][] queueList){
        int size = queueList.length;
        for(int i = 0; i < size; i++){
            if(!queueList[i][4].equals("Sí") && !queueList[i][4].equals("No")){
                queueList[i][4] = queueList[i][4].equals(true) ? "Sí" : "No";
            }
        }
        return queueList;
    }

    public void setValuesToPartitionsTable(Object[][] list){
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.TABLE_HEADERS);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void hideCreateAndModifyProcessDialog(){
        this.dialogCreateProcess.setVisible(false);
        this.dialogCreateProcess.cleanAllFields();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToReportMenu() {
        this.remove(this.panelMenu);
        this.add(this.panelMenuReport, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToMenu(){
        this.remove(panelMenuReport);
        this.add(this.panelMenu, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

}
