package sschr15.qol.api.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * An annotation to define Mixin JSON files to use
 * @see #value() MixinConfig.value
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MixinConfig {
    /**
     * A list of configuration files to use, as found on the classpath. Most
     * devs shouldn't have to worry about the path, as it is probably just in
     * the root of the {@code resources} folder. When in a subfolder, it may
     * be necessary to list the path to the file, which can be a bit of a problem
     * due to Windows using a different path separator. For best compatibility,
     * I suggest keeping all of these in the root of your mod's {@code resources}
     * folder or describing the file as a path using
     * {@link Paths#get(String, String...) Paths.get()} (Java 8) or
     * {@code Path.of()} (at least Java 11)
     * then getting the path with {@link Path#toString() toString()}.
     * <br/>
     * This works with priority as well. The earlier in the list, the higher the
     * priority for your set of Mixin configs. All configs are loaded at the same
     * time according to the set {@link #priority() priority}. The default
     * priority is 128.
     */
    String[] value();

    /**
     * A preferred priority value, anywhere from {@link Byte#MIN_VALUE -128} to {@link Byte#MAX_VALUE 127}.
     * The lower the value, the higher the priority.
     */
    // using byte because it is 8 bits
    byte priority() default 0;
}
