package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.texture.*;

public class BlockDaylightDetector extends BlockContainer
{
    private IIcon[] field_149958_a;
    private static final String __OBFID = "CL_00000223";
    
    public BlockDaylightDetector() {
        super(Material.wood);
        this.field_149958_a = new IIcon[2];
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.375f, 1.0f);
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        return p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
    }
    
    public void func_149957_e(final World p_149957_1_, final int p_149957_2_, final int p_149957_3_, final int p_149957_4_) {
        if (!p_149957_1_.provider.hasNoSky) {
            final int var5 = p_149957_1_.getBlockMetadata(p_149957_2_, p_149957_3_, p_149957_4_);
            int var6 = p_149957_1_.getSavedLightValue(EnumSkyBlock.Sky, p_149957_2_, p_149957_3_, p_149957_4_) - p_149957_1_.skylightSubtracted;
            float var7 = p_149957_1_.getCelestialAngleRadians(1.0f);
            if (var7 < 3.1415927f) {
                var7 += (0.0f - var7) * 0.2f;
            }
            else {
                var7 += (6.2831855f - var7) * 0.2f;
            }
            var6 = Math.round(var6 * MathHelper.cos(var7));
            if (var6 < 0) {
                var6 = 0;
            }
            if (var6 > 15) {
                var6 = 15;
            }
            if (var5 != var6) {
                p_149957_1_.setBlockMetadataWithNotify(p_149957_2_, p_149957_3_, p_149957_4_, var6, 3);
            }
        }
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return false;
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityDaylightDetector();
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149958_a[0] : this.field_149958_a[1];
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149958_a[0] = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_149958_a[1] = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }
}
