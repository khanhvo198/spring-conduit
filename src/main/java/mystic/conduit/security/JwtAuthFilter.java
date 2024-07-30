package mystic.conduit.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mystic.conduit.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {
    private final JwtUtils jwtUtils;
    private final AuthenticationProvider authenticationProvider;
    private final String PREFIX_TOKEN = "Token ";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional.ofNullable(((HttpServletRequest) request).getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith(PREFIX_TOKEN))
                .map(authHeader -> authHeader.substring(PREFIX_TOKEN.length()))
                .map(jwtUtils::getSubject)
                .map(authenticationProvider::getAuthentication)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);

        filterChain.doFilter(request, response);
    }
}
