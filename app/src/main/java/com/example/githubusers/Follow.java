package com.example.githubusers;

public class Follow {
    private String imageURL, userName;

    public Follow(String imageURL, String userName) {
        this.imageURL = imageURL;
        this.userName = userName;

    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
