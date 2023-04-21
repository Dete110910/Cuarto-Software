package controllers;

import models.Partition;
import models.ProcessManager;
import views.Utilities;
import views.ViewManager;
import models.Process;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class Controller implements ActionListener, KeyListener {

    private ViewManager viewManager;
    private ProcessManager processManager;

    public Controller(){
        this.processManager = new ProcessManager();
        this.viewManager = new ViewManager(this, this);
        this.viewManager.showCreatePartitionDialog();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "AñadirParticion":
                this.addPartition();
                break;
            case "CancelarParticion":
                this.cancelAddPartition();
                break;
            case "CrearProceso":
                this.showCreateProcessDialog();
                break;
            case "AñadirProceso":
                this.confirmAddProcess();
                break;
            case "CancelarAñadirProceso":
                this.cancelAddProcess();
                break;
            case "Reportes":
                this.changeToReportMenu();
                break;
            case "Enviar":
                this.intiSimulation();
                this.p();
            case "Atras":
                this.changeToMenu();
                break;
            case "ManualUsuario":
                this.openManual();
                break;
            case "Salir":
                System.exit(0);
                break;
        }
    }

 /*   private void  sendCPU(){
        int response = Utilities.showWarningSendCPU();
        if(response == 0){
            processManager.sendCPU();
            Utilities.showDoneCPUProcess();
            this.saveReports();
            processManager.copyToCurrentProcess();
            processManager.cleanQueueList();
            this.cleanMainTableProcess();
            viewManager.showTableProcessPanel();

        }
    }*/
    private void intiSimulation(){
        int response = Utilities.showConfirmationWarning();
        if(response == 0){
            processManager.initSimulation();
            Utilities.showDoneCPUProcess();
            //this.saveReports();
            processManager.cleanQueueList();
            this.cleanMainTableProcess();
            //this.loadReportList();
        }
    }
    private void p(){
        //processManager.p();
    }

    private void cleanMainTableProcess(){
        this.viewManager.setValuesToTable(processManager.getProcessListAsMatrixObject(processManager.getInQueue()), "Procesos Actuales");
    }

    private void addPartition(){
        String partitionName = this.viewManager.getPartitionName();
        BigInteger partitionSize = this.viewManager.getPartitionSize();

        if(this.processManager.isAlreadyPartitionName(partitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");
        }
        else if(partitionName.trim().equals("")){
            Utilities.showErrorDialog("Ese nombre no está permitido para las particiones");
        }
        else if(partitionSize.equals(new BigInteger("-1"))){
            Utilities.showErrorDialog("Debe ingresar un tamaño para su partición");
        }
        else {
            this.processManager.addPartition(partitionName, partitionSize);
            this.viewManager.setValuesToPartitionsTable(processManager.getPartitionsListAsMatrixObject(processManager.getPartitions()));
            this.viewManager.cleanFieldsPartitionDialog();
        }
    }

    private void cancelAddPartition(){
        if(this.processManager.getPartitionsSize() > 0){
            this.viewManager.hideCreatePartitionsDialog();
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        }

        else
            Utilities.showErrorDialog("Debe ingresar al menos una partición");
    }

    private void showCreateProcessDialog(){
        this.viewManager.setComboPartitions(processManager.getPartitionsAsArray());
        this.viewManager.showCreateProcessDialog();
    }

    private void confirmAddProcess(){
        String processName = this.viewManager.getProcessName();
        BigInteger timeProcess = this.viewManager.getProcessTime();
        BigInteger sizeProcess = this.viewManager.getProcessSize();
        boolean isBlock = this.viewManager.isBlock();
        Partition partition = this.processManager.getPartitionByName(this.viewManager.getSelectedPartition());

        if(this.processManager.isAlreadyNameInPartition(partition.getName(), processName)){
            Utilities.showErrorDialog("Ya existe un proceso con este nombre en esta partición");
        }
        else if(processName.trim().isEmpty()){
            Utilities.showErrorDialog("El nombre del proceso está vacío. Ingrese algún valor");
        }
        else if(timeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío. Ingrese un valor numérico entero");
        }
        else{
            Process newProcess = new Process(partition, processName, timeProcess, sizeProcess, isBlock);
            this.processManager.addToInQueue(newProcess);
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.hideCreateAndModifyProcessDialog();
        }
    }

    private void cancelAddProcess(){
        this.viewManager.hideCreateAndModifyProcessDialog();
    }

    private void changeToReportMenu(){
        this.viewManager.changeToReportMenu();
    }

    private void changeToMenu(){
        this.viewManager.changeToMenu();
    }

    private void openManual(){
        try{
            java.lang.Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+"C:\\Users\\Usuario\\Desktop\\SO\\Software\\Renovar - ICETEX 2023-1.pdf");
        } catch (Exception e){
            System.out.println("El archivo no se puede abrir");
        }

    }














    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        new Controller();
    }
}
