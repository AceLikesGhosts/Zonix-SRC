package net.minecraft.block;

import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.material.*;

public class BlockEndPortal extends BlockContainer
{
    public static boolean field_149948_a;
    private static final String __OBFID = "CL_00000236";
    
    protected BlockEndPortal(final Material p_i45404_1_) {
        super(p_i45404_1_);
        this.setLightLevel(1.0f);
    }
    
    @Override
    public TileEntity createNewTileEntity(final World p_149915_1_, final int p_149915_2_) {
        return new TileEntityEndPortal();
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final float var5 = 0.0625f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var5, 1.0f);
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return p_149646_5_ == 0 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
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
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        if (p_149670_5_.ridingEntity == null && p_149670_5_.riddenByEntity == null && !p_149670_1_.isClient) {
            p_149670_5_.travelToDimension(1);
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        final double var6 = p_149734_2_ + p_149734_5_.nextFloat();
        final double var7 = p_149734_3_ + 0.8f;
        final double var8 = p_149734_4_ + p_149734_5_.nextFloat();
        final double var9 = 0.0;
        final double var10 = 0.0;
        final double var11 = 0.0;
        p_149734_1_.spawnParticle("smoke", var6, var7, var8, var9, var10, var11);
    }
    
    @Override
    public int getRenderType() {
        return -1;
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        if (!BlockEndPortal.field_149948_a && p_149726_1_.provider.dimensionId != 0) {
            p_149726_1_.setBlockToAir(p_149726_2_, p_149726_3_, p_149726_4_);
        }
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemById(0);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("portal");
    }
    
    @Override
    public MapColor getMapColor(final int p_149728_1_) {
        return MapColor.field_151654_J;
    }
}
