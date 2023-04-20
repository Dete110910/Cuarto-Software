package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {

    private ArrayList<ArrayList<Object>> partitions;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
    }

    public boolean isAlreadyPartitionName(String name){
        for(ArrayList<Object> partition: partitions){
            if(partition.get(0).equals(name))
                return true;
        }
        return false;
    }

    public void addPartition(String partitionName, BigInteger partitionSize){
        ArrayList<Object> newPartition = new ArrayList<>();
        newPartition.add(partitionName);
        newPartition.add(partitionSize);
        this.partitions.add(newPartition);
    }

    public ArrayList<ArrayList<Object>> getPartitions(){
        return this.partitions;
    }

}
