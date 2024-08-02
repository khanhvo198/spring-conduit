package mystic.conduit.domain.article.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mystic.conduit.domain.profile.dto.ProfileDto;

import java.util.Date;

@Getter
@Setter
@Builder
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;
    private String[] tagList;
    private Date createdAt;
    private Date updatedAt;
    private Boolean favorited;
    private Integer favoritesCount;
    private ProfileDto author;
}
