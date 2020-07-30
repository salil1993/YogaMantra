package com.deftminds.coronavirusapp.model;

public class Benefit {
    private String title;
    private String images;


    public Benefit() {
    }

    public Benefit(String title, String images) {
        this.title = title;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
