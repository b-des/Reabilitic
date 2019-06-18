package com.reabilitic.models;

public class CategoryModel {
    int id;
    String name;
    String translit;

    public CategoryModel(String name, String translit) {
        this.name = name;
        this.translit = translit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
