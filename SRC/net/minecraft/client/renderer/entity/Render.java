package net.minecraft.client.renderer.entity;

import org.lwjgl.opengl.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;

public abstract class Render
{
    private static final ResourceLocation shadowTextures;
    protected RenderManager renderManager;
    protected RenderBlocks field_147909_c;
    protected float shadowSize;
    protected float shadowOpaque;
    private boolean field_147908_f;
    private static final String __OBFID = "CL_00000992";
    
    public Render() {
        this.field_147909_c = new RenderBlocks();
        this.shadowOpaque = 1.0f;
        this.field_147908_f = false;
    }
    
    public abstract void doRender(final Entity p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    public abstract ResourceLocation getEntityTexture(final Entity p0);
    
    public boolean func_147905_a() {
        return this.field_147908_f;
    }
    
    protected void bindEntityTexture(final Entity p_110777_1_) {
        this.bindTexture(this.getEntityTexture(p_110777_1_));
    }
    
    protected void bindTexture(final ResourceLocation p_110776_1_) {
        this.renderManager.renderEngine.bindTexture(p_110776_1_);
    }
    
    private void renderEntityOnFire(final Entity p_76977_1_, final double p_76977_2_, final double p_76977_4_, final double p_76977_6_, final float p_76977_8_) {
        GL11.glDisable(2896);
        final IIcon var9 = Blocks.fire.func_149840_c(0);
        final IIcon var10 = Blocks.fire.func_149840_c(1);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)p_76977_2_, (float)p_76977_4_, (float)p_76977_6_);
        final float var11 = p_76977_1_.width * 1.4f;
        GL11.glScalef(var11, var11, var11);
        final Tessellator var12 = Tessellator.instance;
        float var13 = 0.5f;
        final float var14 = 0.0f;
        float var15 = p_76977_1_.height / var11;
        float var16 = (float)(p_76977_1_.posY - p_76977_1_.boundingBox.minY);
        GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(0.0f, 0.0f, -0.3f + (int)var15 * 0.02f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float var17 = 0.0f;
        int var18 = 0;
        var12.startDrawingQuads();
        while (var15 > 0.0f) {
            final IIcon var19 = (var18 % 2 == 0) ? var9 : var10;
            this.bindTexture(TextureMap.locationBlocksTexture);
            float var20 = var19.getMinU();
            final float var21 = var19.getMinV();
            float var22 = var19.getMaxU();
            final float var23 = var19.getMaxV();
            if (var18 / 2 % 2 == 0) {
                final float var24 = var22;
                var22 = var20;
                var20 = var24;
            }
            var12.addVertexWithUV(var13 - var14, 0.0f - var16, var17, var22, var23);
            var12.addVertexWithUV(-var13 - var14, 0.0f - var16, var17, var20, var23);
            var12.addVertexWithUV(-var13 - var14, 1.4f - var16, var17, var20, var21);
            var12.addVertexWithUV(var13 - var14, 1.4f - var16, var17, var22, var21);
            var15 -= 0.45f;
            var16 -= 0.45f;
            var13 *= 0.9f;
            var17 += 0.03f;
            ++var18;
        }
        var12.draw();
        GL11.glPopMatrix();
        GL11.glEnable(2896);
    }
    
