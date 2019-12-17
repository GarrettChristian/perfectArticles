package com.perfectArticles.perfectArticlesBack.util;

import com.perfectArticles.perfectArticlesBack.domain.dto.CommentDto;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Comment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentUtil {

    public static Comment commentDtoToPersistence(CommentDto commentDto) {

        if (commentDto == null) {
            return new Comment();
        }

        Comment comment = new Comment()
                .setText(commentDto.getText())
                .setDislikes(commentDto.getDislikes())
                .setLikes(commentDto.getLikes())
                .setUserName(commentDto.getUserName())
                .setId(commentDto.getId())
                .setDate(new Date());

        return comment;
    }

    public static CommentDto commentPersistenceToDto(Comment comment) {

        if (comment == null) {
            return new CommentDto();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a - MM / dd / yyyy");
        String date = dateFormat.format(comment.getDate());

        CommentDto commentDto = new CommentDto()
                .setText(comment.getText())
                .setDislikes(comment.getDislikes())
                .setLikes(comment.getLikes())
                .setUserName(comment.getUserName())
                .setId(comment.getId())
                .setDate(date);

        return commentDto;
    }

    public static boolean isValidComment(Comment comment) {

        if (comment.getText() == null || comment.getUserName() == null) {
            return false;
        }

        return (comment.getText().length() > 0 && comment.getUserName().length() > 0);

    }

}
