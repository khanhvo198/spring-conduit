package mystic.conduit.domain.article.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class MultipleArticlesDto {
    private List<ArticleDto> articles;
    private Integer articlesCount;
}
