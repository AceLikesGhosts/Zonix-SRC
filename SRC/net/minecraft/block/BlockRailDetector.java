package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.texture.*;

public class BlockRailDetector extends BlockRailBase
{
    private IIcon[] field_150055_b;
    private static final String __OBFID = "CL_00000225";
    
    public BlockRailDetector() {
        super(true);
        this.setTickRandomly(true);
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 20;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (!p_149670_1_.isClient) {
            final int var6 = p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_);
            if ((var6 & 0x8) == 0x0) {
                this.func_150054_a(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, var6);
            }
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            if ((var6 & 0x8) != 0x0) {
                this.func_150054_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, var6);
            }
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return ((p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_) & 0x8) != 0x0) ? 15 : 0;
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return ((p_149748_1_.getBlockMetadata(p_149748_2_, p_149748_3_, p_149748_4_) & 0x8) == 0x0) ? 0 : ((p_149748_5_ == 1) ? 15 : 0);
    }
    
    private void func_150054_a(final World p_150054_1_, final int p_150054_2_, final int p_150054_3_, final int p_150054_4_, final int p_150054_5_) {
        final boolean var6 = (p_150054_5_ & 0x8) != 0x0;
        boolean var7 = false;
        final float var8 = 0.125f;
        final List var9 = p_150054_1_.getEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getBoundingBox(p_150054_2_ + var8, p_150054_3_, p_150054_4_ + var8, p_150054_2_ + 1 - var8, p_150054_3_ + 1 - var8, p_150054_4_ + 1 - var8));
        if (!var9.isEmpty()) {
            var7 = true;
        }
        if (var7 && !var6) {
            p_150054_1_.setBlockMetadataWithNotify(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_5_ | 0x8, 3);
            p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_, p_150054_4_, this);
            p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_ - 1, p_150054_4_, this);
            p_150054_1_.markBlockRangeForRenderUpdate(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_2_, p_150054_3_, p_150054_4_);
        }
        if (!var7 && var6) {
            p_150054_1_.setBlockMetadataWithNotify(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_5_ & 0x7, 3);
            p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_, p_150054_4_, this);
            p_150054_1_.notifyBlocksOfNeighborChange(p_150054_2_, p_150054_3_ - 1, p_150054_4_, this);
            p_150054_1_.markBlockRangeForRenderUpdate(p_150054_2_, p_150054_3_, p_150054_4_, p_150054_2_, p_150054_3_, p_150054_4_);
        }
        if (var7) {
            p_150054_1_.scheduleBlockUpdate(p_150054_2_, p_150054_3_, p_150054_4_, this, this.func_149738_a(p_150054_1_));
        }
        p_150054_1_.func_147453_f(p_150054_2_, p_150054_3_, p_150054_4_, this);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_150054_a(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_, p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_));
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        if ((p_149736_1_.getBlockMetadata(p_149736_2_, p_149736_3_, p_149736_4_) & 0x8) > 0) {
            final float var6 = 0.125f;
            final List var7 = p_149736_1_.getEntitiesWithinAABB(EntityMinecartCommandBlock.class, AxisAlignedBB.getBoundingBox(p_149736_2_ + var6, p_149736_3_, p_149736_4_ + var6, p_149736_2_ + 1 - var6, p_149736_3_ + 1 - var6, p_149736_4_ + 1 - var6));
            if (var7.size() > 0) {
                return var7.get(0).func_145822_e().func_145760_g();
            }
            final List var8 = p_149736_1_.selectEntitiesWithinAABB(EntityMinecart.class, AxisAlignedBB.getBoundingBox(p_149736_2_ + var6, p_149736_3_, p_149736_4_ + var6, p_149736_2_ + 1 - var6, p_149736_3_ + 1 - var6, p_149736_4_ + 1 - var6), IEntitySelector.selectInventories);
            if (var8.size() > 0) {
                return Container.calcRedstoneFromInventory(var8.get(0));
            }
        }
        return 0;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        (this.field_150055_b = new IIcon[2])[0] = p_149651_1_.registerIcon(this.getTextureName());
        this.field_150055_b[1] = p_149651_1_.registerIcon(this.getTextureName() + "_powered");
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return ((p_149691_2_ & 0x8) != 0x0) ? this.field_150055_b[1] : this.field_150055_b[0];
    }
}
