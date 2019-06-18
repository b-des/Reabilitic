package com.reabilitic.models;

import java.io.Serializable;

public class FilmModel implements Serializable{
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String name_ru;

    public String getNameRu() { return this.name_ru; }

    public void setNameRu(String name_ru) { this.name_ru = name_ru; }

    private String name_en;

    public String getNameEn() { return this.name_en; }

    public void setNameEn(String name_en) { this.name_en = name_en; }

    private String country;

    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }

    private String categories;

    public String getCategories() { return this.categories; }

    public void setCategories(String categories) { this.categories = categories; }

    private String category_name;


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    private String year;

    public String getYear() { return this.year; }

    public void setYear(String year) { this.year = year; }

    private String directed;

    public String getDirected() { return this.directed; }

    public void setDirected(String directed) { this.directed = directed; }

    private String casts;

    public String getCasts() { return this.casts; }

    public void setCasts(String casts) { this.casts = casts; }

    private String raiting;

    public String getRaiting() { return this.raiting; }

    public void setRaiting(String raiting) { this.raiting = raiting; }

    private String image;

    public String getImage() { return this.image; }

    public void setImage(String image) { this.image = image; }

    private String url;

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }

    private String full_desc;

    public String getFull_desc() {
        return full_desc;
    }

    public void setFull_desc(String full_desc) {
        this.full_desc = full_desc;
    }

    @Override
    public String toString() {
        return "FilmModel{" +
                "id='" + id + '\'' +
                ", name_ru='" + name_ru + '\'' +
                ", name_en='" + name_en + '\'' +
                ", country='" + country + '\'' +
                ", categories='" + categories + '\'' +
                ", year='" + year + '\'' +
                ", directed='" + directed + '\'' +
                ", casts='" + casts + '\'' +
                ", raiting='" + raiting + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
