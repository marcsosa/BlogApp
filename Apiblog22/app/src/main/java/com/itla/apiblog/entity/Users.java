package com.itla.apiblog.entity;

public class Users {
    long createdAt;
    String email;
    int id;
    String name;
    int posts;
    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "Users{" +
                "createdAt" + createdAt +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", posts='" + posts + '\'' +
                '}';
    }



}
