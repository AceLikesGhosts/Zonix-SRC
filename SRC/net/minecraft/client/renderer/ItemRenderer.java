package net.minecraft.client.renderer;

import net.minecraft.client.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import us.zonix.client.module.impl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.storage.*;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;

public class ItemRenderer
{
    private static final ResourceLocation RES_ITEM_GLINT;
    private static final ResourceLocation RES_MAP_BACKGROUND;
    private static final ResourceLocation RES_UNDERWATER_OVERLAY;
    private Minecraft mc;
    private ItemStack itemToRender;
    private float equippedProgress;
    private float prevEquippedProgress;
    private RenderBlocks renderBlocksIr;
    private int equippedItemSlot;
    private static final String __OBFID = "CL_00000953";
    
    public ItemRenderer(final Minecraft p_i1247_1_) {
        this.renderBlocksIr = new RenderBlocks();
        this.equippedItemSlot = -1;
        this.mc = p_i1247_1_;
    }
    
    public void renderItem(final EntityLivingBase p_78443_1_, final ItemStack p_78443_2_, final int p_78443_3_) {
        GL11.glPushMatrix();
        final TextureManager var4 = this.mc.getTextureManager();
        final Item var5 = p_78443_2_.getItem();
        final Block var6 = Block.getBlockFromItem(var5);
        if (p_78443_2_ != null && var6 != null && var6.getRenderBlockPass() != 0) {
            GL11.glEnable(3042);
            GL11.glEnable(2884);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }
        if (p_78443_2_.getItemSpriteNumber() == 0 && var5 instanceof ItemBlock && RenderBlocks.renderItemIn3d(var6.getRenderType())) {
            var4.bindTexture(var4.getResourceLocation(0));
            if (p_78443_2_ != null && var6 != null && var6.getRenderBlockPass() != 0) {
                GL11.glDepthMask(false);
                this.renderBlocksIr.renderBlockAsItem(var6, p_78443_2_.getItemDamage(), 1.0f);
                GL11.glDepthMask(true);
            }
            else {
                this.renderBlocksIr.renderBlockAsItem(var6, p_78443_2_.getItemDamage(), 1.0f);
            }
        }
        else {
            final IIcon var7 = p_78443_1_.getItemIcon(p_78443_2_, p_78443_3_);
            if (var7 == null) {
                GL11.glPopMatrix();
                return;
            }
            var4.bindTexture(var4.getResourceLocation(p_78443_2_.getItemSpriteNumber()));
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
            renderItemIn2D(var8, var10, var11, var9, var12, var7.getIconWidth(), var7.getIconHeight(), 0.0625f);
            if (p_78443_2_.hasEffect() && p_78443_3_ == 0 && FPSBoost.ITEM_GLINT.getValue()) {
                GL11.glDepthFunc(514);
                GL11.glDisable(2896);
                var4.bindTexture(ItemRenderer.RES_ITEM_GLINT);
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
                renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                GL11.glScalef(var17, var17, var17);
                var18 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
                GL11.glTranslatef(-var18, 0.0f, 0.0f);
                GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
                GL11.glPopMatrix();
                GL11.glMatrixMode(5888);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glDepthFunc(515);
            }
            GL11.glDisable(32826);
            var4.bindTexture(var4.getResourceLocation(p_78443_2_.getItemSpriteNumber()));
            TextureUtil.func_147945_b();
        }
        if (p_78443_2_ != null && var6 != null && var6.getRenderBlockPass() != 0) {
            GL11.glDisable(3042);
        }
        GL11.glPopMatrix();
    }
    
