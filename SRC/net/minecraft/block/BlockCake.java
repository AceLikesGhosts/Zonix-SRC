package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;

public class BlockCake extends Block
{
    private IIcon field_150038_a;
    private IIcon field_150037_b;
    private IIcon field_150039_M;
    private static final String __OBFID = "CL_00000211";
    
    protected BlockCake() {
        super(Material.field_151568_F);
        this.setTickRandomly(true);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        this.setBlockBounds(var7, 0.0f, var6, 1.0f - var6, var8, 1.0f - var6);
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.0625f;
        final float var2 = 0.5f;
        this.setBlockBounds(var1, 0.0f, var1, 1.0f - var1, var2, 1.0f - var1);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        return AxisAlignedBB.getBoundingBox(p_149668_2_ + var7, p_149668_3_, p_149668_4_ + var6, p_149668_2_ + 1 - var6, p_149668_3_ + var8 - var6, p_149668_4_ + 1 - var6);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        final int var5 = p_149633_1_.getBlockMetadata(p_149633_2_, p_149633_3_, p_149633_4_);
        final float var6 = 0.0625f;
        final float var7 = (1 + var5 * 2) / 16.0f;
        final float var8 = 0.5f;
        return AxisAlignedBB.getBoundingBox(p_149633_2_ + var7, p_149633_3_, p_149633_4_ + var6, p_149633_2_ + 1 - var6, p_149633_3_ + var8, p_149633_4_ + 1 - var6);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_150038_a : ((p_149691_1_ == 0) ? this.field_150037_b : ((p_149691_2_ > 0 && p_149691_1_ == 4) ? this.field_150039_M : this.blockIcon));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150039_M = p_149651_1_.registerIcon(this.getTextureName() + "_inner");
        this.field_150038_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150037_b = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
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
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        this.func_150036_b(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_);
        return true;
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
        this.func_150036_b(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }
    
    private void func_150036_b(final World p_150036_1_, final int p_150036_2_, final int p_150036_3_, final int p_150036_4_, final EntityPlayer p_150036_5_) {
        if (p_150036_5_.canEat(false)) {
            p_150036_5_.getFoodStats().addStats(2, 0.1f);
            final int var6 = p_150036_1_.getBlockMetadata(p_150036_2_, p_150036_3_, p_150036_4_) + 1;
            if (var6 >= 6) {
                p_150036_1_.setBlockToAir(p_150036_2_, p_150036_3_, p_150036_4_);
            }
            else {
                p_150036_1_.setBlockMetadataWithNotify(p_150036_2_, p_150036_3_, p_150036_4_, var6, 2);
            }
        }
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && this.canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!this.canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        return p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_).getMaterial().isSolid();
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.cake;
    }
}
