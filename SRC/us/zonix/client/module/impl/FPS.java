package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import net.minecraft.client.*;
import us.zonix.client.util.*;
import net.minecraft.client.gui.*;

public final class FPS extends AbstractModule
{
    private static final BooleanSetting DRAW_BACKGROUND;
    
    public FPS() {
        super("FPS");
        this.x = 4.0f;
        this.y = 91.0f;
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(FPS.DRAW_BACKGROUND);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(new ColorSetting("Foreground", -65536));
        this.addSetting(new ColorSetting("Background", 1862270976));
    }
    
    @Override
    public void renderReal() {
        final int height = 13;
        final int width = 70;
        this.setHeight(height);
        this.setWidth(width);
        final FontRenderer fontRenderer = this.mc.fontRenderer;
        final String fpsString = Minecraft.debugFPS + " FPS";
        if (FPS.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.x, this.y, this.x + width, this.y + height, this.getColorSetting("Background").getValue());
        }
        fontRenderer.drawString(fpsString, this.x + (width / 2 - fontRenderer.getStringWidth(fpsString) / 2), this.y + 3.0f, this.getColorSetting("Foreground").getValue());
    }
    
    static {
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
    }
}
