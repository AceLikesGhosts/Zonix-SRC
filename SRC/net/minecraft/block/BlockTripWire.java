package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;

public class BlockTripWire extends Block
{
    private static final String __OBFID = "CL_00000328";
    
    public BlockTripWire() {
        super(Material.circuits);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.15625f, 1.0f);
        this.setTickRandomly(true);
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 10;
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public int getRenderBlockPass() {
        return 1;
    }
    
    @Override
    public int getRenderType() {
        return 30;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.string;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.string;
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        final boolean var7 = (var6 & 0x2) == 0x2;
        final boolean var8 = !World.doesBlockHaveSolidTopSurface(p_149695_1_, p_149695_2_, p_149695_3_ - 1, p_149695_4_);
        if (var7 != var8) {
            this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, var6, 0);
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        final boolean var6 = (var5 & 0x4) == 0x4;
        final boolean var7 = (var5 & 0x2) == 0x2;
        if (!var7) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.09375f, 1.0f);
        }
        else if (!var6) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0625f, 0.0f, 1.0f, 0.15625f, 1.0f);
        }
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        final int var5 = World.doesBlockHaveSolidTopSurface(p_149726_1_, p_149726_2_, p_149726_3_ - 1, p_149726_4_) ? 0 : 2;
        p_149726_1_.setBlockMetadataWithNotify(p_149726_2_, p_149726_3_, p_149726_4_, var5, 3);
        this.func_150138_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, var5);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        this.func_150138_a(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_ | 0x1);
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (!p_149681_1_.isClient && p_149681_6_.getCurrentEquippedItem() != null && p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149681_1_.setBlockMetadataWithNotify(p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_ | 0x8, 4);
        }
    }
    
    private void func_150138_a(final World p_150138_1_, final int p_150138_2_, final int p_150138_3_, final int p_150138_4_, final int p_150138_5_) {
        for (int var6 = 0; var6 < 2; ++var6) {
            for (int var7 = 1; var7 < 42; ++var7) {
                final int var8 = p_150138_2_ + Direction.offsetX[var6] * var7;
                final int var9 = p_150138_4_ + Direction.offsetZ[var6] * var7;
                final Block var10 = p_150138_1_.getBlock(var8, p_150138_3_, var9);
                if (var10 == Blocks.tripwire_hook) {
                    final int var11 = p_150138_1_.getBlockMetadata(var8, p_150138_3_, var9) & 0x3;
                    if (var11 == Direction.rotateOpposite[var6]) {
                        Blocks.tripwire_hook.func_150136_a(p_150138_1_, var8, p_150138_3_, var9, false, p_150138_1_.getBlockMetadata(var8, p_150138_3_, var9), true, var7, p_150138_5_);
                    }
                    break;
                }
                if (var10 != Blocks.tripwire) {
                    break;
                }
            }
        }
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (!p_149670_1_.isClient && (p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_) & 0x1) != 0x1) {
            this.func_150140_e(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient && (p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_) & 0x1) == 0x1) {
            this.func_150140_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }
    
    private void func_150140_e(final World p_150140_1_, final int p_150140_2_, final int p_150140_3_, final int p_150140_4_) {
        int var5 = p_150140_1_.getBlockMetadata(p_150140_2_, p_150140_3_, p_150140_4_);
        final boolean var6 = (var5 & 0x1) == 0x1;
        boolean var7 = false;
        final List var8 = p_150140_1_.getEntitiesWithinAABBExcludingEntity(null, AxisAlignedBB.getBoundingBox(p_150140_2_ + this.field_149759_B, p_150140_3_ + this.field_149760_C, p_150140_4_ + this.field_149754_D, p_150140_2_ + this.field_149755_E, p_150140_3_ + this.field_149756_F, p_150140_4_ + this.field_149757_G));
        if (!var8.isEmpty()) {
            for (final Entity var10 : var8) {
                if (!var10.doesEntityNotTriggerPressurePlate()) {
                    var7 = true;
                    break;
                }
            }
        }
        if (var7 && !var6) {
            var5 |= 0x1;
        }
        if (!var7 && var6) {
            var5 &= 0xFFFFFFFE;
        }
        if (var7 != var6) {
            p_150140_1_.setBlockMetadataWithNotify(p_150140_2_, p_150140_3_, p_150140_4_, var5, 3);
            this.func_150138_a(p_150140_1_, p_150140_2_, p_150140_3_, p_150140_4_, var5);
        }
        if (var7) {
            p_150140_1_.scheduleBlockUpdate(p_150140_2_, p_150140_3_, p_150140_4_, this, this.func_149738_a(p_150140_1_));
        }
    }
    
    public static boolean func_150139_a(final IBlockAccess p_150139_0_, final int p_150139_1_, final int p_150139_2_, final int p_150139_3_, final int p_150139_4_, final int p_150139_5_) {
        final int var6 = p_150139_1_ + Direction.offsetX[p_150139_5_];
        final int var7 = p_150139_3_ + Direction.offsetZ[p_150139_5_];
        final Block var8 = p_150139_0_.getBlock(var6, p_150139_2_, var7);
        final boolean var9 = (p_150139_4_ & 0x2) == 0x2;
        if (var8 == Blocks.tripwire_hook) {
            final int var10 = p_150139_0_.getBlockMetadata(var6, p_150139_2_, var7);
            final int var11 = var10 & 0x3;
            return var11 == Direction.rotateOpposite[p_150139_5_];
        }
        if (var8 == Blocks.tripwire) {
            final int var10 = p_150139_0_.getBlockMetadata(var6, p_150139_2_, var7);
            final boolean var12 = (var10 & 0x2) == 0x2;
            return var9 == var12;
        }
        return false;
    }
}
