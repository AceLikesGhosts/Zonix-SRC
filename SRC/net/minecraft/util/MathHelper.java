package net.minecraft.util;

import us.zonix.client.module.impl.*;
import java.util.*;

public class MathHelper
{
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    public static final float PId2 = 1.5707964f;
    private static final float radFull = 6.2831855f;
    private static final float degFull = 360.0f;
    private static final float radToIndex = 651.8986f;
    private static final float degToIndex = 11.377778f;
    public static final float deg2Rad = 0.017453292f;
    private static final float[] SIN_TABLE_FAST;
    private static float[] SIN_TABLE;
    private static final int[] multiplyDeBruijnBitPosition;
    private static final String __OBFID = "CL_00001496";
    
    public static final float sin(final float par0) {
        return (FPSBoost.SMART_PERFORMANCE.getValue() > 20.0f) ? MathHelper.SIN_TABLE_FAST[(int)(par0 * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(par0 * 10430.378f) & 0xFFFF];
    }
    
    public static final float cos(final float par0) {
        return (FPSBoost.SMART_PERFORMANCE.getValue() >= 20.0f) ? MathHelper.SIN_TABLE_FAST[(int)((par0 + 1.5707964f) * 651.8986f) & 0xFFF] : MathHelper.SIN_TABLE[(int)(par0 * 10430.378f + 16384.0f) & 0xFFFF];
    }
    
    public static final float sqrt_float(final float par0) {
        return (float)Math.sqrt(par0);
    }
    
    public static final float sqrt_double(final double par0) {
        return (float)Math.sqrt(par0);
    }
    
    public static int floor_float(final float par0) {
        final int var1 = (int)par0;
        return (par0 < var1) ? (var1 - 1) : var1;
    }
    
    public static int truncateDoubleToInt(final double par0) {
        return (int)(par0 + 1024.0) - 1024;
    }
    
    public static int floor_double(final double par0) {
        final int var2 = (int)par0;
        return (par0 < var2) ? (var2 - 1) : var2;
    }
    
    public static long floor_double_long(final double par0) {
        final long var2 = (long)par0;
        return (par0 < var2) ? (var2 - 1L) : var2;
    }
    
    public static int func_154353_e(final double p_154353_0_) {
        return (int)((p_154353_0_ >= 0.0) ? p_154353_0_ : (-p_154353_0_ + 1.0));
    }
    
    public static float abs(final float par0) {
        return (par0 >= 0.0f) ? par0 : (-par0);
    }
    
    public static int abs_int(final int par0) {
        return (par0 >= 0) ? par0 : (-par0);
    }
    
    public static int ceiling_float_int(final float par0) {
        final int var1 = (int)par0;
        return (par0 > var1) ? (var1 + 1) : var1;
    }
    
    public static int ceiling_double_int(final double par0) {
        final int var2 = (int)par0;
        return (par0 > var2) ? (var2 + 1) : var2;
    }
    
    public static int clamp_int(final int par0, final int par1, final int par2) {
        return (par0 < par1) ? par1 : ((par0 > par2) ? par2 : par0);
    }
    
    public static float clamp_float(final float par0, final float par1, final float par2) {
        return (par0 < par1) ? par1 : ((par0 > par2) ? par2 : par0);
    }
    
    public static double clamp_double(final double p_151237_0_, final double p_151237_2_, final double p_151237_4_) {
        return (p_151237_0_ < p_151237_2_) ? p_151237_2_ : ((p_151237_0_ > p_151237_4_) ? p_151237_4_ : p_151237_0_);
    }
    
    public static double denormalizeClamp(final double p_151238_0_, final double p_151238_2_, final double p_151238_4_) {
        return (p_151238_4_ < 0.0) ? p_151238_0_ : ((p_151238_4_ > 1.0) ? p_151238_2_ : (p_151238_0_ + (p_151238_2_ - p_151238_0_) * p_151238_4_));
    }
    
    public static double abs_max(double par0, double par2) {
        if (par0 < 0.0) {
            par0 = -par0;
        }
        if (par2 < 0.0) {
            par2 = -par2;
        }
        return (par0 > par2) ? par0 : par2;
    }
    
    public static int bucketInt(final int par0, final int par1) {
        return (par0 < 0) ? (-((-par0 - 1) / par1) - 1) : (par0 / par1);
    }
    
    public static boolean stringNullOrLengthZero(final String par0Str) {
        return par0Str == null || par0Str.length() == 0;
    }
    
    public static int getRandomIntegerInRange(final Random par0Random, final int par1, final int par2) {
        return (par1 >= par2) ? par1 : (par0Random.nextInt(par2 - par1 + 1) + par1);
    }
    
    public static float randomFloatClamp(final Random p_151240_0_, final float p_151240_1_, final float p_151240_2_) {
        return (p_151240_1_ >= p_151240_2_) ? p_151240_1_ : (p_151240_0_.nextFloat() * (p_151240_2_ - p_151240_1_) + p_151240_1_);
    }
    
    public static double getRandomDoubleInRange(final Random par0Random, final double par1, final double par3) {
        return (par1 >= par3) ? par1 : (par0Random.nextDouble() * (par3 - par1) + par1);
    }
    
    public static double average(final long[] par0ArrayOfLong) {
        long var1 = 0L;
        final long[] var2 = par0ArrayOfLong;
        for (int var3 = par0ArrayOfLong.length, var4 = 0; var4 < var3; ++var4) {
            final long var5 = var2[var4];
            var1 += var5;
        }
        return var1 / (double)par0ArrayOfLong.length;
    }
    
    public static float wrapAngleTo180_float(float par0) {
        par0 %= 360.0f;
        if (par0 >= 180.0f) {
            par0 -= 360.0f;
        }
        if (par0 < -180.0f) {
            par0 += 360.0f;
        }
        return par0;
    }
    
    public static double wrapAngleTo180_double(double par0) {
        par0 %= 360.0;
        if (par0 >= 180.0) {
            par0 -= 360.0;
        }
        if (par0 < -180.0) {
            par0 += 360.0;
        }
        return par0;
    }
    
    public static int parseIntWithDefault(final String par0Str, final int par1) {
        int var2 = par1;
        try {
            var2 = Integer.parseInt(par0Str);
        }
        catch (Throwable t) {}
        return var2;
    }
    
    public static int parseIntWithDefaultAndMax(final String par0Str, final int par1, final int par2) {
        int var3 = par1;
        try {
            var3 = Integer.parseInt(par0Str);
        }
        catch (Throwable t) {}
        if (var3 < par2) {
            var3 = par2;
        }
        return var3;
    }
    
    public static double parseDoubleWithDefault(final String par0Str, final double par1) {
        double var3 = par1;
        try {
            var3 = Double.parseDouble(par0Str);
        }
        catch (Throwable t) {}
        return var3;
    }
    
    public static double parseDoubleWithDefaultAndMax(final String par0Str, final double par1, final double par3) {
        double var5 = par1;
        try {
            var5 = Double.parseDouble(par0Str);
        }
        catch (Throwable t) {}
        if (var5 < par3) {
            var5 = par3;
        }
        return var5;
    }
    
    public static int roundUpToPowerOfTwo(final int p_151236_0_) {
        int var1 = p_151236_0_ - 1;
        var1 |= var1 >> 1;
        var1 |= var1 >> 2;
        var1 |= var1 >> 4;
        var1 |= var1 >> 8;
        var1 |= var1 >> 16;
        return var1 + 1;
    }
    
    private static boolean isPowerOfTwo(final int p_151235_0_) {
        return p_151235_0_ != 0 && (p_151235_0_ & p_151235_0_ - 1) == 0x0;
    }
    
    private static int calculateLogBaseTwoDeBruijn(int p_151241_0_) {
        p_151241_0_ = (isPowerOfTwo(p_151241_0_) ? p_151241_0_ : roundUpToPowerOfTwo(p_151241_0_));
        return MathHelper.multiplyDeBruijnBitPosition[(int)(p_151241_0_ * 125613361L >> 27) & 0x1F];
    }
    
    public static int calculateLogBaseTwo(final int p_151239_0_) {
        return calculateLogBaseTwoDeBruijn(p_151239_0_) - (isPowerOfTwo(p_151239_0_) ? 0 : 1);
    }
    
    public static int func_154354_b(final int p_154354_0_, int p_154354_1_) {
        if (p_154354_1_ == 0) {
            return 0;
        }
        if (p_154354_0_ < 0) {
            p_154354_1_ *= -1;
        }
        final int var2 = p_154354_0_ % p_154354_1_;
        return (var2 == 0) ? p_154354_0_ : (p_154354_0_ + p_154354_1_ - var2);
    }
    
    static {
        SIN_TABLE_FAST = new float[4096];
        MathHelper.SIN_TABLE = new float[65536];
        for (int i = 0; i < 65536; ++i) {
            MathHelper.SIN_TABLE[i] = (float)Math.sin(i * 3.141592653589793 * 2.0 / 65536.0);
        }
        multiplyDeBruijnBitPosition = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
        for (int i = 0; i < 4096; ++i) {
            MathHelper.SIN_TABLE_FAST[i] = (float)Math.sin((i + 0.5f) / 4096.0f * 6.2831855f);
        }
        for (int i = 0; i < 360; i += 90) {
            MathHelper.SIN_TABLE_FAST[(int)(i * 11.377778f) & 0xFFF] = (float)Math.sin(i * 0.017453292f);
        }
    }
}
