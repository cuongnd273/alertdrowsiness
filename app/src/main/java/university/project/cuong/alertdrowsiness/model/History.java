package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class History implements Serializable {
    private long time;
    private double latlocation;
    private double longlocation;
    private int duration;

    public History() {

    }

    public History(long time, int duration, double latLocation, double longLocation) {
        this.time = time;
        this.latlocation = latLocation;
        this.longlocation = longLocation;
        this.duration = duration;
    }

    public History(double latlocation, double longlocation) {
        this.latlocation = latlocation;
        this.longlocation = longlocation;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLatlocation() {
        return latlocation;
    }

    public void setLatlocation(double latlocation) {
        this.latlocation = latlocation;
    }

    public double getLonglocation() {
        return longlocation;
    }

    public void setLonglocation(double longlocation) {
        this.longlocation = longlocation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
