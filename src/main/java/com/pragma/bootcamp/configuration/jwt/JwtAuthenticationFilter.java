package com.pragma.bootcamp.configuration.jwt;

import com.pragma.bootcamp.configuration.Constants;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final Logger loggerClass = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final IJwtServiceAuthorization jwtService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    request.getSession().removeAttribute("error_token");
    final var token = getTokenFromRequest(request);

    if(token.isEmpty()) {
      request.getSession().setAttribute("error_token_message", Constants.TOKEN_NOT_FOUND_MESSAGE);
      filterChain.doFilter(request, response);
      return;
    }

    try {
      Authentication authenticatedUser = extractAuthenticatedUserFromToken(token.get());
      updateSecurityContext(authenticatedUser);

    } catch (JwtException e) {
      loggerClass.error("INVALID TOKEN: %s".formatted(e.getMessage()));
      request.getSession().setAttribute("error_token", e.getMessage());

    } finally {
      filterChain.doFilter(request, response);
    }

  }

  private Optional<String> getTokenFromRequest(HttpServletRequest request) {

    final String authHeader = request.getHeader("Authorization");

    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return Optional.of(authHeader.substring(7));
    }
    return Optional.empty();
  }

  private Authentication extractAuthenticatedUserFromToken(String token) {

    String subject = jwtService.getSubjectFromToken(token);
    String roleFromUser = jwtService.getRoleFromToken(token);
    var grantedAuthority = generateGrantedAuthorityForSecurityContext(roleFromUser);

    return new UsernamePasswordAuthenticationToken(
        subject,
        null,
        grantedAuthority);
  }

  private void updateSecurityContext(Authentication authenticatedUser) {
    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
  }

  private List<GrantedAuthority> generateGrantedAuthorityForSecurityContext(String rolesFromUser) {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+rolesFromUser));
  }
}
