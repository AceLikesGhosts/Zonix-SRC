package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import net.minecraft.util.*;
import us.zonix.client.util.*;
import net.minecraft.client.gui.*;

public final class Coordinates extends AbstractModule
{
    private static final LabelSetting GENERAL_LABEL;
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final BooleanSetting SHOW_DECIMAL;
    private static final StringSetting DISPLAY_TYPE;
    private static final BooleanSetting SHOW_Y_COORD;
    private static final BooleanSetting SHOW_DIRECTION;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    
    public Coordinates() {
        super("Coordinates");
        this.x = 4.0f;
        this.y = 106.0f;
        this.addSetting(Coordinates.GENERAL_LABEL);
        this.addSetting(Coordinates.SHOW_Y_COORD);
        this.addSetting(Coordinates.SHOW_DIRECTION);
        this.addSetting(Coordinates.SHOW_DECIMAL);
        this.addSetting(Coordinates.DISPLAY_TYPE);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(Coordinates.FOREGROUND);
        this.addSetting(Coordinates.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        final String[] rotations = { "N", "NE", "E", "SE", "S", "SW", "W", "NW" };
        double angle = MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);
        angle = (angle + 202.5) % 360.0 / 45.0;
        final String direction = rotations[MathHelper.floor_double(angle)];
        final double x = this.mc.thePlayer.posX;
        final double y = this.mc.thePlayer.posY;
        final double z = this.mc.thePlayer.posZ;
        this.render(direction, x, y, z);
    }
    
    private void render(final String direction, final double x, final double y, final double z) {
        switch (Coordinates.DISPLAY_TYPE.getIndex()) {
            case 0: {
                this.renderCompact(direction, x, y, z, Coordinates.DRAW_BACKGROUND.getValue());
                break;
            }
            case 1: {
                this.renderHorizontal(direction, x, y, z);
                break;
            }
        }
    }
    
    private void renderHorizontal(final String direction, final double x, final double y, final double z) {
        String text = String.format("%.2f, %.2f, %.2f", x, y, z);
        if (!Coordinates.SHOW_DECIMAL.getValue()) {
            text = String.format("%d, %d, %d", (int)x, (int)y, (int)z);
            if (!Coordinates.SHOW_Y_COORD.getValue()) {
                text = String.format("%d, %d", (int)x, (int)z);
            }
        }
        else if (!Coordinates.SHOW_Y_COORD.getValue()) {
            text = String.format("%.2f, %.2f", x, z);
        }
        if (Coordinates.SHOW_DIRECTION.getValue()) {
            text = text + " - " + direction;
        }
        text = "[" + text + "]";
        this.setWidth(this.mc.fontRenderer.getStringWidth(text) + 4);
        this.setHeight(this.mc.fontRenderer.FONT_HEIGHT + 4);
        this.mc.fontRenderer.drawString(text, this.x + 3.0f, this.y + 3.0f, this.getColorSetting("Foreground").getValue());
    }
    
    private void renderCompact(final String direction, final double x, final double y, final double z, final boolean background) {
        final FontRenderer fontRenderer = this.mc.fontRenderer;
        String sX = String.format("X %.2f", x);
        String sY = String.format("Y %.2f", y);
        String sZ = String.format("Z %.2f", z);
        if (!Coordinates.SHOW_DECIMAL.getValue()) {
            sX = "X " + (int)x;
            sY = "Y " + (int)y;
            sZ = "Z " + (int)z;
        }
        int height = 12 + fontRenderer.FONT_HEIGHT * 2;
        if (this.getBooleanSetting("Show Y coordinate").getValue()) {
            height += fontRenderer.FONT_HEIGHT;
        }
        int width = 40;
        if (this.getBooleanSetting("Show direction").getValue()) {
            width = 70;
        }
        this.setHeight(height);
        this.setWidth(width);
        if (background) {
            RenderUtil.drawRect(this.x, this.y, this.x + width, this.y + height, this.getColorSetting("Background").getValue());
        }
        float yPos = this.y + 4.0f;
        fontRenderer.drawString(sX, this.x + 3.0f, yPos, this.getColorSetting("Foreground").getValue());
        yPos += fontRenderer.FONT_HEIGHT;
        if (this.getBooleanSetting("Show Y coordinate").getValue()) {
            fontRenderer.drawString(sY, this.x + 3.0f, yPos + 3.0f, this.getColorSetting("Foreground").getValue());
            yPos += fontRenderer.FONT_HEIGHT;
        }
        if (this.getBooleanSetting("Show direction").getValue()) {
            fontRenderer.drawString(direction, this.x + width - 3.0f - fontRenderer.getStringWidth(direction), yPos + 3.0f - fontRenderer.FONT_HEIGHT, this.getColorSetting("Foreground").getValue());
        }
        fontRenderer.drawString(sZ, this.x + 3.0f, yPos + (this.getBooleanSetting("Show Y coordinate").getValue() ? 6 : 3), this.getColorSetting("Foreground").getValue());
    }
    
    static {
        GENERAL_LABEL = new LabelSetting("General Settings");
        DRAW_BACKGROUND = new BooleanSetting("Draw background", true);
        SHOW_DECIMAL = new BooleanSetting("Show decimal", true);
        DISPLAY_TYPE = new StringSetting("Display Type", new String[] { "Zonix", "Compact" });
        SHOW_Y_COORD = new BooleanSetting("Show Y coordinate", true);
        SHOW_DIRECTION = new BooleanSetting("Show direction", true);
        FOREGROUND = new ColorSetting("Foreground", -65536);
        BACKGROUND = new ColorSetting("Background", 1862270976);
    }
}
