package annotations;

import interfaces.BaseEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(PARAMETER)
public @interface ParamField {
    
    String key() default "";
    
    String name() default "";
    
    String demo() default "";
    
    String comment() default "";
    
    Class<? extends BaseEnum>[] enums() default {};
    
    boolean required() default true;
    
}
