package com.perfectArticles.perfectArticlesBack.service.article;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Article;
import com.perfectArticles.perfectArticlesBack.service.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ArticlePersistenceService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticlePersistenceService(@Autowired ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public AddResponse addArticle(Article article) {

        AddResponse addResponse = new AddResponse();

        article.setDate(new Date());

        if (article.getId() != null) {
            if (articleRepository.existsById(article.getId())) {
                articleRepository.deleteById(article.getId());
                articleRepository.flush();
                addResponse.updateMessage("Removed old article with ID: " + article.getId());
            }
        }

        articleRepository.save(article);
        articleRepository.flush();

        return addResponse;
    }

    public List<Article> getAllArticles() {

        List<Article> articles = articleRepository.findAllOrderedDate();

        return articles;
    }

    public Article getArticleById(Integer id) {

        Article article = null;

        if (id != null) {
            if (articleRepository.existsById(id)) {
                article = articleRepository.getOne(id);
            }
        }

        return article;
    }

    public Page<Article> getRecentArticles(Pageable pageable) {

        Page<Article> articles = null;

        if (pageable != null) {
            articles = articleRepository.findAll(pageable);
        }

        return articles;
    }

    public void updateArticle(Article article) {

        articleRepository.save(article);
        articleRepository.flush();
    }

}
