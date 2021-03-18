package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import java.util.*;
import us.zonix.client.setting.impl.*;
import us.zonix.client.util.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.settings.*;
import java.beans.*;

public final class Keystrokes extends AbstractModule
{
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    private final boolean[] buttonStates;
    private final double[] brightness;
    private final long[] buttonTimes;
    private final int[] buttonColors;
    
    public Keystrokes() {
        super("Keystrokes");
        final float n = 4.0f;
        this.y = n;
        this.x = n;
        this.buttonStates = new boolean[6];
        this.brightness = new double[6];
        this.buttonTimes = new long[6];
        this.buttonColors = new int[6];
        Arrays.fill(this.buttonTimes, System.currentTimeMillis());
        Arrays.fill(this.buttonStates, true);
        Arrays.fill(this.buttonColors, 255);
        Arrays.fill(this.brightness, 1.0);
        this.setHeight(70);
        this.setWidth(70);
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(new BooleanSetting("Show mouse buttons", true));
        this.addSetting(new BooleanSetting("Show key buttons", true));
        this.addSetting(Keystrokes.DRAW_BACKGROUND);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(Keystrokes.FOREGROUND);
        this.addSetting(Keystrokes.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        int height = 0;
        if (this.getBooleanSetting("Show key buttons").getValue()) {
            final RenderKey forward = new RenderKey(this.mc.gameSettings.keyBindForward, 26, 2);
            final RenderKey right = new RenderKey(this.mc.gameSettings.keyBindRight, 50, 26);
            final RenderKey back = new RenderKey(this.mc.gameSettings.keyBindBack, 26, 26);
            final RenderKey left = new RenderKey(this.mc.gameSettings.keyBindLeft, 2, 26);
            final RenderKey[] keys = { forward, back, left, right };
            this.renderKeys(keys);
            height += 46;
        }
        if (this.getBooleanSetting("Show mouse buttons").getValue()) {
            final RenderButton lmb = new RenderButton("LMB", 0, 2, 50);
            final RenderButton rmb = new RenderButton("RMB", 1, 38, 50);
            final RenderButton[] buttons = { lmb, rmb };
            this.renderButtons(buttons);
            height += 24;
        }
        this.setHeight(height);
        this.setWidth(70);
    }
    
    private void renderButtons(final RenderButton[] buttons) {
        for (int i = 0; i < buttons.length; ++i) {
            final RenderButton button = buttons[i];
            final boolean wasButtonDown = this.buttonStates[i + 4];
            final boolean buttonDown = Mouse.isButtonDown(button.button);
            Mouse.poll();
            final boolean polledButtonDown = Mouse.isButtonDown(button.button);
            if (polledButtonDown != wasButtonDown && buttonDown == polledButtonDown) {
                this.buttonTimes[i + 4] = System.currentTimeMillis();
                this.buttonStates[i + 4] = polledButtonDown;
            }
            if (polledButtonDown) {
                this.buttonColors[i + 4] = Math.min(255, (int)((System.currentTimeMillis() - this.buttonTimes[i + 4]) * 2L));
                this.brightness[i + 4] = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.buttonTimes[i + 4]) / 20.0);
            }
            else {
                this.buttonColors[i + 4] = Math.max(0, (int)(255L - (System.currentTimeMillis() - this.buttonTimes[i + 4]) * 2L));
                this.brightness[i + 4] = Math.min(1.0, (System.currentTimeMillis() - this.buttonTimes[i + 4]) / 20.0);
            }
            final int color = this.buttonColors[i + 4];
            int y = button.y;
            if (!this.getBooleanSetting("Show key buttons").getValue()) {
                y -= 47;
            }
            if (Keystrokes.DRAW_BACKGROUND.getValue()) {
                RenderUtil.drawRect(this.x + button.x - 2.0f, this.y + y - 2.0f, this.x + button.x + 32.0f, this.y + y + 20.0f, 2013265920 + (color << 16) + (color << 8) + color);
            }
            final int currentColor = this.getColorSetting("Foreground").getValue();
            final int green = currentColor >> 8 & 0xFF;
            final int red = currentColor >> 16 & 0xFF;
            final int blue = currentColor & 0xFF;
            final int renderColor = -16777216 + ((int)(red * this.brightness[i + 4]) << 16) + ((int)(green * this.brightness[i + 4]) << 8) + (int)(blue * this.brightness[i + 4]);
            final FontRenderer fontRenderer = this.mc.fontRenderer;
            fontRenderer.drawString(button.name, this.x + button.x + 6.0f, this.y + y + 6.0f, renderColor);
        }
    }
    
    private void renderKeys(final RenderKey[] keys) {
        for (int i = 0; i < keys.length; ++i) {
            final RenderKey key = keys[i];
            final KeyBinding keyBinding = key.key;
            final String name = Keyboard.getKeyName(keyBinding.getKeyCode());
            final boolean buttonDown = Keyboard.isKeyDown(keyBinding.getKeyCode());
            final boolean wasButtonDown = this.buttonStates[i];
            Keyboard.poll();
            final boolean polledButtonDown = Keyboard.isKeyDown(keyBinding.getKeyCode());
            if (polledButtonDown != wasButtonDown && buttonDown == polledButtonDown) {
                this.buttonTimes[i] = System.currentTimeMillis();
                this.buttonStates[i] = buttonDown;
            }
            if (buttonDown) {
                this.buttonColors[i] = Math.min(255, (int)((System.currentTimeMillis() - this.buttonTimes[i]) * 2L));
                this.brightness[i] = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.buttonTimes[i]) / 20.0);
            }
            else {
                this.buttonColors[i] = Math.max(0, (int)(255L - (System.currentTimeMillis() - this.buttonTimes[i]) * 2L));
                this.brightness[i] = Math.min(1.0, (System.currentTimeMillis() - this.buttonTimes[i]) / 20.0);
            }
            final int color = this.buttonColors[i];
            if (Keystrokes.DRAW_BACKGROUND.getValue()) {
                RenderUtil.drawRect(this.x + key.x - 2.0f, this.y + key.y - 2.0f, this.x + key.x + 20.0f, this.y + key.y + 20.0f, 2013265920 + (color << 16) + (color << 8) + color);
            }
            final int currentColor = this.getColorSetting("Foreground").getValue();
            final int green = currentColor >> 8 & 0xFF;
            final int red = currentColor >> 16 & 0xFF;
            final int blue = currentColor & 0xFF;
            final int renderColor = -16777216 + ((int)(red * this.brightness[i]) << 16) + ((int)(green * this.brightness[i]) << 8) + (int)(blue * this.brightness[i]);
            this.mc.fontRenderer.drawString(name, this.x + key.x + 6.0f, this.y + key.y + 6.0f, renderColor);
        }
    }
    
    static {
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
    
    private class RenderButton
    {
        private final String name;
        private final int button;
        private final int x;
        private final int y;
        
        @ConstructorProperties({ "name", "button", "x", "y" })
        public RenderButton(final String name, final int button, final int x, final int y) {
            this.name = name;
            this.button = button;
            this.x = x;
            this.y = y;
        }
    }
    
    private class RenderKey
    {
        private final KeyBinding key;
        private final int x;
        private final int y;
        
        @ConstructorProperties({ "key", "x", "y" })
        public RenderKey(final KeyBinding key, final int x, final int y) {
            this.key = key;
            this.x = x;
            this.y = y;
        }
    }
}
