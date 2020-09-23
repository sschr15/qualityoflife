package sschr15.qol.mixin.client;

import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiNewChat.class)
public abstract class GuiNewChatMixin {
    @Redirect(method = "printChatMessageWithOptionalDeletion", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"))
    private void silenceInfo(Logger logger, String format, Object o) {
        logger.debug(format, o);
    }
}
