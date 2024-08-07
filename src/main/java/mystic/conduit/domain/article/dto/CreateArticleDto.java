package mystic.conduit.domain.article.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonTypeName("article")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
public class CreateArticleDto {
    private String title;
    private String description;
    private String body;
    private List<String> tagList;
}
