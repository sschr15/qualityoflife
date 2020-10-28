package sschr15.qol.mixin.mod.minecraftforge;

import org.spongepowered.asm.mixin.Mixin;
import sschr15.qol.code.Ref;

import net.minecraftforge.fml.common.ProgressManager;

@Mixin(ProgressManager.class)
public abstract class ProgressManagerMixin {
    static {
        Ref.stopPreLaunchWindow();
    }
}
