package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelMenuPartitions panelMenuPartitions;
    private PanelMenuReport panelMenuReport;
    private PanelTable panelTable;
    private DialogCreateProcess dialogCreateProcess;
    private DialogContainerCreatePartitionAndTable dialogContainerCreatePartitionAndTable;

    private Object[][] inQueue, currentList, readyList, dispatchList, executionList, expirationList, blockList, wakeUpList, finishedList, noExecutionList;

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

        this.panelMenuPartitions = new PanelMenuPartitions(actionListener);

        this.panelMenuReport = new PanelMenuReport(actionListener);

        this.dialogCreateProcess = new DialogCreateProcess(actionListener, keyListener);
        this.dialogContainerCreatePartitionAndTable = new DialogContainerCreatePartitionAndTable(actionListener, keyListener);

        this.inQueue = new Object[0][0];
        this.readyList = new Object[0][0];
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

    public void setValuesToPartitionsTableInCreatePartition(Object[][] list){
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.PARTITIONS_TABLE_HEADERS);
        this.dialogContainerCreatePartitionAndTable.setTableProcess(defaultTableModel);
    }

    public void setValuesToPartitionsTableInCrud(Object[][] list){
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.PARTITIONS_TABLE_HEADERS);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void hideCreateAndModifyProcessDialog(){
        this.dialogCreateProcess.setVisible(false);
        this.dialogCreateProcess.cleanAllFields();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToPartitionsMenu(){
        this.remove(this.panelMenu);
        this.add(this.panelMenuPartitions, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToReportMenu() {
        this.remove(this.panelMenu);
        this.add(this.panelMenuReport, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void changeToMainMenu(){
        this.remove(panelMenuReport);
        this.remove(panelMenuPartitions);
        this.add(this.panelMenu, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setCurrentList(Object[][] currentList) {
        this.currentList = currentList;
    }
    public void setInQueueList(Object[][] inQueue) {
        this.inQueue = inQueue;
    }

    public void setReadyList(Object[][] readyList) {
        this.readyList = readyList;
    }
    public void setDispatchList(Object[][] dispatchList) {
        this.dispatchList = dispatchList;
    }
    public void setExecutionList(Object[][] executionList) {
        this.executionList = executionList;
    }
    public void setExpirationList(Object[][] expirationList) {
        this.expirationList = expirationList;
    }
    public void setBlockList(Object[][] blockList) {
        this.blockList = blockList;
    }

    public void setWakeList(Object[][] wakeUpList) {
        this.wakeUpList = wakeUpList;
    }
    public void setFinishedList(Object[][] finishedList) {
        this.finishedList = finishedList;
    }
    public void setNoExecutionList(Object[][] noExecutionList) {
        this.noExecutionList = noExecutionList;
    }

    public void setValuesToCurrentProcess(){
        this.setValuesToTable(this.inQueue, "Procesos Existentes");
    }

      public void setValuesToCurrentReport(){
        this.setValuesToTable(this.currentList, "Procesos Actuales");
    }

    public void setValuesToReadyReport(){
        this.setValuesToTable(this.readyList, "Procesos Listos");
    }
    public void setValuesToDispatchReport(){
        this.setValuesToTable(this.dispatchList, "Procesos Despachados");
    }

    public void setValuesToExecReport(){
        this.setValuesToTable(this.executionList, "Procesos en Ejecución");
    }

    public void setValuesToExepReport(){
        this.setValuesToTable(this.expirationList, "Procesos Expirados");
    }
    public void setValuesToBlockReport(){
        this.setValuesToTable(this.blockList, "Procesos Bloqueados");
    }
    public void setValuesToWakeReport(){
        this.setValuesToTable(this.wakeUpList, "Procesos Despiertos");
    }
    public void setValuesToFinishedReport(){
        this.setValuesToTable(this.finishedList, "Procesos Finalizados");
    }
    public void setValuesToNoExecReport(){
        this.setValuesToTable(this.noExecutionList, "Procesos No Ejecutados");
    }
}
