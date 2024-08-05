package mystic.conduit.domain.article.service;

import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.ArticleDto;
import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.mapper.ArticleMapper;
import mystic.conduit.domain.article.repository.ArticleRepository;
import mystic.conduit.domain.article.specification.ArticleSpecification;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final Slugify slugify;

    @Override
    public MultipleArticlesDto getArticles(String tag, String author, String favoritedBy) {
        Specification<ArticleEntity> specifications = Specification.where(ArticleSpecification.hasTag(tag))
                .and(ArticleSpecification.hasAuthor(author))
                .and(ArticleSpecification.isFavoritedBy(favoritedBy));

        List<ArticleEntity> articles = articleRepository.findAll(specifications);
        Integer articlesCount = articles.size();

        List<ArticleDto> articleDtos = articles.stream().map(articleMapper::mapToArticle).toList();

        return MultipleArticlesDto.builder().articles(articleDtos).articlesCount(articlesCount).build();
    }

    @Override
    public SingleArticleDto createArticle(CreateArticleDto article, AuthUserDetails auth) {
        String slug = slugify.slugify(article.getTitle());
        UserEntity author = UserEntity.builder().id(auth.getId()).build();
        ArticleEntity newArticle = ArticleEntity.builder()
                .slug(slug)
                .title(article.getTitle())
                .body(article.getBody())
                .description(article.getDescription())
                .author(author)
                .tagList(article.getTagList().stream().map(tag -> TagEntity.builder().name(tag).build()).toList())
                .build();

        newArticle = articleRepository.save(newArticle);
        return articleMapper.mapToSingleArticle(newArticle, auth, 0, false);
    }


}
