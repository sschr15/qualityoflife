package sschr15.qol.code;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import sschr15.qol.api.annotations.MixinConfig;
import sschr15.qol.api.annotations.qolevents.RunOnCoremodLoad;
import sschr15.qol.interfaces.IGameData;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.GameData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Mod(
        modid = Ref.MOD_ID,
        name = Ref.MOD_NAME,
        version = Ref.VERSION
)
@ParametersAreNonnullByDefault
@MixinConfig("qol.mixins.json")
public class QualityOfLife {
    @Mod.Instance(Ref.MOD_ID)
    public static QualityOfLife INSTANCE;

    private static boolean doesModFixCrashes = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @Mod.EventHandler
    public void loadingComplete(FMLLoadCompleteEvent event) {
        if (IGameData.class.isAssignableFrom(GameData.class)) {
            //noinspection InstantiationOfUtilityClass
            List<Ref.ConflictingResource> conflictingResourceList = ((IGameData) new GameData()).getConflictingResourceList();

            Multimap<String, String> multimap = HashMultimap.create();
            conflictingResourceList.forEach(a -> multimap.put(String.format("%s -> %s", a.expectedPrefix, a.setPrefix), a.resourceName));

            if (!multimap.isEmpty()) {
                FMLLog.log.warn("Alternative prefixes were found!");
                multimap.asMap().forEach((prefixThing, names) -> FMLLog.log.warn("Prefixes mapped " + prefixThing + ": " + String.join(", ", names.toArray(new String[0]))));
            }
        }
    }

    @RunOnCoremodLoad
    public static void runOnCoremodLoad() {
        Ref.LOGGER.error("Loaded");
    }

    public static void modFixesCrashes() {
        doesModFixCrashes = true;
    }

    public static boolean doesModFixCrashes() {
        return doesModFixCrashes;
    }
}
