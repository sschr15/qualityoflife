package sschr15.qol.code;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import sschr15.qol.api.annotations.MixinConfig;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber;
import sschr15.qol.interfaces.IGameData;

import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.GameData;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

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
        String[] steps = new String[]{
                "Abnormalizing the matrices...",
                "Postfragmenting the widget layer...",
                "Causing the heat death of the universe...",
                "Setting phasers to stun...",
                "Done!"
        };
        Random random = new Random();
        ProgressManager.ProgressBar bar = ProgressManager.push("PreInit Shenanigans", 5);
        for (String step : steps) {
            bar.step(step);
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException ignored) {
            }
        }
        ProgressManager.pop(bar);
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
            @SuppressWarnings("ConstantConditions")
            List<Ref.ConflictingResource> conflictingResourceList = ((IGameData) new GameData()).getConflictingResourceList();

            Multimap<String, String> multimap = HashMultimap.create();
            for (Ref.ConflictingResource a : conflictingResourceList) {
                // the reason we don't just use foreach is because :concern:
                multimap.put(String.format("%s -> %s", a.expectedPrefix, a.setPrefix), a.resourceName);
            }

            if (!multimap.isEmpty()) {
                FMLLog.log.warn("Alternative prefixes were found!");
                multimap.asMap().forEach((prefixThing, names) -> FMLLog.log.warn("Prefixes mapped " + prefixThing + ": " + String.join(", ", names)));
            }
        }
    }

    @QOLSubscriber.RunOnCoremodLoad
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
