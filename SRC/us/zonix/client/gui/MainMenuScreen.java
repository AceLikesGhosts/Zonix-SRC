package us.zonix.client.gui;

import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import us.zonix.client.util.*;
import us.zonix.client.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;

public final class MainMenuScreen extends GuiScreen
{
    public static final ResourceLocation closeIcon;
    private static final ResourceLocation panoramaBackground;
    private static final ResourceLocation panoramaBlur;
    private static final ResourceLocation languageIcon;
    private static final ResourceLocation settingsIcon;
    private static final ResourceLocation zonixLogo;
    private int panoramaTimer;
    
    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int button) {
        if (button != 0) {
            return;
        }
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        final float buttonWidth = 90.0f;
        float startX = resolution.getScaledWidth() - 10.0f;
        for (int i = 0; i < 3; ++i) {
            final boolean hovering = mouseX > startX - buttonWidth && mouseX < startX && mouseY > 7.5f && mouseY < 35.0f;
            if (hovering) {
                switch (i) {
                    case 0: {
                        this.mc.shutdown();
                        break;
                    }
                    case 1: {
                        this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                        break;
                    }
                    case 2: {
                        this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
                        break;
                    }
                }
                return;
            }
            startX -= buttonWidth + 10.0f;
        }
        final float height = 200.0f;
        float minX = resolution.getScaledWidth() / 2 - 165.0f;
        final float minY = resolution.getScaledHeight() / 2 - height / 2.0f + height - 60.0f;
        for (int j = 0; j < 2; ++j) {
            if (mouseX > minX && mouseX < minX + 150.0f && mouseY > minY && mouseY < minY + 35.0f) {
                switch (j) {
                    case 0: {
                        this.mc.displayGuiScreen(new GuiSelectWorld(this));
                        break;
                    }
                    case 1: {
                        this.mc.displayGuiScreen(new GuiMultiplayer(this));
                        break;
                    }
                }
                return;
            }
            minX += 180.0f;
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        RenderUtil.drawTexture(MainMenuScreen.panoramaBackground, 0.0f, 0.0f, (float)resolution.getScaledWidth(), (float)resolution.getScaledHeight());
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), (float)resolution.getScaledHeight(), -1358954496);
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), 40.0f, -1450294203);
        RenderUtil.drawString(Client.getInstance().getLargeBoldFontRenderer(), "ZONIX CLIENT", 50.0f, 12.0f, -1);
        final float buttonWidth = 90.0f;
        float startX = resolution.getScaledWidth() - 10.0f;
        for (int i = 0; i < 3; ++i) {
            final boolean hovering = mouseX > startX - buttonWidth && mouseX < startX && mouseY > 7.5f && mouseY < 35.0f;
            RenderUtil.drawBorderedRoundedRect(startX - buttonWidth, 9.5f, startX, 33.0f, 5.0f, -11000539, -7848387);
            switch (i) {
                case 0: {
                    RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), "Close", (float)(int)(startX - buttonWidth / 2.0f + 10.0f + 0.5f), 21.0f, hovering ? -1 : -1442840577);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, hovering ? 1.0f : 0.6f);
                    RenderUtil.drawSquareTexture(MainMenuScreen.closeIcon, 9.0f, startX - buttonWidth / 2.0f - 28.0f, 12.0f);
                    break;
                }
                case 1: {
                    RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), "Settings", (float)(int)(startX - buttonWidth / 2.0f + 10.0f + 0.5f), 21.0f, hovering ? -1 : -1442840577);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, hovering ? 1.0f : 0.6f);
                    RenderUtil.drawSquareTexture(MainMenuScreen.settingsIcon, 9.0f, startX - buttonWidth / 2.0f - 37.0f, 12.0f);
                    break;
                }
                case 2: {
                    RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), "Language", (float)(int)(startX - buttonWidth / 2.0f + 9.5f), 21.0f, hovering ? -1 : -1442840577);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, hovering ? 1.0f : 0.6f);
                    RenderUtil.drawSquareTexture(MainMenuScreen.languageIcon, 9.0f, startX - buttonWidth / 2.0f - 42.0f, 12.0f);
                    break;
                }
            }
            startX -= buttonWidth + 10.0f;
        }
        final float width = 400.0f;
        final float height = 200.0f;
        float minX = resolution.getScaledWidth() / 2 - width / 2.0f;
        float minY = resolution.getScaledHeight() / 2 - height / 2.0f;
        RenderUtil.drawBorderedRect(minX, minY, minX + width, minY + height, 5.0f, -1904659663, 1935618353);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.drawSquareTexture(MainMenuScreen.zonixLogo, 130.0f, 120.0f, resolution.getScaledWidth() / 2 - 65.0f, minY + 12.0f);
        GL11.glPopMatrix();
        final String[] strings = { "SINGLEPLAYER", "MULTIPLAYER" };
        minX = resolution.getScaledWidth() / 2 - 165.0f;
        minY += height - 60.0f;
        for (int j = 0; j < 2; ++j) {
            RenderUtil.drawBorderedRoundedRect(minX, minY, minX + 150.0f, minY + 35.0f, 5.0f, 2.0f, -7593185, -14413294);
            final boolean hovering2 = mouseX >= minX && mouseX <= minX + 150.0f && mouseY >= minY && mouseY <= minY + 35.0f;
            RenderUtil.drawCenteredString(Client.getInstance().getMediumBoldFontRenderer(), strings[j], (float)(int)(minX + 73.0f), (float)(int)(minY + 17.5f), hovering2 ? -1 : -1442840577);
            minX += 180.0f;
        }
        RenderUtil.drawRect(0.0f, resolution.getScaledHeight() - 20.0f, (float)resolution.getScaledWidth(), (float)resolution.getScaledHeight(), -1455867591);
        RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), "Property of Zonix, LLC", 10.0f, resolution.getScaledHeight() - 12.0f, -1);
        RenderUtil.drawCenteredString(Client.getInstance().getSmallFontRenderer(), "Zonix is in no way affiliated with Mojang, AB.", (float)(resolution.getScaledWidth() / 2), (float)(resolution.getScaledHeight() - 10), -1);
        final String version = "1.0.0-Dev-Snapshot";
        RenderUtil.drawString(Client.getInstance().getSmallFontRenderer(), version, resolution.getScaledWidth() - 10.0f - Client.getInstance().getSmallFontRenderer().getStringWidth(version), resolution.getScaledHeight() - 12.0f, -1);
    }
    
    @Override
    public void updateScreen() {
        ++this.panoramaTimer;
    }
    
    private void renderSkybox(final float partialTicks) {
        this.mc.getFramebuffer().unbindFramebuffer();
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(partialTicks);
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.rotateAndBlurSkybox();
        this.mc.getFramebuffer().bindFramebuffer(true);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        final Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        final float var5 = (this.width > this.height) ? (120.0f / this.width) : (120.0f / this.height);
        final float var6 = this.height * var5 / 256.0f;
        final float var7 = this.width * var5 / 256.0f;
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        final int var8 = this.width;
        final int var9 = this.height;
        var4.addVertexWithUV(0.0, var9, this.zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, this.zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, this.zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, this.zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }
    
    private void drawPanorama(final float partialTicks) {
        final Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        final byte var5 = 8;
        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GL11.glPushMatrix();
            final float var7 = (var6 % var5 / (float)var5 - 0.5f) / 64.0f;
            final float var8 = (var6 / var5 / (float)var5 - 0.5f) / 64.0f;
            final float var9 = 0.0f;
            GL11.glTranslatef(var7, var8, var9);
            GL11.glRotatef(MathHelper.sin((this.panoramaTimer + partialTicks) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-(this.panoramaTimer + partialTicks) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int var10 = 0; var10 < 6; ++var10) {
                GL11.glPushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                this.mc.getTextureManager().bindTexture(MainMenuScreen.panoramaBackground);
                var4.startDrawingQuads();
                var4.setColorRGBA_I(16777215, 255 / (var6 + 1));
                final float var11 = 0.0f;
                var4.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var11, 1.0f - var11);
                var4.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var11, 1.0f - var11);
                var4.draw();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        var4.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(2929);
    }
    
    private void rotateAndBlurSkybox() {
        this.mc.getTextureManager().bindTexture(MainMenuScreen.panoramaBlur);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColorMask(true, true, true, false);
        final Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        GL11.glDisable(3008);
        final byte var3 = 3;
        for (int var4 = 0; var4 < var3; ++var4) {
            var2.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (var4 + 1));
            final int var5 = this.width;
            final int var6 = this.height;
            final float var7 = (var4 - var3 / 2) / 256.0f;
            var2.addVertexWithUV(var5, var6, this.zLevel, 0.0f + var7, 1.0);
            var2.addVertexWithUV(var5, 0.0, this.zLevel, 1.0f + var7, 1.0);
            var2.addVertexWithUV(0.0, 0.0, this.zLevel, 1.0f + var7, 0.0);
            var2.addVertexWithUV(0.0, var6, this.zLevel, 0.0f + var7, 0.0);
        }
        var2.draw();
        GL11.glEnable(3008);
        GL11.glColorMask(true, true, true, true);
    }
    
    static {
        closeIcon = new ResourceLocation("icon/close.png");
        panoramaBackground = new ResourceLocation("background.png");
        languageIcon = new ResourceLocation("icon/language.png");
        settingsIcon = new ResourceLocation("icon/settings.png");
        zonixLogo = new ResourceLocation("zonix.png");
        final DynamicTexture viewportTexture = new DynamicTexture(256, 256);
        panoramaBlur = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("background", viewportTexture);
    }
}
