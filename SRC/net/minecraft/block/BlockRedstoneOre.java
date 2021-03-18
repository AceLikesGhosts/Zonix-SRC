package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;

public class BlockRedstoneOre extends Block
{
    private boolean field_150187_a;
    private static final String __OBFID = "CL_00000294";
    
    public BlockRedstoneOre(final boolean p_i45420_1_) {
        super(Material.rock);
        if (p_i45420_1_) {
            this.setTickRandomly(true);
        }
        this.field_150187_a = p_i45420_1_;
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 30;
    }
    
    @Override
    public void onBlockClicked(final World p_149699_1_, final int p_149699_2_, final int p_149699_3_, final int p_149699_4_, final EntityPlayer p_149699_5_) {
        this.func_150185_e(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_);
        super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_, p_149699_5_);
    }
    
    @Override
    public void onEntityWalking(final World p_149724_1_, final int p_149724_2_, final int p_149724_3_, final int p_149724_4_, final Entity p_149724_5_) {
        this.func_150185_e(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_);
        super.onEntityWalking(p_149724_1_, p_149724_2_, p_149724_3_, p_149724_4_, p_149724_5_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        this.func_150185_e(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_);
        return super.onBlockActivated(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, p_149727_5_, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }
    
    private void func_150185_e(final World p_150185_1_, final int p_150185_2_, final int p_150185_3_, final int p_150185_4_) {
        this.func_150186_m(p_150185_1_, p_150185_2_, p_150185_3_, p_150185_4_);
        if (this == Blocks.redstone_ore) {
            p_150185_1_.setBlock(p_150185_2_, p_150185_3_, p_150185_4_, Blocks.lit_redstone_ore);
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (this == Blocks.lit_redstone_ore) {
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_ore);
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.redstone;
    }
    
    @Override
    public int quantityDroppedWithBonus(final int p_149679_1_, final Random p_149679_2_) {
        return this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(p_149679_1_ + 1);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 4 + p_149745_1_.nextInt(2);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        if (this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_) != Item.getItemFromBlock(this)) {
            final int var8 = 1 + p_149690_1_.rand.nextInt(5);
            this.dropXpOnBlockBreak(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, var8);
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (this.field_150187_a) {
            this.func_150186_m(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_);
        }
    }
    
    private void func_150186_m(final World p_150186_1_, final int p_150186_2_, final int p_150186_3_, final int p_150186_4_) {
        final Random var5 = p_150186_1_.rand;
        final double var6 = 0.0625;
        for (int var7 = 0; var7 < 6; ++var7) {
            double var8 = p_150186_2_ + var5.nextFloat();
            double var9 = p_150186_3_ + var5.nextFloat();
            double var10 = p_150186_4_ + var5.nextFloat();
            if (var7 == 0 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ + 1, p_150186_4_).isOpaqueCube()) {
                var9 = p_150186_3_ + 1 + var6;
            }
            if (var7 == 1 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_ - 1, p_150186_4_).isOpaqueCube()) {
                var9 = p_150186_3_ + 0 - var6;
            }
            if (var7 == 2 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ + 1).isOpaqueCube()) {
                var10 = p_150186_4_ + 1 + var6;
            }
            if (var7 == 3 && !p_150186_1_.getBlock(p_150186_2_, p_150186_3_, p_150186_4_ - 1).isOpaqueCube()) {
                var10 = p_150186_4_ + 0 - var6;
            }
            if (var7 == 4 && !p_150186_1_.getBlock(p_150186_2_ + 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
                var8 = p_150186_2_ + 1 + var6;
            }
            if (var7 == 5 && !p_150186_1_.getBlock(p_150186_2_ - 1, p_150186_3_, p_150186_4_).isOpaqueCube()) {
                var8 = p_150186_2_ + 0 - var6;
            }
            if (var8 < p_150186_2_ || var8 > p_150186_2_ + 1 || var9 < 0.0 || var9 > p_150186_3_ + 1 || var10 < p_150186_4_ || var10 > p_150186_4_ + 1) {
                p_150186_1_.spawnParticle("reddust", var8, var9, var10, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Blocks.redstone_ore);
    }
}
