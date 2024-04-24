package com.pragma.bootcamp.configuration.jwt;

public interface IJwtServiceAuthorization {

  String getSubjectFromToken(String token);
  String getRoleFromToken(String token);
}
