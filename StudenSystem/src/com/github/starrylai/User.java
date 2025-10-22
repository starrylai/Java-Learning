package com.github.starrylai;

public class User {
//用户名、密码、身份证号码、手机号码
    private String userName;
    private String password;
    private String citizenID;
    private int phoneNumber;

    public User(){
    }

    public User(String userName, String password, String citizenID, int phoneNumber){
        this.userName = userName;
        this.password = password;
        this.citizenID = citizenID;
        this.phoneNumber = phoneNumber;
    }

    public void setUsername(String userName){
        this.userName = userName;
    }

    public String getUsername(){
        return userName;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setCitizenID(String citizenID){
        this.citizenID = citizenID;
    }

    public String getCitizenID(){
        return citizenID;
    }

    public void setPhoneNumber(int phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber(){
        return phoneNumber;
    }


}
