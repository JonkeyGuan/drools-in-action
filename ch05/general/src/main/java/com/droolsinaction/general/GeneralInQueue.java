package com.droolsinaction.general;

import java.io.Serializable;

public class GeneralInQueue implements Serializable {

    static final long serialVersionUID = 1L;

    private General general;

    public GeneralInQueue() {
    }

    public GeneralInQueue(General general) {
        this.general = general;
    }

    public General getGeneral() {
        return general;
    }

    public void setGeneral(General general) {
        this.general = general;
    }

    @Override
    public String toString() {
        return "" + general;
    }

}