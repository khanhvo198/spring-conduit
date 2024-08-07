package mystic.conduit.domain.tag.service;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.tag.dto.TagDto;
import mystic.conduit.domain.tag.entity.TagEntity;
import mystic.conduit.domain.tag.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService{

    private final TagRepository tagRepository;

    @Override
    public TagDto getTags() {
        return TagDto.builder()
                        .tags(tagRepository.findAll().stream().map(TagEntity::getName).distinct().toList())
                        .build();
    }
}
