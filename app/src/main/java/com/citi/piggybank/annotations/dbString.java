package com.citi.piggybank.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * dbString
 * Version information
 * 12.01.2015
 * Created by Dzmitry Slutskiy.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbString {
    public static String DATA_TYPE = "TEXT";
}
