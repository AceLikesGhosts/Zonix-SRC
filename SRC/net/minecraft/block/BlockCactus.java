package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;

public class BlockCactus extends Block
{
    private IIcon field_150041_a;
    private IIcon field_150040_b;
    private static final String __OBFID = "CL_00000210";
    
    protected BlockCactus() {
        super(Material.field_151570_A);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (p_149674_1_.isAirBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_)) {
            int var6;
            for (var6 = 1; p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - var6, p_149674_4_) == this; ++var6) {}
            if (var6 < 3) {
                final int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
                if (var7 == 15) {
                    p_149674_1_.setBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, 0, 4);
                    this.onNeighborBlockChange(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, this);
                }
                else {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7 + 1, 4);
                }
            }
        }
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final float var5 = 0.0625f;
        return AxisAlignedBB.getBoundingBox(p_149668_2_ + var5, p_149668_3_, p_149668_4_ + var5, p_149668_2_ + 1 - var5, p_149668_3_ + 1 - var5, p_149668_4_ + 1 - var5);
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(final World p_149633_1_, final int p_149633_2_, final int p_149633_3_, final int p_149633_4_) {
        final float var5 = 0.0625f;
        return AxisAlignedBB.getBoundingBox(p_149633_2_ + var5, p_149633_3_, p_149633_4_ + var5, p_149633_2_ + 1 - var5, p_149633_3_ + 1, p_149633_4_ + 1 - var5);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_150041_a : ((p_149691_1_ == 0) ? this.field_150040_b : this.blockIcon);
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
    public int getRenderType() {
        return 13;
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && this.canBlockStay(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_);
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!this.canBlockStay(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_)) {
            p_149695_1_.func_147480_a(p_149695_2_, p_149695_3_, p_149695_4_, true);
        }
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        if (p_149718_1_.getBlock(p_149718_2_ - 1, p_149718_3_, p_149718_4_).getMaterial().isSolid()) {
            return false;
        }
        if (p_149718_1_.getBlock(p_149718_2_ + 1, p_149718_3_, p_149718_4_).getMaterial().isSolid()) {
            return false;
        }
        if (p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_ - 1).getMaterial().isSolid()) {
            return false;
        }
        if (p_149718_1_.getBlock(p_149718_2_, p_149718_3_, p_149718_4_ + 1).getMaterial().isSolid()) {
            return false;
        }
        final Block var5 = p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_);
        return var5 == Blocks.cactus || var5 == Blocks.sand;
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        p_149670_5_.attackEntityFrom(DamageSource.cactus, 1.0f);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150041_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150040_b = p_149651_1_.registerIcon(this.getTextureName() + "_bottom");
    }
}
