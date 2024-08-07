package mystic.conduit.domain.comment.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SingleCommentDto {
    public CommentDto comment;
}
