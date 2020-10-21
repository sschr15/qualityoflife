package sschr15.qol.mixin.common;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.chunk.Chunk;

@Mixin(Chunk.class)
public abstract class ChunkMixin {
    /**
     * @reason quiet cascading worldgen lag
     */
    @Redirect(method = "logCascadingWorldGeneration", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V"), remap = false)
    private void preventWarns(Logger fml, String format, Object p0, Object p1, Object p2, Object p3, Object p4) {
        fml.debug("{} caused chunk {} in {} (id {}) during {} population. Forge would request you report this.",
                p0, p1, p3, p2, p4);
    }

    /**
     * @reason quiet cascading worldgen lag
     */
    @Redirect(method = "logCascadingWorldGeneration", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V"), remap = false)
    private void cancelExtraWarning(Logger fml, String text) {
        // do nothing
    }
}
