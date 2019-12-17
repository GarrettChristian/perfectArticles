package com.perfectArticles.perfectArticlesBack.service.repositories;

import com.perfectArticles.perfectArticlesBack.domain.persistence.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "SELECT * FROM article ORDER BY date desc",
            nativeQuery = true)
    List<Article> findAllOrderedDate();

}