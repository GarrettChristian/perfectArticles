package com.perfectArticles.perfectArticlesBack.domain.persistence;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "author")
    private String author;

    @Column(name = "text", length = 100000)
    private String text;

    @Column(name = "imageLocation")
    private String imageLocation;

    @Column(name = "imageCaption")
    private String imageCaption;

    @Column(name = "tags")
    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Integer getId() {
        return id;
    }

    public Article setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Article setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Article setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getText() {
        return text;
    }

    public Article setText(String text) {
        this.text = text;
        return this;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Article setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public Article setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public Article setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Article setComments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Article addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.add(comment);
        comment.setArticle(this);
        return this;
    }
}
