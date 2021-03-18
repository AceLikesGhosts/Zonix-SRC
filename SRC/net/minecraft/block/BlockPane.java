package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockPane extends Block
{
    private final String field_150100_a;
    private final boolean field_150099_b;
    private final String field_150101_M;
    private IIcon field_150102_N;
    private static final String __OBFID = "CL_00000322";
    
    protected BlockPane(final String p_i45432_1_, final String p_i45432_2_, final Material p_i45432_3_, final boolean p_i45432_4_) {
        super(p_i45432_3_);
        this.field_150100_a = p_i45432_2_;
        this.field_150099_b = p_i45432_4_;
        this.field_150101_M = p_i45432_1_;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return this.field_150099_b ? super.getItemDropped(p_149650_1_, p_149650_2_, p_149650_3_) : null;
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
    public int getRenderType() {
        return (this.blockMaterial == Material.glass) ? 41 : 18;
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess p_149646_1_, final int p_149646_2_, final int p_149646_3_, final int p_149646_4_, final int p_149646_5_) {
        return p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_) != this && super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
    }
    
    @Override
    public void addCollisionBoxesToList(final World p_149743_1_, final int p_149743_2_, final int p_149743_3_, final int p_149743_4_, final AxisAlignedBB p_149743_5_, final List p_149743_6_, final Entity p_149743_7_) {
        final boolean var8 = this.func_150098_a(p_149743_1_.getBlock(p_149743_2_, p_149743_3_, p_149743_4_ - 1));
        final boolean var9 = this.func_150098_a(p_149743_1_.getBlock(p_149743_2_, p_149743_3_, p_149743_4_ + 1));
        final boolean var10 = this.func_150098_a(p_149743_1_.getBlock(p_149743_2_ - 1, p_149743_3_, p_149743_4_));
        final boolean var11 = this.func_150098_a(p_149743_1_.getBlock(p_149743_2_ + 1, p_149743_3_, p_149743_4_));
        if ((!var10 || !var11) && (var10 || var11 || var8 || var9)) {
            if (var10 && !var11) {
                this.setBlockBounds(0.0f, 0.0f, 0.4375f, 0.5f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
            }
            else if (!var10 && var11) {
                this.setBlockBounds(0.5f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
            }
        }
        else {
            this.setBlockBounds(0.0f, 0.0f, 0.4375f, 1.0f, 1.0f, 0.5625f);
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
        if ((!var8 || !var9) && (var10 || var11 || var8 || var9)) {
            if (var8 && !var9) {
                this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 0.5f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
            }
            else if (!var8 && var9) {
                this.setBlockBounds(0.4375f, 0.0f, 0.5f, 0.5625f, 1.0f, 1.0f);
                super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
            }
        }
        else {
            this.setBlockBounds(0.4375f, 0.0f, 0.0f, 0.5625f, 1.0f, 1.0f);
            super.addCollisionBoxesToList(p_149743_1_, p_149743_2_, p_149743_3_, p_149743_4_, p_149743_5_, p_149743_6_, p_149743_7_);
        }
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        float var5 = 0.4375f;
        float var6 = 0.5625f;
        float var7 = 0.4375f;
        float var8 = 0.5625f;
        final boolean var9 = this.func_150098_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ - 1));
        final boolean var10 = this.func_150098_a(p_149719_1_.getBlock(p_149719_2_, p_149719_3_, p_149719_4_ + 1));
        final boolean var11 = this.func_150098_a(p_149719_1_.getBlock(p_149719_2_ - 1, p_149719_3_, p_149719_4_));
        final boolean var12 = this.func_150098_a(p_149719_1_.getBlock(p_149719_2_ + 1, p_149719_3_, p_149719_4_));
        if ((!var11 || !var12) && (var11 || var12 || var9 || var10)) {
            if (var11 && !var12) {
                var5 = 0.0f;
            }
            else if (!var11 && var12) {
                var6 = 1.0f;
            }
        }
        else {
            var5 = 0.0f;
            var6 = 1.0f;
        }
        if ((!var9 || !var10) && (var11 || var12 || var9 || var10)) {
            if (var9 && !var10) {
                var7 = 0.0f;
            }
            else if (!var9 && var10) {
                var8 = 1.0f;
            }
        }
        else {
            var7 = 0.0f;
            var8 = 1.0f;
        }
        this.setBlockBounds(var5, 0.0f, var7, var6, 1.0f, var8);
    }
    
    public IIcon func_150097_e() {
        return this.field_150102_N;
    }
    
    public final boolean func_150098_a(final Block p_150098_1_) {
        return p_150098_1_.func_149730_j() || p_150098_1_ == this || p_150098_1_ == Blocks.glass || p_150098_1_ == Blocks.stained_glass || p_150098_1_ == Blocks.stained_glass_pane || p_150098_1_ instanceof BlockPane;
    }
    
    @Override
    protected boolean canSilkHarvest() {
        return true;
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_);
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.field_150101_M);
        this.field_150102_N = p_149651_1_.registerIcon(this.field_150100_a);
    }
}