    public static void renderItemIn2D(final Tessellator p_78439_0_, final float p_78439_1_, final float p_78439_2_, final float p_78439_3_, final float p_78439_4_, final int p_78439_5_, final int p_78439_6_, final float p_78439_7_) {
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(0.0f, 0.0f, 1.0f);
        p_78439_0_.addVertexWithUV(0.0, 0.0, 0.0, p_78439_1_, p_78439_4_);
        p_78439_0_.addVertexWithUV(1.0, 0.0, 0.0, p_78439_3_, p_78439_4_);
        p_78439_0_.addVertexWithUV(1.0, 1.0, 0.0, p_78439_3_, p_78439_2_);
        p_78439_0_.addVertexWithUV(0.0, 1.0, 0.0, p_78439_1_, p_78439_2_);
        p_78439_0_.draw();
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(0.0f, 0.0f, -1.0f);
        p_78439_0_.addVertexWithUV(0.0, 1.0, 0.0f - p_78439_7_, p_78439_1_, p_78439_2_);
        p_78439_0_.addVertexWithUV(1.0, 1.0, 0.0f - p_78439_7_, p_78439_3_, p_78439_2_);
        p_78439_0_.addVertexWithUV(1.0, 0.0, 0.0f - p_78439_7_, p_78439_3_, p_78439_4_);
        p_78439_0_.addVertexWithUV(0.0, 0.0, 0.0f - p_78439_7_, p_78439_1_, p_78439_4_);
        p_78439_0_.draw();
        final float var8 = 0.5f * (p_78439_1_ - p_78439_3_) / p_78439_5_;
        final float var9 = 0.5f * (p_78439_4_ - p_78439_2_) / p_78439_6_;
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(-1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < p_78439_5_; ++var10) {
            final float var11 = var10 / (float)p_78439_5_;
            final float var12 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * var11 - var8;
            p_78439_0_.addVertexWithUV(var11, 0.0, 0.0f - p_78439_7_, var12, p_78439_4_);
            p_78439_0_.addVertexWithUV(var11, 0.0, 0.0, var12, p_78439_4_);
            p_78439_0_.addVertexWithUV(var11, 1.0, 0.0, var12, p_78439_2_);
            p_78439_0_.addVertexWithUV(var11, 1.0, 0.0f - p_78439_7_, var12, p_78439_2_);
        }
        p_78439_0_.draw();
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(1.0f, 0.0f, 0.0f);
        for (int var10 = 0; var10 < p_78439_5_; ++var10) {
            final float var11 = var10 / (float)p_78439_5_;
            final float var12 = p_78439_1_ + (p_78439_3_ - p_78439_1_) * var11 - var8;
            final float var13 = var11 + 1.0f / p_78439_5_;
            p_78439_0_.addVertexWithUV(var13, 1.0, 0.0f - p_78439_7_, var12, p_78439_2_);
            p_78439_0_.addVertexWithUV(var13, 1.0, 0.0, var12, p_78439_2_);
            p_78439_0_.addVertexWithUV(var13, 0.0, 0.0, var12, p_78439_4_);
            p_78439_0_.addVertexWithUV(var13, 0.0, 0.0f - p_78439_7_, var12, p_78439_4_);
        }
        p_78439_0_.draw();
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(0.0f, 1.0f, 0.0f);
        for (int var10 = 0; var10 < p_78439_6_; ++var10) {
            final float var11 = var10 / (float)p_78439_6_;
            final float var12 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * var11 - var9;
            final float var13 = var11 + 1.0f / p_78439_6_;
            p_78439_0_.addVertexWithUV(0.0, var13, 0.0, p_78439_1_, var12);
            p_78439_0_.addVertexWithUV(1.0, var13, 0.0, p_78439_3_, var12);
            p_78439_0_.addVertexWithUV(1.0, var13, 0.0f - p_78439_7_, p_78439_3_, var12);
            p_78439_0_.addVertexWithUV(0.0, var13, 0.0f - p_78439_7_, p_78439_1_, var12);
        }
        p_78439_0_.draw();
        p_78439_0_.startDrawingQuads();
        p_78439_0_.setNormal(0.0f, -1.0f, 0.0f);
        for (int var10 = 0; var10 < p_78439_6_; ++var10) {
            final float var11 = var10 / (float)p_78439_6_;
            final float var12 = p_78439_4_ + (p_78439_2_ - p_78439_4_) * var11 - var9;
            p_78439_0_.addVertexWithUV(1.0, var11, 0.0, p_78439_3_, var12);
            p_78439_0_.addVertexWithUV(0.0, var11, 0.0, p_78439_1_, var12);
            p_78439_0_.addVertexWithUV(0.0, var11, 0.0f - p_78439_7_, p_78439_1_, var12);
            p_78439_0_.addVertexWithUV(1.0, var11, 0.0f - p_78439_7_, p_78439_3_, var12);
        }
        p_78439_0_.draw();
    }
    
