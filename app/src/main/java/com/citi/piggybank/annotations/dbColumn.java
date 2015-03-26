package com.citi.piggybank.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * dbColumn
 * Version information
 * 12.01.2015
 * Created by Dzmitry Slutskiy.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbColumn {
    public static final String DEFAULT = "SUB_OBJECT";

    String column() default DEFAULT;
}
