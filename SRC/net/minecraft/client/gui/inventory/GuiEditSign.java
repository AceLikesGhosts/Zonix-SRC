package net.minecraft.client.gui.inventory;

import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.resources.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.*;

public class GuiEditSign extends GuiScreen
{
    private TileEntitySign field_146848_f;
    private int field_146849_g;
    private int field_146851_h;
    private GuiButton field_146852_i;
    private static final String __OBFID = "CL_00000764";
    
    public GuiEditSign(final TileEntitySign p_i1097_1_) {
        this.field_146848_f = p_i1097_1_;
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(this.field_146852_i = new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120, I18n.format("gui.done", new Object[0])));
        this.field_146848_f.func_145913_a(false);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        final NetHandlerPlayClient var1 = this.mc.getNetHandler();
        if (var1 != null) {
            var1.addToSendQueue(new C12PacketUpdateSign(this.field_146848_f.field_145851_c, this.field_146848_f.field_145848_d, this.field_146848_f.field_145849_e, this.field_146848_f.field_145915_a));
        }
        this.field_146848_f.func_145913_a(true);
    }
    
    @Override
    public void updateScreen() {
        ++this.field_146849_g;
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.enabled && p_146284_1_.id == 0) {
            this.field_146848_f.onInventoryChanged();
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    protected void keyTyped(final char p_73869_1_, final int p_73869_2_) {
        if (p_73869_2_ == 200) {
            this.field_146851_h = (this.field_146851_h - 1 & 0x3);
        }
        if (p_73869_2_ == 208 || p_73869_2_ == 28 || p_73869_2_ == 156) {
            this.field_146851_h = (this.field_146851_h + 1 & 0x3);
        }
        if (p_73869_2_ == 14 && this.field_146848_f.field_145915_a[this.field_146851_h].length() > 0) {
            this.field_146848_f.field_145915_a[this.field_146851_h] = this.field_146848_f.field_145915_a[this.field_146851_h].substring(0, this.field_146848_f.field_145915_a[this.field_146851_h].length() - 1);
        }
        if (ChatAllowedCharacters.isAllowedCharacter(p_73869_1_) && this.field_146848_f.field_145915_a[this.field_146851_h].length() < 15) {
            this.field_146848_f.field_145915_a[this.field_146851_h] += p_73869_1_;
        }
        if (p_73869_2_ == 1) {
            this.actionPerformed(this.field_146852_i);
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("sign.edit", new Object[0]), this.width / 2, 40, 16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.width / 2), 0.0f, 50.0f);
        final float var4 = 93.75f;
        GL11.glScalef(-var4, -var4, -var4);
        GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
        final Block var5 = this.field_146848_f.getBlockType();
        if (var5 == Blocks.standing_sign) {
            final float var6 = this.field_146848_f.getBlockMetadata() * 360 / 16.0f;
            GL11.glRotatef(var6, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        }
        else {
            final int var7 = this.field_146848_f.getBlockMetadata();
            float var8 = 0.0f;
            if (var7 == 2) {
                var8 = 180.0f;
            }
            if (var7 == 4) {
                var8 = 90.0f;
            }
            if (var7 == 5) {
                var8 = -90.0f;
            }
            GL11.glRotatef(var8, 0.0f, 1.0f, 0.0f);
            GL11.glTranslatef(0.0f, -1.0625f, 0.0f);
        }
        if (this.field_146849_g / 6 % 2 == 0) {
            this.field_146848_f.field_145918_i = this.field_146851_h;
        }
        TileEntityRendererDispatcher.instance.func_147549_a(this.field_146848_f, -0.5, -0.75, -0.5, 0.0f);
        this.field_146848_f.field_145918_i = -1;
        GL11.glPopMatrix();
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
