package com.rabbitMQ.entity;


import java.io.Serializable;

public class User implements Serializable {
    private String id;
    private String name;

    // Constructors
    public User() {}
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "User{id='" + id + "', name='" + name + "'}";
    }
}

