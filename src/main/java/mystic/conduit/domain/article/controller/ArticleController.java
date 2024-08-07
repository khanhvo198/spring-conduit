package mystic.conduit.domain.article.controller;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.dto.UpdateArticleDto;
import mystic.conduit.domain.article.service.ArticleService;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<MultipleArticlesDto> getArticles(
            @RequestParam(value = "tag", required = false) String tag,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "favorited", required = false) String favorited,
            @AuthenticationPrincipal AuthUserDetails auth
    ) {
        return ResponseEntity.ok(articleService.getArticles(tag, author, favorited, auth));
    }

    @GetMapping("/feed")
    public ResponseEntity<MultipleArticlesDto> getFeedArticles(
            @AuthenticationPrincipal AuthUserDetails auth
    ) {
        return ResponseEntity.ok(articleService.getFeedArticles(auth));
    }


    @PostMapping
    public ResponseEntity<SingleArticleDto> createArticle(@RequestBody CreateArticleDto article, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.createArticle(article, auth));
    };


    @GetMapping("/{slug}")
    public ResponseEntity<SingleArticleDto> getArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.getArticle(slug, auth));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<SingleArticleDto> updateArticle(@PathVariable String slug, @RequestBody UpdateArticleDto article, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.updateArticle(slug, article, auth));
    };

    @PostMapping("/{slug}/favorite")
    public ResponseEntity<SingleArticleDto> favoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.favoriteArticle(slug, auth));
    }

    @DeleteMapping("/{slug}/favorite")
    public ResponseEntity<SingleArticleDto> unFavoriteArticle(@PathVariable String slug, @AuthenticationPrincipal AuthUserDetails auth) {
        return ResponseEntity.ok(articleService.unFavoriteArticle(slug, auth));
    }








}
