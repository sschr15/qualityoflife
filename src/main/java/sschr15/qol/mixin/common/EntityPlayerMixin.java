package sschr15.qol.mixin.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sschr15.qol.api.events.EventResult;
import sschr15.qol.api.events.PlayerEditEvent;
import sschr15.qol.interfaces.TestInterfaces;

@Mixin(value = EntityPlayer.class)
public abstract class EntityPlayerMixin extends EntityLivingBase implements TestInterfaces.I1 {

    @Inject(method = "canPlayerEdit", at = @At("HEAD"), cancellable = true)
    private void canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        PlayerEditEvent event = new PlayerEditEvent(pos, facing, stack);
        MinecraftForge.EVENT_BUS.post(event);
        EventResult result = event.getEditResult();
        if (result == EventResult.OK) cir.setReturnValue(true);
        else if (result == EventResult.NO) cir.setReturnValue(false);

        // if no return value is set by this point, the normal code will run
    }

    // boo! why must java do this to us
    public EntityPlayerMixin(World worldIn) {
        super(worldIn);
    }
}
