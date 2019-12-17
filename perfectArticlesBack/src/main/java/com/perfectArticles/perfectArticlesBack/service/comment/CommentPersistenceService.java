package com.perfectArticles.perfectArticlesBack.service.comment;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Article;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Comment;
import com.perfectArticles.perfectArticlesBack.service.article.ArticlePersistenceService;
import com.perfectArticles.perfectArticlesBack.service.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentPersistenceService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticlePersistenceService articlePersistenceService;

    public CommentPersistenceService(@Autowired CommentRepository commentRepository,
                                     @Autowired ArticlePersistenceService articlePersistenceService) {
        this.commentRepository = commentRepository;
        this.articlePersistenceService = articlePersistenceService;
    }

    @Transactional
    public AddResponse addComment(Comment comment, Integer id) {

        AddResponse addResponse = new AddResponse();

        Article article = articlePersistenceService.getArticleById(id);
        if (article == null) {
            return addResponse.setMessage("Failure, Article ID: " + id + " does not exist to add a comment to").setSuccess(false);
        }

        comment.setDate(new Date());

        article.addComment(comment);

        articlePersistenceService.updateArticle(article); //Save from parent
        commentRepository.flush();

        return addResponse;
    }

    public Comment getCommentById(Integer id) {

        Comment comments = null;

        if (id != null) {
            if (commentRepository.existsById(id)) {
                comments = commentRepository.getOne(id);
            }
        }

        return comments;
    }


    public List<Comment> getAllCommentsForArticle(Integer idArticle) {

        List<Comment> comments = new ArrayList<>();

        if (idArticle != null) {
            comments = commentRepository.findAllArticleCommentsOrderedByDate(idArticle);
        }

        return comments;
    }

    public Page<Comment> getTopComment(Integer id, Pageable pageable) {

        Page<Comment> comments = null;

        if (pageable != null) {
            comments = commentRepository.findCommentWithPagination(id, pageable);
        }

        return comments;
    }

    @Transactional
    public AddResponse updateCommentLikesDislikes(Comment comment) {

        AddResponse addResponse = new AddResponse();

        if (comment == null) {
            return addResponse.setMessage("Failure, cannot have null comment").setSuccess(false);
        }

        commentRepository.save(comment);
        commentRepository.flush();

        return addResponse;
    }
}
