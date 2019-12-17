package com.perfectArticles.perfectArticlesBack.domain.persistence;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "userName")
    private String userName;

    @Column(name = "text", length = 100000)
    private String text;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "dislikes")
    private Integer dislikes;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    public Integer getId() {
        return id;
    }

    public Comment setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Comment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getLikes() {
        return likes;
    }

    public Comment setLikes(Integer likes) {
        this.likes = likes;
        return this;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public Comment setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
        return this;
    }

    public Article getArticle() {
        return article;
    }

    public Comment setArticle(Article article) {
        this.article = article;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Comment setDate(Date date) {
        this.date = date;
        return this;
    }
}
