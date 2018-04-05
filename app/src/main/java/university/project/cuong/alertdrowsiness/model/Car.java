package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class Car implements Serializable {
    public String CarName;
    public String LicensePlate;


    public String getCarName() {
        return CarName;
    }
    public void setCarName(String CarName) {
        this.CarName = CarName;
    }

    public void getLicensePlate(String LicensePlate) {
        this.LicensePlate = LicensePlate;
    }
    public String setLicensePlate() { return LicensePlate;}




    public Car(){}
    public Car(String CarName, String LicensePlate) {
        this.CarName = CarName;
        this.LicensePlate = LicensePlate;
    }

}
