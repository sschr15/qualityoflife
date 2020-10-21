package sschr15.qol.util;

import org.objectweb.asm.Opcodes;
import org.jetbrains.annotations.Nullable;
import sschr15.qol.api.GeneralUtils;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QOLSubscriberCaller {
    private static final List<Class<?>> qolSubscribers = new ArrayList<>();

    public static List<Method> getMethods(Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        getQolSubscribers().forEach(clazz -> {
            Method[] methods1 = clazz.getMethods();
            for (Method method : methods1) {
                if (method.isAnnotationPresent(annotation)) methods.add(method);
            }
        });
        return methods;
    }

    public static List<Method> getLegalMethods(Class<? extends Annotation> annotation, @Nullable Object inst, boolean varargs, Class<?>... args) {
        return getMethods(annotation).stream().filter(method -> isLegal(method, inst, varargs, args)).collect(Collectors.toList());
    }

    public static List<Class<?>> getQolSubscribers() {
        if (qolSubscribers.isEmpty()) qolSubscribers.addAll(GeneralUtils.getAnnotatedClasses(QOLSubscriber.class));
        return qolSubscribers;
    }

    public static boolean isLegal(@Nonnull Method m, @Nullable Object inst, boolean varargs, Class<?>... classes) {
        if ((m.getModifiers() & Opcodes.ACC_STATIC) == 0 && inst == null) return false;
        if (varargs != m.isVarArgs()) return false;
        if (classes.length != m.getParameterCount()) return false;

        for (int i = 0; i < classes.length; i++) {
            if (m.getParameterTypes()[i] != classes[i]) return false;
        }

        return true;
    }
}
