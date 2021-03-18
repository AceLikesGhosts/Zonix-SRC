package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockCrops extends BlockBush implements IGrowable
{
    private IIcon[] field_149867_a;
    private static final String __OBFID = "CL_00000222";
    
    protected BlockCrops() {
        this.setTickRandomly(true);
        final float var1 = 0.5f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
        this.setCreativeTab(null);
        this.setHardness(0.0f);
        this.setStepSound(BlockCrops.soundTypeGrass);
        this.disableStats();
    }
    
    @Override
    protected boolean func_149854_a(final Block p_149854_1_) {
        return p_149854_1_ == Blocks.farmland;
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) {
            int var6 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
            if (var6 < 7) {
                final float var7 = this.func_149864_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
                if (p_149674_5_.nextInt((int)(25.0f / var7) + 1) == 0) {
                    ++var6;
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var6, 2);
                }
            }
        }
    }
    
    public void func_149863_m(final World p_149863_1_, final int p_149863_2_, final int p_149863_3_, final int p_149863_4_) {
        int var5 = p_149863_1_.getBlockMetadata(p_149863_2_, p_149863_3_, p_149863_4_) + MathHelper.getRandomIntegerInRange(p_149863_1_.rand, 2, 5);
        if (var5 > 7) {
            var5 = 7;
        }
        p_149863_1_.setBlockMetadataWithNotify(p_149863_2_, p_149863_3_, p_149863_4_, var5, 2);
    }
    
    private float func_149864_n(final World p_149864_1_, final int p_149864_2_, final int p_149864_3_, final int p_149864_4_) {
        float var5 = 1.0f;
        final Block var6 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ - 1);
        final Block var7 = p_149864_1_.getBlock(p_149864_2_, p_149864_3_, p_149864_4_ + 1);
        final Block var8 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_);
        final Block var9 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_);
        final Block var10 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ - 1);
        final Block var11 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ - 1);
        final Block var12 = p_149864_1_.getBlock(p_149864_2_ + 1, p_149864_3_, p_149864_4_ + 1);
        final Block var13 = p_149864_1_.getBlock(p_149864_2_ - 1, p_149864_3_, p_149864_4_ + 1);
        final boolean var14 = var8 == this || var9 == this;
        final boolean var15 = var6 == this || var7 == this;
        final boolean var16 = var10 == this || var11 == this || var12 == this || var13 == this;
        for (int var17 = p_149864_2_ - 1; var17 <= p_149864_2_ + 1; ++var17) {
            for (int var18 = p_149864_4_ - 1; var18 <= p_149864_4_ + 1; ++var18) {
                float var19 = 0.0f;
                if (p_149864_1_.getBlock(var17, p_149864_3_ - 1, var18) == Blocks.farmland) {
                    var19 = 1.0f;
                    if (p_149864_1_.getBlockMetadata(var17, p_149864_3_ - 1, var18) > 0) {
                        var19 = 3.0f;
                    }
                }
                if (var17 != p_149864_2_ || var18 != p_149864_4_) {
                    var19 /= 4.0f;
                }
                var5 += var19;
            }
        }
        if (var16 || (var14 && var15)) {
            var5 /= 2.0f;
        }
        return var5;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ < 0 || p_149691_2_ > 7) {
            p_149691_2_ = 7;
        }
        return this.field_149867_a[p_149691_2_];
    }
    
    @Override
    public int getRenderType() {
        return 6;
    }
    
    protected Item func_149866_i() {
        return Items.wheat_seeds;
    }
    
    protected Item func_149865_P() {
        return Items.wheat;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        if (!p_149690_1_.isClient && p_149690_5_ >= 7) {
            for (int var8 = 3 + p_149690_7_, var9 = 0; var9 < var8; ++var9) {
                if (p_149690_1_.rand.nextInt(15) <= p_149690_5_) {
                    this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(this.func_149866_i(), 1, 0));
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return (p_149650_1_ == 7) ? this.func_149865_P() : this.func_149866_i();
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return this.func_149866_i();
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149867_a = new IIcon[8];
        for (int var2 = 0; var2 < this.field_149867_a.length; ++var2) {
            this.field_149867_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_stage_" + var2);
        }
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        return p_149851_1_.getBlockMetadata(p_149851_2_, p_149851_3_, p_149851_4_) != 7;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return true;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        this.func_149863_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
    }
}
