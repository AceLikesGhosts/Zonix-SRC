package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public class BlockSnow extends Block
{
    private static final String __OBFID = "CL_00000309";
    
    protected BlockSnow() {
        super(Material.field_151597_y);
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.func_150154_b(0);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("snow");
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final int var5 = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_) & 0x7;
        final float var6 = 0.125f;
        return AxisAlignedBB.getBoundingBox(p_149668_2_ + this.field_149759_B, p_149668_3_ + this.field_149760_C, p_149668_4_ + this.field_149754_D, p_149668_2_ + this.field_149755_E, p_149668_3_ + var5 * var6, p_149668_4_ + this.field_149757_G);
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
    public void setBlockBoundsForItemRender() {
        this.func_150154_b(0);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.func_150154_b(p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_));
    }
    
    protected void func_150154_b(final int p_150154_1_) {
        final int var2 = p_150154_1_ & 0x7;
        final float var3 = 2 * (1 + var2) / 16.0f;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, var3, 1.0f);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        final Block var5 = p_149742_1_.getBlock(p_149742_2_, p_149742_3_ - 1, p_149742_4_);
        return var5 != Blocks.ice && var5 != Blocks.packed_ice && (var5.getMaterial() == Material.leaves || (var5 == this && (p_149742_1_.getBlockMetadata(p_149742_2_, p_149742_3_ - 1, p_149742_4_) & 0x7) == 0x7) || (var5.isOpaqueCube() && var5.blockMaterial.blocksMovement()));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        this.func_150155_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }
    
    private boolean func_150155_m(final World p_150155_1_, final int p_150155_2_, final int p_150155_3_, final int p_150155_4_) {
        if (!this.canPlaceBlockAt(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_)) {
            this.dropBlockAsItem(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_, p_150155_1_.getBlockMetadata(p_150155_2_, p_150155_3_, p_150155_4_), 0);
            p_150155_1_.setBlockToAir(p_150155_2_, p_150155_3_, p_150155_4_);
            return false;
        }
        return true;
    }
    
    @Override
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        final int var7 = p_149636_6_ & 0x7;
        this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Items.snowball, var7 + 1, 0));
        p_149636_1_.setBlockToAir(p_149636_3_, p_149636_4_, p_149636_5_);
        p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.snowball;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (p_149674_1_.getSavedLightValue(EnumSkyBlock.Block, p_149674_2_, p_149674_3_, p_149674_4_) > 11) {
            this.dropBlockAsItem(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 0);
            p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return p_149646_5_ == 1 || super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
}
