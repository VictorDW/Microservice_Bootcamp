package com.pragma.bootcamp.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.bootcamp.configuration.Constants;
import com.pragma.bootcamp.configuration.error.ExceptionManager;
import com.pragma.bootcamp.configuration.error.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class ExceptionConfig {

  private final Logger loggerClass = LoggerFactory.getLogger(ExceptionConfig.class);
  private static final String CONTENT_TYPE = "application/json";
  private static final String CHARACTER_ENCODING = "UTF-8";

  @Bean
  public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
    return (request, response, authException) -> {

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(CONTENT_TYPE);
      response.setCharacterEncoding(CHARACTER_ENCODING);

      Object errorToken = request.getSession().getAttribute("error_token");
      String message = Objects.isNull(errorToken) ?
          Constants.ACCESS_DENIED_MESSAGE :
          errorToken.toString();

        response.getWriter().write(this.convertExceptionToString(message, HttpStatus.UNAUTHORIZED));
    };
  }

  @Bean
  public AccessDeniedHandler permissionsAccessDeniedHandler() {
    return (request, response, accessDeniedException) -> {

      response.setStatus(HttpStatus.FORBIDDEN.value());
      response.setContentType(CONTENT_TYPE);
      response.setCharacterEncoding(CHARACTER_ENCODING);
      response.getWriter().write(this.convertExceptionToString(
          String.format("%s: %s", accessDeniedException.getMessage(), Constants.PERMISSIONS_ACCESS_DENIED_MESSAGE),
          HttpStatus.FORBIDDEN));
    };
  }

  private String convertExceptionToString(String message, HttpStatus status) {

    ExceptionResponse responseBody = ExceptionManager.generalExceptionHandler(message,status).getBody();
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.writeValueAsString(responseBody);
    } catch (JsonProcessingException e) {
      loggerClass.error(e.getMessage());
      return Constants.ACCESS_DENIED_MESSAGE;
    }
  }
}
