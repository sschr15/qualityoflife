package sschr15.qol.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Some tools to help with reflection.
 *
 * @author sschr15
 */
@SuppressWarnings("unchecked")
public class ReflectionTool {
    static Logger atLogger = LogManager.getLogger("ReflectionTool");

    /**
     * Basic class containing data. This allows all methods in
     * this class to return non-null, but still identify if there
     * was an error.
     * <br/>
     * If there was an error, {@link #error} will be true. Otherwise,
     * it'll be false.
     * <br/>
     * To get the data, simply get the {@link #data} field.
     *
     * @param <T> the type of data
     */
    public static final class Data<T> {
        public final T data;
        public final boolean error;
        public Data(T data) {
            this.data = data;
            this.error = false;
        }
        private Data() {
            this.data = null;
            this.error = true;
        }
    }

    /**
     * Get any field from a class
     *
     * @param inst      an instance of the target class
     * @param fieldName the string name of the field
     * @return the field contents as a {@link Data} object
     */
    public static <T, C> Data<T> getField(C inst, String fieldName, boolean isStatic) {
        try {
            Field field = inst.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return new Data<>((T) field.get(isStatic ? null : inst));
        } catch (Throwable t) {
            atLogger.error("Could not get field " + fieldName, t);
            return new Data<>();
        }
    }

    /**
     * Set a field in any class
     *
     * @param inst      an instance of the target class
     * @param fieldName the string name of the field
     * @param newState  the value you want to set
     * @return {@code true} if there was no error
     */
    public static <T, C> boolean setField(C inst, String fieldName, T newState, boolean isStatic) {
        try {
            Field field = inst.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(isStatic ? null : inst, newState);
            return true;
        } catch (Throwable t) {
            atLogger.error("Could not set " + fieldName + " to " + newState.toString());
            return false;
        }
    }

    /**
     * Set a field in any class. If the field is
     * final, it will still overwrite the previous
     * value. :)
     *
     * @param inst      an instance of the target class
     * @param fieldName the string name of the field
     * @param newState  the value you want to set
     * @return {@code true} if there was no error
     */
    public static <T, C> boolean setFieldOverrideFinal(C inst, String fieldName, T newState, boolean isStatic) {
        try {
            Field field = inst.getClass().getDeclaredField(fieldName);

            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(isStatic ? null : inst, newState);
            return true;
        } catch (Throwable t) {
            atLogger.error("Could not set " + fieldName + " to " + newState.toString());
            return false;
        }
    }

    /**
     * Run a method that doesn't return anything
     *
     * @param inst       an instance of the target class
     * @param methodName the string name of the method
     * @param params     parameters to the method
     * @return {@code true} if there was no error
     */
    public static <C> boolean runVoid(C inst, String methodName, Object... params) {
        try {
            //noinspection rawtypes
            Class[] classes = new Class[params.length];
            for (int i = 0; i < params.length; i++) {
                classes[i] = params[i].getClass();
            }
            Method method = inst.getClass().getDeclaredMethod(methodName, classes);
            method.setAccessible(true);
            method.invoke(inst, params);
            return true;
        } catch (Throwable t) {
            atLogger.error("Could not execute void method " + methodName, t);
            return false;
        }
    }

    /**
     * Run a method and get a return value
     *
     * @param inst       an instance of the target class
     * @param methodName the string name of the method
     * @param params     parameters to the method
     * @return whatever the method returns as a {@link Data} object
     */
    public static <T, C> Data<T> runReturning(C inst, String methodName, Object... params) {
        try {
            Class<?>[] classes = new Class<?>[params.length];
            for (int i = 0; i < params.length; i++) {
                classes[i] = params[i].getClass();
            }
            Method method = inst.getClass().getDeclaredMethod(methodName, classes);
            method.setAccessible(true);
            return new Data<>((T) method.invoke(inst, params));
        } catch (Throwable t) {
            atLogger.error("Could not execute returning method " + methodName, t);
            return new Data<>();
        }
    }

}
