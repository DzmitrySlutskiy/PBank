package com.citi.piggybank.provider;

import android.net.Uri;
import android.provider.BaseColumns;

import com.citi.piggybank.annotations.dbInteger;
import com.citi.piggybank.annotations.dbPKey;
import com.citi.piggybank.annotations.dbString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;


/**
 * BaseContract
 * Version 1.0
 * 12.11.2014
 * Created by Dzmitry Slutskiy.
 */
public class BaseContract implements BaseColumns {

    public static final String AUTHORITY = "com.citi.piggybank.provider";
    public static final String ERROR_DESCRIPTION = "ErrorDescription";
    public static final String SCHEMA = "content://";

    @dbPKey
    public static final String COLUMN_ID = _ID;

    public static final Uri AUTHORITY_URI = Uri.parse(SCHEMA + AUTHORITY);

    public static String buildCreateSQL(Class clazz, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS ")
                .append(tableName)
                .append(" (");
        String fieldsQuery = "";
        Field[] fields = clazz.getFields();
        for (Field field : fields) {

            Annotation[] annotations = field.getAnnotations();
            if (field.getType().equals(String.class) &&
                    Modifier.isStatic(field.getModifiers()) && annotations != null && annotations.length != 0) {

                fieldsQuery = buildFieldDefForAnnotation(fieldsQuery, dbPKey.class, dbPKey.DATA_TYPE, field);
                fieldsQuery = buildFieldDefForAnnotation(fieldsQuery, dbString.class, dbString.DATA_TYPE, field);
                fieldsQuery = buildFieldDefForAnnotation(fieldsQuery, dbInteger.class, dbInteger.DATA_TYPE, field);
            }
        }
        builder.append(fieldsQuery)
                .append(")");
        return builder.toString();
    }

    public static String buildFieldDefForAnnotation(String query, Class<? extends Annotation> annotationClass,
                                                    String dataType, Field field) {
        Annotation fieldAnnotation = field.getAnnotation(annotationClass);
        String fieldDefinition = null;
        if (fieldAnnotation != null) {
            try {
                fieldDefinition = field.get(null) + " " + dataType;
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }
        if (fieldDefinition != null) {
            if (query.length() > 0) {
                query += ", ";
            }
            query += fieldDefinition;
        }
        return query;
    }

    public static String[] buildAvailableColumns(Class clazz) {
        List<String> columnList = new ArrayList<>();

        Field[] fields = clazz.getFields();
        for (Field field : fields) {

            Annotation[] annotations = field.getAnnotations();
            int modifiers = field.getModifiers();
            if (field.getType().equals(String.class) &&
                    Modifier.isStatic(modifiers) &&
                    Modifier.isPublic(modifiers) &&
                    (annotations != null && annotations.length != 0)) {
                for (Annotation annotation : annotations) {
                    try {
                        if (annotation instanceof dbString ||
                                annotation instanceof dbInteger ||
                                annotation instanceof dbPKey) {
                            columnList.add((String) field.get(null));
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return columnList.toArray(new String[columnList.size()]);
    }
}
