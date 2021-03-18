package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.resources.*;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry
{
    private final Minecraft field_148288_a;
    private static final String __OBFID = "CL_00000815";
    
    public ServerListEntryLanScan() {
        this.field_148288_a = Minecraft.getMinecraft();
    }
    
    @Override
    public void func_148279_a(final int p_148279_1_, final int p_148279_2_, final int p_148279_3_, final int p_148279_4_, final int p_148279_5_, final Tessellator p_148279_6_, final int p_148279_7_, final int p_148279_8_, final boolean p_148279_9_) {
        final int var10 = p_148279_3_ + p_148279_5_ / 2 - this.field_148288_a.fontRenderer.FONT_HEIGHT / 2;
        this.field_148288_a.fontRenderer.drawString(I18n.format("lanServer.scanning", new Object[0]), this.field_148288_a.currentScreen.width / 2 - this.field_148288_a.fontRenderer.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, var10, 16777215);
        String var11 = null;
        switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
            default: {
                var11 = "O o o";
                break;
            }
            case 1:
            case 3: {
                var11 = "o O o";
                break;
            }
            case 2: {
                var11 = "o o O";
                break;
            }
        }
        this.field_148288_a.fontRenderer.drawString(var11, this.field_148288_a.currentScreen.width / 2 - this.field_148288_a.fontRenderer.getStringWidth(var11) / 2, var10 + this.field_148288_a.fontRenderer.FONT_HEIGHT, 8421504);
    }
    
    @Override
    public boolean func_148278_a(final int p_148278_1_, final int p_148278_2_, final int p_148278_3_, final int p_148278_4_, final int p_148278_5_, final int p_148278_6_) {
        return false;
    }
    
    @Override
    public void func_148277_b(final int p_148277_1_, final int p_148277_2_, final int p_148277_3_, final int p_148277_4_, final int p_148277_5_, final int p_148277_6_) {
    }
}
