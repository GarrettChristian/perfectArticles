package com.perfectArticles.perfectArticlesBack.domain.dto;

public class CommentDto {

    private Integer id;

    private String userName;

    private String text;

    private Integer likes;

    private Integer dislikes;

    private String date;

    public Integer getId() {
        return id;
    }

    public CommentDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CommentDto setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getText() {
        return text;
    }

    public CommentDto setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public CommentDto setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public CommentDto setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public String getDate() {
        return date;
    }

    public CommentDto setDate(String date) {
        this.date = date;
        return this;
    }
}
