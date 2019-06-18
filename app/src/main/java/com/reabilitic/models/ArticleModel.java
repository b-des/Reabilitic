package com.reabilitic.models;

public class ArticleModel {
    int id = 1;
    String name;
    String short_desc;
    String categories;
    String full_desc;

    public ArticleModel(String name, String short_desc) {
        this.name = name;
        this.short_desc = short_desc;
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

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", short_desc='" + short_desc + '\'' +
                ", categories='" + categories + '\'' +
                ", full_desc='" + full_desc + '\'' +
                '}';
    }
}
