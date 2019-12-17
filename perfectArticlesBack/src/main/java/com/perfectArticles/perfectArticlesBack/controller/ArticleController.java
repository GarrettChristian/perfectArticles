package com.perfectArticles.perfectArticlesBack.controller;

import com.perfectArticles.perfectArticlesBack.domain.AddResponse;
import com.perfectArticles.perfectArticlesBack.domain.dto.ArticleDto;
import com.perfectArticles.perfectArticlesBack.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    public ArticleController(@Autowired ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles/full")
    public List<ArticleDto> getAllArticles() {

        List<ArticleDto> articleDtos = articleService.getAllArticles();

        return articleDtos;
    }

    @GetMapping("/{id}/id")
    public ArticleDto getArticle(@PathVariable Integer id) {

        ArticleDto articleDto = articleService.getArticleById(id);

        return articleDto;
    }

    @GetMapping("/{amount}/amount")
    public List<ArticleDto> getArticlesByDate(@PathVariable Integer amount) {

        return articleService.getRecentArticles(3);
    }

    @PostMapping("/articles")
    public AddResponse loadExampleArticles() {

        return articleService.loadTestArticles();
    }

}
