package mystic.conduit.domain.article.controller;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.service.ArticleService;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/")
    public ResponseEntity<MultipleArticlesDto> getArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favorited
    ) {
        return ResponseEntity.ok(articleService.getArticles(tag, author, favorited));
    }


    @PostMapping
    public ResponseEntity<SingleArticleDto> createArticle(@RequestBody CreateArticleDto article, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.createArticle(article, auth));
    };


    @GetMapping("/feed")
    public String getFeed() {
        return "Feed";
    }


}
