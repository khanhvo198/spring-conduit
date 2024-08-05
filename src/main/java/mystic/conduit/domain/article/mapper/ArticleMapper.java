package mystic.conduit.domain.article.mapper;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.dto.ArticleDto;
import mystic.conduit.domain.article.dto.SingleArticleDto;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.service.ProfileService;
import mystic.conduit.domain.tag.entity.TagEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ArticleMapper {
    private final ModelMapper mapper;
    private final ProfileService profileService;

    public ArticleDto mapToArticle (ArticleEntity article) {
        return mapper.map(article, ArticleDto.class);
    }

    public SingleArticleDto mapToSingleArticle (ArticleEntity article, AuthUserDetails auth, Integer favoritesCount, Boolean favorited) {
        ProfileDto author = profileService.getProfile(auth.getUsername(), auth);
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

}
