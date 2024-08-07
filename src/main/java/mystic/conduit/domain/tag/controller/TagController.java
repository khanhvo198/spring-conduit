package mystic.conduit.domain.tag.controller;

import lombok.AllArgsConstructor;
import mystic.conduit.domain.tag.dto.TagDto;
import mystic.conduit.domain.tag.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tags")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public TagDto getTags() {
        return tagService.getTags();
    }

}
