package mystic.conduit.domain.article.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;

@RestController("/articles")
@AllArgsConstructor
public class ArticleController {

    @GetMapping("/")
    public String getArticles () {
        return "Hello world";
    }
}
