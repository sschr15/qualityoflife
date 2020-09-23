package sschr15.qol.util;

import sschr15.qol.api.GeneralUtils;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class QOLSubscriberCaller {
    private static final List<Class<?>> qolSubscribers = new ArrayList<>();

    public static <T extends Annotation> List<Method> getMethods(Class<T> annotation) {
        List<Method> methods = new ArrayList<>();
        getQolSubscribers().forEach(clazz -> {
            Method[] methods1 = clazz.getMethods();
            for (Method method : methods1) {
                if (method.isAnnotationPresent(annotation)) methods.add(method);
            }
        });
        return methods;
    }

    private static List<Class<?>> getQolSubscribers() {
        if (qolSubscribers.isEmpty()) qolSubscribers.addAll(GeneralUtils.getAnnotatedClasses(QOLSubscriber.class));
        return qolSubscribers;
    }
}
