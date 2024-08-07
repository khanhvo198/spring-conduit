package mystic.conduit.domain.article.service;

import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.dto.UpdateArticleDto;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ArticleService {
    MultipleArticlesDto getArticles (String tag, String author, String favoritedBy, AuthUserDetails auth, Integer limit, Integer offset);

    MultipleArticlesDto getFeedArticles(AuthUserDetails auth, Integer limit, Integer offset);

    SingleArticleDto createArticle(CreateArticleDto article, AuthUserDetails auth);

    SingleArticleDto getArticle(String slug, AuthUserDetails auth);

    SingleArticleDto updateArticle(String slug, UpdateArticleDto article, AuthUserDetails auth);

    SingleArticleDto favoriteArticle(String slug, AuthUserDetails auth) ;

    SingleArticleDto unFavoriteArticle(String slug, AuthUserDetails auth);

    void deleteArticle(String slug, AuthUserDetails auth);
}
