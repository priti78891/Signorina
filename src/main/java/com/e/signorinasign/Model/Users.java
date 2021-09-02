package com.e.signorinasign.Model;

public class Users {

    private String name,phone,password;
    public Users()
    {
        this.name = null;
        this.phone = null;
        this.password = null;

    }

    public Users(String name,String phone,String password) {
        this.name = name;
        this.phone=phone;
        this.password=password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}


