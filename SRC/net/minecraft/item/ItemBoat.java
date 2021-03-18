package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.util.*;

public class ItemBoat extends Item
{
    private static final String __OBFID = "CL_00001774";
    
    public ItemBoat() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final float var4 = 1.0f;
        final float var5 = p_77659_3_.prevRotationPitch + (p_77659_3_.rotationPitch - p_77659_3_.prevRotationPitch) * var4;
        final float var6 = p_77659_3_.prevRotationYaw + (p_77659_3_.rotationYaw - p_77659_3_.prevRotationYaw) * var4;
        final double var7 = p_77659_3_.prevPosX + (p_77659_3_.posX - p_77659_3_.prevPosX) * var4;
        final double var8 = p_77659_3_.prevPosY + (p_77659_3_.posY - p_77659_3_.prevPosY) * var4 + 1.62 - p_77659_3_.yOffset;
        final double var9 = p_77659_3_.prevPosZ + (p_77659_3_.posZ - p_77659_3_.prevPosZ) * var4;
        final Vec3 var10 = Vec3.createVectorHelper(var7, var8, var9);
        final float var11 = MathHelper.cos(-var6 * 0.017453292f - 3.1415927f);
        final float var12 = MathHelper.sin(-var6 * 0.017453292f - 3.1415927f);
        final float var13 = -MathHelper.cos(-var5 * 0.017453292f);
        final float var14 = MathHelper.sin(-var5 * 0.017453292f);
        final float var15 = var12 * var13;
        final float var16 = var11 * var13;
        final double var17 = 5.0;
        final Vec3 var18 = var10.addVector(var15 * var17, var14 * var17, var16 * var17);
        final MovingObjectPosition var19 = p_77659_2_.rayTraceBlocks(var10, var18, true);
        if (var19 == null) {
            return p_77659_1_;
        }
        final Vec3 var20 = p_77659_3_.getLook(var4);
        boolean var21 = false;
        final float var22 = 1.0f;
        final List var23 = p_77659_2_.getEntitiesWithinAABBExcludingEntity(p_77659_3_, p_77659_3_.boundingBox.addCoord(var20.xCoord * var17, var20.yCoord * var17, var20.zCoord * var17).expand(var22, var22, var22));
        for (int var24 = 0; var24 < var23.size(); ++var24) {
            final Entity var25 = var23.get(var24);
            if (var25.canBeCollidedWith()) {
                final float var26 = var25.getCollisionBorderSize();
                final AxisAlignedBB var27 = var25.boundingBox.expand(var26, var26, var26);
                if (var27.isVecInside(var10)) {
                    var21 = true;
                }
            }
        }
        if (var21) {
            return p_77659_1_;
        }
        if (var19.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final int var24 = var19.blockX;
            int var28 = var19.blockY;
            final int var29 = var19.blockZ;
            if (p_77659_2_.getBlock(var24, var28, var29) == Blocks.snow_layer) {
                --var28;
            }
            final EntityBoat var30 = new EntityBoat(p_77659_2_, var24 + 0.5f, var28 + 1.0f, var29 + 0.5f);
            var30.rotationYaw = (float)(((MathHelper.floor_double(p_77659_3_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) - 1) * 90);
            if (!p_77659_2_.getCollidingBoundingBoxes(var30, var30.boundingBox.expand(-0.1, -0.1, -0.1)).isEmpty()) {
                return p_77659_1_;
            }
            if (!p_77659_2_.isClient) {
                p_77659_2_.spawnEntityInWorld(var30);
            }
            if (!p_77659_3_.capabilities.isCreativeMode) {
                --p_77659_1_.stackSize;
            }
        }
        return p_77659_1_;
    }
}
