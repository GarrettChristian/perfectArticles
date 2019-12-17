package com.perfectArticles.perfectArticlesBack.controller;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.dto.CommentDto;
import com.perfectArticles.perfectArticlesBack.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(@Autowired CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{idArticle}/id_article")
    public List<CommentDto> getArticleComments(@PathVariable Integer idArticle) {

        List<CommentDto> commentDtoList = commentService.getAllComments(idArticle);

        return commentDtoList;
    }

    @GetMapping("/top/{idArticle}/id_article")
    public CommentDto getArticleCommentTop(@PathVariable Integer idArticle) {

        CommentDto commentDto = commentService.getArticleCommentTop(idArticle);

        return commentDto;
    }

    @GetMapping("/{id}/id")
    public CommentDto getCommentById(@PathVariable Integer id) {

        CommentDto commentDto = commentService.getCommentById(id);

        return commentDto;
    }

    @PostMapping("/comment/{idArticle}/id_article")
    public AddResponse addCommentToArticle(@PathVariable Integer idArticle,
                                           @RequestBody CommentDto commentDto) {

        AddResponse addResponse = commentService.addComment(idArticle, commentDto);

        return addResponse;
    }

    @PostMapping("{id}/id/{likeDislikes}/like_dislikes/{value}/value")
    public AddResponse changeCommentLikeDislikes(@PathVariable Integer id,
                                                 @PathVariable String likeDislikes,
                                                 @PathVariable Integer value) {

        AddResponse addResponse = commentService.updateCommentLikesDislikes(id, likeDislikes, value);

        return addResponse;
    }

}
