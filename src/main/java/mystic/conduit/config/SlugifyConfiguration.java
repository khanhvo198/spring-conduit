package mystic.conduit.config;

import com.github.slugify.Slugify;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlugifyConfiguration {
    @Bean
    public Slugify slugify() {
        return Slugify.builder().build();
    }
}
