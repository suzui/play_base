package annotations;

import interfaces.BaseEnum;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(FIELD)
public @interface DataField {
    
    String name() default "";
    
    String demo() default "";
    
    String comment() default "";
    
    Class<? extends BaseEnum>[] enums() default {};
    
    boolean enable() default true;
}
