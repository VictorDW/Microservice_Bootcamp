package com.pragma.bootcamp.configuration.util;


import com.pragma.bootcamp.domain.util.IMessageUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageUtil implements IMessageUtil {

  private final MessageSource messageSource;
  private final Logger loggerClass = LoggerFactory.getLogger(MessageUtil.class);
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
