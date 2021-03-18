package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;
import net.minecraft.command.server.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;

public class BlockCommandBlock extends BlockContainer
{
    private static final String __OBFID = "CL_00000219";
    
    public BlockCommandBlock() {
        super(Material.iron);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityCommandBlock();
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!p_149695_1_.isClient) {
            final boolean var6 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
            final int var7 = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
            final boolean var8 = (var7 & 0x1) != 0x0;
            if (var6 && !var8) {
                p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 | 0x1, 4);
                p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.func_149738_a(p_149695_1_));
            }
            else if (!var6 && var8) {
                p_149695_1_.setBlockMetadataWithNotify(p_149695_2_, p_149695_3_, p_149695_4_, var7 & 0xFFFFFFFE, 4);
            }
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        final TileEntity var6 = p_149674_1_.getTileEntity(p_149674_2_, p_149674_3_, p_149674_4_);
        if (var6 != null && var6 instanceof TileEntityCommandBlock) {
            final CommandBlockLogic var7 = ((TileEntityCommandBlock)var6).func_145993_a();
            var7.func_145755_a(p_149674_1_);
            p_149674_1_.func_147453_f(p_149674_2_, p_149674_3_, p_149674_4_, this);
        }
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 1;
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final TileEntityCommandBlock var10 = (TileEntityCommandBlock)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            p_149727_5_.func_146100_a(var10);
        }
        return true;
    }
    
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }
    
    @Override
    public int getComparatorInputOverride(final World p_149736_1_, final int p_149736_2_, final int p_149736_3_, final int p_149736_4_, final int p_149736_5_) {
        final TileEntity var6 = p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_);
        return (var6 != null && var6 instanceof TileEntityCommandBlock) ? ((TileEntityCommandBlock)var6).func_145993_a().func_145760_g() : 0;
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final TileEntityCommandBlock var7 = (TileEntityCommandBlock)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_);
        if (p_149689_6_.hasDisplayName()) {
            var7.func_145993_a().func_145754_b(p_149689_6_.getDisplayName());
        }
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
}
