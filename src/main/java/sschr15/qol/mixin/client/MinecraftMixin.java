package sschr15.qol.mixin.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import sschr15.qol.api.GeneralUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.io.File;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow private static final @Final Logger LOGGER = LogManager.getLogger("Minecraft");
    @Shadow public EntityPlayerSP player;
    @Shadow @Final public File gameDir;

    /**
     * @reason Logging to a logger is a better idea than logging to {@link System#out}
     */
    @Redirect(method = "displayCrashReport", at = @At(value = "INVOKE", target = "Lnet/minecraft/init/Bootstrap;printToSYSOUT(Ljava/lang/String;)V"))
    private void printToSysout(String string) {
        LOGGER.error(string);
    }

    public String toString() {
        return "Minecraft{" +
                "player=" + GeneralUtils.toString(this.player) +
                ", gamedir=" + GeneralUtils.toString(this.gameDir) +
                "}";
    }
}
