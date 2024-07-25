package mystic.conduit.config;

import mystic.conduit.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtUtilsConfiguration {

    @Bean
    public JwtUtils getJwtUtils (
            @Value("${conduit.auth.token.sign-key}") String secretKey,
            @Value("${conduit.auth.token.expiration}") Long expiration) throws Exception {
        return new JwtUtils(secretKey, expiration);
    }
}
