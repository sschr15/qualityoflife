package sschr15.qol.api.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PlayerDeathEvent extends Event {
    private final EntityPlayerMP killedPlayer;
    private final DamageSource causeOfDeath;

    public PlayerDeathEvent(EntityPlayerMP killedPlayer, DamageSource causeOfDeath) {
        super();
        this.killedPlayer = killedPlayer;
        this.causeOfDeath = causeOfDeath;
    }

    public EntityPlayerMP getKilledPlayer() {
        return killedPlayer;
    }

    public DamageSource getCauseOfDeath() {
        return causeOfDeath;
    }
}
