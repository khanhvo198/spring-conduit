package mystic.conduit.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import mystic.conduit.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilter {
    private final JwtUtils jwtUtils;
    private final AuthenticationProvider authenticationProvider;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String headers = ((HttpServletRequest)request).getHeader(HttpHeaders.AUTHORIZATION);
        String token = headers.substring(7);
        System.out.println(token);

        boolean isValidToken = jwtUtils.validateToken(token);

        if (isValidToken) {
            String subject = jwtUtils.getSubject(token);
            Authentication auth = authenticationProvider.getAuthentication(subject);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
