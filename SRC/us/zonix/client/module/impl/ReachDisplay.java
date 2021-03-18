package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import us.zonix.client.util.*;

public final class ReachDisplay extends AbstractModule
{
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    
    public ReachDisplay() {
        super("Reach Display");
        this.setEnabled(false);
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(ReachDisplay.DRAW_BACKGROUND);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(ReachDisplay.FOREGROUND);
        this.addSetting(ReachDisplay.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        String text = String.format("%.2f blocks", this.mc.entityRenderer.lastRange);
        if (this.mc.entityRenderer.lastAttackTime + 2000L < System.currentTimeMillis()) {
            text = "0.0 blocks";
        }
        float width = (float)(this.mc.fontRenderer.getStringWidth(text) + 4);
        if (width < 70.0f) {
            width = 70.0f;
        }
        this.setWidth((int)width);
        this.setHeight(13);
        if (ReachDisplay.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), ReachDisplay.BACKGROUND.getValue());
        }
        this.mc.fontRenderer.drawString(text, this.getX() + this.getWidth() / 2 - this.mc.fontRenderer.getStringWidth(text) / 2, this.y + 3.0f, ReachDisplay.FOREGROUND.getValue());
    }
    
    static {
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
