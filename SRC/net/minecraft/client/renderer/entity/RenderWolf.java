package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderWolf extends RenderLiving
{
    private static final ResourceLocation wolfTextures;
    private static final ResourceLocation tamedWolfTextures;
    private static final ResourceLocation anrgyWolfTextures;
    private static final ResourceLocation wolfCollarTextures;
    private static final String __OBFID = "CL_00001036";
    
    public RenderWolf(final ModelBase p_i1269_1_, final ModelBase p_i1269_2_, final float p_i1269_3_) {
        super(p_i1269_1_, p_i1269_3_);
        this.setRenderPassModel(p_i1269_2_);
    }
    
    protected float handleRotationFloat(final EntityWolf p_77044_1_, final float p_77044_2_) {
        return p_77044_1_.getTailRotation();
    }
    
    public int shouldRenderPass(final EntityWolf p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ == 0 && p_77032_1_.getWolfShaking()) {
            final float var5 = p_77032_1_.getBrightness(p_77032_3_) * p_77032_1_.getShadingWhileShaking(p_77032_3_);
            this.bindTexture(RenderWolf.wolfTextures);
            GL11.glColor3f(var5, var5, var5);
            return 1;
        }
        if (p_77032_2_ == 1 && p_77032_1_.isTamed()) {
            this.bindTexture(RenderWolf.wolfCollarTextures);
            final int var6 = p_77032_1_.getCollarColor();
            GL11.glColor3f(EntitySheep.fleeceColorTable[var6][0], EntitySheep.fleeceColorTable[var6][1], EntitySheep.fleeceColorTable[var6][2]);
            return 1;
        }
        return -1;
    }
    
    public ResourceLocation getEntityTexture(final EntityWolf p_110775_1_) {
        return p_110775_1_.isTamed() ? RenderWolf.tamedWolfTextures : (p_110775_1_.isAngry() ? RenderWolf.anrgyWolfTextures : RenderWolf.wolfTextures);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityWolf)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    protected float handleRotationFloat(final EntityLivingBase p_77044_1_, final float p_77044_2_) {
        return this.handleRotationFloat((EntityWolf)p_77044_1_, p_77044_2_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityWolf)p_110775_1_);
    }
    
    static {
        wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
        tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
        anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
        wolfCollarTextures = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }
}
