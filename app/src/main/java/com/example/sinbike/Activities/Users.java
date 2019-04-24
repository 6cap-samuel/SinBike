package com.example.sinbike.Activities;
import java.io.Serializable;

public class Users implements Serializable {


    private String name;
    private String gender;
    private String email;
    private String phone;
    private String dob;


    public Users(String name, String gender, String email, String phone, String dob) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
    }

    public Users(){

    }



    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }




    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }


}
