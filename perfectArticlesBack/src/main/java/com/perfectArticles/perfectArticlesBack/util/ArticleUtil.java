package com.perfectArticles.perfectArticlesBack.util;

import com.perfectArticles.perfectArticlesBack.domain.dto.ArticleDto;
import com.perfectArticles.perfectArticlesBack.domain.persistence.Article;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class ArticleUtil {

    public static Article articleDtoToPersistence(ArticleDto articleDto) {

        if (articleDto == null) {
            return new Article();
        }

        Article article = new Article()
                .setAuthor(articleDto.getAuthor())
                .setId(articleDto.getId())
                .setImageCaption(articleDto.getImageCaption())
                .setImageLocation(articleDto.getImageLocation())
                .setText(articleDto.getText())
                .setTitle(articleDto.getTitle())
                .setDate(new Date());

        return article;
    }

    public static ArticleDto articlePersistenceToDto(Article article) {

        if (article == null) {
            return new ArticleDto();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM / dd / yyyy");
        String date = dateFormat.format(article.getDate());

        ArticleDto articleDto = new ArticleDto()
                .setAuthor(article.getAuthor())
                .setId(article.getId())
                .setImageCaption(article.getImageCaption())
                .setImageLocation(article.getImageLocation())
                .setText(article.getText())
                .setTitle(article.getTitle())
                .setDate(date);

        String tagString = "";
        for (String tag : article.getTags()) {
            tagString = tagString + " " + tag;
        }
        articleDto.setTags(tagString);

        return articleDto;
    }

    public static List<String> generateArticleTags(String text) {

        List<String> tags = new ArrayList<>();

        if (text == null || text.isEmpty()) { //Empty article
            return tags;
        }

        Stream<String> streamWords = Arrays.stream(text.split(" "));

        Map<String, Integer> mapTags = new HashMap<>();
        streamWords.map(w -> w.replaceAll("\\W", "")) //replace all non word characters
                .map(String::toLowerCase)
                .filter(word -> !FinalsUtil.IGNORE_IN_TAG.contains(word)) //filter common words
                .forEach(word -> { //add to hash map of tags
                    if (mapTags.containsKey(word)) {
                        mapTags.put(word, mapTags.get(word) + 1);
                    } else {
                        mapTags.put(word, 1);
                    }
                });

        //priority queue with custom comparator
        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
            @Override
            public int compare(String tag1, String tag2) {
                if (mapTags.get(tag1).equals(mapTags.get(tag2))) {
                    return tag1.compareTo(tag2);
                }
                return mapTags.get(tag2) - mapTags.get(tag1);
            }
        });

        pq.addAll(mapTags.keySet());

        for (int i = 0; i < 3; i++) {
            String tag = pq.poll();
            if (tag != null) {
                tags.add(tag);
            }
        }

        return tags;
    }

}
