package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class History implements Serializable {
    private java.util.Date time;
    private long latlocation;
    private long longlocation;
    private int duration;


    public java.util.Date gettime() { return time;}
    public void settime(java.util.Date time) {
        this.time = time;
    }

    public int getduration() { return duration;}
    public void settduration(int duration) { this.duration = duration;}

    public long getlatlocation() { return latlocation;}
    public void setlatlocation(long time) {this.latlocation = latlocation;}

    public long getlonglocation() { return longlocation;}
    public void setlonglocation(long longlocation) {this.longlocation = longlocation;}
    public History()
    {

    }

    public History(java.util.Date time, int duration, long latLocation, long longLocation) {
        this.time = time;
        this.latlocation = latLocation;
        this.longlocation = longLocation;
        this.duration = duration;
    }
}
