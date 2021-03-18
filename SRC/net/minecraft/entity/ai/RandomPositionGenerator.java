package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;

public class RandomPositionGenerator
{
    private static Vec3 staticVector;
    private static final String __OBFID = "CL_00001629";
    
    public static Vec3 findRandomTarget(final EntityCreature p_75463_0_, final int p_75463_1_, final int p_75463_2_) {
        return findRandomTargetBlock(p_75463_0_, p_75463_1_, p_75463_2_, null);
    }
    
    public static Vec3 findRandomTargetBlockTowards(final EntityCreature p_75464_0_, final int p_75464_1_, final int p_75464_2_, final Vec3 p_75464_3_) {
        RandomPositionGenerator.staticVector.xCoord = p_75464_3_.xCoord - p_75464_0_.posX;
        RandomPositionGenerator.staticVector.yCoord = p_75464_3_.yCoord - p_75464_0_.posY;
        RandomPositionGenerator.staticVector.zCoord = p_75464_3_.zCoord - p_75464_0_.posZ;
        return findRandomTargetBlock(p_75464_0_, p_75464_1_, p_75464_2_, RandomPositionGenerator.staticVector);
    }
    
    public static Vec3 findRandomTargetBlockAwayFrom(final EntityCreature p_75461_0_, final int p_75461_1_, final int p_75461_2_, final Vec3 p_75461_3_) {
        RandomPositionGenerator.staticVector.xCoord = p_75461_0_.posX - p_75461_3_.xCoord;
        RandomPositionGenerator.staticVector.yCoord = p_75461_0_.posY - p_75461_3_.yCoord;
        RandomPositionGenerator.staticVector.zCoord = p_75461_0_.posZ - p_75461_3_.zCoord;
        return findRandomTargetBlock(p_75461_0_, p_75461_1_, p_75461_2_, RandomPositionGenerator.staticVector);
    }
    
    private static Vec3 findRandomTargetBlock(final EntityCreature p_75462_0_, final int p_75462_1_, final int p_75462_2_, final Vec3 p_75462_3_) {
        final Random var4 = p_75462_0_.getRNG();
        boolean var5 = false;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        float var9 = -99999.0f;
        boolean var12;
        if (p_75462_0_.hasHome()) {
            final double var10 = p_75462_0_.getHomePosition().getDistanceSquared(MathHelper.floor_double(p_75462_0_.posX), MathHelper.floor_double(p_75462_0_.posY), MathHelper.floor_double(p_75462_0_.posZ)) + 4.0f;
            final double var11 = p_75462_0_.func_110174_bM() + p_75462_1_;
            var12 = (var10 < var11 * var11);
        }
        else {
            var12 = false;
        }
        for (int var13 = 0; var13 < 10; ++var13) {
            int var14 = var4.nextInt(2 * p_75462_1_) - p_75462_1_;
            int var15 = var4.nextInt(2 * p_75462_2_) - p_75462_2_;
            int var16 = var4.nextInt(2 * p_75462_1_) - p_75462_1_;
            if (p_75462_3_ == null || var14 * p_75462_3_.xCoord + var16 * p_75462_3_.zCoord >= 0.0) {
                var14 += MathHelper.floor_double(p_75462_0_.posX);
                var15 += MathHelper.floor_double(p_75462_0_.posY);
                var16 += MathHelper.floor_double(p_75462_0_.posZ);
                if (!var12 || p_75462_0_.isWithinHomeDistance(var14, var15, var16)) {
                    final float var17 = p_75462_0_.getBlockPathWeight(var14, var15, var16);
                    if (var17 > var9) {
                        var9 = var17;
                        var6 = var14;
                        var7 = var15;
                        var8 = var16;
                        var5 = true;
                    }
                }
            }
        }
        if (var5) {
            return Vec3.createVectorHelper(var6, var7, var8);
        }
        return null;
    }
    
    static {
        RandomPositionGenerator.staticVector = Vec3.createVectorHelper(0.0, 0.0, 0.0);
    }
}
