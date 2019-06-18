package com.reabilitic.models;

import java.io.Serializable;

public class GenreModel implements Serializable{
    String name;
    String translit;
    String icon;
    String color;

    public GenreModel(String name,String translit, String icon, String color) {
        this.name = name;
        this.translit = translit;
        this.icon = icon;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslit() {
        return translit;
    }

    public void setTranslit(String translit) {
        this.translit = translit;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return "#"+color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
