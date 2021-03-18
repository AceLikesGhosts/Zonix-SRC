package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import us.zonix.client.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;

public final class ToggleSneak extends AbstractModule
{
    private final TextSetting vanillaSprintText;
    private final TextSetting toggleSprintText;
    private final TextSetting toggleSneakText;
    private final TextSetting heldSprintText;
    private final TextSetting heldSneakText;
    private final TextSetting flyBoostText;
    private final TextSetting dismountingText;
    private final TextSetting descendingText;
    private final TextSetting ridingText;
    private final TextSetting flyText;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    private String hudText;
    private boolean sprintHeldAndReleased;
    private boolean sprintPressTicked;
    private boolean sprinting;
    private boolean inventoryPress;
    private boolean sneakPressTicked;
    private boolean wasRiding;
    private boolean sneaking;
    private long lastSprintPressed;
    private long lastSneakPressed;
    
    public ToggleSneak() {
        super("ToggleSneak");
        this.vanillaSprintText = new TextSetting("Vanilla Sprint Text", "[Sprinting (Vanilla)]");
        this.toggleSprintText = new TextSetting("Toggle Sprint Text", "[Sprinting (Toggled)]");
        this.toggleSneakText = new TextSetting("Toggle Sneak Text", "[Sneaking (Toggled)]");
        this.heldSprintText = new TextSetting("Held Sprint Text", "[Sprinting (Key Held)]");
        this.heldSneakText = new TextSetting("Held Sneak Text", "[Sneaking (Key Held)]");
        this.flyBoostText = new TextSetting("Fly Boost Text", "[Flying (10x boost)]");
        this.dismountingText = new TextSetting("Dismounting Text", "[Dismounting]");
        this.descendingText = new TextSetting("Descending Text", "[Descending]");
        this.ridingText = new TextSetting("Riding Text", "[Riding]");
        this.flyText = new TextSetting("Fly Text", "[Flying]");
        this.y = 4.0f;
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(new BooleanSetting("Hide from HUD", false));
        this.addSetting(new BooleanSetting("Draw Background", false));
        this.addSetting(new BooleanSetting("Toggle Sprint", true));
        this.addSetting(new BooleanSetting("Toggle Sneak", true));
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(ToggleSneak.FOREGROUND);
        this.addSetting(ToggleSneak.BACKGROUND);
        this.addSetting(new LabelSetting("Text Settings"));
        this.addSetting(this.toggleSprintText);
        this.addSetting(this.toggleSneakText);
        this.addSetting(this.heldSprintText);
        this.addSetting(this.heldSneakText);
        this.addSetting(this.vanillaSprintText);
        this.addSetting(this.flyBoostText);
        this.addSetting(this.flyText);
        this.addSetting(this.ridingText);
        this.addSetting(this.descendingText);
        this.addSetting(this.dismountingText);
    }
    
    @Override
    public void renderPreview() {
        if (this.hudText != null) {
            this.renderReal();
            return;
        }
        this.hudText = this.toggleSprintText.getValue().replace("&", "§");
        if (this.getBooleanSetting("Draw Background").getValue()) {
            this.hudText = this.hudText.replace("[", "").replace("]", "");
        }
        this.setWidth(this.mc.fontRenderer.getStringWidth(this.hudText) + 3);
        this.setHeight(this.mc.fontRenderer.FONT_HEIGHT + 3);
        if (this.getBooleanSetting("Draw Background").getValue()) {
            this.setHeight(this.getHeight() + 2);
            this.setWidth(this.getWidth() + 2);
        }
        this.renderReal();
        this.hudText = null;
    }
    
    @Override
    public void renderReal() {
        if (this.getBooleanSetting("Hide from HUD").getValue()) {
            return;
        }
        if (this.hudText == null) {
            return;
        }
        final FontRenderer fontRenderer = this.mc.fontRenderer;
        if (this.getBooleanSetting("Draw Background").getValue()) {
            RenderUtil.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), ToggleSneak.BACKGROUND.getValue());
            fontRenderer.drawString(this.hudText, this.x + 2.0f, this.y + 2.0f, this.getColorSetting("Foreground").getValue());
            return;
        }
        fontRenderer.drawStringWithShadow(this.hudText, this.getX() + 2.0f, this.getY() + 2.0f, this.getColorSetting("Foreground").getValue());
    }
    
    private void setSneaking(final boolean sneaking) {
        KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindSneak.getKeyCode(), sneaking);
        this.sneaking = sneaking;
    }
    
    @Override
    public void onPrePlayerUpdate() {
        final KeyBinding keySprint = this.mc.gameSettings.keyBindSprint;
        final KeyBinding keySneak = this.mc.gameSettings.keyBindSneak;
        if (this.getBooleanSetting("Toggle Sneak").getValue() && !this.mc.thePlayer.capabilities.isFlying && this.mc.inGameHasFocus) {
            if (Keyboard.isKeyDown(keySneak.getKeyCode()) && !this.sneakPressTicked) {
                this.lastSneakPressed = System.currentTimeMillis();
                this.sneakPressTicked = true;
            }
            else if (!Keyboard.isKeyDown(keySneak.getKeyCode()) && this.sneakPressTicked) {
                if (System.currentTimeMillis() - this.lastSneakPressed < 300L) {
                    this.setSneaking(this.sneaking = !this.sneaking);
                }
                this.sneakPressTicked = false;
            }
        }
        if (this.getBooleanSetting("Toggle Sprint").getValue() && !this.mc.thePlayer.capabilities.isFlying) {
            if (keySprint.getIsKeyPressed() && !this.sprintPressTicked) {
                this.lastSprintPressed = System.currentTimeMillis();
                this.sprintPressTicked = true;
            }
            else if (!keySprint.getIsKeyPressed() && this.sprintPressTicked) {
                if (System.currentTimeMillis() - this.lastSprintPressed < 300L) {
                    this.sprinting = !this.sprinting;
                }
                this.sprintPressTicked = false;
            }
        }
        if (this.sneaking) {
            this.setSneaking(true);
        }
        if (this.sprinting) {
            this.mc.thePlayer.setSprinting(true);
        }
        this.hudText = null;
        if (this.mc.thePlayer.capabilities.isFlying) {
            if (keySprint.getIsKeyPressed()) {
                this.hudText = this.flyBoostText.getValue();
            }
            else if (keySneak.getIsKeyPressed()) {
                this.hudText = this.flyText.getValue() + " " + this.descendingText.getValue();
            }
            else {
                this.hudText = this.flyText.getValue();
            }
        }
        else if (this.mc.thePlayer.isRiding()) {
            this.hudText = this.ridingText.getValue();
        }
        else if (this.mc.thePlayer.isSneaking()) {
            if (this.sneaking) {
                this.hudText = this.toggleSneakText.getValue();
            }
            else {
                this.hudText = this.heldSneakText.getValue();
            }
        }
        else if (this.mc.thePlayer.isSprinting()) {
            if (this.sprinting) {
                this.hudText = this.toggleSprintText.getValue();
            }
            else if (keySprint.getIsKeyPressed()) {
                this.hudText = this.heldSprintText.getValue();
            }
            else {
                this.hudText = this.vanillaSprintText.getValue();
            }
        }
        if (this.hudText == null) {
            this.setHeight(0);
            this.setWidth(0);
        }
        else {
            this.setWidth(this.mc.fontRenderer.getStringWidth(this.hudText) + 3);
            this.setHeight(this.mc.fontRenderer.FONT_HEIGHT + 3);
        }
    }
    
    static {
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
