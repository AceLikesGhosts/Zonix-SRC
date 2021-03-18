package net.minecraft.src;

import net.minecraft.client.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.*;
import us.zonix.client.module.impl.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import net.minecraft.util.*;

public class ItemRendererOF extends ItemRenderer
{
    private Minecraft mc;
    private static final ResourceLocation RES_ITEM_GLINT;
    
    public ItemRendererOF(final Minecraft par1Minecraft) {
        super(par1Minecraft);
        this.mc = null;
        this.mc = par1Minecraft;
    }
    
    @Override
    public void renderItem(final EntityLivingBase par1EntityLivingBase, final ItemStack par2ItemStack, final int par3) {
        GL11.glPushMatrix();
        final TextureManager var4 = this.mc.getTextureManager();
        final Item var5 = par2ItemStack.getItem();
        final Block var6 = Block.getBlockFromItem(var5);
        if (par2ItemStack != null && var6 != null && var6.getRenderBlockPass() != 0) {
            GL11.glEnable(3042);
            GL11.glEnable(2884);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }
        if (par2ItemStack.getItemSpriteNumber() == 0 && var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(var6.getRenderType())) {
            var4.bindTexture(var4.getResourceLocation(0));
            if (par2ItemStack != null && var6 != null && var6.getRenderBlockPass() != 0) {
                GL11.glDepthMask(false);
                this.mc.renderGlobal.renderBlocksRg.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0f);
                GL11.glDepthMask(true);
            }
            else {
                this.mc.renderGlobal.renderBlocksRg.renderBlockAsItem(var6, par2ItemStack.getItemDamage(), 1.0f);
            }
        }
        else {
            final IIcon var7 = par1EntityLivingBase.getItemIcon(par2ItemStack, par3);
            if (var7 == null) {
                GL11.glPopMatrix();
                return;
            }
            var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
            TextureUtil.func_152777_a(false, false, 1.0f);
            final Tessellator var8 = Tessellator.instance;
            final float var9 = var7.getMinU();
            final float var10 = var7.getMaxU();
            final float var11 = var7.getMinV();
            final float var12 = var7.getMaxV();
            final float var13 = 0.0f;
            final float var14 = 0.3f;
            GL11.glEnable(32826);
            GL11.glTranslatef(-var13, -var14, 0.0f);
            final float var15 = 1.5f;
            GL11.glScalef(var15, var15, var15);
            GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.9375f, -0.0625f, 0.0f);
            ItemRenderer.renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625f);
            final boolean renderEffect = par2ItemStack.hasEffect() && par3 == 0;
            if (renderEffect && FPSBoost.ITEM_GLINT.getValue()) {
                GL11.glDepthFunc(514);
                GL11.glDisable(2896);
                var4.bindTexture(ItemRendererOF.RES_ITEM_GLINT);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(768, 1, 1, 0);
                final float var16 = 0.76f;
                GL11.glColor4f(0.5f * var16, 0.25f * var16, 0.8f * var16, 1.0f);
                GL11.glMatrixMode(5890);
                GL11.glPushMatrix();
                final float var17 = 0.125f;
                GL11.glScalef(var17, var17, var17);
                float var18 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
                GL11.glTranslatef(var18, 0.0f, 0.0f);
                GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
                ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 16, 16, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(var17, var17, var17);
                var18 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
                GL11.glTranslatef(-var18, 0.0f, 0.0f);
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 16, 16, 0.0625f);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glDepthFunc(515);
            }
            GL11.glDisable(32826);
            var4.bindTexture(var4.getResourceLocation(par2ItemStack.getItemSpriteNumber()));
            TextureUtil.func_147945_b();
        }
        if (par2ItemStack != null && var6 != null && var6.getRenderBlockPass() != 0) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderItemInFirstPerson(final float par1) {
        this.mc.theWorld.renderItemInFirstPerson = true;
        super.renderItemInFirstPerson(par1);
        this.mc.theWorld.renderItemInFirstPerson = false;
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
