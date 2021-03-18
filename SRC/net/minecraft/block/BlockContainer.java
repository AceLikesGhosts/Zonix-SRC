package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;

public abstract class BlockContainer extends Block implements ITileEntityProvider
{
    private static final String __OBFID = "CL_00000193";
    
    protected BlockContainer(final Material p_i45386_1_) {
        super(p_i45386_1_);
        this.isBlockContainer = true;
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }
    
    @Override
    public boolean onBlockEventReceived(final World p_149696_1_, final int p_149696_2_, final int p_149696_3_, final int p_149696_4_, final int p_149696_5_, final int p_149696_6_) {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        final TileEntity var7 = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return var7 != null && var7.receiveClientEvent(p_149696_5_, p_149696_6_);
    }
}
