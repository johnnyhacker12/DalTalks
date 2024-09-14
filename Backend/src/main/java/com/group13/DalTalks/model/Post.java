package com.group13.DalTalks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PostId;

    private int UserID;

    private String PostTitle;

    private String PostBodyContent;

    public int getPostId() {
        return PostId;
    }

    public void setPostId(int postId) {
        PostId = postId;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getPostTitle() {
        return PostTitle;
    }

    public void setPostTitle(String postTitle) {
        PostTitle = postTitle;
    }

    public String getPostBodyContent() {
        return PostBodyContent;
    }

    public void setPostBodyContent(String postBodyContent) {
        PostBodyContent = postBodyContent;
    }

    public Post(){

    }

    public Post(int UserID, String PostTitle, String PostBodyContent) {
        this.UserID = UserID;
        this.PostTitle = PostTitle;
        this.PostBodyContent = PostBodyContent;
    }
}





