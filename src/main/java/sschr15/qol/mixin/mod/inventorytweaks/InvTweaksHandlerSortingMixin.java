package sschr15.qol.mixin.mod.inventorytweaks;

import invtweaks.InvTweaksHandlerSorting;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * it's not a mod until you mix into other mods!
 */
@Mixin(value = InvTweaksHandlerSorting.class, remap = false)
public abstract class InvTweaksHandlerSortingMixin {
    @Redirect(method = {
            "sort",
            "sortWithRules",
            "sortInventory",
            "sortMergeArmor",
            "sortEvenStacks",
            "defaultSorting"
    }, at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V"))
    private void quieterLogs(Logger logger, String message) {
        if (message.matches("Sorting done in \\d+ns")) logger.info(message);
        else logger.debug(message);
    }
}
