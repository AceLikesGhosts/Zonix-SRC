package us.zonix.client.module.impl;

import us.zonix.client.module.modules.*;
import us.zonix.client.setting.impl.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import us.zonix.client.*;
import us.zonix.client.util.font.*;
import us.zonix.client.util.*;
import net.minecraft.util.*;
import java.util.*;

public final class DirectionHUD extends AbstractModule
{
    private static final ResourceLocation CARET_DOWN;
    private static final ResourceLocation COMPASS;
    private static final Map<Integer, String> directionMap;
    private static final StringSetting DISPLAY_TYPE;
    private static final BooleanSetting DRAW_BACKGROUND;
    private static final ColorSetting FOREGROUND;
    private static final ColorSetting BACKGROUND;
    
    public DirectionHUD() {
        super("Direction HUD");
        this.addSetting(new LabelSetting("General Settings"));
        this.addSetting(DirectionHUD.DRAW_BACKGROUND);
        this.addSetting(DirectionHUD.DISPLAY_TYPE);
        this.addSetting(new LabelSetting("Color Settings"));
        this.addSetting(DirectionHUD.FOREGROUND);
        this.addSetting(DirectionHUD.BACKGROUND);
    }
    
    @Override
    public void renderReal() {
        switch (DirectionHUD.DISPLAY_TYPE.getIndex()) {
            case 0: {
                this.renderZonix();
                break;
            }
            case 1: {
                this.renderClassic(false);
                break;
            }
            case 2: {
                this.renderClassic(true);
                break;
            }
        }
    }
    
    private void renderZonix() {
        final int hex = DirectionHUD.FOREGROUND.getValue();
        Color color = new Color(DirectionHUD.FOREGROUND.getValue());
        final int opaque = new Color(color.getRed(), color.getGreen(), color.getBlue(), 169).getRGB();
        float direction;
        for (direction = this.mc.thePlayer.rotationYaw; direction > 360.0f; direction -= 360.0f) {}
        while (direction < 0.0f) {
            direction += 360.0f;
        }
        GL11.glPushMatrix();
        final float y = this.y + 17.5f;
        GL11.glPushMatrix();
        RenderUtil.startScissorBox(this.y, y + 25.0f, this.x, this.x + 300.0f);
        if (DirectionHUD.DRAW_BACKGROUND.getValue()) {
            RenderUtil.drawRect(this.x, this.y, this.x + 300.0f, this.y + 30.0f, DirectionHUD.BACKGROUND.getValue());
        }
        GL11.glTranslatef(-direction * 2.0f + 150.0f, 0.0f, 0.0f);
        final float steps = 15.0f;
        final List<Integer> directions = new LinkedList<Integer>();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 360; j += (int)steps) {
                directions.add(j);
            }
        }
        float x = this.x - directions.size() / 2 * steps;
        for (final Integer k : directions) {
            final String mapped = DirectionHUD.directionMap.get(k);
            if (mapped != null) {
                ZFontRenderer fontRenderer = Client.getInstance().getLargeBoldFontRenderer();
                if (mapped.length() == 2) {
                    fontRenderer = Client.getInstance().getRegularFontRenderer();
                }
                RenderUtil.drawCenteredString(fontRenderer, mapped, x, y + 5.0f, hex, false);
            }
            else {
                RenderUtil.drawRect(x, y + 1.5f, x + 1.0f, y + 5.0f, opaque);
                RenderUtil.drawCenteredString(Client.getInstance().getTinyFontRenderer(), String.valueOf(k), x, y + 10.0f, hex, false);
            }
            x += steps * 2.0f;
        }
        RenderUtil.endScissorBox();
        GL11.glPopMatrix();
        final float width = 7.5f;
        GL11.glPushMatrix();
        color = new Color(hex);
        GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, color.getAlpha() / 255.0f);
        RenderUtil.drawSquareTexture(DirectionHUD.CARET_DOWN, width, width + 5.0f, this.x + 150.0f - width / 2.0f, this.y + 5.0f);
        RenderUtil.drawCenteredString(Client.getInstance().getSmallFontRenderer(), String.valueOf((int)direction), this.x + 150.0f, this.y + Client.getInstance().getSmallFontRenderer().getHeight(), hex, false);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
        this.setHeight(35);
        this.setWidth(300);
    }
    
    private void renderClassic(final boolean dark) {
        final int direction = MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 256.0f / 360.0f + 0.5) & 0xFF;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.bindTexture(DirectionHUD.COMPASS);
        if (direction < 128) {
            HUDUtils.drawTexturedModalRect((int)this.getX(), (int)this.getY(), direction, dark ? 0 : 24, 65, 12, -100.0f);
        }
        else {
            HUDUtils.drawTexturedModalRect((int)this.getX(), (int)this.getY(), direction - 128, 12 + (dark ? 0 : 24), 65, 12, -100.0f);
        }
        RenderUtil.drawString(EnumChatFormatting.RED + "|", this.getX() + 32.0f, this.getY() + 1.0f, 16777215);
        RenderUtil.drawString(EnumChatFormatting.RED + "|" + EnumChatFormatting.RESET, this.getX() + 32.0f, this.getY() + 5.0f, 16777215);
        GL11.glPopMatrix();
        this.setHeight(12);
        this.setWidth(65);
    }
    
    static {
        CARET_DOWN = new ResourceLocation("icon/caret-down.png");
        COMPASS = new ResourceLocation("compass.png");
        directionMap = new HashMap<Integer, String>();
        DISPLAY_TYPE = new StringSetting("Display Type", new String[] { "Zonix", "Zonix", "Classic", "Classic - Dark" });
        DRAW_BACKGROUND = new BooleanSetting("Draw Background");
        FOREGROUND = new ColorSetting("Foreground", -1);
        BACKGROUND = new ColorSetting("Background", 1862270976);
        DirectionHUD.directionMap.put(0, "S");
        DirectionHUD.directionMap.put(45, "SW");
        DirectionHUD.directionMap.put(90, "W");
        DirectionHUD.directionMap.put(135, "NW");
        DirectionHUD.directionMap.put(180, "N");
        DirectionHUD.directionMap.put(225, "NE");
        DirectionHUD.directionMap.put(270, "E");
        DirectionHUD.directionMap.put(315, "SE");
    }
}
