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
    String value();
}
