package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;

public class BlockNote extends BlockContainer
{
    private static final String __OBFID = "CL_00000278";
    
    public BlockNote() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final boolean var6 = p_149695_1_.isBlockIndirectlyGettingPowered(p_149695_2_, p_149695_3_, p_149695_4_);
        final TileEntityNote var7 = (TileEntityNote)p_149695_1_.getTileEntity(p_149695_2_, p_149695_3_, p_149695_4_);
        if (var7 != null && var7.field_145880_i != var6) {
            if (var6) {
                var7.func_145878_a(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
            }
            var7.field_145880_i = var6;
        }
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        if (p_149727_1_.isClient) {
            return true;
        }
        final TileEntityNote var10 = (TileEntityNote)p_149727_1_.getTileEntity(p_149727_2_, p_149727_3_, p_149727_4_);
        if (var10 != null) {
            var10.func_145877_a();
            var10.func_145878_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        }
        return true;
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
        if (!p_149699_1_.isClient) {
            final TileEntityNote var6 = (TileEntityNote)p_149699_1_.getTileEntity(p_149699_2_, p_149699_3_, p_149699_4_);
            if (var6 != null) {
                var6.func_145878_a(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
            }
        }
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityNote();
    }
    
    @Override
    public boolean onBlockEventReceived(final World p_149696_1_, final int p_149696_2_, final int p_149696_3_, final int p_149696_4_, final int p_149696_5_, final int p_149696_6_) {
        final float var7 = (float)Math.pow(2.0, (p_149696_6_ - 12) / 12.0);
        String var8 = "harp";
        if (p_149696_5_ == 1) {
            var8 = "bd";
        }
        if (p_149696_5_ == 2) {
            var8 = "snare";
        }
        if (p_149696_5_ == 3) {
            var8 = "hat";
        }
        if (p_149696_5_ == 4) {
            var8 = "bassattack";
        }
        p_149696_1_.playSoundEffect(p_149696_2_ + 0.5, p_149696_3_ + 0.5, p_149696_4_ + 0.5, "note." + var8, 3.0f, var7);
        p_149696_1_.spawnParticle("note", p_149696_2_ + 0.5, p_149696_3_ + 1.2, p_149696_4_ + 0.5, p_149696_6_ / 24.0, 0.0, 0.0);
        return true;
    }
}
