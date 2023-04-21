package models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProcessManager {

    private final int PROCESS_TIME = 5;
    private ArrayList<Process> inQueue, currentList, readyList, dispatchList, executionList, expirationList, blockList, wakeUpList, finishedList, noExecutionList;
    private ArrayList<Partition> partitions;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
        this.inQueue = new ArrayList<>();
        this.currentList = new ArrayList<>();
        this.readyList = new ArrayList<>();
        this.dispatchList = new ArrayList<>();
        this.executionList = new ArrayList<>();
        this.expirationList = new ArrayList<>();
        this.blockList = new ArrayList<>();
        this.wakeUpList = new ArrayList<>();
        this.finishedList = new ArrayList<>();
        this.noExecutionList = new ArrayList<>();
    }

    public boolean isAlreadyPartitionName(String name){
        for(Partition partition: partitions){
            if(partition.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addPartition(String partitionName, BigInteger partitionSize){
        this.partitions.add(new Partition(partitionName, partitionSize));
    }

    public boolean isAlreadyName(String nameProcess) {
        for (Process process : inQueue) {
            if (process.getName().equals(nameProcess))
                return true;
        }
        return false;
    }

    public ArrayList<Partition> getPartitions(){
        return this.partitions;
    }

    public Partition getPartitionByName(String partitionName){
        for(Partition partition: partitions){
            if(partition.getName().equals(partitionName))
                return partition;
        }
        return null;
    }

    public Object[][] getListAsMatrixObject(ArrayList<Process> list){
        return this.parseArrayListToMatrixObject(list);
    }

    private Object[][] parseArrayListToMatrixObject(ArrayList<Process> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getPartition();
            processList[i][1] = list.get(i).getName();
            processList[i][2] = list.get(i).getTime();
            processList[i][3] = list.get(i).getSize();
            processList[i][4] = list.get(i).isBlock();
        }

        return processList;
    }

    public ArrayList<Process> getInQueue() {
        return inQueue;
    }
    public void addToInQueue(Process process){
        this.inQueue.add(process);
    }

    public int getPartitionsSize(){
        return this.partitions.size();
    }

    public void setInQueue(ArrayList<Process> inQueue) {
        this.inQueue = inQueue;
    }

    public ArrayList<Process> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(ArrayList<Process> currentList) {
        this.currentList = currentList;
    }

    public ArrayList<Process> getReadyList() {
        return readyList;
    }

    public void setReadyList(ArrayList<Process> readyList) {
        this.readyList = readyList;
    }

    public ArrayList<Process> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(ArrayList<Process> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public ArrayList<Process> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(ArrayList<Process> executionList) {
        this.executionList = executionList;
    }

    public ArrayList<Process> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(ArrayList<Process> expirationList) {
        this.expirationList = expirationList;
    }

    public ArrayList<Process> getBlockList() {
        return blockList;
    }

    public void setBlockList(ArrayList<Process> blockList) {
        this.blockList = blockList;
    }

    public ArrayList<Process> getWakeUpList() {
        return wakeUpList;
    }

    public void setWakeUpList(ArrayList<Process> wakeUpList) {
        this.wakeUpList = wakeUpList;
    }

    public ArrayList<Process> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<Process> finishedList) {
        this.finishedList = finishedList;
    }

    public ArrayList<Process> getNoExecutionList() {
        return noExecutionList;
    }

    public void setNoExecutionList(ArrayList<Process> noExecutionList) {
        this.noExecutionList = noExecutionList;
    }

    public void setPartitions(ArrayList<Partition> partitions) {
        this.partitions = partitions;
    }

    public void initSimulation(){
        this.cleanAllLists();
        this.copyToCurrentProcess();
        this.initLoadToReady();
        this.initPartitions();
        for (int j = 0; j < partitions.size(); j++) {
            for (int i = 0; i < readyList.size(); i++) {
                if(readyList.get(i).getPartition().getName().equals(partitions.get(j).getName())) {
                    if (readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0 || readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) {
                        System.out.println(readyList.get(i).getName());
                        this.loadToDispatchQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                        if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0) {
                            this.loadToExecQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                        } else {
                            this.loadToExecQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                        }
                        if (!(readyList.get(i).getTime().compareTo(BigInteger.valueOf(0)) == 0)) {
                            if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 && readyList.get(i).isBlock()) {
                                this.loadToBlockQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                                this.loadToWakeUpQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                                this.loadToReadyQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                            } else if (readyList.get(i).getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 && !readyList.get(i).isBlock()) {
                                this.loadToExpirationQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                                this.loadToReadyQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), this.consumeTimeProcess(readyList.get(i)), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                            } else {
                                this.loadToFinishedQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                            }

                        } else {
                            this.loadToFinishedQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), BigInteger.valueOf(0), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                        }
                    }else {
                        this.loadToNoExecQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                    }
                }
            }
        }
    }
    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void printReport(){
        System.out.println(finishedList.toString());
    }
    private void copyToCurrentProcess(){
        currentList.addAll(inQueue);
        for (int i = 0; i < currentList.size(); i++) {
            System.out.println(currentList.get(i).getName() + " " + currentList.get(i).getTime() + " "+ "Actuales");
        }
    }

    private void loadToReadyQueue(Process process) {
        this.readyList.add(process);
        for (int i = 0; i < readyList.size(); i++) {
            System.out.println(readyList.get(i).getName()+ " " + readyList.get(i).getTime() + " " + "Listos");
        }
    }

    private void loadToDispatchQueue(Process process) {
        this.dispatchList.add(process);
        for (int i = 0; i < dispatchList.size(); i++) {
            System.out.println(dispatchList.get(i).getName() + " " + dispatchList.get(i).getTime() + " "+ "despachados");
        }
    }
    private void loadToExecQueue(Process process) {
        this.executionList.add(process);
        for (int i = 0; i < executionList.size(); i++) {
            System.out.println(executionList.get(i).getName() + " " + executionList.get(i).getTime()+ " " + "Ejecución");
        }
    }
    private void loadToExpirationQueue(Process process) {
        this.expirationList.add(process);
        for (int i = 0; i < expirationList.size(); i++) {
            System.out.println(expirationList.get(i).getName() +" "+ executionList.get(i).getTime() + " "+ "Expiración");
        }
    }

    private void loadToBlockQueue(Process process) {
        this.blockList.add(process);
        for (int i = 0; i < blockList.size(); i++) {
            System.out.println(blockList.get(i).getName() + " "+blockList.get(i).getTime() +  " "+ "Bloquedados");
        }
    }

    private void loadToWakeUpQueue(Process process) {
        this.wakeUpList.add(process);
        for (int i = 0; i < wakeUpList.size(); i++) {
            System.out.println(wakeUpList.get(i).getName() + " " + wakeUpList.get(i).getTime() + " "+ "Despiertos");
        }
    }

    private void loadToFinishedQueue(Process process) {
        this.finishedList.add(process);
        for (int i = 0; i < finishedList.size(); i++) {
            System.out.println(finishedList.get(i).getName() + " " + finishedList.get(i).getTime() + " "+ "Terminados");
        }
    }

    private void loadToNoExecQueue(Process process) {
        this.noExecutionList.add(process);
        for (int i = 0; i < noExecutionList.size(); i++) {
            System.out.println(noExecutionList.get(i).getName() +" "+ noExecutionList.get(i).getTime() + " "+"no ejecutados");
        }
    }

    private void cleanAllLists(){
        this.currentList.clear();
        this.inQueue.clear();
        this.readyList.clear();
        this.dispatchList.clear();
        this.readyList.clear();
        this.executionList.clear();
        this.readyList.clear();
        this.expirationList.clear();
        this.blockList.clear();
        this.wakeUpList.clear();
        this.blockList.clear();
        this.finishedList.clear();
        this.noExecutionList.clear();
    }
    private void initPartitions(){
        partitions.add(new Partition("part1", new BigInteger("10")));
        partitions.add(new Partition("part2", new BigInteger("20")));
        partitions.add(new Partition("part3", new BigInteger("30")));
    }

    private void initLoadToReady() {
        //readyList.addAll(inQueue);

        //Caso de prueba
        readyList.add(new Process(new Partition("part1",new BigInteger("10")), "p1", new BigInteger("10"),new BigInteger("2"), true));
        readyList.add(new Process(new Partition("part2",new BigInteger("20")), "p2", new BigInteger("10"),new BigInteger("3"), true));
        readyList.add(new Process(new Partition("part3",new BigInteger("30")), "p3", new BigInteger("15"),new BigInteger("40"), false));
        readyList.add(new Process(new Partition("part1",new BigInteger("10")), "p4", new BigInteger("20"),new BigInteger("5"), true));
        readyList.add(new Process(new Partition("part1",new BigInteger("10")), "p5", new BigInteger("20"),new BigInteger("5"), true));
        readyList.add(new Process(new Partition("part1",new BigInteger("10")), "p6", new BigInteger("20"),new BigInteger("5"), true));
    }
    public void cleanQueueList(){
        inQueue.clear();
    }
}
