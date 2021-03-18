package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import us.zonix.client.util.*;

public final class ComboDisplay extends AbstractModule
{
    private static final StringSetting DISPLAY_TYPE;
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    
    public ComboDisplay() {
        super("Combo");
        this.setEnabled(false);
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(ComboDisplay.DRAW_BACKGROUND);
        this.addSetting(ComboDisplay.DISPLAY_TYPE);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(ComboDisplay.FOREGROUND);
        this.addSetting(ComboDisplay.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        String text = null;
        switch (ComboDisplay.DISPLAY_TYPE.getIndex()) {
            case 0: {
                text = "Combo: " + this.mc.thePlayer.combo;
                break;
            }
            case 1: {
                text = this.mc.thePlayer.combo + " hits";
                if (this.mc.thePlayer.combo == 1) {
                    text = "1 hit";
                    break;
                }
                break;
            }
            default: {
                text = String.valueOf(this.mc.thePlayer.combo);
                break;
            }
        }
        float width = (float)(this.mc.fontRenderer.getStringWidth(text) + 4);
        if (width < 70.0f) {
            width = 70.0f;
        }
        this.setWidth((int)width);
        this.setHeight(13);
        if (ComboDisplay.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), ComboDisplay.BACKGROUND.getValue());
        }
        this.mc.fontRenderer.drawString(text, this.getX() + this.getWidth() / 2 - this.mc.fontRenderer.getStringWidth(text) / 2, this.y + 3.0f, ComboDisplay.FOREGROUND.getValue());
    }
    
    static {
        DISPLAY_TYPE = new StringSetting("Display Type", new String[] { "Combo: 0", "0 hits", "0" });
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
