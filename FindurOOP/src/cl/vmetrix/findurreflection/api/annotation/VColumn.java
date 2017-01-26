package cl.vmetrix.findurreflection.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // Make this annotation accessible at runtime via reflection.
@Target({ElementType.FIELD})
public @interface VColumn {
	String name() default "";
	String title() default "";
}
