package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public abstract class BlockSlab extends Block
{
    protected final boolean field_150004_a;
    private static final String __OBFID = "CL_00000253";
    
    public BlockSlab(final boolean p_i45410_1_, final Material p_i45410_2_) {
        super(p_i45410_2_);
        this.field_150004_a = p_i45410_1_;
        if (p_i45410_1_) {
            this.opaque = true;
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
        this.setLightOpacity(255);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        if (this.field_150004_a) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            final boolean var5 = (p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) & 0x8) != 0x0;
            if (var5) {
                this.setBlockBounds(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
            }
            else {
                this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
            }
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        if (this.field_150004_a) {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
        }
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        this.setBlockBoundsBasedOnState(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_);
        super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
    }
    
    @Override
    public boolean isOpaqueCube() {
        return this.field_150004_a;
    }
    
    @Override
    public int onBlockPlaced(final World p_149660_1_, final int p_149660_2_, final int p_149660_3_, final int p_149660_4_, final int p_149660_5_, final float p_149660_6_, final float p_149660_7_, final float p_149660_8_, final int p_149660_9_) {
        return this.field_150004_a ? p_149660_9_ : ((p_149660_5_ != 0 && (p_149660_5_ == 1 || p_149660_7_ <= 0.5)) ? p_149660_9_ : (p_149660_9_ | 0x8));
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return this.field_150004_a ? 2 : 1;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_ & 0x7;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
        return this.field_150004_a;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        if (this.field_150004_a) {
            return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
        }
        if (p_149646_5_ != 1 && p_149646_5_ != 0 && !super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_)) {
            return false;
        }
        final int var6 = p_149646_2_ + Facing.offsetsXForSide[Facing.oppositeSide[p_149646_5_]];
        final int var7 = p_149646_3_ + Facing.offsetsYForSide[Facing.oppositeSide[p_149646_5_]];
        final int var8 = p_149646_4_ + Facing.offsetsZForSide[Facing.oppositeSide[p_149646_5_]];
        final boolean var9 = (p_149646_1_.getBlockMetadata(var6, var7, var8) & 0x8) != 0x0;
        return var9 ? (p_149646_5_ == 0 || (p_149646_5_ == 1 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_)) || (!func_150003_a(p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_)) || (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) & 0x8) == 0x0)) : (p_149646_5_ == 1 || (p_149646_5_ == 0 && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_)) || (!func_150003_a(p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_)) || (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) & 0x8) != 0x0));
    }
    
    private static boolean func_150003_a(final Block p_150003_0_) {
        return p_150003_0_ == Blocks.stone_slab || p_150003_0_ == Blocks.wooden_slab;
    }
    
    public abstract String func_150002_b(final int p0);
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        return super.getDamageValue(p_149643_1_, p_149643_2_, p_149643_3_, p_149643_4_) & 0x7;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return func_150003_a(this) ? Item.getItemFromBlock(this) : ((this == Blocks.double_stone_slab) ? Item.getItemFromBlock(Blocks.stone_slab) : ((this == Blocks.double_wooden_slab) ? Item.getItemFromBlock(Blocks.wooden_slab) : Item.getItemFromBlock(Blocks.stone_slab)));
    }
}
