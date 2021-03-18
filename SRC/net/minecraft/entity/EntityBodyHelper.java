package net.minecraft.entity;

import net.minecraft.util.*;

public class EntityBodyHelper
{
    private EntityLivingBase theLiving;
    private int field_75666_b;
    private float field_75667_c;
    private static final String __OBFID = "CL_00001570";
    
    public EntityBodyHelper(final EntityLivingBase p_i1611_1_) {
        this.theLiving = p_i1611_1_;
    }
    
    public void func_75664_a() {
        final double var1 = this.theLiving.posX - this.theLiving.prevPosX;
        final double var2 = this.theLiving.posZ - this.theLiving.prevPosZ;
        if (var1 * var1 + var2 * var2 > 2.500000277905201E-7) {
            this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
            this.theLiving.rotationYawHead = this.func_75665_a(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0f);
            this.field_75667_c = this.theLiving.rotationYawHead;
            this.field_75666_b = 0;
        }
        else {
            float var3 = 75.0f;
            if (Math.abs(this.theLiving.rotationYawHead - this.field_75667_c) > 15.0f) {
                this.field_75666_b = 0;
                this.field_75667_c = this.theLiving.rotationYawHead;
            }
            else {
                ++this.field_75666_b;
                final boolean var4 = true;
                if (this.field_75666_b > 10) {
                    var3 = Math.max(1.0f - (this.field_75666_b - 10) / 10.0f, 0.0f) * 75.0f;
                }
            }
            this.theLiving.renderYawOffset = this.func_75665_a(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, var3);
        }
    }
    
    private float func_75665_a(final float p_75665_1_, final float p_75665_2_, final float p_75665_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_75665_1_ - p_75665_2_);
        if (var4 < -p_75665_3_) {
            var4 = -p_75665_3_;
        }
        if (var4 >= p_75665_3_) {
            var4 = p_75665_3_;
        }
        return p_75665_1_ - var4;
    }
}
