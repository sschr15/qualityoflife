package sschr15.qol.api.annotations.qolevents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Run code in a static method during the Coremod initialization phase.
 * <p />
 * This can only be found if it's in a class annotated with {@link QOLSubscriber}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunOnCoremodLoad {
}
