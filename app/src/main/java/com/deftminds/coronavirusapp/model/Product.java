package com.deftminds.coronavirusapp.model;

import androidx.annotation.StringDef;

public class Product {
    private String title;
    private String subtitle;
    private String time;
    private String calories;
    private String images;

    public Product() {
    }

    public Product( String title, String subtitle, String time, String calories ,String images) {

        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.calories = calories;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtittle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
