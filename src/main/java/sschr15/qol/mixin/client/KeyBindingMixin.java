package sschr15.qol.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sschr15.qol.api.events.KeyBindingEvent;
import sschr15.qol.interfaces.TestInterfaces;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.common.MinecraftForge;

@Mixin(value = KeyBinding.class)
public abstract class KeyBindingMixin implements TestInterfaces.I1 {
    @Shadow(remap = false) private KeyModifier keyModifierDefault;
    @Shadow private boolean pressed;
    @Shadow private int keyCode;
    @Shadow(remap = false) private KeyModifier keyModifier;
    @Shadow(remap = false) private IKeyConflictContext keyConflictContext;
    @Shadow private int pressTime;
    @Shadow @Final private static KeyBindingMap HASH;
    private KeyBinding[] self;

    @Inject(method = "<init>(Ljava/lang/String;ILjava/lang/String;)V", at = @At("RETURN"))
    private void onInit(String keyDescription, int keyCode, String keyCategory, CallbackInfo ci) {
        this.self = new KeyBinding[]{(KeyBinding) ((Object) this)};
        MinecraftForge.EVENT_BUS.post(new KeyBindingEvent.Register(this.self, keyCode));
    }

    @Inject(method = "<init>(Ljava/lang/String;Lnet/minecraftforge/client/settings/IKeyConflictContext;Lnet/minecraftforge/client/settings/KeyModifier;ILjava/lang/String;)V", at = @At("RETURN"))
    private void onInit(String keyDescription, IKeyConflictContext keyConflictContext, KeyModifier keyModifier,
                        int keyCode, String keyCategory, CallbackInfo ci) {
        this.self = new KeyBinding[]{(KeyBinding) ((Object) this)};
        MinecraftForge.EVENT_BUS.post(new KeyBindingEvent.Register(
                this.self, keyCode, keyModifier, keyModifierDefault, keyConflictContext
        ));
    }

    @Inject(method = "unpressKey", at = @At("HEAD"))
    private void onUnpressKey(CallbackInfo ci) {
        if (this.pressed) {
            MinecraftForge.EVENT_BUS.post(new KeyBindingEvent.Release(
                    self, keyCode, pressTime,
                    keyModifier, keyModifierDefault, keyConflictContext
            ));
        }
    }

    @Inject(method = "setKeyCode", at = @At("HEAD"))
    private void onSetKeyCode(int keyCode, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new KeyBindingEvent.KeyCodeSet(
                self, keyCode, this.keyModifier, this.keyModifierDefault, this.keyConflictContext
        ));
    }

    @Inject(method = "setKeyBindState", at = @At("HEAD"))
    private static void onSetState(int keyCode, boolean pressed, CallbackInfo ci) {
        if (keyCode != 0) {
            KeyBinding[] keyBindings = HASH.lookupAll(keyCode).toArray(new KeyBinding[0]);
            MinecraftForge.EVENT_BUS.post(new KeyBindingEvent.Press(keyBindings, keyCode));
        }
    }
}
