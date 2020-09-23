package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.qol.interfaces.IPotionEffect;
import sschr15.qol.interfaces.TestInterfaces;

import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;

import java.util.Collection;

@Mixin(PotionUtils.class)
public abstract class PotionUtilsMixin implements TestInterfaces.I1 {
    @Inject(method = "getPotionColorFromEffectList", at = @At("HEAD"), cancellable = true)
    private static void onGetFromEffects(Collection<PotionEffect> effects, CallbackInfoReturnable<Integer> cir) {
        if (!effects.isEmpty()) for (PotionEffect effect : effects) {
            IPotionEffect effect1 = (IPotionEffect) effect;
            if (effect1.hasColor()) {
                cir.setReturnValue(effect1.getColor());
                return;
            }
        }
    }
}
