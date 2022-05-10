package com.example.demo.user;

import java.util.Map;

public class UserDao {

    private String id;
    private String name;
    private int age;
    private String phone;
    private String interested;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getInterested() {
        return interested;
    }

    public void setInterested(String interested) {
        this.interested = interested;
    }

    public void FromMap(Map<String, Object> map) {
        if (map.containsKey("id"))
            setId(map.get("id").toString());
        if (map.containsKey("name"))
            setName(map.get("name").toString());
        if (map.containsKey("phone"))
            setPhone(map.get("phone").toString());
        if (map.containsKey("interested"))
            setInterested(map.get("interested").toString());
    }
}