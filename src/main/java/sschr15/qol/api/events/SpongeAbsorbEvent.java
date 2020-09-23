package sschr15.qol.api.events;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

/**
 * This is probably the closest thing to actual Forge events...
 * <br />
 * To prevent the sponge from absorbing water, {@link #cancel() cancel} the event.
 * To force the sponge to be wet (or not wet), set the {@link #setResult(Result) result}.
 */
@Cancelable
@HasResult
public class SpongeAbsorbEvent extends Event {
    private final World world;
    private final BlockPos pos;

    public SpongeAbsorbEvent(World world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }

    public void cancel() {
        this.setCanceled(true);
    }

    public World getWorld() {
        return world;
    }

    public BlockPos getPos() {
        return pos;
    }
}
