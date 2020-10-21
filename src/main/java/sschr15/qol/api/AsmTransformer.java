package sschr15.qol.api;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber.TransformClass;
import sschr15.qol.code.Ref;
import sschr15.qol.util.QOLSubscriberCaller;

import net.minecraft.launchwrapper.IClassTransformer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public final class AsmTransformer implements IClassTransformer {
    private static final Logger LOGGER = LogManager.getLogger("QOL ASM Transformer");
    private final ImmutableMultimap<Method, String> multimap;

    public AsmTransformer() {
        Multimap<Method, String> m = HashMultimap.create();
        QOLSubscriberCaller.getLegalMethods(TransformClass.class, null, false, ClassNode.class, String.class).forEach(method -> {
            List<String> classes = Arrays.asList(method.getAnnotation(TransformClass.class).value());
            m.putAll(method,classes);
        });
        multimap = ImmutableMultimap.copyOf(m);
    }

    /**
     * voldeloader gibes bad transforming by bytes instead of classnode
     */
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (!this.shouldTransform(name)) return basicClass;
        ClassNode node = new ClassNode();
        ClassReader reader = new ClassReader(basicClass);
        reader.accept(node, 0);

        this.transform(name, node);

        ClassWriter writer = new ClassWriter(0);
        node.accept(writer);
        return writer.toByteArray();
    }

    protected boolean shouldTransform(String name) {
        return multimap.containsValue(name);
    }

    protected void transform(String name, ClassNode node) {
        multimap.asMap().forEach((method, strings) -> {
            if (strings.contains(name)) {
                LOGGER.debug("Transforming " + name + " from " + method.getDeclaringClass().getName() + '#' + method.getName());
                try {
                    method.setAccessible(true);
                    method.invoke(null, node, name);
                } catch (Exception e) {
                    Ref.LOGGER.warn("Unable to invoke method " + method.getName() +
                            " from class " + method.getDeclaringClass().getName() + '!');
                    try {
                        throw e;
                    } catch (IllegalAccessException | InvocationTargetException ignored) {}
                }
            }
        });
    }
}
