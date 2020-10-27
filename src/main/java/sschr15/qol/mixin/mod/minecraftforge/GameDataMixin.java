package sschr15.qol.mixin.mod.minecraftforge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.qol.code.Ref;
import sschr15.qol.interfaces.IGameData;

import net.minecraftforge.registries.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(value = GameData.class, remap = false)
public abstract class GameDataMixin implements IGameData {
    private static final List<Ref.ConflictingResource> CONFLICTING_RESOURCE_LIST = new ArrayList<>();

    @Redirect(method = "checkPrefix(Ljava/lang/String;Z)Lnet/minecraft/util/ResourceLocation;", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private static void redirectPrefixWarnings(Logger fml, String text, Object p0, Object p1, Object p2) {
        String prefix   = (String) p0;
        String name     = (String) p1;
        String expected = (String) p2;
        fml.debug(text, p0, p1, p2);
        CONFLICTING_RESOURCE_LIST.add(new Ref.ConflictingResource(prefix, name, expected));
    }

    @Override
    public List<Ref.ConflictingResource> getConflictingResourceList() {
        return CONFLICTING_RESOURCE_LIST;
    }

    static {
        String[] loggerOptions = {
                "HAHA! This class is not meant to be used by modders, but *here we are!*",
                "haha mixin go brrr",
                "This error is brought to you by Forge being :concern:",
                ":concernedtater:",
                "gradle 6 broke everything by requiring pom files",
                ":voldethonk:",
                "Why are you still using Forge 1.12.2? You should switch to https://fabricmc.net instead!"
        };

        LogManager.getLogger().error(loggerOptions[new Random().nextInt(loggerOptions.length)]);
    }
}
