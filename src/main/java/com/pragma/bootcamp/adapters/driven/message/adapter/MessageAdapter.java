package com.pragma.bootcamp.adapters.driven.message.adapter;


import com.pragma.bootcamp.domain.spi.IMessagePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;

import java.util.Locale;

@RequiredArgsConstructor
public class MessageAdapter implements IMessagePort {

  private final MessageSource messageSource;
  private final Logger loggerClass = LoggerFactory.getLogger(MessageAdapter.class);
  @Override
  public String getMessage(String code) {
    return message(code);
  }

  @Override
  public String getMessage(String code, Object... arg) {
    return message(code, arg);
  }

  private String message(String code,@Nullable Object ...arg) {
    try {
      return messageSource.getMessage(code, arg, Locale.getDefault());
    } catch (NoSuchMessageException e) {
      loggerClass.warn(e.getMessage());
    }
    return messageSource.getMessage("error.default.message", null, Locale.getDefault());
  }
}
