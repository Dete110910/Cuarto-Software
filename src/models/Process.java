package models;

import java.math.BigInteger;

public class Process {

    private Partition partition;

    private String name;
    private BigInteger time;
    private BigInteger size;
    private boolean isBlock;

    public Process(Partition partition, String name, BigInteger time, BigInteger size, boolean isBlock) {
        this.partition = partition;
        this.name = name;
        this.time = time;
        this.size = size;
        this.isBlock = isBlock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getTime() {
        return time;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public boolean isBlock() {
        return isBlock;
    }

    public void setBlock(boolean block) {
        isBlock = block;
    }

    public Partition getPartition() {
        return partition;
    }

    public void setPartition(Partition partition) {
        this.partition = partition;
    }
}
