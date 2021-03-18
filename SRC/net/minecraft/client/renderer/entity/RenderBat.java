package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderBat extends RenderLiving
{
    private static final ResourceLocation batTextures;
    private int renderedBatSize;
    private static final String __OBFID = "CL_00000979";
    
    public RenderBat() {
        super(new ModelBat(), 0.25f);
        this.renderedBatSize = ((ModelBat)this.mainModel).getBatSize();
    }
    
    public void doRender(final EntityBat p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        final int var10 = ((ModelBat)this.mainModel).getBatSize();
        if (var10 != this.renderedBatSize) {
            this.renderedBatSize = var10;
            this.mainModel = new ModelBat();
        }
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntityBat p_110775_1_) {
        return RenderBat.batTextures;
    }
    
    public void preRenderCallback(final EntityBat p_77041_1_, final float p_77041_2_) {
        GL11.glScalef(0.35f, 0.35f, 0.35f);
    }
    
    protected void renderLivingAt(final EntityBat p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        super.renderLivingAt(p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
    }
    
    protected void rotateCorpse(final EntityBat p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        if (!p_77043_1_.getIsBatHanging()) {
            GL11.glTranslatef(0.0f, MathHelper.cos(p_77043_2_ * 0.3f) * 0.1f, 0.0f);
        }
        else {
            GL11.glTranslatef(0.0f, -0.1f, 0.0f);
        }
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityBat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityBat)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected void rotateCorpse(final EntityLivingBase p_77043_1_, final float p_77043_2_, final float p_77043_3_, final float p_77043_4_) {
        this.rotateCorpse((EntityBat)p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }
    
    @Override
    protected void renderLivingAt(final EntityLivingBase p_77039_1_, final double p_77039_2_, final double p_77039_4_, final double p_77039_6_) {
        this.renderLivingAt((EntityBat)p_77039_1_, p_77039_2_, p_77039_4_, p_77039_6_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityBat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityBat)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntityBat)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        batTextures = new ResourceLocation("textures/entity/bat.png");
    }
}
