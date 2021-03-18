package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import java.util.*;
import us.zonix.client.setting.impl.*;
import org.lwjgl.input.*;
import us.zonix.client.util.*;
import net.minecraft.client.gui.*;

public final class CPS extends AbstractModule
{
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    private final Set<Long> clicks;
    private boolean buttonDown;
    
    public CPS() {
        super("CPS");
        this.clicks = new HashSet<Long>();
        this.x = 4.0f;
        this.y = 76.0f;
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(CPS.DRAW_BACKGROUND);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(CPS.FOREGROUND);
        this.addSetting(CPS.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        final boolean buttonDown = Mouse.isButtonDown(0);
        Mouse.poll();
        final boolean polledButtonDown = Mouse.isButtonDown(0);
        if (polledButtonDown && buttonDown && !this.buttonDown) {
            this.clicks.add(System.currentTimeMillis());
        }
        this.buttonDown = polledButtonDown;
        this.clicks.removeIf(l -> l + 1000L < System.currentTimeMillis());
        final FontRenderer fontRenderer = this.mc.fontRenderer;
        final String fps = this.clicks.size() + " CPS";
        final int width = 70;
        this.setWidth(width);
        final int height = 13;
        this.setHeight(height);
        if (CPS.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.x, this.y, this.x + width, this.y + height, this.getColorSetting("Background").getValue());
        }
        fontRenderer.drawString(fps, this.x + (width / 2 - fontRenderer.getStringWidth(fps) / 2), this.y + 3.0f, this.getColorSetting("Foreground").getValue());
    }
    
    static {
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
