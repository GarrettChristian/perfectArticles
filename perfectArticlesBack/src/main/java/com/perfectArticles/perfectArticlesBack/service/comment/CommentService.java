package com.perfectArticles.perfectArticlesBack.service.comment;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.dto.CommentDto;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Comment;
import com.perfectArticles.perfectArticlesBack.util.CommentUtil;
import com.perfectArticles.perfectArticlesBack.util.FinalsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentPersistenceService commentPersistenceService;

    public CommentService(@Autowired CommentPersistenceService commentPersistenceService) {
        this.commentPersistenceService = commentPersistenceService;
    }

    public AddResponse addComment(Integer idArticle, CommentDto commentDto) {

        AddResponse addResponse = new AddResponse();

        if (commentDto == null) {
            return addResponse.setSuccess(false).setMessage("Failure, cannot have null commentDto");
        } else if (idArticle == null) {
            return addResponse.setSuccess(false).setMessage("Failure, cannot have null article id");
        }

        Comment comment = CommentUtil.commentDtoToPersistence(commentDto);

        if (!CommentUtil.isValidComment(comment)) {
            return addResponse.setSuccess(false).setMessage("Failure, user name and comment text must be > 0");
        }

        AddResponse addComment = commentPersistenceService.addComment(comment, idArticle);

        return addResponse.updateMessage(addComment.getMessage());

    }

    public List<CommentDto> getAllComments(Integer articleId) {

        List<Comment> comments = commentPersistenceService.getAllCommentsForArticle(articleId);

        List<CommentDto> commentDtos = comments.stream()
                .filter(Objects::nonNull)
                .map(CommentUtil::commentPersistenceToDto)
                .collect(Collectors.toList());

        return commentDtos;

    }

    public CommentDto getArticleCommentTop(Integer articleId) {

        Pageable pageable = PageRequest.of(0, 1, Sort.by("date").descending());

        Page<Comment> comments = commentPersistenceService.getTopComment(articleId, pageable);

        CommentDto commentDto = new CommentDto();

        if (comments.getNumberOfElements() == 1) {
            Comment comment = comments.getContent().get(0);
            commentDto = CommentUtil.commentPersistenceToDto(comment);
        } else {
            commentDto.setId(-1000); // for use in the front end to determine if there is a top comment for the article
        }

        return commentDto;

    }

    public CommentDto getCommentById(Integer commentId) {

        Comment comment = commentPersistenceService.getCommentById(commentId);

        CommentDto commentDto = CommentUtil.commentPersistenceToDto(comment);

        return commentDto;
    }

    public AddResponse updateCommentLikesDislikes(Integer id, String likeDislikes, Integer value) {

        AddResponse addResponse = new AddResponse();

        if (id == null) {
            return addResponse.setSuccess(false).setMessage("Failure, cannot have null id");
        } else if (likeDislikes == null) {
            return addResponse.setSuccess(false).setMessage("Failure, cannot have null likes dislikes option");
        } else if (value == null) {
            return addResponse.setSuccess(false).setMessage("Failure, cannot have null value");
        }

        Comment comment = commentPersistenceService.getCommentById(id);

        if (FinalsUtil.LIKES.equals(likeDislikes)) {
            if (comment.getLikes() != null) {
                comment.setLikes(comment.getLikes() + value);
                addResponse.updateMessage("Added " + value + " to likes");
            }
        } else {
            if (comment.getDislikes() != null) {
                comment.setDislikes(comment.getDislikes() + value);
                addResponse.updateMessage("Added " + value + " to dislikes");
            }
        }

        AddResponse updateComment = commentPersistenceService.updateCommentLikesDislikes(comment);

        return addResponse.updateMessage(updateComment.getMessage());

    }

}
