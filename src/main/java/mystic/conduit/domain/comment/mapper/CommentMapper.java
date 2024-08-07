package mystic.conduit.domain.comment.mapper;


import lombok.AllArgsConstructor;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.comment.dto.CommentDto;
import mystic.conduit.domain.comment.dto.MultipleCommentsDto;
import mystic.conduit.domain.comment.dto.SingleCommentDto;
import mystic.conduit.domain.comment.entity.CommentEntity;
import mystic.conduit.domain.profile.dto.ProfileDto;
import mystic.conduit.domain.profile.dto.SingleProfileDto;
import mystic.conduit.domain.profile.service.ProfileService;
import mystic.conduit.domain.user.repository.UserRepository;
import mystic.conduit.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CommentMapper {
    private final ModelMapper mapper;
    private final ProfileService profileService;
    private final UserRepository userRepository;

    public SingleCommentDto mapToSingleComment(CommentEntity comment, AuthUserDetails auth) {
        String username = userRepository.findById(comment.getAuthor().getId()).orElseThrow(UserNotFoundException::new).getUsername();
        ProfileDto author = profileService.getProfile(username, auth).getProfile();
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        commentDto.setAuthor(author);
        return SingleCommentDto.builder().comment(commentDto).build();
    }


    public MultipleCommentsDto mapToMultipleComments(List<CommentEntity> comments, AuthUserDetails auth) {
        return MultipleCommentsDto
                    .builder()
                    .comments(comments.stream().map(commentEntity -> mapToSingleComment(commentEntity, auth).getComment()).toList())
                    .build();
    }
}
