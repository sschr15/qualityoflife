package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.qol.api.events.SpongeAbsorbEvent;

import net.minecraft.block.BlockSponge;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

@Mixin(BlockSponge.class)
public abstract class BlockSpongeMixin {
    private SpongeAbsorbEvent event;

    @SuppressWarnings("unchecked") // we already make sure we can cast Boolean to V, just in case
    @Redirect(method = "tryAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;withProperty(Lnet/minecraft/block/properties/IProperty;Ljava/lang/Comparable;)Lnet/minecraft/block/state/IBlockState;"))
    private <T extends Comparable<T>, V extends T> IBlockState forceWetness(IBlockState obj, IProperty<T> property, V value) {
        Event.Result result = event.getResult();
        event = null; // no persisting because i say so

        if (result == Event.Result.DEFAULT || !(value instanceof Boolean)) return obj.withProperty(property, value);
        else return obj.withProperty(property, (V) Boolean.valueOf(result == Event.Result.ALLOW));
    }

    @Inject(method = "absorb", at = @At("HEAD"), cancellable = true)
    private void onAbsorb(World world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        event = new SpongeAbsorbEvent(world, pos);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled()) cir.setReturnValue(false);
    }
}
