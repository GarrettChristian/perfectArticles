package com.perfectArticles.perfectArticlesBack.domain.dto;

public class ArticleDto {

    private Integer id;

    private String title;

    private String date;

    private String author;

    private String text;

    private String imageLocation;

    private String imageCaption;

    private String tags;

    public Integer getId() {
        return id;
    }

    public ArticleDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ArticleDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public ArticleDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public ArticleDto setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public ArticleDto setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
        return this;
    }

    public String getTags() {
        return tags;
    }

    public ArticleDto setTags(String tags) {
        this.tags = tags;
        return this;
    }

    public String getDate() {
        return date;
    }

    public ArticleDto setDate(String date) {
        this.date = date;
        return this;
    }
}
