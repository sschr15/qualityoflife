package sschr15.qol.api;

import org.reflections.Reflections;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.FMLInjectionData;

import java.io.File;
import java.lang.annotation.Annotation;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class GeneralUtils {
    private static final Reflections REFLECTIONS = new Reflections();
    @SuppressWarnings("FieldMayBeFinal")
    private static List<ModContainer> LOADED_MODS = null; //set through reflection

    public static Path getMinecraftDirectory() {
        return ((File) FMLInjectionData.data()[6]).toPath();
    }

    public static <T> Set<Class<? extends T>> getSubclasses(Class<T> clazz) {
        return REFLECTIONS.getSubTypesOf(clazz);
    }

    public static Set<Class<?>> getAnnotatedClasses(Class<? extends Annotation> annotation) {
        return REFLECTIONS.getTypesAnnotatedWith(annotation);
    }

    public static Reflections reflections() {
        return REFLECTIONS;
    }

    /**
     * Place a number in a range
     * @param min the minimum value
     * @param num the number
     * @param max the maximum value
     * @return the value as a double
     */
    public static Number range(Number min, Number num, Number max) {
        assert min.doubleValue() <= max.doubleValue();
        return Math.min(max.doubleValue(), Math.max(num.doubleValue(), min.doubleValue()));
    }

    public static List<ModContainer> getLoadedMods() {
        return LOADED_MODS == null ? Collections.emptyList() : LOADED_MODS;
    }

    public static List<String> getLoadedModIds() {
        if (LOADED_MODS == null) return Collections.emptyList();
        return LOADED_MODS.stream().map(ModContainer::getModId).collect(Collectors.toList());
    }
}
