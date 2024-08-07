package mystic.conduit.domain.article.service;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.CreateArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.dto.UpdateArticleDto;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.entity.FavoriteEntity;
import mystic.conduit.domain.article.mapper.ArticleMapper;
import mystic.conduit.domain.article.repository.ArticleRepository;
import mystic.conduit.domain.article.repository.FavoriteRepository;
import mystic.conduit.domain.article.specification.ArticleSpecification;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.ArticleNotFoundException;
import mystic.conduit.exception.SlugTakenException;
import mystic.conduit.exception.UserNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService{
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final Slugify slugify;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public MultipleArticlesDto getFeedArticles(AuthUserDetails auth, Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(offset / limit, limit);
        UserEntity user = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);
        List<Long> followings = user.getFollowing().stream().map(UserEntity::getId).toList();

        List<ArticleEntity> articles = articleRepository.findByAuthorIdIn(followings, pageable);
        return articleMapper.mapToMultipleArticles(articles, auth);
    }

    @Override
    public MultipleArticlesDto getArticles(String tag, String author, String favoritedBy, AuthUserDetails auth, Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of( offset / limit, limit);

        Specification<ArticleEntity> specifications = Specification.where(ArticleSpecification.hasTag(tag))
                .and(ArticleSpecification.hasAuthor(author))
                .and(ArticleSpecification.isFavoritedBy(favoritedBy));

        List<ArticleEntity> articles = articleRepository.findAll(specifications, pageable).stream().toList();
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


    @Override
    public SingleArticleDto favoriteArticle(String slug, AuthUserDetails auth) {
        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        UserEntity user = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);

        Optional<FavoriteEntity> favoriteFound = favoriteRepository.findByArticleIdAndUserId(article.getId(), user.getId());

        if (favoriteFound.isPresent()) {
            return articleMapper.mapToSingleArticle(article, auth);
        }

        article.getFavoritedBy().add(FavoriteEntity.builder().article(article).user(user).build());
        article = articleRepository.save(article);

        return articleMapper.mapToSingleArticle(article, auth);
    }

    @Override
    public SingleArticleDto unFavoriteArticle(String slug, AuthUserDetails auth) {
        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        UserEntity user = userRepository.findById(auth.getId()).orElseThrow(UserNotFoundException::new);

        Optional<FavoriteEntity> favoriteFound = article.getFavoritedBy().stream().filter(favoriteEntity -> favoriteEntity.getArticle().getId().equals(article.getId()) && favoriteEntity.getUser().getId().equals(user.getId())).findAny();

        if (favoriteFound.isEmpty()) {
            return articleMapper.mapToSingleArticle(article, auth);
        }

        article.getFavoritedBy().remove(favoriteFound.get());
        ArticleEntity updatedArticle = articleRepository.save(article);

        return articleMapper.mapToSingleArticle(updatedArticle, auth);
    }

    @Transactional
    @Override
    public void deleteArticle(String slug, AuthUserDetails auth) {
        articleRepository.findBySlugAndAuthorId(slug, auth.getId()).orElseThrow(ArticleNotFoundException::new);
        articleRepository.deleteBySlug(slug);
    }
}
