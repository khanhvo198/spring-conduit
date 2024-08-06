package mystic.conduit.domain.article.mapper;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.ArticleDto;
import mystic.conduit.domain.article.dto.MultipleArticlesDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.entity.FavoriteEntity;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.service.ProfileService;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ArticleMapper {
    private final ModelMapper mapper;
    private final ProfileService profileService;
    private final UserRepository userRepository;

    public ArticleDto mapToArticle (ArticleEntity article) {
        return mapper.map(article, ArticleDto.class);
    }

    public SingleArticleDto mapToSingleArticle (ArticleEntity article, AuthUserDetails auth) {
        String username = userRepository.findById(article.getAuthor().getId()).orElseThrow(UserNotFoundException::new).getUsername();
        ProfileDto author = profileService.getProfile(username, auth);

        List<FavoriteEntity> favorites = article.getFavoriteBy();
        boolean favorited = false;
        int favoritesCount = 0;
        if (favorites != null) {
            favorited = favorites.stream().anyMatch(favoriteEntity -> favoriteEntity.getUser().getId().equals(auth.getId()));
            favoritesCount = favorites.size();
        }

        ArticleDto articleDto =  ArticleDto.builder()
                .slug(article.getSlug())
                .body(article.getBody())
                .title(article.getTitle())
                .description(article.getDescription())
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .author(author)
                .tagList(article.getTagList().stream().map(TagEntity::getName).toList())
                .favoritesCount(favoritesCount)
                .favorited(favorited).build();
        return SingleArticleDto.builder().article(articleDto).build();
    }

    public MultipleArticlesDto mapToMultipleArticles(List<ArticleEntity> articles, AuthUserDetails auth) {
        Integer articlesCount = articles.size();
        List<ArticleDto> articleDtos = articles.stream().map(article -> mapToSingleArticle(article, auth).getArticle()).toList();
        return MultipleArticlesDto.builder().articles(articleDtos).articlesCount(articlesCount).build();
    };


}