    private void renderShadow(final Entity p_76975_1_, final double p_76975_2_, final double p_76975_4_, final double p_76975_6_, final float p_76975_8_, final float p_76975_9_) {
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        this.renderManager.renderEngine.bindTexture(Render.shadowTextures);
        final World var10 = this.getWorldFromRenderManager();
        GL11.glDepthMask(false);
        float var11 = this.shadowSize;
        if (p_76975_1_ instanceof EntityLiving) {
            final EntityLiving var12 = (EntityLiving)p_76975_1_;
            var11 *= var12.getRenderSizeModifier();
            if (var12.isChild()) {
                var11 *= 0.5f;
            }
        }
        final double var13 = p_76975_1_.lastTickPosX + (p_76975_1_.posX - p_76975_1_.lastTickPosX) * p_76975_9_;
        final double var14 = p_76975_1_.lastTickPosY + (p_76975_1_.posY - p_76975_1_.lastTickPosY) * p_76975_9_ + p_76975_1_.getShadowSize();
        final double var15 = p_76975_1_.lastTickPosZ + (p_76975_1_.posZ - p_76975_1_.lastTickPosZ) * p_76975_9_;
        final int var16 = MathHelper.floor_double(var13 - var11);
        final int var17 = MathHelper.floor_double(var13 + var11);
        final int var18 = MathHelper.floor_double(var14 - var11);
        final int var19 = MathHelper.floor_double(var14);
        final int var20 = MathHelper.floor_double(var15 - var11);
        final int var21 = MathHelper.floor_double(var15 + var11);
        final double var22 = p_76975_2_ - var13;
        final double var23 = p_76975_4_ - var14;
        final double var24 = p_76975_6_ - var15;
        final Tessellator var25 = Tessellator.instance;
        var25.startDrawingQuads();
        for (int var26 = var16; var26 <= var17; ++var26) {
            for (int var27 = var18; var27 <= var19; ++var27) {
                for (int var28 = var20; var28 <= var21; ++var28) {
                    final Block var29 = var10.getBlock(var26, var27 - 1, var28);
                    if (var29.getMaterial() != Material.air && var10.getBlockLightValue(var26, var27, var28) > 3) {
                        this.func_147907_a(var29, p_76975_2_, p_76975_4_ + p_76975_1_.getShadowSize(), p_76975_6_, var26, var27, var28, p_76975_8_, var11, var22, var23 + p_76975_1_.getShadowSize(), var24);
                    }
                }
            }
        }
        var25.draw();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
    }
    
    private World getWorldFromRenderManager() {
        return this.renderManager.worldObj;
    }
    
    private void func_147907_a(final Block p_147907_1_, final double p_147907_2_, final double p_147907_4_, final double p_147907_6_, final int p_147907_8_, final int p_147907_9_, final int p_147907_10_, final float p_147907_11_, final float p_147907_12_, final double p_147907_13_, final double p_147907_15_, final double p_147907_17_) {
        final Tessellator var19 = Tessellator.instance;
        if (p_147907_1_.renderAsNormalBlock()) {
            double var20 = (p_147907_11_ - (p_147907_4_ - (p_147907_9_ + p_147907_15_)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(p_147907_8_, p_147907_9_, p_147907_10_);
            if (var20 >= 0.0) {
                if (var20 > 1.0) {
                    var20 = 1.0;
                }
                var19.setColorRGBA_F(1.0f, 1.0f, 1.0f, (float)var20);
                final double var21 = p_147907_8_ + p_147907_1_.getBlockBoundsMinX() + p_147907_13_;
                final double var22 = p_147907_8_ + p_147907_1_.getBlockBoundsMaxX() + p_147907_13_;
                final double var23 = p_147907_9_ + p_147907_1_.getBlockBoundsMinY() + p_147907_15_ + 0.015625;
                final double var24 = p_147907_10_ + p_147907_1_.getBlockBoundsMinZ() + p_147907_17_;
                final double var25 = p_147907_10_ + p_147907_1_.getBlockBoundsMaxZ() + p_147907_17_;
                final float var26 = (float)((p_147907_2_ - var21) / 2.0 / p_147907_12_ + 0.5);
                final float var27 = (float)((p_147907_2_ - var22) / 2.0 / p_147907_12_ + 0.5);
                final float var28 = (float)((p_147907_6_ - var24) / 2.0 / p_147907_12_ + 0.5);
                final float var29 = (float)((p_147907_6_ - var25) / 2.0 / p_147907_12_ + 0.5);
                var19.addVertexWithUV(var21, var23, var24, var26, var28);
                var19.addVertexWithUV(var21, var23, var25, var26, var29);
                var19.addVertexWithUV(var22, var23, var25, var27, var29);
                var19.addVertexWithUV(var22, var23, var24, var27, var28);
            }
        }
    }
    
    public static void renderOffsetAABB(final AxisAlignedBB p_76978_0_, final double p_76978_1_, final double p_76978_3_, final double p_76978_5_) {
        GL11.glDisable(3553);
        final Tessellator var7 = Tessellator.instance;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        var7.startDrawingQuads();
        var7.setTranslation(p_76978_1_, p_76978_3_, p_76978_5_);
        var7.setNormal(0.0f, 0.0f, -1.0f);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.setNormal(0.0f, 0.0f, 1.0f);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.setNormal(0.0f, -1.0f, 0.0f);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.setNormal(0.0f, 1.0f, 0.0f);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.setNormal(-1.0f, 0.0f, 0.0f);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.minX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.setNormal(1.0f, 0.0f, 0.0f);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.minZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.maxY, p_76978_0_.maxZ);
        var7.addVertex(p_76978_0_.maxX, p_76978_0_.minY, p_76978_0_.maxZ);
        var7.setTranslation(0.0, 0.0, 0.0);
        var7.draw();
        GL11.glEnable(3553);
    }
    
    public static void renderAABB(final AxisAlignedBB p_76980_0_) {
        final Tessellator var1 = Tessellator.instance;
        var1.startDrawingQuads();
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.minX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.minZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.maxY, p_76980_0_.maxZ);
        var1.addVertex(p_76980_0_.maxX, p_76980_0_.minY, p_76980_0_.maxZ);
        var1.draw();
    }
    
