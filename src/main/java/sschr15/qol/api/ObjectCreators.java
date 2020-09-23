package sschr15.qol.api;

import sschr15.qol.interfaces.IPotionEffect;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class ObjectCreators {
    /**
     *
     * @param potionIn the potion to start, or {@code null} to create one from another potion effect
     * @param params one of the following:
     *               <ul>
     *                  <li>an empty array / no value given</li>
     *                  <li>a PotionEffect, if {@code potionIn} is {@code null}</li>
     *                  <li>duration (int)</li>
     *                  <li>duration (int), amplifier (int)</li>
     *                  <li>duration (int), amplifier (int), ambient (bool), particles (bool)</li>
     *               </ul>
     *               Any other arrangement of values for params will cause an {@link IllegalArgumentException}.
     * @return the created potion effect
     * @throws IllegalArgumentException if the parameters given are invalid.
     */
    @Nonnull
    public static IPotionEffect newPotionEffect(Potion potionIn, Object... params) {
        PotionEffect potionEffect;
        try {
            switch (params.length) {
                case 0:
                    potionEffect = new PotionEffect(potionIn);
                    break;
                case 1:
                    if (potionIn == null) potionEffect = new PotionEffect((PotionEffect) params[0]);
                    else potionEffect = new PotionEffect(potionIn, (int) params[0]);
                    break;
                case 2:
                    potionEffect = new PotionEffect(potionIn, (int) params[0], (int) params[1]);
                    break;
                case 4:
                    potionEffect = new PotionEffect(potionIn, (int) params[0], (int) params[1], (boolean) params[2], (boolean) params[3]);
                    break;
                default:
                    throw new ClassCastException();
            }
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Parameters were not valid!");
        }

        //noinspection ConstantConditions mixins go brrrr
        return (IPotionEffect) potionEffect;
    }
}
