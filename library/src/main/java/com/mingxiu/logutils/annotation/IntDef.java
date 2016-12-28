package com.mingxiu.logutils.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * 1.@Target 表示该注解目标,可能的 ElemenetType 参数包括:
 * ElemenetType.CONSTRUCTOR 构造器声明
 * ElemenetType.FIELD 域声明(包括 enum 实例)
 * ElemenetType.LOCAL_VARIABLE 局部变量声明
 * ElemenetType.METHOD 方法声明
 * ElemenetType.PACKAGE 包声明
 * ElemenetType.PARAMETER 参数声明
 * ElemenetType.TYPE 类，接口(包括注解类型)或enum声明
 * <p>
 * 2. @Retention 表示该注解的生命周期,可选的 RetentionPolicy 参数包括
 * RetentionPolicy.SOURCE 注解将被编译器丢弃
 * RetentionPolicy.CLASS 注解在class文件中可用，但会被VM丢弃
 * RetentionPolicy.RUNTIME VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
 * <p>
 * 3. @Documented 指示将此注解包含在 javadoc 中
 * <p>
 * 4. @Inherited 指示允许子类继承父类中的注解
 */
@Retention(SOURCE)
@Target({ANNOTATION_TYPE})
public @interface IntDef {
    /**
     * Defines the allowed constants for this element
     * 定义此元素的允许常数
     */
    long[] value() default {};

    /**
     * Defines whether the constants can be used as a flag, or just as an enum (the default)
     * 定义常量是否可以用作标志，或者只是作为枚举（默认值）
     */
    boolean flag() default false;
}