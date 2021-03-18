package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.*;
import net.minecraft.client.resources.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.achievement.*;

public class GuiInventory extends InventoryEffectRenderer
{
    private float field_147048_u;
    private float field_147047_v;
    private static final String __OBFID = "CL_00000761";
    
    public GuiInventory(final EntityPlayer p_i1094_1_) {
        super(p_i1094_1_.inventoryContainer);
        this.field_146291_p = true;
    }
    
    @Override
    public void updateScreen() {
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        if (this.mc.playerController.isInCreativeMode()) {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.thePlayer));
        }
        else {
            super.initGui();
        }
    }
    
    @Override
    protected void func_146979_b(final int p_146979_1_, final int p_146979_2_) {
        this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86, 16, 4210752);
    }
    
    @Override
    public void drawScreen(final int p_73863_1_, final int p_73863_2_, final float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.field_147048_u = (float)p_73863_1_;
        this.field_147047_v = (float)p_73863_2_;
    }
    
    @Override
    protected void func_146976_a(final float p_146976_1_, final int p_146976_2_, final int p_146976_3_) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiInventory.field_147001_a);
        final int var4 = this.field_147003_i;
        final int var5 = this.field_147009_r;
        this.drawTexturedModalRect(var4, var5, 0, 0, this.field_146999_f, this.field_147000_g);
        func_147046_a(var4 + 51, var5 + 75, 30, var4 + 51 - this.field_147048_u, var5 + 75 - 50 - this.field_147047_v, this.mc.thePlayer);
    }
    
    public static void func_147046_a(final int p_147046_0_, final int p_147046_1_, final int p_147046_2_, final float p_147046_3_, final float p_147046_4_, final EntityLivingBase p_147046_5_) {
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, 50.0f);
        GL11.glScalef((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        final float var6 = p_147046_5_.renderYawOffset;
        final float var7 = p_147046_5_.rotationYaw;
        final float var8 = p_147046_5_.rotationPitch;
        final float var9 = p_147046_5_.prevRotationYawHead;
        final float var10 = p_147046_5_.rotationYawHead;
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f, 1.0f, 0.0f, 0.0f);
        p_147046_5_.renderYawOffset = (float)Math.atan(p_147046_3_ / 40.0f) * 20.0f;
        p_147046_5_.rotationYaw = (float)Math.atan(p_147046_3_ / 40.0f) * 40.0f;
        p_147046_5_.rotationPitch = -(float)Math.atan(p_147046_4_ / 40.0f) * 20.0f;
        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
        GL11.glTranslatef(0.0f, p_147046_5_.yOffset, 0.0f);
        RenderManager.instance.playerViewY = 180.0f;
        RenderManager.instance.func_147940_a(p_147046_5_, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        p_147046_5_.renderYawOffset = var6;
        p_147046_5_.rotationYaw = var7;
        p_147046_5_.rotationPitch = var8;
        p_147046_5_.prevRotationYawHead = var9;
        p_147046_5_.rotationYawHead = var10;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    @Override
    protected void actionPerformed(final GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.func_146107_m()));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.func_146107_m()));
        }
    }
}
