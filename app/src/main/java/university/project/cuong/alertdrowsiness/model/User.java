package university.project.cuong.alertdrowsiness.model;

import java.io.Serializable;

/**
 * Created by Pham Thi Men on 3/29/2018.
 */

public class User implements Serializable {

    public String UserName;
    public String Password;
    public String Telephone;
    public String Address;
    public String Sex;

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String moTa) {
        this.UserName = UserName;
    }

    public void getPassword(String maSach) {
        this.Password = Password;
    }
    public String setPassword() { return Password;}

    public void getTelephone(String maSach) {
        this.Telephone = Telephone;
    }
    public String setTelephone() { return Telephone;}

    public void getAddress(String Address) {
        this.Address = Address;
    }
    public String setAddress() { return Address;}

    public void getSex(String Sex) {
        this.Sex = Sex;
    }
    public String setSex() { return Sex;}


    public User(){}
    public User(String UserName, String Password, String Telephone, String Address, String Sex) {
        this.UserName = UserName;
        this.Password = Password;
        this.Telephone = Telephone;
        this.Address = Address;
        this.Sex= Sex;
    }

}
