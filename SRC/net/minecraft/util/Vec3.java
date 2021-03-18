package net.minecraft.util;

public class Vec3
{
    public double xCoord;
    public double yCoord;
    public double zCoord;
    private static final String __OBFID = "CL_00000612";
    
    public static Vec3 createVectorHelper(final double p_72443_0_, final double p_72443_2_, final double p_72443_4_) {
        return new Vec3(p_72443_0_, p_72443_2_, p_72443_4_);
    }
    
    protected Vec3(double p_i1108_1_, double p_i1108_3_, double p_i1108_5_) {
        if (p_i1108_1_ == -0.0) {
            p_i1108_1_ = 0.0;
        }
        if (p_i1108_3_ == -0.0) {
            p_i1108_3_ = 0.0;
        }
        if (p_i1108_5_ == -0.0) {
            p_i1108_5_ = 0.0;
        }
        this.xCoord = p_i1108_1_;
        this.yCoord = p_i1108_3_;
        this.zCoord = p_i1108_5_;
    }
    
    protected Vec3 setComponents(final double p_72439_1_, final double p_72439_3_, final double p_72439_5_) {
        this.xCoord = p_72439_1_;
        this.yCoord = p_72439_3_;
        this.zCoord = p_72439_5_;
        return this;
    }
    
    public Vec3 subtract(final Vec3 p_72444_1_) {
        return createVectorHelper(p_72444_1_.xCoord - this.xCoord, p_72444_1_.yCoord - this.yCoord, p_72444_1_.zCoord - this.zCoord);
    }
    
    public Vec3 normalize() {
        final double var1 = MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
        return (var1 < 1.0E-4) ? createVectorHelper(0.0, 0.0, 0.0) : createVectorHelper(this.xCoord / var1, this.yCoord / var1, this.zCoord / var1);
    }
    
    public double dotProduct(final Vec3 p_72430_1_) {
        return this.xCoord * p_72430_1_.xCoord + this.yCoord * p_72430_1_.yCoord + this.zCoord * p_72430_1_.zCoord;
    }
    
    public Vec3 crossProduct(final Vec3 p_72431_1_) {
        return createVectorHelper(this.yCoord * p_72431_1_.zCoord - this.zCoord * p_72431_1_.yCoord, this.zCoord * p_72431_1_.xCoord - this.xCoord * p_72431_1_.zCoord, this.xCoord * p_72431_1_.yCoord - this.yCoord * p_72431_1_.xCoord);
    }
    
    public Vec3 addVector(final double p_72441_1_, final double p_72441_3_, final double p_72441_5_) {
        return createVectorHelper(this.xCoord + p_72441_1_, this.yCoord + p_72441_3_, this.zCoord + p_72441_5_);
    }
    
    public double distanceTo(final Vec3 p_72438_1_) {
        final double var2 = p_72438_1_.xCoord - this.xCoord;
        final double var3 = p_72438_1_.yCoord - this.yCoord;
        final double var4 = p_72438_1_.zCoord - this.zCoord;
        return MathHelper.sqrt_double(var2 * var2 + var3 * var3 + var4 * var4);
    }
    
    public double squareDistanceTo(final Vec3 p_72436_1_) {
        final double var2 = p_72436_1_.xCoord - this.xCoord;
        final double var3 = p_72436_1_.yCoord - this.yCoord;
        final double var4 = p_72436_1_.zCoord - this.zCoord;
        return var2 * var2 + var3 * var3 + var4 * var4;
    }
    
    public double squareDistanceTo(final double p_72445_1_, final double p_72445_3_, final double p_72445_5_) {
        final double var7 = p_72445_1_ - this.xCoord;
        final double var8 = p_72445_3_ - this.yCoord;
        final double var9 = p_72445_5_ - this.zCoord;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public double lengthVector() {
        return MathHelper.sqrt_double(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
    }
    
    public Vec3 getIntermediateWithXValue(final Vec3 p_72429_1_, final double p_72429_2_) {
        final double var4 = p_72429_1_.xCoord - this.xCoord;
        final double var5 = p_72429_1_.yCoord - this.yCoord;
        final double var6 = p_72429_1_.zCoord - this.zCoord;
        if (var4 * var4 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (p_72429_2_ - this.xCoord) / var4;
        return (var7 >= 0.0 && var7 <= 1.0) ? createVectorHelper(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    public Vec3 getIntermediateWithYValue(final Vec3 p_72435_1_, final double p_72435_2_) {
        final double var4 = p_72435_1_.xCoord - this.xCoord;
        final double var5 = p_72435_1_.yCoord - this.yCoord;
        final double var6 = p_72435_1_.zCoord - this.zCoord;
        if (var5 * var5 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (p_72435_2_ - this.yCoord) / var5;
        return (var7 >= 0.0 && var7 <= 1.0) ? createVectorHelper(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    public Vec3 getIntermediateWithZValue(final Vec3 p_72434_1_, final double p_72434_2_) {
        final double var4 = p_72434_1_.xCoord - this.xCoord;
        final double var5 = p_72434_1_.yCoord - this.yCoord;
        final double var6 = p_72434_1_.zCoord - this.zCoord;
        if (var6 * var6 < 1.0000000116860974E-7) {
            return null;
        }
        final double var7 = (p_72434_2_ - this.zCoord) / var6;
        return (var7 >= 0.0 && var7 <= 1.0) ? createVectorHelper(this.xCoord + var4 * var7, this.yCoord + var5 * var7, this.zCoord + var6 * var7) : null;
    }
    
    @Override
    public String toString() {
        return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
    }
    
    public void rotateAroundX(final float p_72440_1_) {
        final float var2 = MathHelper.cos(p_72440_1_);
        final float var3 = MathHelper.sin(p_72440_1_);
        final double var4 = this.xCoord;
        final double var5 = this.yCoord * var2 + this.zCoord * var3;
        final double var6 = this.zCoord * var2 - this.yCoord * var3;
        this.setComponents(var4, var5, var6);
    }
    
    public void rotateAroundY(final float p_72442_1_) {
        final float var2 = MathHelper.cos(p_72442_1_);
        final float var3 = MathHelper.sin(p_72442_1_);
        final double var4 = this.xCoord * var2 + this.zCoord * var3;
        final double var5 = this.yCoord;
        final double var6 = this.zCoord * var2 - this.xCoord * var3;
        this.setComponents(var4, var5, var6);
    }
    
    public void rotateAroundZ(final float p_72446_1_) {
        final float var2 = MathHelper.cos(p_72446_1_);
        final float var3 = MathHelper.sin(p_72446_1_);
        final double var4 = this.xCoord * var2 + this.yCoord * var3;
        final double var5 = this.yCoord * var2 - this.xCoord * var3;
        final double var6 = this.zCoord;
        this.setComponents(var4, var5, var6);
    }
}
