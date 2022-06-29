package com.example.caller_2;

public class history {
    int id;
    String name;
    String phone;
    String time;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public history(String name, String phone, String time) {
        this.name = name;
        this.phone = phone;
        this.time = time;
    }

    public history(int id, String name, String phone, String time) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.time = time;
    }
}
