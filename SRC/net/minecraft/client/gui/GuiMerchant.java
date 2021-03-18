package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import io.netty.buffer.*;
import org.lwjgl.opengl.*;
import net.minecraft.village.*;
import net.minecraft.client.renderer.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;
import net.minecraft.client.*;

public class GuiMerchant extends GuiContainer
{
    private static final Logger logger;
    private static final ResourceLocation field_147038_v;
    private IMerchant field_147037_w;
    private MerchantButton field_147043_x;
    private MerchantButton field_147042_y;
    private int field_147041_z;
    private String field_147040_A;
    private static final String __OBFID = "CL_00000762";
    
    public GuiMerchant(final InventoryPlayer p_i46380_1_, final IMerchant p_i46380_2_, final World p_i46380_3_, final String p_i46380_4_) {
        super(new ContainerMerchant(p_i46380_1_, p_i46380_2_, p_i46380_3_));
        this.field_147037_w = p_i46380_2_;
        this.field_147040_A = ((p_i46380_4_ != null && p_i46380_4_.length() >= 1) ? p_i46380_4_ : I18n.format("entity.Villager.name", new Object[0]));
    }
    
    @Override
    public void initGui() {
        super.initGui();
        final int var1 = (this.width - this.field_146999_f) / 2;
        final int var2 = (this.height - this.field_147000_g) / 2;
        this.buttonList.add(this.field_147043_x = new MerchantButton(1, var1 + 120 + 27, var2 + 24 - 1, true));
        this.buttonList.add(this.field_147042_y = new MerchantButton(2, var1 + 36 - 19, var2 + 24 - 1, false));
        this.field_147043_x.enabled = false;
        this.field_147042_y.enabled = false;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString(this.field_147040_A, this.field_146999_f / 2 - this.fontRendererObj.getStringWidth(this.field_147040_A) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        final MerchantRecipeList var1 = this.field_147037_w.getRecipes(this.mc.thePlayer);
        if (var1 != null) {
            this.field_147043_x.enabled = (this.field_147041_z < var1.size() - 1);
            this.field_147042_y.enabled = (this.field_147041_z > 0);
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        boolean var2 = false;
        if (p_146284_1_ == this.field_147043_x) {
            ++this.field_147041_z;
            var2 = true;
        }
        else if (p_146284_1_ == this.field_147042_y) {
            --this.field_147041_z;
            var2 = true;
        }
        if (var2) {
            ((ContainerMerchant)this.field_147002_h).setCurrentRecipeIndex(this.field_147041_z);
            final ByteBuf var3 = Unpooled.buffer();
            try {
                var3.writeInt(this.field_147041_z);
                this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("MC|TrSel", var3));
            }
            catch (Exception var4) {
                GuiMerchant.logger.error("Couldn't send trade info", (Throwable)var4);
            }
            finally {
                var3.release();
            }
        }
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        final MerchantRecipeList var6 = this.field_147037_w.getRecipes(this.mc.thePlayer);
        if (var6 != null && !var6.isEmpty()) {
            final int var7 = this.field_147041_z;
            final MerchantRecipe var8 = var6.get(var7);
            if (var8.isRecipeDisabled()) {
                this.mc.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glDisable(2896);
                this.drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 21, 212, 0, 28, 21);
                this.drawTexturedModalRect(this.field_147003_i + 83, this.field_147009_r + 51, 212, 0, 28, 21);
            }
        }
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        final MerchantRecipeList var4 = this.field_147037_w.getRecipes(this.mc.thePlayer);
        if (var4 != null && !var4.isEmpty()) {
            final int var5 = (this.width - this.field_146999_f) / 2;
            final int var6 = (this.height - this.field_147000_g) / 2;
            final int var7 = this.field_147041_z;
            final MerchantRecipe var8 = var4.get(var7);
            GL11.glPushMatrix();
            final ItemStack var9 = var8.getItemToBuy();
            final ItemStack var10 = var8.getSecondItemToBuy();
            final ItemStack var11 = var8.getItemToSell();
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(2896);
            GL11.glEnable(32826);
            GL11.glEnable(2903);
            GL11.glEnable(2896);
            GuiMerchant.itemRender.zLevel = 100.0f;
            GuiMerchant.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);
            GuiMerchant.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var9, var5 + 36, var6 + 24);
            if (var10 != null) {
                GuiMerchant.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
                GuiMerchant.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var10, var5 + 62, var6 + 24);
            }
            GuiMerchant.itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
            GuiMerchant.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var11, var5 + 120, var6 + 24);
            GuiMerchant.itemRender.zLevel = 0.0f;
            GL11.glDisable(2896);
            if (this.func_146978_c(36, 24, 16, 16, p_73863_1_, p_73863_2_)) {
                this.func_146285_a(var9, p_73863_1_, p_73863_2_);
            }
            else if (var10 != null && this.func_146978_c(62, 24, 16, 16, p_73863_1_, p_73863_2_)) {
                this.func_146285_a(var10, p_73863_1_, p_73863_2_);
            }
            else if (this.func_146978_c(120, 24, 16, 16, p_73863_1_, p_73863_2_)) {
                this.func_146285_a(var11, p_73863_1_, p_73863_2_);
            }
            GL11.glPopMatrix();
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            RenderHelper.enableStandardItemLighting();
        }
    }
    
    public IMerchant func_147035_g() {
        return this.field_147037_w;
    }
    
    static {
        logger = LogManager.getLogger();
        field_147038_v = new ResourceLocation("textures/gui/container/villager.png");
    }
    
    static class MerchantButton extends GuiButton
    {
        private final boolean field_146157_o;
        private static final String __OBFID = "CL_00000763";
        
        public MerchantButton(final int p_i1095_1_, final int p_i1095_2_, final int p_i1095_3_, final boolean p_i1095_4_) {
            super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 19, "");
            this.field_146157_o = p_i1095_4_;
        }
        
        @Override
        public void drawButton(final Minecraft p_146112_1_, final int p_146112_2_, final int p_146112_3_) {
            if (this.field_146125_m) {
                p_146112_1_.getTextureManager().bindTexture(GuiMerchant.field_147038_v);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                final boolean var4 = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
                int var5 = 0;
                int var6 = 176;
                if (!this.enabled) {
                    var6 += this.width * 2;
                }
                else if (var4) {
                    var6 += this.width;
                }
                if (!this.field_146157_o) {
                    var5 += this.height;
                }
                this.drawTexturedModalRect(this.x, this.y, var6, var5, this.width, this.height);
            }
        }
    }
}
