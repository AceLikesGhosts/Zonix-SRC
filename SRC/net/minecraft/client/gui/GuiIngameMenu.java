package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.achievement.*;
import us.zonix.client.util.*;
import us.zonix.client.*;
import org.lwjgl.opengl.*;
import us.zonix.client.gui.*;

public class GuiIngameMenu extends GuiScreen
{
    private final ResourceLocation usersIcon;
    private int field_146445_a;
    private int field_146444_f;
    private static final String __OBFID = "CL_00000703";
    private final EscapeButton[] escapeButtons;
    
    public GuiIngameMenu() {
        this.usersIcon = new ResourceLocation("icon/users.png");
        this.escapeButtons = new EscapeButton[5];
    }
    
    @Override
    public void initGui() {
        this.mc.entityRenderer.setBlur(true);
        this.escapeButtons[0] = new EscapeButton("BACK TO GAME") {
            @Override
            protected void onClick(final int mouseX, final int mouseY) {
                GuiIngameMenu.this.mc.displayGuiScreen(null);
                GuiIngameMenu.this.mc.setIngameFocus();
            }
        };
        this.escapeButtons[1] = new EscapeButton("OPTIONS") {
            @Override
            protected void onClick(final int mouseX, final int mouseY) {
                GuiIngameMenu.this.mc.displayGuiScreen(new GuiOptions(GuiIngameMenu.this, GuiIngameMenu.this.mc.gameSettings));
            }
        };
        this.escapeButtons[2] = new EscapeButton("SERVER SELECTOR") {
            @Override
            protected void onClick(final int mouseX, final int mouseY) {
                GuiIngameMenu.this.mc.displayGuiScreen(new GuiMultiplayer(GuiIngameMenu.this));
            }
        };
        this.escapeButtons[3] = new EscapeButton("OPEN TO LAN") {
            @Override
            protected void onClick(final int mouseX, final int mouseY) {
                GuiIngameMenu.this.mc.displayGuiScreen(new GuiShareToLan(GuiIngameMenu.this));
            }
        };
        this.escapeButtons[4] = new EscapeButton("DISCONNECT") {
            @Override
            protected void onClick(final int mouseX, final int mouseY) {
                GuiIngameMenu.this.mc.theWorld.sendQuittingDisconnectingPacket();
                GuiIngameMenu.this.mc.loadWorld(null);
                GuiIngameMenu.this.mc.displayGuiScreen(new GuiMainMenu());
            }
        };
        this.field_146445_a = 0;
        this.buttonList.clear();
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        switch (p_146284_1_.id) {
            case 0: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
            case 1: {
                p_146284_1_.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new GuiMainMenu());
                break;
            }
            case 4: {
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            }
            case 5: {
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
                break;
            }
            case 6: {
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
                break;
            }
            case 7: {
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            }
        }
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.field_146444_f;
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        float buttonWidth = 90.0f;
        float startX = resolution.getScaledWidth() - 10.0f;
        if (mouseX >= startX - buttonWidth && mouseX <= startX && mouseY >= 11.5f && mouseY <= 31.0f) {
            this.mc.shutdown();
            return;
        }
        startX -= buttonWidth + 10.0f;
        buttonWidth = 70.0f;
        if (mouseX >= startX - buttonWidth && mouseX <= startX && mouseY >= 11.5f && mouseY <= 31.0f) {
            System.out.println("Open friend menu");
            return;
        }
        final float boxWidth = 225.0f;
        final float boxHeight = 150.0f;
        final float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f + 10.0f;
        float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f + 10.0f;
        final float buttonHeight = 25.0f;
        for (final EscapeButton escapeButton : this.escapeButtons) {
            if (!escapeButton.text.equals("OPEN TO LAN") || this.mc.isSingleplayer()) {
                if (!escapeButton.text.equals("SERVER SELECTOR") || !this.mc.isSingleplayer()) {
                    if (mouseX >= minX && mouseX <= minX + boxWidth - 20.0f && mouseY >= minY && mouseY <= minY + buttonHeight) {
                        escapeButton.onClick(mouseX, mouseY);
                        return;
                    }
                    minY += buttonHeight + 10.0f;
                }
            }
        }
    }
    
    @Override
    public void onGuiClosed() {
        this.mc.entityRenderer.setBlur(false);
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution resolution = new ScaledResolution(this.mc);
        RenderUtil.drawRect(0.0f, 0.0f, (float)resolution.getScaledWidth(), 40.0f, -291356865);
        RenderUtil.drawString(Client.getInstance().getLargeBoldFontRenderer(), "ZONIX CLIENT", 10.0f, 12.5f, -1, true);
        float buttonWidth = 90.0f;
        float startX = resolution.getScaledWidth() - 10.0f;
        RenderUtil.drawBorderedRoundedRect(startX - buttonWidth, 11.5f, startX, 31.0f, 5.0f, -11000539, -7848387);
        RenderUtil.drawCenteredString(Client.getInstance().getSmallBoldFontRenderer(), "EXIT TO DESKTOP", (int)(startX - buttonWidth / 2.0f) + 6.0f, 21.0f, -1);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.drawSquareTexture(MainMenuScreen.closeIcon, 7.0f, startX - buttonWidth + 2.0f, 14.5f);
        GL11.glPopMatrix();
        startX -= buttonWidth + 10.0f;
        buttonWidth = 70.0f;
        RenderUtil.drawBorderedRoundedRect(startX - buttonWidth, 11.5f, startX, 31.0f, 5.0f, -11000539, -7848387);
        final int onlineFriends = Client.getInstance().getFriendManager().getOnlineFriends().size();
        RenderUtil.drawCenteredString(Client.getInstance().getSmallBoldFontRenderer(), onlineFriends + " ONLINE", (int)(startX - buttonWidth / 2.0f) + 7.5f, 21.0f, -1);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.drawSquareTexture(this.usersIcon, 7.0f, startX - buttonWidth + 7.5f, 14.5f);
        GL11.glPopMatrix();
        final float boxWidth = 225.0f;
        final float boxHeight = 150.0f;
        float minX = resolution.getScaledWidth() / 2 - boxWidth / 2.0f;
        float minY = resolution.getScaledHeight() / 2 - boxHeight / 2.0f;
        RenderUtil.drawRect(minX, minY, minX + boxWidth, minY + boxHeight, 1997935379);
        minX += 10.0f;
        minY += 10.0f;
        final float buttonHeight = 25.0f;
        for (final EscapeButton button : this.escapeButtons) {
            if (!button.text.equals("OPEN TO LAN") || this.mc.isSingleplayer()) {
                if (!button.text.equals("SERVER SELECTOR") || !this.mc.isSingleplayer()) {
                    RenderUtil.drawBorderedRect(minX, minY, minX + boxWidth - 20.0f, minY + buttonHeight, 1.0f, 1727987712, 1711276032);
                    RenderUtil.drawCenteredString(Client.getInstance().getRegularMediumBoldFontRenderer(), button.text, (float)(int)(minX + (boxWidth - 20.0f) / 2.0f), (float)((int)(minY + buttonHeight / 2.0f) + 1), -1);
                    minY += buttonHeight + 10.0f;
                }
            }
        }
    }
    
    private abstract class EscapeButton
    {
        private final String text;
        
        EscapeButton(final String text) {
            this.text = text;
        }
        
        protected abstract void onClick(final int p0, final int p1);
    }
}
