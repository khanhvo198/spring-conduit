package mystic.conduit.domain.comment.dto;

import lombok.*;
import mystic.conduit.domain.profile.dto.ProfileDto;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDto {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProfileDto author;
}
