package com.pragma.bootcamp.configuration.util;

public interface IMessageUtil {

  String getMessage(String code);
  String getMessage(String code, Object ...arg);
}
