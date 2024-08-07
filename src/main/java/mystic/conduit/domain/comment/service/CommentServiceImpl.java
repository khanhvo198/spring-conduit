package mystic.conduit.domain.comment.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.article.entity.ArticleEntity;
import mystic.conduit.domain.article.repository.ArticleRepository;
import mystic.conduit.domain.auth.entity.AuthUserDetails;
import mystic.conduit.domain.comment.dto.CreateCommentDto;
import mystic.conduit.domain.comment.dto.MultipleCommentsDto;
import mystic.conduit.domain.comment.dto.SingleCommentDto;
import mystic.conduit.domain.comment.entity.CommentEntity;
import mystic.conduit.domain.comment.mapper.CommentMapper;
import mystic.conduit.domain.comment.repository.CommentRepository;
import mystic.conduit.domain.user.entity.UserEntity;
import mystic.conduit.exception.ArticleNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Override
    public SingleCommentDto addComment(String slug, CreateCommentDto comment, AuthUserDetails auth) {

        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        CommentEntity newComment = CommentEntity
                                            .builder()
                                            .author(UserEntity.builder().id(auth.getId()).build())
                                            .article(article)
                                            .body(comment.getBody())
                                            .build();

        newComment = commentRepository.save(newComment);

        return commentMapper.mapToSingleComment(newComment, auth);
    }

    @Override
    public MultipleCommentsDto getComments(String slug, AuthUserDetails auth) {
        List<CommentEntity> comments = commentRepository.findByArticleSlug(slug);
        return commentMapper.mapToMultipleComments(comments, auth);
    }

    @Override
    public void deleteComment(String slug, Long id, AuthUserDetails auth) {
        ArticleEntity article = articleRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);

        Optional<CommentEntity> foundComment = article.getComments().stream().filter(commentEntity -> commentEntity.getId().equals(id)).findAny();

        if (foundComment.isEmpty()) {
            return;
        }

        article.getComments().remove(foundComment.get());

        articleRepository.save(article);

    }
}
