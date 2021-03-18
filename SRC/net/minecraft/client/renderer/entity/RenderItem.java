package net.minecraft.client.renderer.entity;

import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.gui.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import us.zonix.client.module.impl.*;
import net.minecraft.crash.*;

public class RenderItem extends Render
{
    private static final ResourceLocation RES_ITEM_GLINT;
    private RenderBlocks field_147913_i;
    private Random random;
    public boolean renderWithColor;
    public float zLevel;
    public static boolean renderInFrame;
    private static final String __OBFID = "CL_00001003";
    
    public RenderItem() {
        this.field_147913_i = new RenderBlocks();
        this.random = new Random();
        this.renderWithColor = true;
        this.shadowSize = 0.15f;
        this.shadowOpaque = 0.75f;
    }
    
    public void doRender(final EntityItem p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final ItemStack var10 = p_76986_1_.getEntityItem();
        if (var10.getItem() != null) {
            this.bindEntityTexture(p_76986_1_);
            TextureUtil.func_152777_a(false, false, 1.0f);
            this.random.setSeed(187L);
            GL11.glPushMatrix();
            final float var11 = MathHelper.sin((p_76986_1_.age + p_76986_9_) / 10.0f + p_76986_1_.hoverStart) * 0.1f + 0.1f;
            final float var12 = ((p_76986_1_.age + p_76986_9_) / 20.0f + p_76986_1_.hoverStart) * 57.295776f;
            byte var13 = 1;
            if (p_76986_1_.getEntityItem().stackSize > 1) {
                var13 = 2;
            }
            if (p_76986_1_.getEntityItem().stackSize > 5) {
                var13 = 3;
            }
            if (p_76986_1_.getEntityItem().stackSize > 20) {
                var13 = 4;
            }
            if (p_76986_1_.getEntityItem().stackSize > 40) {
                var13 = 5;
            }
            GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_ + var11, (float)p_76986_6_);
            GL11.glEnable(32826);
            if (var10.getItemSpriteNumber() == 0 && var10.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(var10.getItem()).getRenderType())) {
                final Block var14 = Block.getBlockFromItem(var10.getItem());
                GL11.glRotatef(var12, 0.0f, 1.0f, 0.0f);
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(1.25f, 1.25f, 1.25f);
                    GL11.glTranslatef(0.0f, 0.05f, 0.0f);
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                float var15 = 0.25f;
                final int var16 = var14.getRenderType();
                if (var16 == 1 || var16 == 19 || var16 == 12 || var16 == 2) {
                    var15 = 0.5f;
                }
                if (var14.getRenderBlockPass() > 0) {
                    GL11.glAlphaFunc(516, 0.1f);
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }
                GL11.glScalef(var15, var15, var15);
                for (int var17 = 0; var17 < var13; ++var17) {
                    GL11.glPushMatrix();
                    if (var17 > 0) {
                        final float var18 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        final float var19 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        final float var20 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.2f / var15;
                        GL11.glTranslatef(var18, var19, var20);
                    }
                    this.field_147913_i.renderBlockAsItem(var14, var10.getItemDamage(), 1.0f);
                    GL11.glPopMatrix();
                }
                if (var14.getRenderBlockPass() > 0) {
                    GL11.glDisable(3042);
                }
            }
            else if (var10.getItemSpriteNumber() == 1 && var10.getItem().requiresMultipleRenderPasses()) {
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                }
                else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                for (int var21 = 0; var21 <= 1; ++var21) {
                    this.random.setSeed(187L);
                    final IIcon var22 = var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var21);
                    if (this.renderWithColor) {
                        final int var16 = var10.getItem().getColorFromItemStack(var10, var21);
                        final float var23 = (var16 >> 16 & 0xFF) / 255.0f;
                        final float var18 = (var16 >> 8 & 0xFF) / 255.0f;
                        final float var19 = (var16 & 0xFF) / 255.0f;
                        GL11.glColor4f(var23, var18, var19, 1.0f);
                        this.renderDroppedItem(p_76986_1_, var22, var13, p_76986_9_, var23, var18, var19);
                    }
                    else {
                        this.renderDroppedItem(p_76986_1_, var22, var13, p_76986_9_, 1.0f, 1.0f, 1.0f);
                    }
                }
            }
            else {
                if (var10 != null && var10.getItem() instanceof ItemCloth) {
                    GL11.glAlphaFunc(516, 0.1f);
                    GL11.glEnable(3042);
                    OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                }
                if (RenderItem.renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                }
                else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                final IIcon var24 = var10.getIconIndex();
                if (this.renderWithColor) {
                    final int var25 = var10.getItem().getColorFromItemStack(var10, 0);
                    final float var26 = (var25 >> 16 & 0xFF) / 255.0f;
                    final float var23 = (var25 >> 8 & 0xFF) / 255.0f;
                    final float var18 = (var25 & 0xFF) / 255.0f;
                    this.renderDroppedItem(p_76986_1_, var24, var13, p_76986_9_, var26, var23, var18);
                }
                else {
                    this.renderDroppedItem(p_76986_1_, var24, var13, p_76986_9_, 1.0f, 1.0f, 1.0f);
                }
                if (var10 != null && var10.getItem() instanceof ItemCloth) {
                    GL11.glDisable(3042);
                }
            }
            GL11.glDisable(32826);
            GL11.glPopMatrix();
            this.bindEntityTexture(p_76986_1_);
            TextureUtil.func_147945_b();
        }
    }
    
    public ResourceLocation getEntityTexture(final EntityItem p_110775_1_) {
        return this.renderManager.renderEngine.getResourceLocation(p_110775_1_.getEntityItem().getItemSpriteNumber());
    }
    
    private void renderDroppedItem(final EntityItem p_77020_1_, IIcon p_77020_2_, final int p_77020_3_, final float p_77020_4_, final float p_77020_5_, final float p_77020_6_, final float p_77020_7_) {
        final Tessellator var8 = Tessellator.instance;
        if (p_77020_2_ == null) {
            final TextureManager var9 = Minecraft.getMinecraft().getTextureManager();
            final ResourceLocation var10 = var9.getResourceLocation(p_77020_1_.getEntityItem().getItemSpriteNumber());
            p_77020_2_ = ((TextureMap)var9.getTexture(var10)).getAtlasSprite("missingno");
        }
        final float var11 = p_77020_2_.getMinU();
        final float var12 = p_77020_2_.getMaxU();
        final float var13 = p_77020_2_.getMinV();
        final float var14 = p_77020_2_.getMaxV();
        final float var15 = 1.0f;
        final float var16 = 0.5f;
        final float var17 = 0.25f;
        if (this.renderManager.options.fancyGraphics) {
            GL11.glPushMatrix();
            if (RenderItem.renderInFrame) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GL11.glRotatef(((p_77020_1_.age + p_77020_4_) / 20.0f + p_77020_1_.hoverStart) * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            final float var18 = 0.0625f;
            final float var19 = 0.021875f;
            final ItemStack var20 = p_77020_1_.getEntityItem();
            final int var21 = var20.stackSize;
            byte var22;
            if (var21 < 2) {
                var22 = 1;
            }
            else if (var21 < 16) {
                var22 = 2;
            }
            else if (var21 < 32) {
                var22 = 3;
            }
            else {
                var22 = 4;
            }
            GL11.glTranslatef(-var16, -var17, -((var18 + var19) * var22 / 2.0f));
            for (int var23 = 0; var23 < var22; ++var23) {
                GL11.glTranslatef(0.0f, 0.0f, var18 + var19);
                if (var20.getItemSpriteNumber() == 0) {
                    this.bindTexture(TextureMap.locationBlocksTexture);
                }
                else {
                    this.bindTexture(TextureMap.locationItemsTexture);
                }
                GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0f);
                ItemRenderer.renderItemIn2D(var8, var12, var13, var11, var14, p_77020_2_.getIconWidth(), p_77020_2_.getIconHeight(), var18);
                if (var20.hasEffect()) {
                    GL11.glDepthFunc(514);
                    GL11.glDisable(2896);
                    this.renderManager.renderEngine.bindTexture(RenderItem.RES_ITEM_GLINT);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(768, 1);
                    final float var24 = 0.76f;
                    GL11.glColor4f(0.5f * var24, 0.25f * var24, 0.8f * var24, 1.0f);
                    GL11.glMatrixMode(5890);
                    GL11.glPushMatrix();
                    final float var25 = 0.125f;
                    GL11.glScalef(var25, var25, var25);
                    float var26 = Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f;
                    GL11.glTranslatef(var26, 0.0f, 0.0f);
                    GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var18);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(var25, var25, var25);
                    var26 = Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f;
                    GL11.glTranslatef(-var26, 0.0f, 0.0f);
                    GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
                    ItemRenderer.renderItemIn2D(var8, 0.0f, 0.0f, 1.0f, 1.0f, 255, 255, var18);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(5888);
                    GL11.glDisable(3042);
                    GL11.glEnable(2896);
                    GL11.glDepthFunc(515);
                }
            }
            GL11.glPopMatrix();
        }
        else {
            for (int var27 = 0; var27 < p_77020_3_; ++var27) {
                GL11.glPushMatrix();
                if (var27 > 0) {
                    final float var19 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    final float var28 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    final float var29 = (this.random.nextFloat() * 2.0f - 1.0f) * 0.3f;
                    GL11.glTranslatef(var19, var28, var29);
                }
                if (!RenderItem.renderInFrame) {
                    GL11.glRotatef(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                }
                GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0f);
                var8.startDrawingQuads();
                var8.setNormal(0.0f, 1.0f, 0.0f);
                var8.addVertexWithUV(0.0f - var16, 0.0f - var17, 0.0, var11, var14);
                var8.addVertexWithUV(var15 - var16, 0.0f - var17, 0.0, var12, var14);
                var8.addVertexWithUV(var15 - var16, 1.0f - var17, 0.0, var12, var13);
                var8.addVertexWithUV(0.0f - var16, 1.0f - var17, 0.0, var11, var13);
                var8.draw();
                GL11.glPopMatrix();
            }
        }
    }
    
    public void renderItemIntoGUI(final FontRenderer p_77015_1_, final TextureManager p_77015_2_, final ItemStack p_77015_3_, final int p_77015_4_, final int p_77015_5_) {
        final int var6 = p_77015_3_.getItemDamage();
        Object var7 = p_77015_3_.getIconIndex();
        if (p_77015_3_.getItemSpriteNumber() == 0 && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(p_77015_3_.getItem()).getRenderType())) {
            p_77015_2_.bindTexture(TextureMap.locationBlocksTexture);
            final Block var8 = Block.getBlockFromItem(p_77015_3_.getItem());
            GL11.glEnable(3008);
            if (var8.getRenderBlockPass() != 0) {
                GL11.glAlphaFunc(516, 0.1f);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            }
            else {
                GL11.glAlphaFunc(516, 0.5f);
                GL11.glDisable(3042);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(p_77015_4_ - 2), (float)(p_77015_5_ + 3), -3.0f + this.zLevel);
            GL11.glScalef(10.0f, 10.0f, 10.0f);
            GL11.glTranslatef(1.0f, 0.5f, 1.0f);
            GL11.glScalef(1.0f, 1.0f, -1.0f);
            GL11.glRotatef(210.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            final int var9 = p_77015_3_.getItem().getColorFromItemStack(p_77015_3_, 0);
            final float var10 = (var9 >> 16 & 0xFF) / 255.0f;
            final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
            final float var12 = (var9 & 0xFF) / 255.0f;
            if (this.renderWithColor) {
                GL11.glColor4f(var10, var11, var12, 1.0f);
            }
            GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            this.field_147913_i.useInventoryTint = this.renderWithColor;
            this.field_147913_i.renderBlockAsItem(var8, var6, 1.0f);
            this.field_147913_i.useInventoryTint = true;
            if (var8.getRenderBlockPass() == 0) {
                GL11.glAlphaFunc(516, 0.1f);
            }
            GL11.glPopMatrix();
        }
        else if (p_77015_3_.getItem().requiresMultipleRenderPasses()) {
            GL11.glDisable(2896);
            GL11.glEnable(3008);
            p_77015_2_.bindTexture(TextureMap.locationItemsTexture);
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(0, 0, 0, 0);
            GL11.glColorMask(false, false, false, true);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final Tessellator var13 = Tessellator.instance;
            var13.startDrawingQuads();
            var13.setColorOpaque_I(-1);
            var13.addVertex(p_77015_4_ - 2, p_77015_5_ + 18, this.zLevel);
            var13.addVertex(p_77015_4_ + 18, p_77015_5_ + 18, this.zLevel);
            var13.addVertex(p_77015_4_ + 18, p_77015_5_ - 2, this.zLevel);
            var13.addVertex(p_77015_4_ - 2, p_77015_5_ - 2, this.zLevel);
            var13.draw();
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            for (int var9 = 0; var9 <= 1; ++var9) {
                final IIcon var14 = p_77015_3_.getItem().getIconFromDamageForRenderPass(var6, var9);
                final int var15 = p_77015_3_.getItem().getColorFromItemStack(p_77015_3_, var9);
                final float var12 = (var15 >> 16 & 0xFF) / 255.0f;
                final float var16 = (var15 >> 8 & 0xFF) / 255.0f;
                final float var17 = (var15 & 0xFF) / 255.0f;
                if (this.renderWithColor) {
                    GL11.glColor4f(var12, var16, var17, 1.0f);
                }
                this.renderIcon(p_77015_4_, p_77015_5_, var14, 16, 16);
            }
            GL11.glEnable(2896);
        }
        else {
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            final ResourceLocation var18 = p_77015_2_.getResourceLocation(p_77015_3_.getItemSpriteNumber());
            p_77015_2_.bindTexture(var18);
            if (var7 == null) {
                var7 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var18)).getAtlasSprite("missingno");
            }
            final int var9 = p_77015_3_.getItem().getColorFromItemStack(p_77015_3_, 0);
            final float var10 = (var9 >> 16 & 0xFF) / 255.0f;
            final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
            final float var12 = (var9 & 0xFF) / 255.0f;
            if (this.renderWithColor) {
                GL11.glColor4f(var10, var11, var12, 1.0f);
            }
            this.renderIcon(p_77015_4_, p_77015_5_, (IIcon)var7, 16, 16);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
        }
        GL11.glEnable(2884);
    }
    
    public void renderItemAndEffectIntoGUI(final FontRenderer p_82406_1_, final TextureManager p_82406_2_, final ItemStack p_82406_3_, final int p_82406_4_, final int p_82406_5_) {
        if (p_82406_3_ != null) {
            this.zLevel += 50.0f;
            try {
                this.renderItemIntoGUI(p_82406_1_, p_82406_2_, p_82406_3_, p_82406_4_, p_82406_5_);
            }
            catch (Throwable var8) {
                final CrashReport var7 = CrashReport.makeCrashReport(var8, "Rendering item");
                final CrashReportCategory var9 = var7.makeCategory("Item being rendered");
                var9.addCrashSectionCallable("Item Type", new Callable() {
                    private static final String __OBFID = "CL_00001004";
                    
                    @Override
                    public String call() {
                        return String.valueOf(p_82406_3_.getItem());
                    }
                });
                var9.addCrashSectionCallable("Item Aux", new Callable() {
                    private static final String __OBFID = "CL_00001005";
                    
                    @Override
                    public String call() {
                        return String.valueOf(p_82406_3_.getItemDamage());
                    }
                });
                var9.addCrashSectionCallable("Item NBT", new Callable() {
                    private static final String __OBFID = "CL_00001006";
                    
                    @Override
                    public String call() {
                        return String.valueOf(p_82406_3_.getTagCompound());
                    }
                });
                var9.addCrashSectionCallable("Item Foil", new Callable() {
                    private static final String __OBFID = "CL_00001007";
                    
                    @Override
                    public String call() {
                        return String.valueOf(p_82406_3_.hasEffect());
                    }
                });
                throw new ReportedException(var7);
            }
            if (p_82406_3_.hasEffect() && FPSBoost.ITEM_GLINT.getValue()) {
                GL11.glDepthFunc(514);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                p_82406_2_.bindTexture(RenderItem.RES_ITEM_GLINT);
                GL11.glEnable(3008);
                GL11.glEnable(3042);
                GL11.glColor4f(0.5f, 0.25f, 0.8f, 1.0f);
                this.renderGlint(p_82406_4_ * 431278612 + p_82406_5_ * 32178161, p_82406_4_ - 2, p_82406_5_ - 2, 20, 20);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glDepthMask(true);
                GL11.glEnable(2896);
                GL11.glDepthFunc(515);
            }
            this.zLevel -= 50.0f;
        }
    }
    
    private void renderGlint(final int p_77018_1_, final int p_77018_2_, final int p_77018_3_, final int p_77018_4_, final int p_77018_5_) {
        final boolean shiny = FPSBoost.SHINY_POTS.getValue();
        for (int var6 = 0; var6 < 2; ++var6) {
            OpenGlHelper.glBlendFunc(shiny ? 773 : 772, 1, 0, 0);
            final float var7 = 0.00390625f;
            final float var8 = 0.00390625f;
            final float var9 = Minecraft.getSystemTime() % (3000 + var6 * 1873) / (3000.0f + var6 * 1873) * 256.0f;
            final float var10 = 0.0f;
            final Tessellator var11 = Tessellator.instance;
            float var12 = 4.0f;
            if (var6 == 1) {
                var12 = -1.0f;
            }
            var11.startDrawingQuads();
            var11.addVertexWithUV(p_77018_2_ + 0, p_77018_3_ + p_77018_5_, this.zLevel, (var9 + p_77018_5_ * var12) * var7, (var10 + p_77018_5_) * var8);
            var11.addVertexWithUV(p_77018_2_ + p_77018_4_, p_77018_3_ + p_77018_5_, this.zLevel, (var9 + p_77018_4_ + p_77018_5_ * var12) * var7, (var10 + p_77018_5_) * var8);
            var11.addVertexWithUV(p_77018_2_ + p_77018_4_, p_77018_3_ + 0, this.zLevel, (var9 + p_77018_4_) * var7, (var10 + 0.0f) * var8);
            var11.addVertexWithUV(p_77018_2_ + 0, p_77018_3_ + 0, this.zLevel, (var9 + 0.0f) * var7, (var10 + 0.0f) * var8);
            var11.draw();
        }
    }
    
    public void renderItemOverlayIntoGUI(final FontRenderer p_77021_1_, final TextureManager p_77021_2_, final ItemStack p_77021_3_, final int p_77021_4_, final int p_77021_5_) {
        this.renderItemOverlayIntoGUI(p_77021_1_, p_77021_2_, p_77021_3_, p_77021_4_, p_77021_5_, null);
    }
    
    public void renderItemOverlayIntoGUI(final FontRenderer p_94148_1_, final TextureManager p_94148_2_, final ItemStack p_94148_3_, final int p_94148_4_, final int p_94148_5_, final String p_94148_6_) {
        if (p_94148_3_ != null) {
            if (p_94148_3_.stackSize > 1 || p_94148_6_ != null) {
                final String var7 = (p_94148_6_ == null) ? String.valueOf(p_94148_3_.stackSize) : p_94148_6_;
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glDisable(3042);
                p_94148_1_.drawStringWithShadow(var7, p_94148_4_ + 19 - 2 - p_94148_1_.getStringWidth(var7), p_94148_5_ + 6 + 3, 16777215);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
            }
            if (p_94148_3_.isItemDamaged()) {
                final int var8 = (int)Math.round(13.0 - p_94148_3_.getItemDamageForDisplay() * 13.0 / p_94148_3_.getMaxDamage());
                final int var9 = (int)Math.round(255.0 - p_94148_3_.getItemDamageForDisplay() * 255.0 / p_94148_3_.getMaxDamage());
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glDisable(3042);
                final Tessellator var10 = Tessellator.instance;
                final int var11 = 255 - var9 << 16 | var9 << 8;
                final int var12 = (255 - var9) / 4 << 16 | 0x3F00;
                this.renderQuad(var10, p_94148_4_ + 2, p_94148_5_ + 13, 13, 2, 0);
                this.renderQuad(var10, p_94148_4_ + 2, p_94148_5_ + 13, 12, 1, var12);
                this.renderQuad(var10, p_94148_4_ + 2, p_94148_5_ + 13, var8, 1, var11);
                GL11.glEnable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }
    }
    
    private void renderQuad(final Tessellator p_77017_1_, final int p_77017_2_, final int p_77017_3_, final int p_77017_4_, final int p_77017_5_, final int p_77017_6_) {
        p_77017_1_.startDrawingQuads();
        p_77017_1_.setColorOpaque_I(p_77017_6_);
        p_77017_1_.addVertex(p_77017_2_ + 0, p_77017_3_ + 0, 0.0);
        p_77017_1_.addVertex(p_77017_2_ + 0, p_77017_3_ + p_77017_5_, 0.0);
        p_77017_1_.addVertex(p_77017_2_ + p_77017_4_, p_77017_3_ + p_77017_5_, 0.0);
        p_77017_1_.addVertex(p_77017_2_ + p_77017_4_, p_77017_3_ + 0, 0.0);
        p_77017_1_.draw();
    }
    
    public void renderIcon(final int p_94149_1_, final int p_94149_2_, final IIcon p_94149_3_, final int p_94149_4_, final int p_94149_5_) {
        final Tessellator var6 = Tessellator.instance;
        var6.startDrawingQuads();
        var6.addVertexWithUV(p_94149_1_ + 0, p_94149_2_ + p_94149_5_, this.zLevel, p_94149_3_.getMinU(), p_94149_3_.getMaxV());
        var6.addVertexWithUV(p_94149_1_ + p_94149_4_, p_94149_2_ + p_94149_5_, this.zLevel, p_94149_3_.getMaxU(), p_94149_3_.getMaxV());
        var6.addVertexWithUV(p_94149_1_ + p_94149_4_, p_94149_2_ + 0, this.zLevel, p_94149_3_.getMaxU(), p_94149_3_.getMinV());
        var6.addVertexWithUV(p_94149_1_ + 0, p_94149_2_ + 0, this.zLevel, p_94149_3_.getMinU(), p_94149_3_.getMinV());
        var6.draw();
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityItem)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityItem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
