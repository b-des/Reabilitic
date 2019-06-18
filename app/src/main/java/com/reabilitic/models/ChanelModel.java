package com.reabilitic.models;

public class ChanelModel {
    String name;
    int logo;
    String url;

    public ChanelModel() { }

    public ChanelModel(String name, int logo, String url) {
        this.name = name;
        this.logo = logo;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ChanelModel{" +
                "name='" + name + '\'' +
                ", logo=" + logo +
                ", url='" + url + '\'' +
                '}';
    }
}
