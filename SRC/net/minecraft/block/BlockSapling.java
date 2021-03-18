package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.gen.feature.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;

public class BlockSapling extends BlockBush implements IGrowable
{
    public static final String[] field_149882_a;
    private static final IIcon[] field_149881_b;
    private static final String __OBFID = "CL_00000305";
    
    protected BlockSapling() {
        final float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, var1 * 2.0f, 0.5f + var1);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            super.updateTick(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9 && p_149674_5_.nextInt(7) == 0) {
                this.func_149879_c(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, p_149674_5_);
            }
        }
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, int p_149691_2_) {
        p_149691_2_ &= 0x7;
        return BlockSapling.field_149881_b[MathHelper.clamp_int(p_149691_2_, 0, 5)];
    }
    
    public void func_149879_c(final World p_149879_1_, final int p_149879_2_, final int p_149879_3_, final int p_149879_4_, final Random p_149879_5_) {
        final int var6 = p_149879_1_.getBlockMetadata(p_149879_2_, p_149879_3_, p_149879_4_);
        if ((var6 & 0x8) == 0x0) {
            p_149879_1_.setBlockMetadataWithNotify(p_149879_2_, p_149879_3_, p_149879_4_, var6 | 0x8, 4);
        }
        else {
            this.func_149878_d(p_149879_1_, p_149879_2_, p_149879_3_, p_149879_4_, p_149879_5_);
        }
    }
    
    public void func_149878_d(final World p_149878_1_, final int p_149878_2_, final int p_149878_3_, final int p_149878_4_, final Random p_149878_5_) {
        final int var6 = p_149878_1_.getBlockMetadata(p_149878_2_, p_149878_3_, p_149878_4_) & 0x7;
        Object var7 = (p_149878_5_.nextInt(10) == 0) ? new WorldGenBigTree(true) : new WorldGenTrees(true);
        int var8 = 0;
        int var9 = 0;
        boolean var10 = false;
        switch (var6) {
            case 1: {
            Label_0230:
                for (var8 = 0; var8 >= -1; --var8) {
                    for (var9 = 0; var9 >= -1; --var9) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 1) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 1)) {
                            var7 = new WorldGenMegaPineTree(false, p_149878_5_.nextBoolean());
                            var10 = true;
                            break Label_0230;
                        }
                    }
                }
                if (!var10) {
                    var9 = 0;
                    var8 = 0;
                    var7 = new WorldGenTaiga2(true);
                    break;
                }
                break;
            }
            case 2: {
                var7 = new WorldGenForest(true, false);
                break;
            }
            case 3: {
            Label_0404:
                for (var8 = 0; var8 >= -1; --var8) {
                    for (var9 = 0; var9 >= -1; --var9) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 3) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 3)) {
                            var7 = new WorldGenMegaJungle(true, 10, 20, 3, 3);
                            var10 = true;
                            break Label_0404;
                        }
                    }
                }
                if (!var10) {
                    var9 = 0;
                    var8 = 0;
                    var7 = new WorldGenTrees(true, 4 + p_149878_5_.nextInt(7), 3, 3, false);
                    break;
                }
                break;
            }
            case 4: {
                var7 = new WorldGenSavannaTree(true);
                break;
            }
            case 5: {
            Label_0583:
                for (var8 = 0; var8 >= -1; --var8) {
                    for (var9 = 0; var9 >= -1; --var9) {
                        if (this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, 5) && this.func_149880_a(p_149878_1_, p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, 5)) {
                            var7 = new WorldGenCanopyTree(true);
                            var10 = true;
                            break Label_0583;
                        }
                    }
                }
                if (!var10) {
                    return;
                }
                break;
            }
        }
        final Block var11 = Blocks.air;
        if (var10) {
            p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
            p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, var11, 0, 4);
        }
        else {
            p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, var11, 0, 4);
        }
        if (!((WorldGenerator)var7).generate(p_149878_1_, p_149878_5_, p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9)) {
            if (var10) {
                p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
                p_149878_1_.setBlock(p_149878_2_ + var8 + 1, p_149878_3_, p_149878_4_ + var9 + 1, this, var6, 4);
            }
            else {
                p_149878_1_.setBlock(p_149878_2_, p_149878_3_, p_149878_4_, this, var6, 4);
            }
        }
    }
    
    public boolean func_149880_a(final World p_149880_1_, final int p_149880_2_, final int p_149880_3_, final int p_149880_4_, final int p_149880_5_) {
        return p_149880_1_.getBlock(p_149880_2_, p_149880_3_, p_149880_4_) == this && (p_149880_1_.getBlockMetadata(p_149880_2_, p_149880_3_, p_149880_4_) & 0x7) == p_149880_5_;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return MathHelper.clamp_int(p_149692_1_ & 0x7, 0, 5);
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 4));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 5));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        for (int var2 = 0; var2 < BlockSapling.field_149881_b.length; ++var2) {
            BlockSapling.field_149881_b[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockSapling.field_149882_a[var2]);
        }
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        return true;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return p_149852_1_.rand.nextFloat() < 0.45;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        this.func_149879_c(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, p_149853_2_);
    }
    
    static {
        field_149882_a = new String[] { "oak", "spruce", "birch", "jungle", "acacia", "roofed_oak" };
        field_149881_b = new IIcon[BlockSapling.field_149882_a.length];
    }
}
