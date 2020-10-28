package sschr15.qol.mixin.common;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandTP;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

@Mixin(CommandTP.class)
public abstract class TpCommandMixin extends CommandBase {
    @Inject(method = "execute", at = @At(value = "NEW", target = "net/minecraft/command/CommandException"), cancellable = true)
    private void onExecute(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci) throws CommandException {
        Entity teleported = args.length == 2 ? getEntity(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
        Entity teleportedTo = getEntity(server, sender, args[args.length - 1]);

        if (teleported instanceof EntityPlayerMP) {
            EntityPlayerMP teleportedPlayer = (EntityPlayerMP) teleported;
            teleportedPlayer.setWorld(teleportedTo.world);
            teleportedPlayer.connection.setPlayerLocation(teleportedTo.posX, teleportedTo.posY, teleported.posZ, teleportedTo.rotationYaw, teleportedTo.rotationPitch);
        } else {
            teleported.setWorld(teleportedTo.world);
            teleported.setLocationAndAngles(teleportedTo.posX, teleportedTo.posY, teleportedTo.posZ, teleportedTo.rotationYaw, teleportedTo.rotationPitch);
        }
    }
}
