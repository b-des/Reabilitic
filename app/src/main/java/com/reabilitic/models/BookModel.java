package com.reabilitic.models;

import java.io.Serializable;

public class BookModel implements Serializable{
    private String id;

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private String categories;

    public String getCategories() { return this.categories; }

    public void setCategories(String categories) { this.categories = categories; }

    private String author;

    public String getAuthor() { return this.author; }

    public void setAuthor(String author) { this.author = author; }

    private String pages;

    public String getPages() { return this.pages; }

    public void setPages(String pages) { this.pages = pages; }

    private String short_desc;

    public String getShortDesc() { return this.short_desc; }

    public void setShortDesc(String short_desc) { this.short_desc = short_desc; }

    private String full_desc;

    public String getFullDesc() { return this.full_desc; }

    public void setFullDesc(String full_desc) { this.full_desc = full_desc; }

    private String image;

    public String getImage() { return this.image; }

    public void setImage(String image) { this.image = image; }

    private String url;

    public String getUrl() { return this.url; }

    public void setUrl(String url) { this.url = url; }

    @Override
    public String toString() {
        return "BookModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", categories='" + categories + '\'' +
                ", author='" + author + '\'' +
                ", pages='" + pages + '\'' +
                ", short_desc='" + short_desc + '\'' +
                ", full_desc='" + full_desc + '\'' +
                ", image='" + image + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
