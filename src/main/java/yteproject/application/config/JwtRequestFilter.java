package yteproject.application.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import yteproject.application.entities.Users;
import yteproject.application.services.CustomUserDetailsService;
import yteproject.application.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    private final CustomUserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer")) {
            String jwtToken = authorization.substring(7);
            String username = JwtUtil.extractUsername(jwtToken,secretKey);
            Users users = (Users) userDetailsService.loadUserByUsername(username);

            var authenticationToken = new UsernamePasswordAuthenticationToken(users,null,users.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
        return;
    }
}
