package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class RenderIronGolem extends RenderLiving
{
    private static final ResourceLocation ironGolemTextures;
    private final ModelIronGolem ironGolemModel;
    private static final String __OBFID = "CL_00001031";
    
    public RenderIronGolem() {
        super(new ModelIronGolem(), 0.5f);
        this.ironGolemModel = (ModelIronGolem)this.mainModel;
    }
    
    public void doRender(final EntityIronGolem p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityIronGolem p_110775_1_) {
        return RenderIronGolem.ironGolemTextures;
    }
    
    protected void rotateCorpse(final EntityIronGolem p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
        if (p_77043_1_.limbSwingAmount >= 0.01) {
            final float var5 = 13.0f;
            final float var6 = p_77043_1_.limbSwing - p_77043_1_.limbSwingAmount * (1.0f - p_77043_4_) + 6.0f;
            final float var7 = (Math.abs(var6 % var5 - var5 * 0.5f) - var5 * 0.25f) / (var5 * 0.25f);
            GL11.glRotatef(6.5f * var7, 0.0f, 0.0f, 1.0f);
        }
    }
    
    protected void renderEquippedItems(final EntityIronGolem p_77029_1_, final float p_77029_2_) {
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        if (p_77029_1_.getHoldRoseTick() != 0) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glRotatef(5.0f + 180.0f * this.ironGolemModel.ironGolemRightArm.rotateAngleX / 3.1415927f, 1.0f, 0.0f, 0.0f);
            GL11.glTranslatef(-0.6875f, 1.25f, -0.9375f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            final float var3 = 0.8f;
            GL11.glScalef(var3, -var3, var3);
            final int var4 = p_77029_1_.getBrightnessForRender(p_77029_2_);
            final int var5 = var4 % 65536;
            final int var6 = var4 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5 / 1.0f, var6 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.bindTexture(TextureMap.locationBlocksTexture);
            this.field_147909_c.renderBlockAsItem(Blocks.red_flower, 0, 1.0f);
            GL11.glPopMatrix();
            GL11.glDisable(32826);
        }
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityIronGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected void renderEquippedItems(final EntityLivingBase p_77029_1_, final float p_77029_2_) {
        this.renderEquippedItems((EntityIronGolem)p_77029_1_, p_77029_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((EntityIronGolem)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityIronGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityIronGolem)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityIronGolem)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        ironGolemTextures = new ResourceLocation("textures/entity/iron_golem.png");
    }
}
