package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;

public abstract class BlockLeaves extends BlockLeavesBase
{
    int[] field_150128_a;
    protected int field_150127_b;
    protected IIcon[][] field_150129_M;
    private static final String __OBFID = "CL_00000263";
    
    public BlockLeaves() {
        super(Material.leaves, false);
        this.field_150129_M = new IIcon[2][];
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.2f);
        this.setLightOpacity(1);
        this.setStepSound(BlockLeaves.soundTypeGrass);
    }
    
    @Override
    public int getBlockColor() {
        final double var1 = 0.5;
        final double var2 = 1.0;
        return ColorizerFoliage.getFoliageColor(var1, var2);
    }
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        return ColorizerFoliage.getFoliageColorBasic();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                final int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).getBiomeFoliageColor(p_149720_2_ + var9, p_149720_3_, p_149720_4_ + var8);
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += (var10 & 0xFF);
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | (var7 / 9 & 0xFF);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final byte var7 = 1;
        final int var8 = var7 + 1;
        if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
            for (int var9 = -var7; var9 <= var7; ++var9) {
                for (int var10 = -var7; var10 <= var7; ++var10) {
                    for (int var11 = -var7; var11 <= var7; ++var11) {
                        if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves) {
                            final int var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11);
                            p_149749_1_.setBlockMetadataWithNotify(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11, var12 | 0x8, 4);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            final int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            if ((var6 & 0x8) != 0x0 && (var6 & 0x4) == 0x0) {
                final byte var7 = 4;
                final int var8 = var7 + 1;
                final byte var9 = 32;
                final int var10 = var9 * var9;
                final int var11 = var9 / 2;
                if (this.field_150128_a == null) {
                    this.field_150128_a = new int[var9 * var9 * var9];
                }
                if (p_149674_1_.checkChunksExist(p_149674_2_ - var8, p_149674_3_ - var8, p_149674_4_ - var8, p_149674_2_ + var8, p_149674_3_ + var8, p_149674_4_ + var8)) {
                    for (int var12 = -var7; var12 <= var7; ++var12) {
                        for (int var13 = -var7; var13 <= var7; ++var13) {
                            for (int var14 = -var7; var14 <= var7; ++var14) {
                                final Block var15 = p_149674_1_.getBlock(p_149674_2_ + var12, p_149674_3_ + var13, p_149674_4_ + var14);
                                if (var15 != Blocks.log && var15 != Blocks.log2) {
                                    if (var15.getMaterial() == Material.leaves) {
                                        this.field_150128_a[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
                                    }
                                    else {
                                        this.field_150128_a[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
                                    }
                                }
                                else {
                                    this.field_150128_a[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
                                }
                            }
                        }
                    }
                    for (int var12 = 1; var12 <= 4; ++var12) {
                        for (int var13 = -var7; var13 <= var7; ++var13) {
                            for (int var14 = -var7; var14 <= var7; ++var14) {
                                for (int var16 = -var7; var16 <= var7; ++var16) {
                                    if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11] == var12 - 1) {
                                        if (this.field_150128_a[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11] == -2) {
                                            this.field_150128_a[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var16 + var11] = var12;
                                        }
                                        if (this.field_150128_a[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11] == -2) {
                                            this.field_150128_a[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var16 + var11] = var12;
                                        }
                                        if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11] == -2) {
                                            this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var16 + var11] = var12;
                                        }
                                        if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11] == -2) {
                                            this.field_150128_a[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var16 + var11] = var12;
                                        }
                                        if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1)] == -2) {
                                            this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + (var16 + var11 - 1)] = var12;
                                        }
                                        if (this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1] == -2) {
                                            this.field_150128_a[(var13 + var11) * var10 + (var14 + var11) * var9 + var16 + var11 + 1] = var12;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                int var12 = this.field_150128_a[var11 * var10 + var11 * var9 + var11];
                if (var12 >= 0) {
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6 & 0xFFFFFFF7, 4);
                }
                else {
                    this.func_150126_e(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
                }
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (p_149734_1_.canLightningStrikeAt(p_149734_2_, p_149734_3_ + 1, p_149734_4_) && !World.doesBlockHaveSolidTopSurface(p_149734_1_, p_149734_2_, p_149734_3_ - 1, p_149734_4_) && p_149734_5_.nextInt(15) == 1) {
            final double var6 = p_149734_2_ + p_149734_5_.nextFloat();
            final double var7 = p_149734_3_ - 0.05;
            final double var8 = p_149734_4_ + p_149734_5_.nextFloat();
            p_149734_1_.spawnParticle("dripWater", var6, var7, var8, 0.0, 0.0, 0.0);
        }
    }
    
    private void func_150126_e(final World p_150126_1_, final int p_150126_2_, final int p_150126_3_, final int p_150126_4_) {
        this.dropBlockAsItem(p_150126_1_, p_150126_2_, p_150126_3_, p_150126_4_, p_150126_1_.getBlockMetadata(p_150126_2_, p_150126_3_, p_150126_4_), 0);
        p_150126_1_.setBlockToAir(p_150126_2_, p_150126_3_, p_150126_4_);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return (p_149745_1_.nextInt(20) == 0) ? 1 : 0;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.sapling);
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            int var8 = this.func_150123_b(p_149690_5_);
            if (p_149690_7_ > 0) {
                var8 -= 2 << p_149690_7_;
                if (var8 < 10) {
                    var8 = 10;
                }
            }
            if (p_149690_1_.rand.nextInt(var8) == 0) {
                final Item var9 = this.getItemDropped(p_149690_5_, p_149690_1_.rand, p_149690_7_);
                this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var9, 1, this.damageDropped(p_149690_5_)));
            }
            var8 = 200;
            if (p_149690_7_ > 0) {
                var8 -= 10 << p_149690_7_;
                if (var8 < 40) {
                    var8 = 40;
                }
            }
            this.func_150124_c(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, var8);
        }
    }
    
    protected void func_150124_c(final World p_150124_1_, final int p_150124_2_, final int p_150124_3_, final int p_150124_4_, final int p_150124_5_, final int p_150124_6_) {
    }
    
    protected int func_150123_b(final int p_150123_1_) {
        return 20;
    }
    
    @Override
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Item.getItemFromBlock(this), 1, p_149636_6_ & 0x3));
        }
        else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return p_149692_1_ & 0x3;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return !this.field_150121_P;
    }
    
    @Override
    public abstract IIcon getIcon(final int p0, final int p1);
    
    public void func_150122_b(final boolean p_150122_1_) {
        this.field_150121_P = p_150122_1_;
        this.field_150127_b = (p_150122_1_ ? 0 : 1);
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(this), 1, p_149644_1_ & 0x3);
    }
    
    public abstract String[] func_150125_e();
}
