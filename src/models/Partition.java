package models;

import java.math.BigInteger;

public class Partition {

    private String name;
    private BigInteger size;

    public Partition(String name, BigInteger size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Partition{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
