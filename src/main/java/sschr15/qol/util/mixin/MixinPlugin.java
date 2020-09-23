package sschr15.qol.util.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import sschr15.qol.api.GeneralUtils;

import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {
    @SuppressWarnings("unchecked")
    @Override
    public void onLoad(String mixinPackage) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRefMapperConfig() {
        return null;
    }

    private static final String CLIENT = "sschr15.qol.mixin.client";
    private static final String SERVER = "sschr15.qol.mixin.server";
    private static final String MODS   = "sschr15.qol.mixin.mods";
    /**
     * {@inheritDoc}
     * <br /><br />
     * This will contain code which will check for various conditions.
     */
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!FMLLaunchHandler.side().isClient() && mixinClassName.startsWith(CLIENT)) {
            return false;
        } else if (!FMLLaunchHandler.side().isServer() && mixinClassName.startsWith(SERVER)) {
            return false;
        } else if (mixinClassName.startsWith(MODS)) {
            if (targetClassName.contains("inventorytweaks")) return GeneralUtils.getLoadedModIds().contains("inventorytweaks");
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getMixins() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
