package com.droolsinaction.general;

import java.io.Serializable;

public class General implements Serializable {

    static final long serialVersionUID = 1L;
    
    private String name;

    private String pantsColor;

    private int position;

    public General() {
    }

    public General(String name, String pantsColor, int position) {
        this.name = name;
        this.pantsColor = pantsColor;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPantsColor() {
        return pantsColor;
    }

    public void setPantsColor(String pantsColor) {
        this.pantsColor = pantsColor;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name + "的位置是 " + position + " 他穿 " + pantsColor + " 的裤子";
    }

}
