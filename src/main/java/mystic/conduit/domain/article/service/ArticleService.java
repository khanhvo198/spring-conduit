package mystic.conduit.domain.article.service;

import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import org.springframework.stereotype.Service;


public interface ArticleService {
    MultipleArticlesDto getArticles (String tag, String author, String favoritedBy);

    SingleArticleDto createArticle(CreateArticleDto article, AuthUserDetails auth);

}
