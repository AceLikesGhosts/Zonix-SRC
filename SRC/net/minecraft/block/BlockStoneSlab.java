package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.item.*;
import java.util.*;

public class BlockStoneSlab extends BlockSlab
{
    public static final String[] field_150006_b;
    private IIcon field_150007_M;
    private static final String __OBFID = "CL_00000320";
    
    public BlockStoneSlab(final boolean p_i45431_1_) {
        super(p_i45431_1_, Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(int p_149691_1_, final int p_149691_2_) {
        final int var3 = p_149691_2_ & 0x7;
        if (this.field_150004_a && (p_149691_2_ & 0x8) != 0x0) {
            p_149691_1_ = 1;
        }
        return (var3 == 0) ? ((p_149691_1_ != 1 && p_149691_1_ != 0) ? this.field_150007_M : this.blockIcon) : ((var3 == 1) ? Blocks.sandstone.getBlockTextureFromSide(p_149691_1_) : ((var3 == 2) ? Blocks.planks.getBlockTextureFromSide(p_149691_1_) : ((var3 == 3) ? Blocks.cobblestone.getBlockTextureFromSide(p_149691_1_) : ((var3 == 4) ? Blocks.brick_block.getBlockTextureFromSide(p_149691_1_) : ((var3 == 5) ? Blocks.stonebrick.getIcon(p_149691_1_, 0) : ((var3 == 6) ? Blocks.nether_brick.getBlockTextureFromSide(1) : ((var3 == 7) ? Blocks.quartz_block.getBlockTextureFromSide(p_149691_1_) : this.blockIcon)))))));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("stone_slab_top");
        this.field_150007_M = p_149651_1_.registerIcon("stone_slab_side");
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.stone_slab);
    }
    
    @Override
    protected ItemStack createStackedBlock(final int p_149644_1_) {
        return new ItemStack(Item.getItemFromBlock(Blocks.stone_slab), 2, p_149644_1_ & 0x7);
    }
    
    @Override
    public String func_150002_b(int p_150002_1_) {
        if (p_150002_1_ < 0 || p_150002_1_ >= BlockStoneSlab.field_150006_b.length) {
            p_150002_1_ = 0;
        }
        return super.getUnlocalizedName() + "." + BlockStoneSlab.field_150006_b[p_150002_1_];
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        if (p_149666_1_ != Item.getItemFromBlock(Blocks.double_stone_slab)) {
            for (int var4 = 0; var4 <= 7; ++var4) {
                if (var4 != 2) {
                    p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
                }
            }
        }
    }
    
    static {
        field_150006_b = new String[] { "stone", "sand", "wood", "cobble", "brick", "smoothStoneBrick", "netherBrick", "quartz" };
    }
}
