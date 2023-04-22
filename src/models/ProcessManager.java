package models;

import java.math.BigInteger;
import java.util.ArrayList;

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

    public boolean isAlreadyNameInPartition( String partitionName, String nameProcess) {
        for (Process process : inQueue) {
            if (process.getPartition().getName().equals(partitionName) && process.getName().equals(nameProcess))
                return true;
        }
        return false;
    }

    public ArrayList<Partition> getPartitions(){
        return this.partitions;
    }

    public String[] getPartitionsAsArray(){
        String[] partitionsList = new String[partitions.size()];
        for(int i = 0; i < partitionsList.length; i++){
            partitionsList[i] = partitions.get(i).getName();
        }
        return partitionsList;
    }

    public Partition getPartitionByName(String partitionName){
        for(Partition partition: partitions){
            if(partition.getName().equals(partitionName))
                return partition;
        }
        return null;
    }

    public Object[][] getProcessListAsMatrixObject(ArrayList<Process> list){
        return this.parseArrayListToMatrixObject(list);
    }

    private Object[][] parseArrayListToMatrixObject(ArrayList<Process> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getPartition().getName();
            processList[i][1] = list.get(i).getName();
            processList[i][2] = list.get(i).getTime();
            processList[i][3] = list.get(i).getSize();
            processList[i][4] = list.get(i).isBlock();
        }
        return processList;
    }

    public Object[][] getPartitionsListAsMatrixObject(ArrayList<Partition> list){
        return this.parseArrayPartitionListToMatrixObject(list);
    }

    private Object[][] parseArrayPartitionListToMatrixObject(ArrayList<Partition> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getSize();
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
        this.copyToCurrentProcess();
        this.initLoadToReady();
        this.initPartitions();
        for (int j = 0; j < partitions.size(); j++) {
            for (int i = 0; i < readyList.size(); i++) {
                if (readyList.get(i).getPartition().getName().equals(partitions.get(j).getName())) {
                    if ((readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == -1) || (readyList.get(i).getSize().compareTo(partitions.get(j).getSize()) == 0)) {
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
                    } else {
                        this.loadToNoExecQueue(new Process(readyList.get(i).getPartition(), readyList.get(i).getName(), readyList.get(i).getTime(), readyList.get(i).getSize(), readyList.get(i).isBlock()));
                    }
                }
            }
        }
    }
    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void copyToCurrentProcess(){
        currentList.addAll(inQueue);
    }

    private void loadToReadyQueue(Process process) {
        this.readyList.add(process);
    }

    private void loadToDispatchQueue(Process process) {
        this.dispatchList.add(process);
    }
    private void loadToExecQueue(Process process) {
        this.executionList.add(process);
    }
    private void loadToExpirationQueue(Process process) {
        this.expirationList.add(process);
    }

    private void loadToBlockQueue(Process process) {
        this.blockList.add(process);
    }

    private void loadToWakeUpQueue(Process process) {
        this.wakeUpList.add(process);
    }

    private void loadToFinishedQueue(Process process) {
        this.finishedList.add(process);
    }

    private void loadToNoExecQueue(Process process) {
        this.noExecutionList.add(process);
    }

    public void cleanAllLists(){
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
        System.out.println(partitions.toString());
    }

    private void initLoadToReady() {
        readyList.addAll(inQueue);
    }
    public void cleanQueueList(){
        inQueue.clear();
    }
}
