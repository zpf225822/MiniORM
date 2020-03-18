package cn.cherry.orm.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//注解的使用类型  作用到字段上
@Target(ElementType.FIELD)
public @interface ORMId {
}
