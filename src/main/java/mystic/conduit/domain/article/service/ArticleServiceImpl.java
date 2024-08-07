package mystic.conduit.domain.article.service;

import com.github.slugify.Slugify;
import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.dto.UpdateArticleDto;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.entity.FavoriteEntity;
import mystic.conduit.domain.article.mapper.ArticleMapper;
import mystic.conduit.domain.article.repository.ArticleRepository;
import mystic.conduit.domain.article.specification.ArticleSpecification;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.ArticleNotFoundException;
import mystic.conduit.exception.SlugTakenException;
import mystic.conduit.exception.UserNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final Slugify slugify;
    private final UserRepository userRepository;

    @Override
    public MultipleArticlesDto getFeedArticles(AuthUserDetails auth) {
        UserEntity user = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);
        List<Long> followings = user.getFollowing().stream().map(UserEntity::getId).toList();

        List<ArticleEntity> articles = articleRepository.findByAuthorIdIn(followings);
        return articleMapper.mapToMultipleArticles(articles, auth);
    }

    @Override
    public MultipleArticlesDto getArticles(String tag, String author, String favoritedBy, AuthUserDetails auth) {
        Specification<ArticleEntity> specifications = Specification.where(ArticleSpecification.hasTag(tag))
                .and(ArticleSpecification.hasAuthor(author))
                .and(ArticleSpecification.isFavoritedBy(favoritedBy));

        List<ArticleEntity> articles = articleRepository.findAll(specifications);
        return articleMapper.mapToMultipleArticles(articles, auth);
    }

    @Override
    public SingleArticleDto createArticle(CreateArticleDto article, AuthUserDetails auth) {
        String slug = slugify.slugify(article.getTitle());
        ArticleEntity found = articleRepository.findBySlug(slug).orElse(null);
        if (found != null) {
            throw new SlugTakenException();
        }

        UserEntity author = UserEntity.builder().id(auth.getId()).build();
        ArticleEntity newArticle = ArticleEntity.builder()
                .slug(slug)
                .title(article.getTitle())
                .body(article.getBody())
                .description(article.getDescription())
                .author(author)
                .build();

        List<TagEntity> tagList = new ArrayList<>();

        for (String tag : article.getTagList()) {
            tagList.add(TagEntity.builder().name(tag).article(newArticle).build());
        }
        newArticle.setTagList(tagList);

        newArticle = articleRepository.save(newArticle);
        return articleMapper.mapToSingleArticle(newArticle, auth);
    }

    @Override
    public SingleArticleDto getArticle(String slug, AuthUserDetails auth) {
        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        return articleMapper.mapToSingleArticle(article, auth);
    }


    @Override
    public SingleArticleDto updateArticle(String slug, UpdateArticleDto article, AuthUserDetails auth) {
        ArticleEntity found = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        if (article.getTitle() != null) {
            String newSlug = slugify.slugify(article.getTitle());
            ArticleEntity foundByNewSlug = articleRepository.findBySlug(newSlug).orElse(null);
            if (foundByNewSlug != null) {
                throw new SlugTakenException();
            }

            found.setTitle(article.getTitle());
            found.setSlug(newSlug);
        }

        if (article.getBody() != null) {
            found.setBody(article.getBody());
        }

        if (article.getDescription() != null) {
            found.setDescription(article.getDescription());
        }

        if (article.getTagList() != null) {
            found.setTagList(article.getTagList().stream().map(tag -> TagEntity.builder().article(found).name(tag).build()).toList());
        }

        ArticleEntity updatedArticle = articleRepository.save(found);

        return articleMapper.mapToSingleArticle(updatedArticle, auth);
    }
}