    public void renderItemInFirstPerson(final float p_78440_1_) {
        final float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * p_78440_1_;
        final EntityClientPlayerMP var3 = this.mc.thePlayer;
        if (this.itemToRender != null && this.itemToRender.getItem() instanceof ItemCloth) {
            return;
        }
        final float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * p_78440_1_;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * p_78440_1_, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        final EntityPlayerSP var5 = var3;
        final float var6 = var5.prevRenderArmPitch + (var5.renderArmPitch - var5.prevRenderArmPitch) * p_78440_1_;
        final float var7 = var5.prevRenderArmYaw + (var5.renderArmYaw - var5.prevRenderArmYaw) * p_78440_1_;
        GL11.glRotatef((var3.rotationPitch - var6) * 0.1f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((var3.rotationYaw - var7) * 0.1f, 0.0f, 1.0f, 0.0f);
        final ItemStack var8 = this.itemToRender;
        if (var8 != null && var8.getItem() instanceof ItemCloth) {
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        }
        final int var9 = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        final int var10 = var9 % 65536;
        final int var11 = var9 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var10 / 1.0f, var11 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (var8 != null) {
            final int var12 = var8.getItem().getColorFromItemStack(var8, 0);
            final float var13 = (var12 >> 16 & 0xFF) / 255.0f;
            final float var14 = (var12 >> 8 & 0xFF) / 255.0f;
            final float var15 = (var12 & 0xFF) / 255.0f;
            GL11.glColor4f(var13, var14, var15, 1.0f);
        }
        else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        if (var8 != null && var8.getItem() == Items.filled_map) {
            GL11.glPushMatrix();
            final float var16 = 0.8f;
            float var13 = var3.getSwingProgress(p_78440_1_);
            float var14 = MathHelper.sin(var13 * 3.1415927f);
            float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glTranslatef(-var15 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.2f, -var14 * 0.2f);
            var13 = 1.0f - var4 / 45.0f + 0.1f;
            if (var13 < 0.0f) {
                var13 = 0.0f;
            }
            if (var13 > 1.0f) {
                var13 = 1.0f;
            }
            var13 = -MathHelper.cos(var13 * 3.1415927f) * 0.5f + 0.5f;
            GL11.glTranslatef(0.0f, 0.0f * var16 - (1.0f - var2) * 1.2f - var13 * 0.5f + 0.04f, -0.9f * var16);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(var13 * -85.0f, 0.0f, 0.0f, 1.0f);
            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            for (int var17 = 0; var17 < 2; ++var17) {
                final int var18 = var17 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0f, -0.6f, 1.1f * var18);
                GL11.glRotatef((float)(-45 * var18), 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(59.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef((float)(-65 * var18), 0.0f, 1.0f, 0.0f);
                final Render var19 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
                final RenderPlayer var20 = (RenderPlayer)var19;
                final float var21 = 1.0f;
                GL11.glScalef(var21, var21, var21);
                var20.renderFirstPersonArm(this.mc.thePlayer);
                GL11.glPopMatrix();
            }
            var14 = var3.getSwingProgress(p_78440_1_);
            var15 = MathHelper.sin(var14 * var14 * 3.1415927f);
            final float var22 = MathHelper.sin(MathHelper.sqrt_float(var14) * 3.1415927f);
            GL11.glRotatef(-var15 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var22 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var22 * 80.0f, 1.0f, 0.0f, 0.0f);
            final float var23 = 0.38f;
            GL11.glScalef(var23, var23, var23);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, -1.0f, 0.0f);
            final float var21 = 0.015625f;
            GL11.glScalef(var21, var21, var21);
            this.mc.getTextureManager().bindTexture(ItemRenderer.RES_MAP_BACKGROUND);
            final Tessellator var24 = Tessellator.instance;
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            var24.startDrawingQuads();
            final byte var25 = 7;
            var24.addVertexWithUV(0 - var25, 128 + var25, 0.0, 0.0, 1.0);
            var24.addVertexWithUV(128 + var25, 128 + var25, 0.0, 1.0, 1.0);
            var24.addVertexWithUV(128 + var25, 0 - var25, 0.0, 1.0, 0.0);
            var24.addVertexWithUV(0 - var25, 0 - var25, 0.0, 0.0, 0.0);
            var24.draw();
            final MapData var26 = Items.filled_map.getMapData(var8, this.mc.theWorld);
            if (var26 != null) {
                this.mc.entityRenderer.getMapItemRenderer().func_148250_a(var26, false);
            }
            GL11.glPopMatrix();
        }
        else if (var8 != null) {
            GL11.glPushMatrix();
            final float var16 = 0.8f;
            if (var3.getItemInUseCount() > 0) {
                final EnumAction var27 = var8.getItemUseAction();
                if (var27 == EnumAction.eat || var27 == EnumAction.drink) {
                    final float var14 = var3.getItemInUseCount() - p_78440_1_ + 1.0f;
                    final float var15 = 1.0f - var14 / var8.getMaxItemUseDuration();
                    float var22 = 1.0f - var15;
                    var22 *= var22 * var22;
                    var22 *= var22 * var22;
                    var22 *= var22 * var22;
                    final float var23 = 1.0f - var22;
                    GL11.glTranslatef(0.0f, MathHelper.abs(MathHelper.cos(var14 / 4.0f * 3.1415927f) * 0.1f) * (float)((var15 > 0.2) ? 1 : 0), 0.0f);
                    GL11.glTranslatef(var23 * 0.6f, -var23 * 0.5f, 0.0f);
                    GL11.glRotatef(var23 * 90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var23 * 10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(var23 * 30.0f, 0.0f, 0.0f, 1.0f);
                }
            }
            else {
                final float var13 = var3.getSwingProgress(p_78440_1_);
                final float var14 = MathHelper.sin(var13 * 3.1415927f);
                final float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
                GL11.glTranslatef(-var15 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.2f, -var14 * 0.2f);
            }
            GL11.glTranslatef(0.7f * var16, -0.65f * var16 - (1.0f - var2) * 0.6f, -0.9f * var16);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            final float var13 = var3.getSwingProgress(p_78440_1_);
            final float var14 = MathHelper.sin(var13 * var13 * 3.1415927f);
            final float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glRotatef(-var14 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var15 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var15 * 80.0f, 1.0f, 0.0f, 0.0f);
            float var22 = 0.4f;
            GL11.glScalef(var22, var22, var22);
            if (var3.getItemInUseCount() > 0) {
                final EnumAction var28 = var8.getItemUseAction();
                if (var28 == EnumAction.block) {
                    GL11.glTranslatef(-0.5f, 0.2f, 0.0f);
                    GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-80.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
                }
                else if (var28 == EnumAction.bow) {
                    GL11.glRotatef(-18.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-12.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-8.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glTranslatef(-0.9f, 0.2f, 0.0f);
                    final float var21 = var8.getMaxItemUseDuration() - (var3.getItemInUseCount() - p_78440_1_ + 1.0f);
                    float var29 = var21 / 20.0f;
                    var29 = (var29 * var29 + var29 * 2.0f) / 3.0f;
                    if (var29 > 1.0f) {
                        var29 = 1.0f;
                    }
                    if (var29 > 0.1f) {
                        GL11.glTranslatef(0.0f, MathHelper.sin((var21 - 0.1f) * 1.3f) * 0.01f * (var29 - 0.1f), 0.0f);
                    }
                    GL11.glTranslatef(0.0f, 0.0f, var29 * 0.1f);
                    GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                    final float var30 = 1.0f + var29 * 0.2f;
                    GL11.glScalef(1.0f, 1.0f, var30);
                    GL11.glTranslatef(0.0f, -0.5f, 0.0f);
                    GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
                }
            }
            if (var8.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var8.getItem().requiresMultipleRenderPasses()) {
                this.renderItem(var3, var8, 0);
                final int var31 = var8.getItem().getColorFromItemStack(var8, 1);
                final float var21 = (var31 >> 16 & 0xFF) / 255.0f;
                final float var29 = (var31 >> 8 & 0xFF) / 255.0f;
                final float var30 = (var31 & 0xFF) / 255.0f;
                GL11.glColor4f(1.0f * var21, 1.0f * var29, 1.0f * var30, 1.0f);
                this.renderItem(var3, var8, 1);
            }
            else {
                this.renderItem(var3, var8, 0);
            }
            GL11.glPopMatrix();
        }
        else if (!var3.isInvisible()) {
            GL11.glPushMatrix();
            final float var16 = 0.8f;
            float var13 = var3.getSwingProgress(p_78440_1_);
            float var14 = MathHelper.sin(var13 * 3.1415927f);
            float var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glTranslatef(-var15 * 0.3f, MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f * 2.0f) * 0.4f, -var14 * 0.4f);
            GL11.glTranslatef(0.8f * var16, -0.75f * var16 - (1.0f - var2) * 0.6f, -0.9f * var16);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            var13 = var3.getSwingProgress(p_78440_1_);
            var14 = MathHelper.sin(var13 * var13 * 3.1415927f);
            var15 = MathHelper.sin(MathHelper.sqrt_float(var13) * 3.1415927f);
            GL11.glRotatef(var15 * 70.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var14 * 20.0f, 0.0f, 0.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            final Render var19 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            final RenderPlayer var20 = (RenderPlayer)var19;
            final float var21 = 1.0f;
            GL11.glScalef(var21, var21, var21);
            var20.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
        }
        if (var8 != null && var8.getItem() instanceof ItemCloth) {
            GL11.glDisable(3042);
        }
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
    }
    
    public void renderOverlays(final float p_78447_1_) {
        GL11.glDisable(3008);
        if (this.mc.thePlayer.isBurning()) {
            this.renderFireInFirstPerson(p_78447_1_);
        }
        if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
            final int var2 = MathHelper.floor_double(this.mc.thePlayer.posX);
            final int var3 = MathHelper.floor_double(this.mc.thePlayer.posY);
            final int var4 = MathHelper.floor_double(this.mc.thePlayer.posZ);
            Block var5 = this.mc.theWorld.getBlock(var2, var3, var4);
            if (this.mc.theWorld.getBlock(var2, var3, var4).isNormalCube()) {
                this.renderInsideOfBlock(p_78447_1_, var5.getBlockTextureFromSide(2));
            }
            else {
                for (int var6 = 0; var6 < 8; ++var6) {
                    final float var7 = ((var6 >> 0) % 2 - 0.5f) * this.mc.thePlayer.width * 0.9f;
                    final float var8 = ((var6 >> 1) % 2 - 0.5f) * this.mc.thePlayer.height * 0.2f;
                    final float var9 = ((var6 >> 2) % 2 - 0.5f) * this.mc.thePlayer.width * 0.9f;
                    final int var10 = MathHelper.floor_float(var2 + var7);
                    final int var11 = MathHelper.floor_float(var3 + var8);
                    final int var12 = MathHelper.floor_float(var4 + var9);
                    if (this.mc.theWorld.getBlock(var10, var11, var12).isNormalCube()) {
                        var5 = this.mc.theWorld.getBlock(var10, var11, var12);
                    }
                }
            }
            if (var5.getMaterial() != Material.air) {
                this.renderInsideOfBlock(p_78447_1_, var5.getBlockTextureFromSide(2));
            }
        }
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            this.renderWarpedTextureOverlay(p_78447_1_);
        }
        GL11.glEnable(3008);
    }
    
    private void renderInsideOfBlock(final float p_78446_1_, final IIcon p_78446_2_) {
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        final Tessellator var3 = Tessellator.instance;
        final float var4 = 0.1f;
        GL11.glColor4f(var4, var4, var4, 0.5f);
        GL11.glPushMatrix();
        final float var5 = -1.0f;
        final float var6 = 1.0f;
        final float var7 = -1.0f;
        final float var8 = 1.0f;
        final float var9 = -0.5f;
        final float var10 = p_78446_2_.getMinU();
        final float var11 = p_78446_2_.getMaxU();
        final float var12 = p_78446_2_.getMinV();
        final float var13 = p_78446_2_.getMaxV();
        var3.startDrawingQuads();
        var3.addVertexWithUV(var5, var7, var9, var11, var13);
        var3.addVertexWithUV(var6, var7, var9, var10, var13);
        var3.addVertexWithUV(var6, var8, var9, var10, var12);
        var3.addVertexWithUV(var5, var8, var9, var11, var12);
        var3.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private void renderWarpedTextureOverlay(final float p_78448_1_) {
        this.mc.getTextureManager().bindTexture(ItemRenderer.RES_UNDERWATER_OVERLAY);
        final Tessellator var2 = Tessellator.instance;
        final float var3 = this.mc.thePlayer.getBrightness(p_78448_1_);
        GL11.glColor4f(var3, var3, var3, 0.5f);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glPushMatrix();
        final float var4 = 4.0f;
        final float var5 = -1.0f;
        final float var6 = 1.0f;
        final float var7 = -1.0f;
        final float var8 = 1.0f;
        final float var9 = -0.5f;
        final float var10 = -this.mc.thePlayer.rotationYaw / 64.0f;
        final float var11 = this.mc.thePlayer.rotationPitch / 64.0f;
        var2.startDrawingQuads();
        var2.addVertexWithUV(var5, var7, var9, var4 + var10, var4 + var11);
        var2.addVertexWithUV(var6, var7, var9, 0.0f + var10, var4 + var11);
        var2.addVertexWithUV(var6, var8, var9, 0.0f + var10, 0.0f + var11);
        var2.addVertexWithUV(var5, var8, var9, var4 + var10, 0.0f + var11);
        var2.draw();
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }
    
    private void renderFireInFirstPerson(final float p_78442_1_) {
        final Tessellator var2 = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.9f);
        GL11.glEnable(3042);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        final float var3 = 1.0f;
        for (int var4 = 0; var4 < 2; ++var4) {
            GL11.glPushMatrix();
            final IIcon var5 = Blocks.fire.func_149840_c(1);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            final float var6 = var5.getMinU();
            final float var7 = var5.getMaxU();
            final float var8 = var5.getMinV();
            final float var9 = var5.getMaxV();
            final float var10 = (0.0f - var3) / 2.0f;
            final float var11 = var10 + var3;
            final float var12 = 0.0f - var3 / 2.0f;
            final float var13 = var12 + var3;
            final float var14 = -0.5f;
            GL11.glTranslatef(-(var4 * 2 - 1) * 0.24f, -0.3f, 0.0f);
            GL11.glRotatef((var4 * 2 - 1) * 10.0f, 0.0f, 1.0f, 0.0f);
            var2.startDrawingQuads();
            var2.addVertexWithUV(var10, var12, var14, var7, var9);
            var2.addVertexWithUV(var11, var12, var14, var6, var9);
            var2.addVertexWithUV(var11, var13, var14, var6, var8);
            var2.addVertexWithUV(var10, var13, var14, var7, var8);
            var2.draw();
            GL11.glPopMatrix();
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
    }
    
    public void updateEquippedItem() {
        this.prevEquippedProgress = this.equippedProgress;
        final EntityClientPlayerMP var1 = this.mc.thePlayer;
        final ItemStack var2 = var1.inventory.getCurrentItem();
        boolean var3 = this.equippedItemSlot == var1.inventory.currentItem && var2 == this.itemToRender;
        if (this.itemToRender == null && var2 == null) {
            var3 = true;
        }
        if (var2 != null && this.itemToRender != null && var2 != this.itemToRender && var2.getItem() == this.itemToRender.getItem() && var2.getItemDamage() == this.itemToRender.getItemDamage()) {
            this.itemToRender = var2;
            var3 = true;
        }
        final float var4 = 0.4f;
        final float var5 = var3 ? 1.0f : 0.0f;
        float var6 = var5 - this.equippedProgress;
        if (var6 < -var4) {
            var6 = -var4;
        }
        if (var6 > var4) {
            var6 = var4;
        }
        this.equippedProgress += var6;
        if (this.equippedProgress < 0.1f) {
            this.itemToRender = var2;
            this.equippedItemSlot = var1.inventory.currentItem;
        }
    }
    
    public void resetEquippedProgress() {
        this.equippedProgress = 0.0f;
    }
    
    public void resetEquippedProgress2() {
        this.equippedProgress = 0.0f;
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
        RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
        RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
    }
}
