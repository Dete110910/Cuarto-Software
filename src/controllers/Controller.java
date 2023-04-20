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
        }
    }

    private void addPartition(){
        String partitionName = this.viewManager.getPartitionName();
        BigInteger partitionSize = this.viewManager.getPartitionSize();
        if(this.processManager.isAlreadyPartitionName(partitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");
        }
        else if(partitionSize.equals(new BigInteger("-1"))){
            Utilities.showErrorDialog("Debe ingresar un tamaño para su partición");
        }
        else {
            this.processManager.addPartition(partitionName, partitionSize);
            this.viewManager.cleanFieldsPartitionDialog();
        }
    }

    private void cancelAddPartition(){
        this.viewManager.hideCreatePartitionsDialog();
    }

    private void showCreateProcessDialog(){
        this.viewManager.showCreateProcessDialog();
    }

    private void confirmAddProcess(){
        String processName = this.viewManager.getProcessName();
        BigInteger timeProcess = this.viewManager.getProcessTime();
        BigInteger sizeProcess = this.viewManager.getProcessSize();
        boolean isBlock = this.viewManager.isBlock();
        Partition partition = this.processManager.getPartitionByName(this.viewManager.getSelectedPartition());

        if(this.processManager.isAlreadyName(processName)){
            Utilities.showErrorDialog("Ya existe un proceso con este nombre");
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
            this.viewManager.setValuesToTable(this.processManager.getListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.hideCreateAndModifyProcessDialog();
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
