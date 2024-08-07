package mystic.conduit.domain.comment.service;

import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.comment.dto.CommentDto;
import mystic.conduit.domain.comment.dto.CreateCommentDto;
import mystic.conduit.domain.comment.dto.MultipleCommentsDto;
import mystic.conduit.domain.comment.dto.SingleCommentDto;

public interface CommentService {
    SingleCommentDto addComment(String slug, CreateCommentDto comment, AuthUserDetails auth);

    MultipleCommentsDto getComments(String slug, AuthUserDetails auth);

    void deleteComment(String slug, Long id, AuthUserDetails auth);

}
