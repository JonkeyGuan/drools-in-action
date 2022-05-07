package com.droolsinaction.fibonacci;

public class Fibonacci {
    
    private int sequence;

    private long value;

    public Fibonacci() {

    }

    public Fibonacci(final int sequence) {
        this.sequence = sequence;
        this.value = -1;
    }

    public int getSequence() {
        return this.sequence;
    }

    public void setValue(final long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    public String toString() {
        return "Fibonacci(" + this.sequence + "/" + this.value + ")";
    }
}
