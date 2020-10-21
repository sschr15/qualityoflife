package sschr15.qol.util.mixin;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import sschr15.qol.api.AsmTransformer;
import sschr15.qol.api.GeneralUtils;
import sschr15.qol.api.annotations.MixinConfig;
import sschr15.qol.api.annotations.qolevents.QOLSubscriber;
import sschr15.qol.code.Ref;
import sschr15.qol.util.QOLSubscriberCaller;
import sschr15.qol.util.ReflectionTool;

import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.FMLInjectionData;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SortingIndex(1)
@MCVersion("1.12.2")
@Name("QOL Mixin Coremod")
public class MixinSetupCoremod implements IFMLLoadingPlugin {
    public MixinSetupCoremod() {
        MixinBootstrap.init();

        // Questionable Thing #0: loading the mods list earlier than it should
        ReflectionTool.setField(Loader.instance(), "mods", new ArrayList<ModContainer>(), false);
        ReflectionTool.setField(Loader.instance(), "injectedContainers",
                Lists.newArrayList("net.minecraftforge.fml.common.FMLContainer",
                        "net.minecraftforge.common.ForgeModContainer"), true);
        ReflectionTool.setField(Loader.instance(), "minecraftDir", (File) FMLInjectionData.data()[6], true);

        // we gotta fake a sided delegate :)
        ReflectionTool.setField(FMLCommonHandler.instance(), "sidedDelegate", new FakeSidedDelegate(), false);

        try {
            Method identifyMods = Loader.class.getDeclaredMethod("identifyMods", List.class);
            identifyMods.setAccessible(true);
            identifyMods.invoke(Loader.instance(), Collections.emptyList());
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {}
        //noinspection unchecked
        List<ModContainer> mods = (List<ModContainer>) ReflectionTool.getField(Loader.instance(), "mods", false).data;
        ReflectionTool.setField(Loader.instance(), "mods", null, false);
        ReflectionTool.setField(Loader.instance(), "injectedContainers", null, true);
        ReflectionTool.setField(Loader.instance(), "namedMods", null, false);

        ReflectionTool.setField(FMLCommonHandler.instance(), "sidedDelegate", null, false);

        //noinspection InstantiationOfUtilityClass
        ReflectionTool.setField(new GeneralUtils(), "LOADED_MODS", mods, true);

        // Questionable Thing #1: using an Annotation to get Mixins and such
        Multimap<Byte, String> mixinFileThings = HashMultimap.create();

        GeneralUtils.getAnnotatedClasses(MixinConfig.class).forEach(clazz -> {
            MixinConfig a = clazz.getAnnotation(MixinConfig.class);
            List<String> classMixinFiles = Arrays.asList(a.value());
            byte priority = a.priority();
            Collections.reverse(classMixinFiles);
            mixinFileThings.putAll(priority, classMixinFiles);
        });

        mixinFileThings.asMap().forEach((b, s) -> Mixins.addConfigurations(s.toArray(new String[0])));

        // Questionable thing #2: using Annotations to allow other mods to run code before they should :)
        QOLSubscriberCaller.getMethods(QOLSubscriber.RunOnCoremodLoad.class).forEach(method -> {
            try {
                method.invoke(new Object());
            } catch (ReflectiveOperationException e) {
                Ref.LOGGER.warn("Unable to invoke method " + method + '!', e);
            }
        });
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{AsmTransformer.class.getName()};
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    private static class FakeSidedDelegate implements IFMLSidedHandler {

        @Override
        public List<String> getAdditionalBrandingInformation() {
            throw new NullPointerException();
        }

        @Override
        public Side getSide() {
            try {
                Class.forName("net.minecraft.client.util.JsonException");
                return Side.CLIENT;
            } catch (ClassNotFoundException e) {
                return Side.SERVER;
            }
        }

        @Override
        public void haltGame(String message, Throwable exception) {
            throw new NullPointerException();
        }

        @Override
        public void showGuiScreen(Object clientGuiElement) {
            throw new NullPointerException();
        }

        @Override
        public void queryUser(StartupQuery query) throws InterruptedException {
            throw new InterruptedException();
        }

        @Override
        public void beginServerLoading(MinecraftServer server) {
            throw new NullPointerException();
        }

        @Override
        public void finishServerLoading() {
            throw new NullPointerException();
        }

        @Override
        public File getSavesDirectory() {
            throw new NullPointerException();
        }

        @Override
        public MinecraftServer getServer() {
            throw new NullPointerException();
        }

        @Override
        public boolean isDisplayCloseRequested() {
            throw new NullPointerException();
        }

        @Override
        public boolean shouldServerShouldBeKilledQuietly() {
            throw new NullPointerException();
        }

        @Override
        public void addModAsResource(ModContainer container) {
            throw new NullPointerException();
        }

        @Override
        public String getCurrentLanguage() {
            throw new NullPointerException();
        }

        @Override
        public void serverStopped() {
            throw new NullPointerException();
        }

        @Override
        public NetworkManager getClientToServerNetworkManager() {
            throw new NullPointerException();
        }

        @Override
        public INetHandler getClientPlayHandler() {
            throw new NullPointerException();
        }

        @Override
        public void fireNetRegistrationEvent(EventBus bus, NetworkManager manager, Set<String> channelSet, String channel, Side side) {
            throw new NullPointerException();
        }

        @Override
        public boolean shouldAllowPlayerLogins() {
            throw new NullPointerException();
        }

        @Override
        public void allowLogins() {
            throw new NullPointerException();
        }

        @Override
        public IThreadListener getWorldThread(INetHandler net) {
            throw new NullPointerException();
        }

        @Override
        public void processWindowMessages() {
            throw new NullPointerException();
        }

        @Override
        public String stripSpecialChars(String message) {
            throw new NullPointerException();
        }

        @Override
        public void reloadRenderers() {
            throw new NullPointerException();
        }

        @Override
        public void fireSidedRegistryEvents() {
            throw new NullPointerException();
        }

        @Override
        public CompoundDataFixer getDataFixer() {
            throw new NullPointerException();
        }

        @Override
        public boolean isDisplayVSyncForced() {
            throw new NullPointerException();
        }
    }
}
