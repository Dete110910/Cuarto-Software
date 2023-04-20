package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {

    private ArrayList<Process> inQueue;
    private ArrayList<Partition> partitions;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
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

}
