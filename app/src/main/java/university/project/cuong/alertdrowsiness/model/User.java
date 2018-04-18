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
    public String IdentityCard;
    public String Email;

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getPassword() { return Password;}

    public void setTelephone(String Telephone) {
        this.Telephone = Telephone;
    }
    public String getTelephone() { return Telephone;}

    public void setAddress(String Address) {
        this.Address = Address;
    }
    public String getAddress() { return Address;}

    public void setSex(String Sex) {
        this.Sex = Sex;
    }
    public String getSex() { return Sex;}

    public void setIdentityCard(String IdentityCard) {
        this.IdentityCard = IdentityCard;
    }
    public String getIdentityCard() { return IdentityCard;}

    public void setEmail(String Email) {
        this.Email = Email;
    }
    public String getEmail() { return Email;}


    public User(){}
    public User(String UserName, String Password, String Telephone, String Address, String Sex, String IdentityCard, String Email) {
        this.UserName = UserName;
        this.Password = Password;
        this.Telephone = Telephone;
        this.Address = Address;
        this.Sex= Sex;
        this.IdentityCard=IdentityCard;
        this.Email=Email;
    }

}
