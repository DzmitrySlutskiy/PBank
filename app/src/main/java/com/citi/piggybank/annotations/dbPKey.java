package com.citi.piggybank.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * dbPKey
 * Version information
 * Date: 18.01.2015
 * Time: 12:59
 * Created by Dzmitry Slutskiy.
 */
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface dbPKey {
    public static String DATA_TYPE = "INTEGER PRIMARY KEY AUTOINCREMENT";
}
