package sschr15.qol.api.events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.common.eventhandler.Event;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KeyBindingEvent extends Event {
    private final KeyBinding[] keyBindings;
    private final int keyCode;
    @Nullable private final Integer pressTime;

    @Nullable private ForgeInfo forgeInfo = null;


    private KeyBindingEvent(KeyBinding[] keyBindings, int keyCode, @Nullable Integer pressTime) {
        super();
        this.keyBindings = keyBindings;
        this.keyCode = keyCode;
        this.pressTime = pressTime;
    }

    private KeyBindingEvent(KeyBinding[] keyBindings, int keyCode, @Nullable Integer pressTime,
                           KeyModifier modifier, KeyModifier defaultModifier, IKeyConflictContext keyConflictContext) {
        this(keyBindings, keyCode, pressTime);
        this.forgeInfo = new ForgeInfo(modifier, defaultModifier, keyConflictContext);
    }

    public KeyBinding[] getKeyBindings() {
        return keyBindings;
    }

    public int getKeyCode() {
        return keyCode;
    }

    @Nullable
    public Integer getPressTime() {
        return pressTime;
    }

    @Nullable
    public ForgeInfo getForgeInfo() {
        return forgeInfo;
    }

    public static class Press extends KeyBindingEvent {
        public Press(KeyBinding[] keyBindings, int keyCode) {
            super(keyBindings, keyCode, null);
        }
    }

    public static class Release extends KeyBindingEvent {
        public Release(KeyBinding[] keyBindings, int keyCode, @Nonnull Integer pressTime,
                          KeyModifier modifier, KeyModifier defaultModifier, IKeyConflictContext keyConflictContext) {
            super(keyBindings, keyCode, pressTime, modifier, defaultModifier, keyConflictContext);
        }
    }

    public static class KeyCodeSet extends KeyBindingEvent {
        public KeyCodeSet(KeyBinding[] keyBindings, int keyCode,
                          KeyModifier modifier, KeyModifier defaultModifier, IKeyConflictContext keyConflictContext) {
            super(keyBindings, keyCode, null, modifier, defaultModifier, keyConflictContext);
        }
    }

    public static class Register extends KeyBindingEvent {
        public Register(KeyBinding[] keyBindings, int keyCode) {
            super(keyBindings, keyCode, null);
        }

        public Register(KeyBinding[] keyBindings, int keyCode,
                        KeyModifier modifier, KeyModifier defaultModifier, IKeyConflictContext keyConflictContext) {
            super(keyBindings, keyCode, null, modifier, defaultModifier, keyConflictContext);
        }
    }

    public static class ForgeInfo {
        private final KeyModifier modifier;
        private final KeyModifier defaultModifier;
        private final IKeyConflictContext keyConflictContext;

        ForgeInfo(KeyModifier modifier, KeyModifier defaultModifier, IKeyConflictContext keyConflictContext) {
            this.modifier = modifier;
            this.defaultModifier = defaultModifier;
            this.keyConflictContext = keyConflictContext;
        }

        public KeyModifier getModifier() {
            return modifier;
        }

        public KeyModifier getDefaultModifier() {
            return defaultModifier;
        }

        public IKeyConflictContext getKeyConflictContext() {
            return keyConflictContext;
        }
    }
}
