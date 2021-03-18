package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderCow extends RenderLiving
{
    private static final ResourceLocation cowTextures;
    private static final String __OBFID = "CL_00000984";
    
    public RenderCow(final ModelBase p_i1253_1_, final float p_i1253_2_) {
        super(p_i1253_1_, p_i1253_2_);
    }
    
    public ResourceLocation getEntityTexture(final EntityCow p_110775_1_) {
        return RenderCow.cowTextures;
    }
    
    @Override
    public ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityCow)p_110775_1_);
    }
    
    static {
        cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    }
}
