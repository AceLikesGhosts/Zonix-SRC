package net.minecraft.client.settings;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import java.util.*;

public class KeyBinding implements Comparable
{
    private static final List keybindArray;
    private static final IntHashMap hash;
    private static final Set keybindSet;
    private final String keyDescription;
    private final int keyCodeDefault;
    private final String keyCategory;
    private int keyCode;
    private boolean pressed;
    private int presses;
    private static final String __OBFID = "CL_00000628";
    
    public static void onTick(final int p_74507_0_) {
        if (p_74507_0_ != 0) {
            final KeyBinding var1 = (KeyBinding)KeyBinding.hash.lookup(p_74507_0_);
            if (var1 != null) {
                final KeyBinding keyBinding = var1;
                ++keyBinding.presses;
            }
        }
    }
    
    public static boolean getKeyBindState(final int key) {
        if (key != 0) {
            final KeyBinding var2 = (KeyBinding)KeyBinding.hash.lookup(key);
            if (var2 != null) {
                return var2.pressed;
            }
        }
        return false;
    }
    
    public static void setKeyBindState(final int p_74510_0_, final boolean p_74510_1_) {
        if (p_74510_0_ != 0) {
            final KeyBinding var2 = (KeyBinding)KeyBinding.hash.lookup(p_74510_0_);
            if (var2 != null) {
                var2.pressed = p_74510_1_;
            }
        }
    }
    
    public static void unPressAllKeys() {
        for (final KeyBinding var2 : KeyBinding.keybindArray) {
            var2.unpressKey();
        }
    }
    
    public static void resetKeyBindingArrayAndHash() {
        KeyBinding.hash.clearMap();
        for (final KeyBinding var2 : KeyBinding.keybindArray) {
            KeyBinding.hash.addKey(var2.keyCode, var2);
        }
    }
    
    public static Set func_151467_c() {
        return KeyBinding.keybindSet;
    }
    
    public KeyBinding(final String p_i45001_1_, final int p_i45001_2_, final String p_i45001_3_) {
        this.keyDescription = p_i45001_1_;
        this.keyCode = p_i45001_2_;
        this.keyCodeDefault = p_i45001_2_;
        this.keyCategory = p_i45001_3_;
        KeyBinding.keybindArray.add(this);
        KeyBinding.hash.addKey(p_i45001_2_, this);
        KeyBinding.keybindSet.add(p_i45001_3_);
    }
    
    public boolean getIsKeyPressed() {
        return this.pressed;
    }
    
    public String getKeyCategory() {
        return this.keyCategory;
    }
    
    public boolean isPressed() {
        if (this.presses == 0) {
            return false;
        }
        --this.presses;
        return true;
    }
    
    private void unpressKey() {
        this.presses = 0;
        this.pressed = false;
    }
    
    public String getKeyDescription() {
        return this.keyDescription;
    }
    
    public int getKeyCodeDefault() {
        return this.keyCodeDefault;
    }
    
    public int getKeyCode() {
        return this.keyCode;
    }
    
    public void setKeyCode(final int p_151462_1_) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (mc.gameSettings != null) {
            if (this == mc.gameSettings.keyBindAttack || this == mc.gameSettings.keyBindUseItem) {
                if (p_151462_1_ != -100 && p_151462_1_ != -99) {
                    return;
                }
            }
            else if (this == mc.gameSettings.keyBindSprint && p_151462_1_ != 0) {
                if (p_151462_1_ == mc.gameSettings.keyBindSneak.keyCode) {
                    mc.gameSettings.keyBindSneak.keyCode = 0;
                }
            }
            else if (this == mc.gameSettings.keyBindSneak && p_151462_1_ != 0 && p_151462_1_ == mc.gameSettings.keyBindSprint.keyCode) {
                mc.gameSettings.keyBindSprint.keyCode = 0;
            }
        }
        this.keyCode = p_151462_1_;
    }
    
    public int compareTo(final KeyBinding p_compareTo_1_) {
        int var2 = I18n.format(this.keyCategory, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyCategory, new Object[0]));
        if (var2 == 0) {
            var2 = I18n.format(this.keyDescription, new Object[0]).compareTo(I18n.format(p_compareTo_1_.keyDescription, new Object[0]));
        }
        return var2;
    }
    
    @Override
    public int compareTo(final Object p_compareTo_1_) {
        return this.compareTo((KeyBinding)p_compareTo_1_);
    }
    
    static {
        keybindArray = new ArrayList();
        hash = new IntHashMap();
        keybindSet = new HashSet();
    }
}
