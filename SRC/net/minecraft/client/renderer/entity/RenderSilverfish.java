package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderSilverfish extends RenderLiving
{
    private static final ResourceLocation silverfishTextures;
    private static final String __OBFID = "CL_00001022";
    
    public RenderSilverfish() {
        super(new ModelSilverfish(), 0.3f);
    }
    
    protected float getDeathMaxRotation(final EntitySilverfish p_77037_1_) {
        return 180.0f;
    }
    
    public void doRender(final EntitySilverfish p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    public ResourceLocation getEntityTexture(final EntitySilverfish p_110775_1_) {
        return RenderSilverfish.silverfishTextures;
    }
    
    public int shouldRenderPass(final EntitySilverfish p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return -1;
    }
    
    @Override
    public void doRender(final EntityLiving p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntitySilverfish)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    protected float getDeathMaxRotation(final EntityLivingBase p_77037_1_) {
        return this.getDeathMaxRotation((EntitySilverfish)p_77037_1_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntitySilverfish)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public void doRender(final EntityLivingBase p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntitySilverfish)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySilverfish)p_110775_1_);
    }
    
    @Override
    public void doRender(final Entity p_76986_1_, final double p_76986_2_, final double p_76986_4_, final double p_76986_6_, final float p_76986_8_, final float p_76986_9_) {
        this.doRender((EntitySilverfish)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
    
    static {
        silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");
    }
}
