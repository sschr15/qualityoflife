package sschr15.qol.api.annotations.qolevents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Want to add your own witty comment? Subscribe with this to have
 * a chance for the method to be called! If it's called, your
 * method will give its own witty comment!
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface WittyCommentSubscriber {
}
