package net.minecraft.client.renderer;

import net.minecraft.util.*;

public class IconFlipped implements IIcon
{
    private final IIcon baseIcon;
    private final boolean flipU;
    private final boolean flipV;
    private static final String __OBFID = "CL_00001511";
    
    public IconFlipped(final IIcon p_i1560_1_, final boolean p_i1560_2_, final boolean p_i1560_3_) {
        this.baseIcon = p_i1560_1_;
        this.flipU = p_i1560_2_;
        this.flipV = p_i1560_3_;
    }
    
    @Override
    public int getIconWidth() {
        return this.baseIcon.getIconWidth();
    }
    
    @Override
    public int getIconHeight() {
        return this.baseIcon.getIconHeight();
    }
    
    @Override
    public float getMinU() {
        return this.flipU ? this.baseIcon.getMaxU() : this.baseIcon.getMinU();
    }
    
    @Override
    public float getMaxU() {
        return this.flipU ? this.baseIcon.getMinU() : this.baseIcon.getMaxU();
    }
    
    @Override
    public float getInterpolatedU(final double p_94214_1_) {
        final float var3 = this.getMaxU() - this.getMinU();
        return this.getMinU() + var3 * ((float)p_94214_1_ / 16.0f);
    }
    
    @Override
    public float getMinV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMinV();
    }
    
    @Override
    public float getMaxV() {
        return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMaxV();
    }
    
    @Override
    public float getInterpolatedV(final double p_94207_1_) {
        final float var3 = this.getMaxV() - this.getMinV();
        return this.getMinV() + var3 * ((float)p_94207_1_ / 16.0f);
    }
    
    @Override
    public String getIconName() {
        return this.baseIcon.getIconName();
    }
}
