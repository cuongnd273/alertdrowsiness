package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;
import java.sql.Time;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class History implements Serializable {
    private Time time;
    private long latLocation;
    private long longLocation;
    private long duration;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
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
