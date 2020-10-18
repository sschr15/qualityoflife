package sschr15.qol.mixin.server;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow @Final private static Logger LOGGER;
    @Shadow private String worldName;

    @Redirect(method = "run", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"), remap = false)
    private void shush(Logger logger, String format, Object p0, Object p1) {
        logger.debug("Lag probably happened. Skipping {} ticks...", p1);
    }

    @Redirect(method = "saveAllWorlds", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void keepItQuiet(Logger logger, String format, Object p0, Object p1) {
        logger.debug(format, p0, p1);
    }

    @Inject(method = "saveAllWorlds", at = @At("HEAD"))
    private void tellPeopleTheWorldsAreSaving(CallbackInfo ci) {
        LOGGER.info("Saving worlds for save {}", this.worldName);
    }
}
