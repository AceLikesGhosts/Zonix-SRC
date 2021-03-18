package net.minecraft.client.model;

import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class ModelMagmaCube extends ModelBase
{
    ModelRenderer[] field_78109_a;
    ModelRenderer field_78108_b;
    private static final String __OBFID = "CL_00000842";
    
    public ModelMagmaCube() {
        this.field_78109_a = new ModelRenderer[8];
        for (int var1 = 0; var1 < this.field_78109_a.length; ++var1) {
            byte var2 = 0;
            int var3;
            if ((var3 = var1) == 2) {
                var2 = 24;
                var3 = 10;
            }
            else if (var1 == 3) {
                var2 = 24;
                var3 = 19;
            }
            (this.field_78109_a[var1] = new ModelRenderer(this, var2, var3)).addBox(-4.0f, (float)(16 + var1), -4.0f, 8, 1, 8);
        }
        (this.field_78108_b = new ModelRenderer(this, 0, 16)).addBox(-2.0f, 18.0f, -2.0f, 4, 4, 4);
    }
    
    @Override
    public void setLivingAnimations(final EntityLivingBase p_78086_1_, final float p_78086_2_, final float p_78086_3_, final float p_78086_4_) {
        final EntityMagmaCube var5 = (EntityMagmaCube)p_78086_1_;
        float var6 = var5.prevSquishFactor + (var5.squishFactor - var5.prevSquishFactor) * p_78086_4_;
        if (var6 < 0.0f) {
            var6 = 0.0f;
        }
        for (int var7 = 0; var7 < this.field_78109_a.length; ++var7) {
            this.field_78109_a[var7].rotationPointY = -(4 - var7) * var6 * 1.7f;
        }
    }
    
    @Override
    public void render(final Entity p_78088_1_, final float p_78088_2_, final float p_78088_3_, final float p_78088_4_, final float p_78088_5_, final float p_78088_6_, final float p_78088_7_) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.field_78108_b.render(p_78088_7_);
        for (int var8 = 0; var8 < this.field_78109_a.length; ++var8) {
            this.field_78109_a[var8].render(p_78088_7_);
        }
    }
}
