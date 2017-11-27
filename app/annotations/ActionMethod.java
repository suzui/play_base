package annotations;

import vos.Data;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface ActionMethod {

	String name() default "";

	String param() default "";

	String except() default "";

	Class<? extends Data>[] clazz() default {};

}
