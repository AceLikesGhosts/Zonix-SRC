package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;

public class BlockMycelium extends Block
{
    private IIcon field_150200_a;
    private IIcon field_150199_b;
    private static final String __OBFID = "CL_00000273";
    
    protected BlockMycelium() {
        super(Material.grass);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_150200_a : ((p_149691_1_ == 0) ? Blocks.dirt.getBlockTextureFromSide(p_149691_1_) : this.blockIcon);
    }
    
    @Override
    public IIcon getIcon(final IBlockAccess p_149673_1_, final int p_149673_2_, final int p_149673_3_, final int p_149673_4_, final int p_149673_5_) {
        if (p_149673_5_ == 1) {
            return this.field_150200_a;
        }
        if (p_149673_5_ == 0) {
            return Blocks.dirt.getBlockTextureFromSide(p_149673_5_);
        }
        final Material var6 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
        return (var6 != Material.field_151597_y && var6 != Material.craftedSnow) ? this.blockIcon : this.field_150199_b;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150200_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_150199_b = p_149651_1_.registerIcon("grass_side_snowed");
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) < 4 && p_149674_1_.getBlock(p_149674_2_, p_149674_3_ + 1, p_149674_4_).getLightOpacity() > 2) {
                p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.dirt);
            }
            else if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9) {
                for (int var6 = 0; var6 < 4; ++var6) {
                    final int var7 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
                    final int var8 = p_149674_3_ + p_149674_5_.nextInt(5) - 3;
                    final int var9 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
                    final Block var10 = p_149674_1_.getBlock(var7, var8 + 1, var9);
                    if (p_149674_1_.getBlock(var7, var8, var9) == Blocks.dirt && p_149674_1_.getBlockMetadata(var7, var8, var9) == 0 && p_149674_1_.getBlockLightValue(var7, var8 + 1, var9) >= 4 && var10.getLightOpacity() <= 2) {
                        p_149674_1_.setBlock(var7, var8, var9, this);
                    }
                }
            }
        }
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
        if (p_149734_5_.nextInt(10) == 0) {
            p_149734_1_.spawnParticle("townaura", p_149734_2_ + p_149734_5_.nextFloat(), p_149734_3_ + 1.1f, p_149734_4_ + p_149734_5_.nextFloat(), 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
    }
}
