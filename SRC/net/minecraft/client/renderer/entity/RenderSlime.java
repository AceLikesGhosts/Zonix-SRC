package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;

public class RenderSlime extends RenderLiving
{
    private static final ResourceLocation slimeTextures;
    private ModelBase scaleAmount;
    private static final String __OBFID = "CL_00001024";
    
    public RenderSlime(final ModelBase p_i1267_1_, final ModelBase p_i1267_2_, final float p_i1267_3_) {
        super(p_i1267_1_, p_i1267_3_);
        this.scaleAmount = p_i1267_2_;
    }
    
    public int shouldRenderPass(final EntitySlime p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_1_.isInvisible()) {
            return 0;
        }
        if (p_77032_2_ == 0) {
            this.setRenderPassModel(this.scaleAmount);
            GL11.glEnable(2977);
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            return 1;
        }
        if (p_77032_2_ == 1) {
            GL11.glDisable(3042);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        return -1;
    }
    
    public void preRenderCallback(final EntitySlime p_77041_1_, final float p_77041_2_) {
        final float var3 = (float)p_77041_1_.getSlimeSize();
        final float var4 = (p_77041_1_.prevSquishFactor + (p_77041_1_.squishFactor - p_77041_1_.prevSquishFactor) * p_77041_2_) / (var3 * 0.5f + 1.0f);
        final float var5 = 1.0f / (var4 + 1.0f);
        GL11.glScalef(var5 * var3, 1.0f / var5 * var3, var5 * var3);
    }
    
    public ResourceLocation getEntityTexture(final EntitySlime p_110775_1_) {
        return RenderSlime.slimeTextures;
    }
    
    @Override
    public void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntitySlime)p_77041_1_, p_77041_2_);
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntitySlime)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntitySlime)p_110775_1_);
    }
    
    static {
        slimeTextures = new ResourceLocation("textures/entity/slime/slime.png");
    }
}
