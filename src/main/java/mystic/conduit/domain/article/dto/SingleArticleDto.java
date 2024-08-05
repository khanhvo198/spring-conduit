package mystic.conduit.domain.article.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SingleArticleDto {
    private ArticleDto article;
}
