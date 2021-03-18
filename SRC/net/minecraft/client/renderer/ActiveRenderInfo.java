package net.minecraft.client.renderer;

import java.nio.*;
import net.minecraft.entity.player.*;
import org.lwjgl.opengl.*;
import org.lwjgl.util.glu.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.*;

public class ActiveRenderInfo
{
    public static float objectX;
    public static float objectY;
    public static float objectZ;
    private static IntBuffer viewport;
    private static FloatBuffer modelview;
    private static FloatBuffer projection;
    private static FloatBuffer objectCoords;
    public static float rotationX;
    public static float rotationXZ;
    public static float rotationZ;
    public static float rotationYZ;
    public static float rotationXY;
    private static final String __OBFID = "CL_00000626";
    
    public static void updateRenderInfo(final EntityPlayer p_74583_0_, final boolean p_74583_1_) {
        GL11.glGetFloat(2982, ActiveRenderInfo.modelview);
        GL11.glGetFloat(2983, ActiveRenderInfo.projection);
        GL11.glGetInteger(2978, ActiveRenderInfo.viewport);
        final float var2 = (float)((ActiveRenderInfo.viewport.get(0) + ActiveRenderInfo.viewport.get(2)) / 2);
        final float var3 = (float)((ActiveRenderInfo.viewport.get(1) + ActiveRenderInfo.viewport.get(3)) / 2);
        GLU.gluUnProject(var2, var3, 0.0f, ActiveRenderInfo.modelview, ActiveRenderInfo.projection, ActiveRenderInfo.viewport, ActiveRenderInfo.objectCoords);
        ActiveRenderInfo.objectX = ActiveRenderInfo.objectCoords.get(0);
        ActiveRenderInfo.objectY = ActiveRenderInfo.objectCoords.get(1);
        ActiveRenderInfo.objectZ = ActiveRenderInfo.objectCoords.get(2);
        final int var4 = p_74583_1_ ? 1 : 0;
        final float var5 = p_74583_0_.rotationPitch;
        final float var6 = p_74583_0_.rotationYaw;
        ActiveRenderInfo.rotationX = MathHelper.cos(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationZ = MathHelper.sin(var6 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationYZ = -ActiveRenderInfo.rotationZ * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationXY = ActiveRenderInfo.rotationX * MathHelper.sin(var5 * 3.1415927f / 180.0f) * (1 - var4 * 2);
        ActiveRenderInfo.rotationXZ = MathHelper.cos(var5 * 3.1415927f / 180.0f);
    }
    
    public static Vec3 projectViewFromEntity(final EntityLivingBase p_74585_0_, final double p_74585_1_) {
        final double var3 = p_74585_0_.prevPosX + (p_74585_0_.posX - p_74585_0_.prevPosX) * p_74585_1_;
        final double var4 = p_74585_0_.prevPosY + (p_74585_0_.posY - p_74585_0_.prevPosY) * p_74585_1_ + p_74585_0_.getEyeHeight();
        final double var5 = p_74585_0_.prevPosZ + (p_74585_0_.posZ - p_74585_0_.prevPosZ) * p_74585_1_;
        final double var6 = var3 + ActiveRenderInfo.objectX * 1.0f;
        final double var7 = var4 + ActiveRenderInfo.objectY * 1.0f;
        final double var8 = var5 + ActiveRenderInfo.objectZ * 1.0f;
        return Vec3.createVectorHelper(var6, var7, var8);
    }
    
    public static Block getBlockAtEntityViewpoint(final World p_151460_0_, final EntityLivingBase p_151460_1_, final float p_151460_2_) {
        final Vec3 var3 = projectViewFromEntity(p_151460_1_, p_151460_2_);
        final ChunkPosition var4 = new ChunkPosition(var3);
        Block var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c);
        if (var5.getMaterial().isLiquid()) {
            final float var6 = BlockLiquid.func_149801_b(p_151460_0_.getBlockMetadata(var4.field_151329_a, var4.field_151327_b, var4.field_151328_c)) - 0.11111111f;
            final float var7 = var4.field_151327_b + 1 - var6;
            if (var3.yCoord >= var7) {
                var5 = p_151460_0_.getBlock(var4.field_151329_a, var4.field_151327_b + 1, var4.field_151328_c);
            }
        }
        return var5;
    }
    
    static {
        ActiveRenderInfo.viewport = GLAllocation.createDirectIntBuffer(16);
        ActiveRenderInfo.modelview = GLAllocation.createDirectFloatBuffer(16);
        ActiveRenderInfo.projection = GLAllocation.createDirectFloatBuffer(16);
        ActiveRenderInfo.objectCoords = GLAllocation.createDirectFloatBuffer(3);
    }
}
