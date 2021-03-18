package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.item.*;
import org.apache.logging.log4j.*;

public class BlockGrass extends Block implements IGrowable
{
    private static final Logger logger;
    private IIcon field_149991_b;
    private IIcon field_149993_M;
    private IIcon field_149994_N;
    private static final String __OBFID = "CL_00000251";
    
    protected BlockGrass() {
        super(Material.grass);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ == 1) ? this.field_149991_b : ((p_149691_1_ == 0) ? Blocks.dirt.getBlockTextureFromSide(p_149691_1_) : this.blockIcon);
    }
    
    @Override
    public IIcon getIcon(final IBlockAccess p_149673_1_, final int p_149673_2_, final int p_149673_3_, final int p_149673_4_, final int p_149673_5_) {
        if (p_149673_5_ == 1) {
            return this.field_149991_b;
        }
        if (p_149673_5_ == 0) {
            return Blocks.dirt.getBlockTextureFromSide(p_149673_5_);
        }
        final Material var6 = p_149673_1_.getBlock(p_149673_2_, p_149673_3_ + 1, p_149673_4_).getMaterial();
        return (var6 != Material.field_151597_y && var6 != Material.craftedSnow) ? this.blockIcon : this.field_149993_M;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_149991_b = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.field_149993_M = p_149651_1_.registerIcon(this.getTextureName() + "_side_snowed");
        this.field_149994_N = p_149651_1_.registerIcon(this.getTextureName() + "_side_overlay");
    }
    
    @Override
    public int getBlockColor() {
        final double var1 = 0.5;
        final double var2 = 1.0;
        return ColorizerGrass.getGrassColor(var1, var2);
    }
    
    @Override
    public int getRenderColor(final int p_149741_1_) {
        return this.getBlockColor();
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                final int var10 = p_149720_1_.getBiomeGenForCoords(p_149720_2_ + var9, p_149720_4_ + var8).getBiomeGrassColor(p_149720_2_ + var9, p_149720_3_, p_149720_4_ + var8);
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += (var10 & 0xFF);
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | (var7 / 9 & 0xFF);
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
                        p_149674_1_.setBlock(var7, var8, var9, Blocks.grass);
                    }
                }
            }
        }
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Blocks.dirt.getItemDropped(0, p_149650_2_, p_149650_3_);
    }
    
    public static IIcon func_149990_e() {
        return Blocks.grass.field_149994_N;
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        return true;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return true;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        int var6 = 0;
    Label_0003:
        while (var6 < 128) {
            int var7 = p_149853_3_;
            int var8 = p_149853_4_ + 1;
            int var9 = p_149853_5_;
            while (true) {
                for (int var10 = 0; var10 < var6 / 16; ++var10) {
                    var7 += p_149853_2_.nextInt(3) - 1;
                    var8 += (p_149853_2_.nextInt(3) - 1) * p_149853_2_.nextInt(3) / 2;
                    var9 += p_149853_2_.nextInt(3) - 1;
                    if (p_149853_1_.getBlock(var7, var8 - 1, var9) != Blocks.grass || p_149853_1_.getBlock(var7, var8, var9).isNormalCube()) {
                        ++var6;
                        continue Label_0003;
                    }
                }
                if (p_149853_1_.getBlock(var7, var8, var9).blockMaterial != Material.air) {
                    continue;
                }
                if (p_149853_2_.nextInt(8) != 0) {
                    if (Blocks.tallgrass.canBlockStay(p_149853_1_, var7, var8, var9)) {
                        p_149853_1_.setBlock(var7, var8, var9, Blocks.tallgrass, 1, 3);
                    }
                    continue;
                }
                else {
                    final String var11 = p_149853_1_.getBiomeGenForCoords(var7, var9).func_150572_a(p_149853_2_, var7, var8, var9);
                    BlockGrass.logger.debug("Flower in " + p_149853_1_.getBiomeGenForCoords(var7, var9).biomeName + ": " + var11);
                    final BlockFlower var12 = BlockFlower.func_149857_e(var11);
                    if (var12 != null && var12.canBlockStay(p_149853_1_, var7, var8, var9)) {
                        final int var13 = BlockFlower.func_149856_f(var11);
                        p_149853_1_.setBlock(var7, var8, var9, var12, var13, 3);
                    }
                    continue;
                }
                break;
            }
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
