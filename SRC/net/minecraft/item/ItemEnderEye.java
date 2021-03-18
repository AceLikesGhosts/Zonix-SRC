package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;

public class ItemEnderEye extends Item
{
    private static final String __OBFID = "CL_00000026";
    
    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        final int var12 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) || var11 != Blocks.end_portal_frame || BlockEndPortalFrame.func_150020_b(var12)) {
            return false;
        }
        if (p_77648_3_.isClient) {
            return true;
        }
        p_77648_3_.setBlockMetadataWithNotify(p_77648_4_, p_77648_5_, p_77648_6_, var12 + 4, 2);
        p_77648_3_.func_147453_f(p_77648_4_, p_77648_5_, p_77648_6_, Blocks.end_portal_frame);
        --p_77648_1_.stackSize;
        for (int var13 = 0; var13 < 16; ++var13) {
            final double var14 = p_77648_4_ + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f;
            final double var15 = p_77648_5_ + 0.8125f;
            final double var16 = p_77648_6_ + (5.0f + ItemEnderEye.itemRand.nextFloat() * 6.0f) / 16.0f;
            final double var17 = 0.0;
            final double var18 = 0.0;
            final double var19 = 0.0;
            p_77648_3_.spawnParticle("smoke", var14, var15, var16, var17, var18, var19);
        }
        int var13 = var12 & 0x3;
        int var20 = 0;
        int var21 = 0;
        boolean var22 = false;
        boolean var23 = true;
        final int var24 = Direction.rotateRight[var13];
        for (int var25 = -2; var25 <= 2; ++var25) {
            final int var26 = p_77648_4_ + Direction.offsetX[var24] * var25;
            final int var27 = p_77648_6_ + Direction.offsetZ[var24] * var25;
            if (p_77648_3_.getBlock(var26, p_77648_5_, var27) == Blocks.end_portal_frame) {
                if (!BlockEndPortalFrame.func_150020_b(p_77648_3_.getBlockMetadata(var26, p_77648_5_, var27))) {
                    var23 = false;
                    break;
                }
                var21 = var25;
                if (!var22) {
                    var20 = var25;
                    var22 = true;
                }
            }
        }
        if (var23 && var21 == var20 + 2) {
            for (int var25 = var20; var25 <= var21; ++var25) {
                int var26 = p_77648_4_ + Direction.offsetX[var24] * var25;
                int var27 = p_77648_6_ + Direction.offsetZ[var24] * var25;
                var26 += Direction.offsetX[var13] * 4;
                var27 += Direction.offsetZ[var13] * 4;
                if (p_77648_3_.getBlock(var26, p_77648_5_, var27) != Blocks.end_portal_frame || !BlockEndPortalFrame.func_150020_b(p_77648_3_.getBlockMetadata(var26, p_77648_5_, var27))) {
                    var23 = false;
                    break;
                }
            }
            for (int var25 = var20 - 1; var25 <= var21 + 1; var25 += 4) {
                for (int var26 = 1; var26 <= 3; ++var26) {
                    int var27 = p_77648_4_ + Direction.offsetX[var24] * var25;
                    int var28 = p_77648_6_ + Direction.offsetZ[var24] * var25;
                    var27 += Direction.offsetX[var13] * var26;
                    var28 += Direction.offsetZ[var13] * var26;
                    if (p_77648_3_.getBlock(var27, p_77648_5_, var28) != Blocks.end_portal_frame || !BlockEndPortalFrame.func_150020_b(p_77648_3_.getBlockMetadata(var27, p_77648_5_, var28))) {
                        var23 = false;
                        break;
                    }
                }
            }
            if (var23) {
                for (int var25 = var20; var25 <= var21; ++var25) {
                    for (int var26 = 1; var26 <= 3; ++var26) {
                        int var27 = p_77648_4_ + Direction.offsetX[var24] * var25;
                        int var28 = p_77648_6_ + Direction.offsetZ[var24] * var25;
                        var27 += Direction.offsetX[var13] * var26;
                        var28 += Direction.offsetZ[var13] * var26;
                        p_77648_3_.setBlock(var27, p_77648_5_, var28, Blocks.end_portal, 0, 2);
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, false);
        if (var4 != null && var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && p_77659_2_.getBlock(var4.blockX, var4.blockY, var4.blockZ) == Blocks.end_portal_frame) {
            return p_77659_1_;
        }
        if (!p_77659_2_.isClient) {
            final ChunkPosition var5 = p_77659_2_.findClosestStructure("Stronghold", (int)p_77659_3_.posX, (int)p_77659_3_.posY, (int)p_77659_3_.posZ);
            if (var5 != null) {
                final EntityEnderEye var6 = new EntityEnderEye(p_77659_2_, p_77659_3_.posX, p_77659_3_.posY + 1.62 - p_77659_3_.yOffset, p_77659_3_.posZ);
                var6.moveTowards(var5.field_151329_a, var5.field_151327_b, var5.field_151328_c);
                p_77659_2_.spawnEntityInWorld(var6);
                p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5f, 0.4f / (ItemEnderEye.itemRand.nextFloat() * 0.4f + 0.8f));
                p_77659_2_.playAuxSFXAtEntity(null, 1002, (int)p_77659_3_.posX, (int)p_77659_3_.posY, (int)p_77659_3_.posZ, 0);
                if (!p_77659_3_.capabilities.isCreativeMode) {
                    --p_77659_1_.stackSize;
                }
            }
        }
        return p_77659_1_;
    }
}
