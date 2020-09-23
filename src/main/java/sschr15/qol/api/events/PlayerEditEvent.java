package sschr15.qol.api.events;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerEditEvent extends Event {
    private final EventResult.ResultCounter counter = new EventResult.ResultCounter();
    private final BlockPos pos;
    private final EnumFacing facing;
    private final ItemStack stack;

    public PlayerEditEvent(BlockPos pos, EnumFacing facing, ItemStack stack) {
        this.pos = pos;
        this.facing = facing;
        this.stack = stack;
    }

    /** @see EventResult.ResultCounter#setResult(EventResult) */
    public boolean editResult(EventResult result) {
        return counter.setResult(result);
    }

    /** @see EventResult.ResultCounter#getResult() */
    public EventResult getEditResult() {
        return counter.getResult();
    }

    public BlockPos getPos() {
        return pos;
    }

    public EnumFacing getFacing() {
        return facing;
    }

    public ItemStack getStack() {
        return stack;
    }
}
