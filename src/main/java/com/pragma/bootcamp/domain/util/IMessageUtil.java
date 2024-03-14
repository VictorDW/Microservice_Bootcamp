package com.pragma.bootcamp.domain.util;

public interface IMessageUtil {

  String getMessage(String code);
  String getMessage(String code, Object ...arg);
}
