package sschr15.qol.interfaces;

import net.minecraft.potion.PotionEffect;

public interface IPotionEffect {
    /**
     * To access methods in the {@link PotionEffect} class,
     * this method returns the potion effect itself.
     */
    PotionEffect getAsPotionEffect();
    void setColor(int color);
    int getColor();
    boolean hasColor();
}
