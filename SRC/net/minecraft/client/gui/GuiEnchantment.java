package net.minecraft.client.gui;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.client.resources.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class GuiEnchantment extends GuiContainer
{
    private static final ResourceLocation field_147078_C;
    private static final ResourceLocation field_147070_D;
    private static final ModelBook field_147072_E;
    private Random field_147074_F;
    private ContainerEnchantment field_147075_G;
    public int field_147073_u;
    public float field_147071_v;
    public float field_147069_w;
    public float field_147082_x;
    public float field_147081_y;
    public float field_147080_z;
    public float field_147076_A;
    ItemStack field_147077_B;
    private String field_147079_H;
    private static final String __OBFID = "CL_00000757";
    
    public GuiEnchantment(final InventoryPlayer p_i46398_1_, final World p_i46398_2_, final int p_i46398_3_, final int p_i46398_4_, final int p_i46398_5_, final String p_i46398_6_) {
        super(new ContainerEnchantment(p_i46398_1_, p_i46398_2_, p_i46398_3_, p_i46398_4_, p_i46398_5_));
        this.field_147074_F = new Random();
        this.field_147075_G = (ContainerEnchantment)this.field_147002_h;
        this.field_147079_H = p_i46398_6_;
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString((this.field_147079_H == null) ? I18n.format("container.enchant", new Object[0]) : this.field_147079_H, 12, 5, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.field_147000_g - 96 + 2, 4210752);
    }
    
    @Override
    public void updateScreen() {
        super.updateScreen();
        this.func_147068_g();
    }
    
    @Override
    protected void mouseClicked(final int p_73864_1_, final int p_73864_2_, final int p_73864_3_) {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        for (int var6 = 0; var6 < 3; ++var6) {
            final int var7 = p_73864_1_ - (var4 + 60);
            final int var8 = p_73864_2_ - (var5 + 14 + 19 * var6);
            if (var7 >= 0 && var8 >= 0 && var7 < 108 && var8 < 19 && this.field_147075_G.enchantItem(this.mc.thePlayer, var6)) {
                this.mc.playerController.sendEnchantPacket(this.field_147075_G.windowId, var6);
            }
        }
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.field_147078_C);
        final int var4 = (this.width - this.field_146999_f) / 2;
        final int var5 = (this.height - this.field_147000_g) / 2;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        GL11.glPushMatrix();
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        final ScaledResolution var6 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glViewport((var6.getScaledWidth() - 320) / 2 * var6.getScaleFactor(), (var6.getScaledHeight() - 240) / 2 * var6.getScaleFactor(), 320 * var6.getScaleFactor(), 240 * var6.getScaleFactor());
        GL11.glTranslatef(-0.34f, 0.23f, 0.0f);
        Project.gluPerspective(90.0f, 1.3333334f, 9.0f, 80.0f);
        final float var7 = 1.0f;
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        RenderHelper.enableStandardItemLighting();
        GL11.glTranslatef(0.0f, 3.3f, -16.0f);
        GL11.glScalef(var7, var7, var7);
        final float var8 = 5.0f;
        GL11.glScalef(var8, var8, var8);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiEnchantment.field_147070_D);
        GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
        final float var9 = this.field_147076_A + (this.field_147080_z - this.field_147076_A) * p_146976_1_;
        GL11.glTranslatef((1.0f - var9) * 0.2f, (1.0f - var9) * 0.1f, (1.0f - var9) * 0.25f);
        GL11.glRotatef(-(1.0f - var9) * 90.0f - 90.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        float var10 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.25f;
        float var11 = this.field_147069_w + (this.field_147071_v - this.field_147069_w) * p_146976_1_ + 0.75f;
        var10 = (var10 - MathHelper.truncateDoubleToInt(var10)) * 1.6f - 0.3f;
        var11 = (var11 - MathHelper.truncateDoubleToInt(var11)) * 1.6f - 0.3f;
        if (var10 < 0.0f) {
            var10 = 0.0f;
        }
        if (var11 < 0.0f) {
            var11 = 0.0f;
        }
        if (var10 > 1.0f) {
            var10 = 1.0f;
        }
        if (var11 > 1.0f) {
            var11 = 1.0f;
        }
        GL11.glEnable(32826);
        GuiEnchantment.field_147072_E.render(null, 0.0f, var10, var11, var9, 0.0f, 0.0625f);
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        GL11.glMatrixMode(5889);
        GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        EnchantmentNameParts.instance.reseedRandomGenerator(this.field_147075_G.nameSeed);
        for (int var12 = 0; var12 < 3; ++var12) {
            final String var13 = EnchantmentNameParts.instance.generateNewRandomName();
            this.zLevel = 0.0f;
            this.mc.getTextureManager().bindTexture(GuiEnchantment.field_147078_C);
            final int var14 = this.field_147075_G.enchantLevels[var12];
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (var14 == 0) {
                this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
            }
            else {
                final String var15 = "" + var14;
                FontRenderer var16 = this.mc.standardGalacticFontRenderer;
                int var17 = 6839882;
                if (this.mc.thePlayer.experienceLevel < var14 && !this.mc.thePlayer.capabilities.isCreativeMode) {
                    this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 185, 108, 19);
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, (var17 & 0xFEFEFE) >> 1);
                    var16 = this.mc.fontRenderer;
                    var17 = 4226832;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
                else {
                    final int var18 = p_146976_2_ - (var4 + 60);
                    final int var19 = p_146976_3_ - (var5 + 14 + 19 * var12);
                    if (var18 >= 0 && var19 >= 0 && var18 < 108 && var19 < 19) {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 204, 108, 19);
                        var17 = 16777088;
                    }
                    else {
                        this.drawTexturedModalRect(var4 + 60, var5 + 14 + 19 * var12, 0, 166, 108, 19);
                    }
                    var16.drawSplitString(var13, var4 + 62, var5 + 16 + 19 * var12, 104, var17);
                    var16 = this.mc.fontRenderer;
                    var17 = 8453920;
                    var16.drawStringWithShadow(var15, var4 + 62 + 104 - var16.getStringWidth(var15), var5 + 16 + 19 * var12 + 7, var17);
                }
            }
        }
    }
    
    public void func_147068_g() {
        final ItemStack var1 = this.field_147002_h.getSlot(0).getStack();
        if (!ItemStack.areItemStacksEqual(var1, this.field_147077_B)) {
            this.field_147077_B = var1;
            do {
                this.field_147082_x += this.field_147074_F.nextInt(4) - this.field_147074_F.nextInt(4);
            } while (this.field_147071_v <= this.field_147082_x + 1.0f && this.field_147071_v >= this.field_147082_x - 1.0f);
        }
        ++this.field_147073_u;
        this.field_147069_w = this.field_147071_v;
        this.field_147076_A = this.field_147080_z;
        boolean var2 = false;
        for (int var3 = 0; var3 < 3; ++var3) {
            if (this.field_147075_G.enchantLevels[var3] != 0) {
                var2 = true;
            }
        }
        if (var2) {
            this.field_147080_z += 0.2f;
        }
        else {
            this.field_147080_z -= 0.2f;
        }
        if (this.field_147080_z < 0.0f) {
            this.field_147080_z = 0.0f;
        }
        if (this.field_147080_z > 1.0f) {
            this.field_147080_z = 1.0f;
        }
        float var4 = (this.field_147082_x - this.field_147071_v) * 0.4f;
        final float var5 = 0.2f;
        if (var4 < -var5) {
            var4 = -var5;
        }
        if (var4 > var5) {
            var4 = var5;
        }
        this.field_147081_y += (var4 - this.field_147081_y) * 0.9f;
        this.field_147071_v += this.field_147081_y;
    }
    
    static {
        field_147078_C = new ResourceLocation("textures/gui/container/enchanting_table.png");
        field_147070_D = new ResourceLocation("textures/entity/enchanting_table_book.png");
        field_147072_E = new ModelBook();
    }
}