    public void setRenderManager(final RenderManager p_76976_1_) {
        this.renderManager = p_76976_1_;
    }
    
    public void doRenderShadowAndFire(final Entity p_76979_1_, final double p_76979_2_, final double p_76979_4_, final double p_76979_6_, final float p_76979_8_, final float p_76979_9_) {
        if (this.renderManager.options.fancyGraphics && this.shadowSize > 0.0f && !p_76979_1_.isInvisible()) {
            final double var10 = this.renderManager.getDistanceToCamera(p_76979_1_.posX, p_76979_1_.posY, p_76979_1_.posZ);
            final float var11 = (float)((1.0 - var10 / 256.0) * this.shadowOpaque);
            if (var11 > 0.0f) {
                this.renderShadow(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, var11, p_76979_9_);
            }
        }
        if (p_76979_1_.canRenderOnFire()) {
            this.renderEntityOnFire(p_76979_1_, p_76979_2_, p_76979_4_, p_76979_6_, p_76979_9_);
        }
    }
    
    public FontRenderer getFontRendererFromRenderManager() {
        return this.renderManager.getFontRenderer();
    }
    
    public void updateIcons(final IIconRegister p_94143_1_) {
    }
    
    protected void func_147906_a(final Entity p_147906_1_, final String p_147906_2_, final double p_147906_3_, final double p_147906_5_, final double p_147906_7_, final int p_147906_9_) {
        final double var10 = p_147906_1_.getDistanceSqToEntity(this.renderManager.livingPlayer);
        if (var10 <= p_147906_9_ * p_147906_9_) {
            final FontRenderer var11 = this.getFontRendererFromRenderManager();
            final float var12 = 1.6f;
            final float var13 = 0.016666668f * var12;
            GL11.glPushMatrix();
            GL11.glTranslatef((float)p_147906_3_ + 0.0f, (float)p_147906_5_ + p_147906_1_.height + 0.5f, (float)p_147906_7_);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
            GL11.glScalef(-var13, -var13, var13);
            GL11.glDisable(2896);
            GL11.glDepthMask(false);
            GL11.glDisable(2929);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            final Tessellator var14 = Tessellator.instance;
            byte var15 = 0;
            if (p_147906_2_.equals("deadmau5")) {
                var15 = -10;
            }
            GL11.glDisable(3553);
            var14.startDrawingQuads();
            final int var16 = var11.getStringWidth(p_147906_2_) / 2;
            var14.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.25f);
            var14.addVertex(-var16 - 1, -1 + var15, 0.0);
            var14.addVertex(-var16 - 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, 8 + var15, 0.0);
            var14.addVertex(var16 + 1, -1 + var15, 0.0);
            var14.draw();
            GL11.glEnable(3553);
            var11.drawString(p_147906_2_, -var11.getStringWidth(p_147906_2_) / 2, var15, 553648127);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            var11.drawString(p_147906_2_, -var11.getStringWidth(p_147906_2_) / 2, var15, -1);
            GL11.glEnable(2896);
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glPopMatrix();
        }
    }
    
    static {
        shadowTextures = new ResourceLocation("textures/misc/shadow.png");
    }
}
