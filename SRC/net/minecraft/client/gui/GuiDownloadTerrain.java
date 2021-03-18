package net.minecraft.client.gui;

import net.minecraft.client.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.resources.*;

public class GuiDownloadTerrain extends GuiScreen
{
    private NetHandlerPlayClient field_146594_a;
    private int field_146593_f;
    private static final String __OBFID = "CL_00000708";
    
    public GuiDownloadTerrain(final NetHandlerPlayClient p_i45023_1_) {
        this.field_146594_a = p_i45023_1_;
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
    }
    
    @Override
    public void updateScreen() {
        ++this.field_146593_f;
        if (this.field_146593_f % 20 == 0) {
            this.field_146594_a.addToSendQueue(new C00PacketKeepAlive());
        }
        if (this.field_146594_a != null) {
            this.field_146594_a.onNetworkTick();
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.func_146278_c(0);
        this.drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
