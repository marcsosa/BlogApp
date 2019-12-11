package com.itla.apiblog.entity;

import java.io.Serializable;

public class Comment implements Serializable {
//util para layout, body,created,username,useremail
    private String body;
    private Long createdAt;
    private int id;
    private int postid;
    private String userEmail;
    private int userId;
    private String userName;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "PostCommentList{" +
                "body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", id=" + id +
                ", postid=" + postid +
                ", userEmail='" + userEmail + '\'' +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }


}
