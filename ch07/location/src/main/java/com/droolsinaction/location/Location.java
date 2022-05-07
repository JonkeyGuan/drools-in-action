package com.droolsinaction.location;

import java.io.Serializable;

import org.kie.api.definition.type.Position;

public class Location implements Serializable {

    static final long serialVersionUID = 1L;

    @Position(0)
    private String item;

    @Position(1)
    private String location;

    public Location(String item, String location) {
        this.item = item;
        this.location = location;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
