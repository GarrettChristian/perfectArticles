package com.perfectArticles.perfectArticlesBack.service.repositories;

import com.perfectArticles.perfectArticlesBack.domain.persistence.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "SELECT * FROM comment WHERE article_id = ?1 ORDER BY date desc",
            nativeQuery = true)
    List<Comment> findAllArticleCommentsOrderedByDate(Integer id);

    @Query(value = "SELECT * FROM Comment WHERE article_id = ?1 ORDER BY date desc",
            nativeQuery = true)
    Page<Comment> findCommentWithPagination(Integer id, Pageable pageable);

}