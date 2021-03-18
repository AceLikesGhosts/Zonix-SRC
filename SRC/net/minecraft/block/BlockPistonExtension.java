package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class BlockPistonExtension extends Block
{
    private IIcon field_150088_a;
    private static final String __OBFID = "CL_00000367";
    
    public BlockPistonExtension() {
        super(Material.piston);
        this.setStepSound(BlockPistonExtension.soundTypePiston);
        this.setHardness(0.5f);
    }
    
    public void func_150086_a(final IIcon p_150086_1_) {
        this.field_150088_a = p_150086_1_;
    }
    
    public void func_150087_e() {
        this.field_150088_a = null;
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (p_149681_6_.capabilities.isCreativeMode) {
            final int var7 = func_150085_b(p_149681_5_);
            final Block var8 = p_149681_1_.getBlock(p_149681_2_ - Facing.offsetsXForSide[var7], p_149681_3_ - Facing.offsetsYForSide[var7], p_149681_4_ - Facing.offsetsZForSide[var7]);
            if (var8 == Blocks.piston || var8 == Blocks.sticky_piston) {
                p_149681_1_.setBlockToAir(p_149681_2_ - Facing.offsetsXForSide[var7], p_149681_3_ - Facing.offsetsYForSide[var7], p_149681_4_ - Facing.offsetsZForSide[var7]);
            }
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, final Block p_149749_5_, int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        final int var7 = Facing.oppositeSide[func_150085_b(p_149749_6_)];
        p_149749_2_ += Facing.offsetsXForSide[var7];
        p_149749_3_ += Facing.offsetsYForSide[var7];
        p_149749_4_ += Facing.offsetsZForSide[var7];
        final Block var8 = p_149749_1_.getBlock(p_149749_2_, p_149749_3_, p_149749_4_);
        if (var8 == Blocks.piston || var8 == Blocks.sticky_piston) {
            p_149749_6_ = p_149749_1_.getBlockMetadata(p_149749_2_, p_149749_3_, p_149749_4_);
            if (BlockPistonBase.func_150075_c(p_149749_6_)) {
                var8.dropBlockAsItem(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_6_, 0);
                p_149749_1_.setBlockToAir(p_149749_2_, p_149749_3_, p_149749_4_);
            }
        }
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        final int var3 = func_150085_b(p_149691_2_);
        return (p_149691_1_ == var3) ? ((this.field_150088_a != null) ? this.field_150088_a : (((p_149691_2_ & 0x8) != 0x0) ? BlockPistonBase.func_150074_e("piston_top_sticky") : BlockPistonBase.func_150074_e("piston_top_normal"))) : ((var3 < 6 && p_149691_1_ == Facing.oppositeSide[var3]) ? BlockPistonBase.func_150074_e("piston_top_normal") : BlockPistonBase.func_150074_e("piston_side"));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    @Override
    public int getRenderType() {
        return 17;
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
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return false;
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World p_149707_1_, final int p_149707_2_, final int p_149707_3_, final int p_149707_4_, final int p_149707_5_) {
        return false;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        final int var8 = p_149743_1_.getBlockMetadata(p_149743_2_, p_149743_3_, p_149743_4_);
        final float var9 = 0.25f;
        final float var10 = 0.375f;
        final float var11 = 0.625f;
        final float var12 = 0.25f;
        final float var13 = 0.75f;
        switch (func_150085_b(var8)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.25f, 0.375f, 0.625f, 1.0f, 0.625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.0f, 0.375f, 0.625f, 0.75f, 0.625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25f, 0.375f, 0.25f, 0.75f, 0.625f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.25f, 0.375f, 0.0f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.375f, 0.25f, 0.25f, 0.625f, 0.75f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                this.setBlockBounds(0.0f, 0.375f, 0.25f, 0.75f, 0.625f, 0.75f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
                break;
            }
        }
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        final int var5 = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        final float var6 = 0.25f;
        switch (func_150085_b(var5)) {
            case 0: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.25f, 1.0f);
                break;
            }
            case 1: {
                this.setBlockBounds(0.0f, 0.75f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 2: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.25f);
                break;
            }
            case 3: {
                this.setBlockBounds(0.0f, 0.0f, 0.75f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case 4: {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 0.25f, 1.0f, 1.0f);
                break;
            }
            case 5: {
                this.setBlockBounds(0.75f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        final int var6 = func_150085_b(p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_));
        final Block var7 = p_149695_1_.getBlock(p_149695_2_ - Facing.offsetsXForSide[var6], p_149695_3_ - Facing.offsetsYForSide[var6], p_149695_4_ - Facing.offsetsZForSide[var6]);
        if (var7 != Blocks.piston && var7 != Blocks.sticky_piston) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
        }
        else {
            var7.onNeighborBlockChange(p_149695_1_, p_149695_2_ - Facing.offsetsXForSide[var6], p_149695_3_ - Facing.offsetsYForSide[var6], p_149695_4_ - Facing.offsetsZForSide[var6], p_149695_5_);
        }
    }
    
    public static int func_150085_b(final int p_150085_0_) {
        return MathHelper.clamp_int(p_150085_0_ & 0x7, 0, Facing.offsetsXForSide.length - 1);
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        final int var5 = p_149694_1_.getBlockMetadata(p_149694_2_, p_149694_3_, p_149694_4_);
        return ((var5 & 0x8) != 0x0) ? Item.getItemFromBlock(Blocks.sticky_piston) : Item.getItemFromBlock(Blocks.piston);
    }
}
