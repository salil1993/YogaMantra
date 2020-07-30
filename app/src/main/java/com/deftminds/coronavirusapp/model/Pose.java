package com.deftminds.coronavirusapp.model;

public class Pose {
    private String heading;
    private String photo;

    @Override
    public String toString() {
        return "Pose{" +
                "heading='" + heading + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    public Pose() {
    }

    public Pose(String heading, String photo) {
        this.heading = heading;
        this.photo = photo;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
