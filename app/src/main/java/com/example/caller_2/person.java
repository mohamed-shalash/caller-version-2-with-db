package com.example.caller_2;

public class person {
    int id;
    String name;
    String pirth_day;
    String image;
    String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPirth_day() {
        return pirth_day;
    }

    public void setPirth_day(String pirth_day) {
        this.pirth_day = pirth_day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public person(String name, String pirth_day, String image, String phone) {
        this.name = name;
        this.pirth_day = pirth_day;
        this.image = image;
        this.phone = phone;
    }

    public person(int id, String name, String pirth_day, String image, String phone) {
        this.id = id;
        this.name = name;
        this.pirth_day = pirth_day;
        this.image = image;
        this.phone = phone;
    }
}
