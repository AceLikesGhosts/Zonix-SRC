package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockStem extends BlockBush implements IGrowable
{
    private final Block field_149877_a;
    private IIcon field_149876_b;
    private static final String __OBFID = "CL_00000316";
    
    protected BlockStem(final Block p_i45430_1_) {
        this.field_149877_a = p_i45430_1_;
        this.setTickRandomly(true);
        final float var2 = 0.125f;
        this.setBlockBounds(0.5f - var2, 0.0f, 0.5f - var2, 0.5f + var2, 0.25f, 0.5f + var2);
        this.setCreativeTab(null);
    }
    
    @Override
    protected boolean func_149854_a(final Block p_149854_1_) {
        return p_149854_1_ == Blocks.farmland;
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
        if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) {
            final float var6 = this.func_149875_n(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
            if (p_149674_5_.nextInt((int)(25.0f / var6) + 1) == 0) {
                int var7 = p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_);
                if (var7 < 7) {
                    ++var7;
                    p_149674_1_.setBlockMetadataWithNotify(p_149674_2_, p_149674_3_, p_149674_4_, var7, 2);
                }
                else {
                    if (p_149674_1_.getBlock(p_149674_2_ - 1, p_149674_3_, p_149674_4_) == this.field_149877_a) {
                        return;
                    }
                    if (p_149674_1_.getBlock(p_149674_2_ + 1, p_149674_3_, p_149674_4_) == this.field_149877_a) {
                        return;
                    }
                    if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ - 1) == this.field_149877_a) {
                        return;
                    }
                    if (p_149674_1_.getBlock(p_149674_2_, p_149674_3_, p_149674_4_ + 1) == this.field_149877_a) {
                        return;
                    }
                    final int var8 = p_149674_5_.nextInt(4);
                    int var9 = p_149674_2_;
                    int var10 = p_149674_4_;
                    if (var8 == 0) {
                        var9 = p_149674_2_ - 1;
                    }
                    if (var8 == 1) {
                        ++var9;
                    }
                    if (var8 == 2) {
                        var10 = p_149674_4_ - 1;
                    }
                    if (var8 == 3) {
                        ++var10;
                    }
                    final Block var11 = p_149674_1_.getBlock(var9, p_149674_3_ - 1, var10);
                    if (p_149674_1_.getBlock(var9, p_149674_3_, var10).blockMaterial == Material.air && (var11 == Blocks.farmland || var11 == Blocks.dirt || var11 == Blocks.grass)) {
                        p_149674_1_.setBlock(var9, p_149674_3_, var10, this.field_149877_a);
                    }
                }
            }
        }
    }
    
    public void func_149874_m(final World p_149874_1_, final int p_149874_2_, final int p_149874_3_, final int p_149874_4_) {
        int var5 = p_149874_1_.getBlockMetadata(p_149874_2_, p_149874_3_, p_149874_4_) + MathHelper.getRandomIntegerInRange(p_149874_1_.rand, 2, 5);
        if (var5 > 7) {
            var5 = 7;
        }
        p_149874_1_.setBlockMetadataWithNotify(p_149874_2_, p_149874_3_, p_149874_4_, var5, 2);
    }
    
    private float func_149875_n(final World p_149875_1_, final int p_149875_2_, final int p_149875_3_, final int p_149875_4_) {
        float var5 = 1.0f;
        final Block var6 = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ - 1);
        final Block var7 = p_149875_1_.getBlock(p_149875_2_, p_149875_3_, p_149875_4_ + 1);
        final Block var8 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_);
        final Block var9 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_);
        final Block var10 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ - 1);
        final Block var11 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ - 1);
        final Block var12 = p_149875_1_.getBlock(p_149875_2_ + 1, p_149875_3_, p_149875_4_ + 1);
        final Block var13 = p_149875_1_.getBlock(p_149875_2_ - 1, p_149875_3_, p_149875_4_ + 1);
        final boolean var14 = var8 == this || var9 == this;
        final boolean var15 = var6 == this || var7 == this;
        final boolean var16 = var10 == this || var11 == this || var12 == this || var13 == this;
        for (int var17 = p_149875_2_ - 1; var17 <= p_149875_2_ + 1; ++var17) {
            for (int var18 = p_149875_4_ - 1; var18 <= p_149875_4_ + 1; ++var18) {
                final Block var19 = p_149875_1_.getBlock(var17, p_149875_3_ - 1, var18);
                float var20 = 0.0f;
                if (var19 == Blocks.farmland) {
                    var20 = 1.0f;
                    if (p_149875_1_.getBlockMetadata(var17, p_149875_3_ - 1, var18) > 0) {
                        var20 = 3.0f;
                    }
                }
                if (var17 != p_149875_2_ || var18 != p_149875_4_) {
                    var20 /= 4.0f;
                }
                var5 += var20;
            }
        }
        if (var16 || (var14 && var15)) {
            var5 /= 2.0f;
        }
        return var5;
    }
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        final int var2 = p_149741_1_ * 32;
        final int var3 = 255 - p_149741_1_ * 8;
        final int var4 = p_149741_1_ * 4;
        return var2 << 16 | var3 << 8 | var4;
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        return this.getRenderColor(p_149720_1_.getBlockMetadata(p_149720_2_, p_149720_3_, p_149720_4_));
    }
    
    @Override
    public void setBlockBoundsForItemRender() {
        final float var1 = 0.125f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.25f, 0.5f + var1);
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.field_149756_F = (p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_) * 2 + 2) / 16.0f;
        final float var5 = 0.125f;
        this.setBlockBounds(0.5f - var5, 0.0f, 0.5f - var5, 0.5f + var5, (float)this.field_149756_F, 0.5f + var5);
    }
    
    @Override
    public int getRenderType() {
        return 19;
    }
    
    public int func_149873_e(final IBlockAccess p_149873_1_, final int p_149873_2_, final int p_149873_3_, final int p_149873_4_) {
        final int var5 = p_149873_1_.getBlockMetadata(p_149873_2_, p_149873_3_, p_149873_4_);
        return (var5 < 7) ? -1 : ((p_149873_1_.getBlock(p_149873_2_ - 1, p_149873_3_, p_149873_4_) == this.field_149877_a) ? 0 : ((p_149873_1_.getBlock(p_149873_2_ + 1, p_149873_3_, p_149873_4_) == this.field_149877_a) ? 1 : ((p_149873_1_.getBlock(p_149873_2_, p_149873_3_, p_149873_4_ - 1) == this.field_149877_a) ? 2 : ((p_149873_1_.getBlock(p_149873_2_, p_149873_3_, p_149873_4_ + 1) == this.field_149877_a) ? 3 : -1))));
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
        if (!p_149690_1_.isClient) {
            Item var8 = null;
            if (this.field_149877_a == Blocks.pumpkin) {
                var8 = Items.pumpkin_seeds;
            }
            if (this.field_149877_a == Blocks.melon_block) {
                var8 = Items.melon_seeds;
            }
            for (int var9 = 0; var9 < 3; ++var9) {
                if (p_149690_1_.rand.nextInt(15) <= p_149690_5_) {
                    this.dropBlockAsItem_do(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(var8));
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return (this.field_149877_a == Blocks.pumpkin) ? Items.pumpkin_seeds : ((this.field_149877_a == Blocks.melon_block) ? Items.melon_seeds : Item.getItemById(0));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_disconnected");
        this.field_149876_b = p_149651_1_.registerIcon(this.getTextureName() + "_connected");
    }
    
    public IIcon func_149872_i() {
        return this.field_149876_b;
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
        this.func_149874_m(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
    }
}
