package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderPig extends RenderLiving
{
    private static final ResourceLocation saddledPigTextures;
    private static final ResourceLocation pigTextures;
    private static final String __OBFID = "CL_00001019";
    
    public RenderPig(final ModelBase p_i1265_1_, final ModelBase p_i1265_2_, final float p_i1265_3_) {
        super(p_i1265_1_, p_i1265_3_);
        this.setRenderPassModel(p_i1265_2_);
    }
    
    public int shouldRenderPass(final EntityPig p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        if (p_77032_2_ == 0 && p_77032_1_.getSaddled()) {
            this.bindTexture(RenderPig.saddledPigTextures);
            return 1;
        }
        return -1;
    }
    
    public ResourceLocation getEntityTexture(final EntityPig p_110775_1_) {
        return RenderPig.pigTextures;
    }
    
    @Override
    public int shouldRenderPass(final EntityLivingBase p_77032_1_, final int p_77032_2_, final float p_77032_3_) {
        return this.shouldRenderPass((EntityPig)p_77032_1_, p_77032_2_, p_77032_3_);
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityPig)p_110775_1_);
    }
    
    static {
        saddledPigTextures = new ResourceLocation("textures/entity/pig/pig_saddle.png");
        pigTextures = new ResourceLocation("textures/entity/pig/pig.png");
    }
}
