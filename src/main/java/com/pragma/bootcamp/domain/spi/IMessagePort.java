package com.pragma.bootcamp.domain.spi;

public interface IMessagePort {

  String getMessage(String code);
  String getMessage(String code, Object ...arg);
}
