package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import sschr15.qol.interfaces.IPotionEffect;

import net.minecraft.potion.PotionEffect;

@Mixin(PotionEffect.class)
public abstract class PotionEffectMixin implements IPotionEffect {
    private Integer color = null;

    @Override
    public PotionEffect getAsPotionEffect() {
        return (PotionEffect) (IPotionEffect) this;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public boolean hasColor() {
        return color != null;
    }
}
