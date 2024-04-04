package com.pragma.bootcamp.adapters.util;

import com.pragma.bootcamp.configuration.Constants;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ValidateFormatDate.class)
public @interface ValidDate {

  String message() default "{"+ Constants.INVALID_FORMAT_TO_PARSE_DATE +"}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
