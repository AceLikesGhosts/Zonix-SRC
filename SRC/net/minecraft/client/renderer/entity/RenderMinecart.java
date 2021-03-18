package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.item.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.block.*;

public class RenderMinecart extends Render
{
    private static final ResourceLocation minecartTextures;
    protected ModelBase modelMinecart;
    protected final RenderBlocks field_94145_f;
    private static final String __OBFID = "CL_00001013";
    
    public RenderMinecart() {
        this.modelMinecart = new ModelMinecart();
        this.shadowSize = 0.5f;
        this.field_94145_f = new RenderBlocks();
    }
    
    public void doRender(final EntityMinecart p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, final float p_76986_9_) {
        GL11.glPushMatrix();
        this.bindEntityTexture(p_76986_1_);
        long var10 = p_76986_1_.getEntityId() * 493286711L;
        var10 = var10 * var10 * 4392167121L + var10 * 98761L;
        final float var11 = (((var10 >> 16 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var12 = (((var10 >> 20 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        final float var13 = (((var10 >> 24 & 0x7L) + 0.5f) / 8.0f - 0.5f) * 0.004f;
        GL11.glTranslatef(var11, var12, var13);
        final double var14 = p_76986_1_.lastTickPosX + (p_76986_1_.posX - p_76986_1_.lastTickPosX) * p_76986_9_;
        final double var15 = p_76986_1_.lastTickPosY + (p_76986_1_.posY - p_76986_1_.lastTickPosY) * p_76986_9_;
        final double var16 = p_76986_1_.lastTickPosZ + (p_76986_1_.posZ - p_76986_1_.lastTickPosZ) * p_76986_9_;
        final double var17 = 0.30000001192092896;
        final Vec3 var18 = p_76986_1_.func_70489_a(var14, var15, var16);
        float var19 = p_76986_1_.prevRotationPitch + (p_76986_1_.rotationPitch - p_76986_1_.prevRotationPitch) * p_76986_9_;
        if (var18 != null) {
            Vec3 var20 = p_76986_1_.func_70495_a(var14, var15, var16, var17);
            Vec3 var21 = p_76986_1_.func_70495_a(var14, var15, var16, -var17);
            if (var20 == null) {
                var20 = var18;
            }
            if (var21 == null) {
                var21 = var18;
            }
            p_76986_2_ += var18.xCoord - var14;
            p_76986_4_ += (var20.yCoord + var21.yCoord) / 2.0 - var15;
            p_76986_6_ += var18.zCoord - var16;
            Vec3 var22 = var21.addVector(-var20.xCoord, -var20.yCoord, -var20.zCoord);
            if (var22.lengthVector() != 0.0) {
                var22 = var22.normalize();
                p_76986_8_ = (float)(Math.atan2(var22.zCoord, var22.xCoord) * 180.0 / 3.141592653589793);
                var19 = (float)(Math.atan(var22.yCoord) * 73.0);
            }
        }
        GL11.glTranslatef((float)p_76986_2_, (float)p_76986_4_, (float)p_76986_6_);
        GL11.glRotatef(180.0f - p_76986_8_, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-var19, 0.0f, 0.0f, 1.0f);
        final float var23 = p_76986_1_.getRollingAmplitude() - p_76986_9_;
        float var24 = p_76986_1_.getDamage() - p_76986_9_;
        if (var24 < 0.0f) {
            var24 = 0.0f;
        }
        if (var23 > 0.0f) {
            GL11.glRotatef(MathHelper.sin(var23) * var23 * var24 / 10.0f * p_76986_1_.getRollingDirection(), 1.0f, 0.0f, 0.0f);
        }
        final int var25 = p_76986_1_.getDisplayTileOffset();
        final Block var26 = p_76986_1_.func_145820_n();
        final int var27 = p_76986_1_.getDisplayTileData();
        if (var26.getRenderType() != -1) {
            GL11.glPushMatrix();
            this.bindTexture(TextureMap.locationBlocksTexture);
            final float var28 = 0.75f;
            GL11.glScalef(var28, var28, var28);
            GL11.glTranslatef(0.0f, var25 / 16.0f, 0.0f);
            this.func_147910_a(p_76986_1_, p_76986_9_, var26, var27);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindEntityTexture(p_76986_1_);
        }
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        this.modelMinecart.render(p_76986_1_, 0.0f, 0.0f, -0.1f, 0.0f, 0.0f, 0.0625f);
        GL11.glPopMatrix();
    }
    
    public ResourceLocation getEntityTexture(final EntityMinecart p_110775_1_) {
        return RenderMinecart.minecartTextures;
    }
    
    protected void func_147910_a(final EntityMinecart p_147910_1_, final float p_147910_2_, final Block p_147910_3_, final int p_147910_4_) {
        final float var5 = p_147910_1_.getBrightness(p_147910_2_);
        GL11.glPushMatrix();
        this.field_94145_f.renderBlockAsItem(p_147910_3_, p_147910_4_, var5);
        GL11.glPopMatrix();
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityMinecart)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityMinecart)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        minecartTextures = new ResourceLocation("textures/entity/minecart.png");
    }
}
