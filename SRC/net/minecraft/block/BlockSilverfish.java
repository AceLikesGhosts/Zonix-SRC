package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.item.*;
import java.util.*;

public class BlockSilverfish extends Block
{
    public static final String[] field_150198_a;
    private static final String __OBFID = "CL_00000271";
    
    public BlockSilverfish() {
        super(Material.field_151571_B);
        this.setHardness(0.0f);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        switch (p_149691_2_) {
            case 1: {
                return Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_);
            }
            case 2: {
                return Blocks.stonebrick.getBlockTextureFromSide(p_149691_1_);
            }
            case 3: {
                return Blocks.stonebrick.getIcon(p_149691_1_, 1);
            }
            case 4: {
                return Blocks.stonebrick.getIcon(p_149691_1_, 2);
            }
            case 5: {
                return Blocks.stonebrick.getIcon(p_149691_1_, 3);
            }
            default: {
                return Blocks.stone.getBlockTextureFromSide(p_149691_1_);
            }
        }
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
    }
    
    @Override
    public void onBlockDestroyedByPlayer(final World p_149664_1_, final int p_149664_2_, final int p_149664_3_, final int p_149664_4_, final int p_149664_5_) {
        if (!p_149664_1_.isClient) {
            final EntitySilverfish var6 = new EntitySilverfish(p_149664_1_);
            var6.setLocationAndAngles(p_149664_2_ + 0.5, p_149664_3_, p_149664_4_ + 0.5, 0.0f, 0.0f);
            p_149664_1_.spawnEntityInWorld(var6);
            var6.spawnExplosionParticle();
        }
        super.onBlockDestroyedByPlayer(p_149664_1_, p_149664_2_, p_149664_3_, p_149664_4_, p_149664_5_);
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 0;
    }
    
    public static boolean func_150196_a(final Block p_150196_0_) {
        return p_150196_0_ == Blocks.stone || p_150196_0_ == Blocks.cobblestone || p_150196_0_ == Blocks.stonebrick;
    }
    
    public static int func_150195_a(final Block p_150195_0_, final int p_150195_1_) {
        if (p_150195_1_ == 0) {
            if (p_150195_0_ == Blocks.cobblestone) {
                return 1;
            }
            if (p_150195_0_ == Blocks.stonebrick) {
                return 2;
            }
        }
        else if (p_150195_0_ == Blocks.stonebrick) {
            switch (p_150195_1_) {
                case 1: {
                    return 3;
                }
                case 2: {
                    return 4;
                }
                case 3: {
                    return 5;
                }
            }
        }
        return 0;
    }
    
    public static ImmutablePair func_150197_b(final int p_150197_0_) {
        switch (p_150197_0_) {
            case 1: {
                return new ImmutablePair((Object)Blocks.cobblestone, (Object)0);
            }
            case 2: {
                return new ImmutablePair((Object)Blocks.stonebrick, (Object)0);
            }
            case 3: {
                return new ImmutablePair((Object)Blocks.stonebrick, (Object)1);
            }
            case 4: {
                return new ImmutablePair((Object)Blocks.stonebrick, (Object)2);
            }
            case 5: {
                return new ImmutablePair((Object)Blocks.stonebrick, (Object)3);
            }
            default: {
                return new ImmutablePair((Object)Blocks.stone, (Object)0);
            }
        }
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        switch (p_149644_1_) {
            case 1: {
                return new ItemStack(Blocks.cobblestone);
            }
            case 2: {
                return new ItemStack(Blocks.stonebrick);
            }
            case 3: {
                return new ItemStack(Blocks.stonebrick, 1, 1);
            }
            case 4: {
                return new ItemStack(Blocks.stonebrick, 1, 2);
            }
            case 5: {
                return new ItemStack(Blocks.stonebrick, 1, 3);
            }
            default: {
                return new ItemStack(Blocks.stone);
            }
        }
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World p_149690_1_, final int p_149690_2_, final int p_149690_3_, final int p_149690_4_, final int p_149690_5_, final float p_149690_6_, final int p_149690_7_) {
        if (!p_149690_1_.isClient) {
            final EntitySilverfish var8 = new EntitySilverfish(p_149690_1_);
            var8.setLocationAndAngles(p_149690_2_ + 0.5, p_149690_3_, p_149690_4_ + 0.5, 0.0f, 0.0f);
            p_149690_1_.spawnEntityInWorld(var8);
            var8.spawnExplosionParticle();
        }
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        return p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < BlockSilverfish.field_150198_a.length; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    static {
        field_150198_a = new String[] { "stone", "cobble", "brick", "mossybrick", "crackedbrick", "chiseledbrick" };
    }
}
