package com.mingxiu.logutils.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@Target({ANNOTATION_TYPE})
public @interface IntDef {
    /** Defines the allowed constants for this element
     * 定义此元素的允许常数*/
    long[] value() default {};

    /** Defines whether the constants can be used as a flag, or just as an enum (the default)
     * 定义常量是否可以用作标志，或者只是作为枚举（默认值）*/
    boolean flag() default false;
}