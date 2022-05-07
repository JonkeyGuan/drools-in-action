package com.droolsinaction.location;

import java.io.Serializable;

public class Query implements Serializable {

    static final long serialVersionUID = 1L;

    private String item;

    private String location;

    public Query() {
    }

    public Query(String item, String location) {
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

    @Override
    public String toString() {
        return item + " 在 " + location + " 里吗？";
    }

}
