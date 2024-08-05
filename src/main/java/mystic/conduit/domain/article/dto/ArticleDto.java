package mystic.conduit.domain.article.dto;


import lombok.*;
import mystic.conduit.domain.profile.dto.ProfileDto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private String slug;
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean favorited;
    private Integer favoritesCount;
    private ProfileDto author;
}
