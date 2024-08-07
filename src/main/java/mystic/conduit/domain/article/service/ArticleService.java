package mystic.conduit.domain.article.service;

import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.dto.UpdateArticleDto;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ArticleService {
    MultipleArticlesDto getArticles (String tag, String author, String favoritedBy, AuthUserDetails auth);

    MultipleArticlesDto getFeedArticles(AuthUserDetails auth);

    SingleArticleDto createArticle(CreateArticleDto article, AuthUserDetails auth);

    SingleArticleDto getArticle(String slug, AuthUserDetails auth);

    SingleArticleDto updateArticle(String slug, UpdateArticleDto article, AuthUserDetails auth);

}
