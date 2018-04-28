package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class History implements Serializable {
    private long time;
    private long latlocation;
    private long longlocation;
    private int duration;

    public History() {

    }

    public History(long time, int duration, long latLocation, long longLocation) {
        this.time = time;
        this.latlocation = latLocation;
        this.longlocation = longLocation;
        this.duration = duration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getLatlocation() {
        return latlocation;
    }

    public void setLatlocation(long latlocation) {
        this.latlocation = latlocation;
    }

    public long getLonglocation() {
        return longlocation;
    }

    public void setLonglocation(long longlocation) {
        this.longlocation = longlocation;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
