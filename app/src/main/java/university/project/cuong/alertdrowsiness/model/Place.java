package university.project.cuong.alertdrowsiness.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cuong on 4/8/2018.
 */

public class Place {
    private String name;
    private double lat;
    private double lng;
    private String icon;
    private float rating;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
