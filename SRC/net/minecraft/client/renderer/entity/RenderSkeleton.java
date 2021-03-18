package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderSkeleton extends RenderBiped
{
    private static final ResourceLocation skeletonTextures;
    private static final ResourceLocation witherSkeletonTextures;
    private static final String __OBFID = "CL_00001023";
    
    public RenderSkeleton() {
        super(new ModelSkeleton(), 0.5f);
    }
    
    public void preRenderCallback(final EntitySkeleton p_77041_1_, final float p_77041_2_) {
        if (p_77041_1_.getSkeletonType() == 1) {
            GL11.glScalef(1.2f, 1.2f, 1.2f);
        }
    }
    
    @Override
    protected void func_82422_c() {
        GL11.glTranslatef(0.09375f, 0.1875f, 0.0f);
    }
    
    public ResourceLocation getEntityTexture(final EntitySkeleton p_110775_1_) {
        return (p_110775_1_.getSkeletonType() == 1) ? RenderSkeleton.witherSkeletonTextures : RenderSkeleton.skeletonTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
        return this.getEntityTexture((EntitySkeleton)p_110775_1_);
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntitySkeleton)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySkeleton)p_110775_1_);
    }
    
    static {
        skeletonTextures = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        witherSkeletonTextures = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
    }
}
