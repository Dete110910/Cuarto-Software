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
import java.util.ArrayList;

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
            case "ModificarProceso":
                this.showModifyProcessDialog();
                break;
            case "ConfirmarModificacionProceso":
                this.confirmModifyProcess();
                break;
            case "EliminarProceso":
                this.deleteProcess();
                break;
            case "MenuParticiones":
                this.changeToPartitionsMenu();
                break;
            case "CrearParticion":
                this.showCreatePartitionDialog();
                break;
            case "ModificarParticion":
                this.showModifyPartitionDialog();
                break;
            case "ConfirmarModificacionParticion":
                this.confirmModifyPartition();
                break;
            case "EliminarParticion":
                this.deletePartition();
                break;
            case "Reportes":
                this.changeToReportMenu();
                break;
            case "Enviar":
                this.intiSimulation();
                break;
            case "Actuales":
                this.setValuesToCurrentReport();
                break;
            case "Listos":
                this.setValuesToReadyReport();
                break;
            case "Despachados":
                this.setValuesToDispatchReport();
                break;
            case "Ejecucion":
                this.setValuesToExecReport();
                break;
            case "Expirados":
                this.setValuesToExpReport();
                break;
            case "Bloqueados":
                this.setValuesToBlockReport();
                break;
            case "Despertar":
                this.setValuesToWakeReport();
                break;
            case "Finalizados":
                this.setValuesToFinishedReport();
                break;
            case "NoEjecutados":
                this.setValuesToNoExecReport();
                break;
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
    private void intiSimulation(){
        if(this.processManager.getInQueue().size() == 0){
            Utilities.showErrorDialog("Debe ingresar al menos un proceso para iniciar la simulación");
        }
        else {
            int response = Utilities.showConfirmationWarning();
            if(response == 0){
                processManager.initSimulation();
                Utilities.showDoneCPUProcess();
                processManager.cleanQueueList();
                processManager.copyToCurrentProcess();
                this.cleanMainTableProcess();
                this.loadReportList();
            }
        }
    }

    private void cleanMainTableProcess(){
        this.viewManager.setValuesToTable(processManager.getProcessListAsMatrixObject(processManager.getInQueue()), "Procesos Existentes");
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
            if(this.viewManager.getIsPartitionsMenuActive())
                this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
            else
                this.viewManager.setValuesToPartitionsTableInCreatePartition(processManager.getPartitionsListAsMatrixObject(processManager.getPartitions()));
            this.viewManager.cleanFieldsPartitionDialog();
        }
    }

    private void cancelAddPartition(){
        if(this.processManager.getPartitionsSize() > 0){
            this.viewManager.hideCreatePartitionsDialog();
            if(this.viewManager.getIsPartitionsMenuActive())
                this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
            else
                this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.cleanFieldsPartitionDialog();
        }

        else
            Utilities.showErrorDialog("Debe ingresar al menos una partición");
    }

    private void showCreateProcessDialog(){
        if(this.processManager.getPartitions().size() == 0){
         Utilities.showErrorDialog("Debe tener al menos una partición para poder crear procesos");
        }
        else {
            this.viewManager.setComboPartitions(processManager.getPartitionsAsArray());
            this.viewManager.showCreateProcessDialog();
        }
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
        else if(sizeProcess.toString().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío. Ingrese un valor numérico entero");
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

    private void showModifyProcessDialog(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
            this.viewManager.setProcessName(processToModify.getName());
            this.viewManager.setProcessTime(processToModify.getTime());
            this.viewManager.setProcessSize(processToModify.getSize());
            this.viewManager.setIsBlock(processToModify.isBlock());
            this.viewManager.setComboPartitions(this.processManager.getPartitionsAsArray());
            this.viewManager.showModifyProcessDialog();
        }

    }

    private void confirmModifyProcess(){
        Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
        String modifyNameProcess = this.viewManager.getProcessName();

        if(modifyNameProcess.trim().equals("")){
            Utilities.showErrorDialog("El nombre del proceso está vacío. Ingrese algún valor");
        }
        else if(!processToModify.getName().equals(modifyNameProcess)
                && this.processManager.isAlreadyNameInPartition(processToModify.getPartition().getName(), modifyNameProcess)){
            Utilities.showErrorDialog("Ya existe un proceso con este nombre en esta partición");
        }
        else if(this.viewManager.getProcessTime().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío. Ingrese un valor numérico entero");
        }
        else if(this.viewManager.getProcessSize().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío. Ingrese un valor numérico entero");
        }
        else {
            Process newProcess = new Process(this.processManager.getPartitionByName(this.viewManager.getSelectedPartition()),
                    this.viewManager.getProcessName(), this.viewManager.getProcessTime(), this.viewManager.getProcessSize(),
            this.viewManager.isBlock());
            this.processManager.updateProcessInQueue(newProcess, this.viewManager.getIndexDataInTable());
            this.viewManager.hideCreateAndModifyProcessDialog();
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        }
    }

    private void deleteProcess(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            int confirmation = Utilities.showConfirmationWarning();
                if(confirmation == 0){
                    this.processManager.deleteProcessInQueue(this.viewManager.getIndexDataInTable());
                    this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
                }

            }

    }


    private void changeToPartitionsMenu(){
        this.viewManager.setPartitionsMenuActive(true);
        this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
        this.viewManager.changeToPartitionsMenu();
    }

    private void showCreatePartitionDialog(){
        this.viewManager.showCreatePartitionDialogWithoutTable();
    }

    private void showModifyPartitionDialog(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar una partición");
        }
        else if(this.processManager.isPartitionUsed(this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable()).getName())){
            Utilities.showErrorDialog("Esta partición está siendo utilizada por un proceso y no se puede modificar.");
        }
        else {
            Partition partitionToModify = this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable());
            this.viewManager.setPartitionName(partitionToModify.getName());
            this.viewManager.setPartitionSize(partitionToModify.getSize().toString());
            this.viewManager.showModifyPartitionDialog();
        }
    }

    private void confirmModifyPartition(){
        Partition partitionToModify = this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable());
        String modifyPartitionName = this.viewManager.getPartitionName();

        if(modifyPartitionName.trim().equals("")){
            Utilities.showErrorDialog("El nombre de la partición está vacío. Ingrese algún valor");
        }
        else if(!partitionToModify.getName().equals(modifyPartitionName) && this.processManager.isAlreadyPartitionName(modifyPartitionName)){
            Utilities.showErrorDialog("Ya existe una partición con este nombre");
        }
        else {
            Partition newPartition = new Partition(this.viewManager.getPartitionName(), this.viewManager.getPartitionSize());
            this.processManager.updatePartitions(newPartition, this.viewManager.getIndexDataInTable());
            this.viewManager.hideCreatePartitionsDialog();
            this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
        }
    }

    private void deletePartition(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar una partición");
        }
        else if(this.processManager.isPartitionUsed(this.processManager.getPartitionByIndex(this.viewManager.getIndexDataInTable()).getName())){
            Utilities.showErrorDialog("Esta partición está siendo utilizada por un proceso y no se puede eliminar.");
        }
        else {
            int confirmation = Utilities.showConfirmationWarning();
            if(confirmation == 0){
                this.processManager.deletePartition(this.viewManager.getIndexDataInTable());
                this.viewManager.setValuesToPartitionsTableInCrud(this.processManager.getPartitionsListAsMatrixObject(this.processManager.getPartitions()));
            }

        }
    }

    private void changeToReportMenu(){
        if(this.processManager.getReadyList().size() != 0){
            this.viewManager.changeToReportMenu();
            this.viewManager.setValuesToCurrentProcess();
        }
        else {
            Utilities.showErrorDialog("Debe iniciar la simulación antes de ver los reportes");
        }

    }

    private void changeToMenu(){
        if(this.viewManager.getIsPartitionsMenuActive()){
            this.viewManager.setPartitionsMenuActive(false);

        }
        else
            this.processManager.cleanAllLists();
        this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        this.viewManager.changeToMainMenu();
    }

    private void openManual(){
        try{
            java.lang.Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+"C:\\Users\\Usuario\\Desktop\\SO\\Software\\Renovar - ICETEX 2023-1.pdf");
        } catch (Exception e){
            System.out.println("El archivo no se puede abrir");
        }

    }

    private ArrayList<Process> sotReadyReport(){
        ArrayList<Process> list = new ArrayList<>();
        for (int j = 0; j < processManager.getPartitions().size(); j++) {
            for (int i = 0; i < processManager.getReadyList().size(); i++) {
                if (processManager.getReadyList().get(i).getPartition().getName().equals(processManager.getPartitions().get(j).getName())) {
                    list.add(processManager.getReadyList().get(i));
                }
            }
        }
        return list;
    }
    private void loadReportList(){
        viewManager.setCurrentList(processManager.getProcessListAsMatrixObject(processManager.getCurrentList()));
        viewManager.setInQueueList(processManager.getProcessListAsMatrixObject(processManager.getInQueue()));
        viewManager.setReadyList(processManager.getProcessListAsMatrixObject(this.sotReadyReport()));
        viewManager.setDispatchList(processManager.getProcessListAsMatrixObject(processManager.getDispatchList()));
        viewManager.setExecutionList(processManager.getProcessListAsMatrixObject(processManager.getExecutionList()));
        viewManager.setExpirationList(processManager.getProcessListAsMatrixObject(processManager.getExpirationList()));
        viewManager.setBlockList(processManager.getProcessListAsMatrixObject(processManager.getBlockList()));
        viewManager.setWakeList(processManager.getProcessListAsMatrixObject(processManager.getWakeUpList()));
        viewManager.setFinishedList(processManager.getProcessListAsMatrixObject(processManager.getFinishedList()));
        viewManager.setNoExecutionList(processManager.getProcessListAsMatrixObject(processManager.getNoExecutionList()));
    }
    public void setValuesToCurrentReport(){
        this.viewManager.setValuesToCurrentReport();
    }

    public void setValuesToReadyReport(){
        this.viewManager.setValuesToReadyReport();
    }
    public void setValuesToDispatchReport(){
        this.viewManager.setValuesToDispatchReport();
    }

    public void setValuesToExecReport(){
        this.viewManager.setValuesToExecReport();
    }

    public void setValuesToExpReport(){
        this.viewManager.setValuesToExepReport();
    }
    public void setValuesToBlockReport(){
        this.viewManager.setValuesToBlockReport();
    }
    public void setValuesToWakeReport(){
        this.viewManager.setValuesToWakeReport();
    }
    public void setValuesToFinishedReport(){
        this.viewManager.setValuesToFinishedReport();
    }
    public void setValuesToNoExecReport(){
        this.viewManager.setValuesToNoExecReport();
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
