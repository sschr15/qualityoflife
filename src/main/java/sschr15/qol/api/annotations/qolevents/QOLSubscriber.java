package sschr15.qol.api.annotations.qolevents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <s>for when forge events aren't good enough</s>
 * <p />
 * Subscribe to custom QOL events, all specially called by us!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QOLSubscriber {
    /** The mod ID. */
    String value();

    /** An optional priority. The lower the number, the higher the priority */
    int priority() default 0;
}
