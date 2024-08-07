package mystic.conduit.domain.comment.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MultipleCommentsDto {
    List<CommentDto> comments;
}
