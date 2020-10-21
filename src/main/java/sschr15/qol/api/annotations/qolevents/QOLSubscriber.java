package sschr15.qol.api.annotations.qolevents;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <s>for when forge events aren't good enough</s>
 * <p />
 * Subscribe to custom QOL events, all specially called by us!
 * (or dirty impostors that want to fake being us)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QOLSubscriber {
    /** The mod ID. */
    String value();

    boolean disableSnooper() default false;

    /** An optional priority. The lower the number, the higher the priority */
    int priority() default 0;

    /**
     * Run code in a static method during the Coremod initialization phase.
     * <p />
     * This can only be found if it's in a class annotated with {@link QOLSubscriber}, and it must
     * be on a method with a signature of {@code ()V}
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface RunOnCoremodLoad {}

    /**
     * Want to add your own witty comment? Subscribe with this to have
     * a chance for the method to be called! If it's called, your
     * method will give its own witty comment!
     * <p />
     * This can only be found if it's in a class annotated with {@link QOLSubscriber}, and it must
     * be on a method with a signature of <code>(){@link String Ljava/lang/String;}</code>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface WittyCommentSubscriber {}

    /**
     * Transform any class you want! (Danger, this is dangerous!)
     * <p />
     * This can only be found if it's in a class annotated with {@link QOLSubscriber}, and it must
     * be on a method with a signature of <code>
     *     ({@link jdk.internal.org.objectweb.asm.tree.ClassNode Ljdk/internal/org/objectweb/asm/tree/ClassNode;}
     *     {@link String Ljava/lang/String;})V
     * </code>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface TransformClass {
        String[] value();
    }
}
