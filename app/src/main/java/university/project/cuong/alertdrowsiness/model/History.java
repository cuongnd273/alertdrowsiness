package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class History implements Serializable {
    private Timestamp time;
    private long latLocation;
    private long longLocation;
    private long duration;

    public History(Timestamp time,long duration, long latLocation, long longLocation) {
        this.time = time;
        this.latLocation = latLocation;
        this.longLocation = longLocation;
        this.duration = duration;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public long getLatLocation() {
        return latLocation;
    }

    public void setLatLocation(long latLocation) {
        this.latLocation = latLocation;
    }

    public long getLongLocation() {
        return longLocation;
    }

    public void setLongLocation(long longLocation) {
        this.longLocation = longLocation;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
