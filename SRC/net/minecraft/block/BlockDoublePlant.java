package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class BlockDoublePlant extends BlockBush implements IGrowable
{
    public static final String[] field_149892_a;
    private IIcon[] field_149893_M;
    private IIcon[] field_149894_N;
    public IIcon[] field_149891_b;
    private static final String __OBFID = "CL_00000231";
    
    public BlockDoublePlant() {
        super(Material.plants);
        this.setHardness(0.0f);
        this.setStepSound(BlockDoublePlant.soundTypeGrass);
        this.setBlockName("doublePlant");
    }
    
    @Override
    public int getRenderType() {
        return 40;
    }
    
    @Override
    public void setBlockBoundsBasedOnState(final IBlockAccess p_149719_1_, final int p_149719_2_, final int p_149719_3_, final int p_149719_4_) {
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public int func_149885_e(final IBlockAccess p_149885_1_, final int p_149885_2_, final int p_149885_3_, final int p_149885_4_) {
        final int var5 = p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_, p_149885_4_);
        return func_149887_c(var5) ? (p_149885_1_.getBlockMetadata(p_149885_2_, p_149885_3_ - 1, p_149885_4_) & 0x7) : (var5 & 0x7);
    }
    
    @Override
    public boolean canPlaceBlockAt(final World p_149742_1_, final int p_149742_2_, final int p_149742_3_, final int p_149742_4_) {
        return super.canPlaceBlockAt(p_149742_1_, p_149742_2_, p_149742_3_, p_149742_4_) && p_149742_1_.isAirBlock(p_149742_2_, p_149742_3_ + 1, p_149742_4_);
    }
    
    @Override
    protected void func_149855_e(final World p_149855_1_, final int p_149855_2_, final int p_149855_3_, final int p_149855_4_) {
        if (!this.canBlockStay(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_)) {
            final int var5 = p_149855_1_.getBlockMetadata(p_149855_2_, p_149855_3_, p_149855_4_);
            if (!func_149887_c(var5)) {
                this.dropBlockAsItem(p_149855_1_, p_149855_2_, p_149855_3_, p_149855_4_, var5, 0);
                if (p_149855_1_.getBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_) == this) {
                    p_149855_1_.setBlock(p_149855_2_, p_149855_3_ + 1, p_149855_4_, Blocks.air, 0, 2);
                }
            }
            p_149855_1_.setBlock(p_149855_2_, p_149855_3_, p_149855_4_, Blocks.air, 0, 2);
        }
    }
    
    @Override
    public boolean canBlockStay(final World p_149718_1_, final int p_149718_2_, final int p_149718_3_, final int p_149718_4_) {
        final int var5 = p_149718_1_.getBlockMetadata(p_149718_2_, p_149718_3_, p_149718_4_);
        return func_149887_c(var5) ? (p_149718_1_.getBlock(p_149718_2_, p_149718_3_ - 1, p_149718_4_) == this) : (p_149718_1_.getBlock(p_149718_2_, p_149718_3_ + 1, p_149718_4_) == this && super.canBlockStay(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_));
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        if (func_149887_c(p_149650_1_)) {
            return null;
        }
        final int var4 = func_149890_d(p_149650_1_);
        return (var4 != 3 && var4 != 2) ? Item.getItemFromBlock(this) : null;
    }
    
    @Override
    public int damageDropped(final int p_149692_1_) {
        return func_149887_c(p_149692_1_) ? 0 : (p_149692_1_ & 0x7);
    }
    
    public static boolean func_149887_c(final int p_149887_0_) {
        return (p_149887_0_ & 0x8) != 0x0;
    }
    
    public static int func_149890_d(final int p_149890_0_) {
        return p_149890_0_ & 0x7;
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return func_149887_c(p_149691_2_) ? this.field_149893_M[0] : this.field_149893_M[p_149691_2_ & 0x7];
    }
    
    public IIcon func_149888_a(final boolean p_149888_1_, final int p_149888_2_) {
        return p_149888_1_ ? this.field_149894_N[p_149888_2_] : this.field_149893_M[p_149888_2_];
    }
    
    @Override
    public int colorMultiplier(final IBlockAccess p_149720_1_, final int p_149720_2_, final int p_149720_3_, final int p_149720_4_) {
        final int var5 = this.func_149885_e(p_149720_1_, p_149720_2_, p_149720_3_, p_149720_4_);
        return (var5 != 2 && var5 != 3) ? 16777215 : p_149720_1_.getBiomeGenForCoords(p_149720_2_, p_149720_4_).getBiomeGrassColor(p_149720_2_, p_149720_3_, p_149720_4_);
    }
    
    public void func_149889_c(final World p_149889_1_, final int p_149889_2_, final int p_149889_3_, final int p_149889_4_, final int p_149889_5_, final int p_149889_6_) {
        p_149889_1_.setBlock(p_149889_2_, p_149889_3_, p_149889_4_, this, p_149889_5_, p_149889_6_);
        p_149889_1_.setBlock(p_149889_2_, p_149889_3_ + 1, p_149889_4_, this, 8, p_149889_6_);
    }
    
    @Override
    public void onBlockPlacedBy(final World p_149689_1_, final int p_149689_2_, final int p_149689_3_, final int p_149689_4_, final EntityLivingBase p_149689_5_, final ItemStack p_149689_6_) {
        final int var7 = ((MathHelper.floor_double(p_149689_5_.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3) + 2) % 4;
        p_149689_1_.setBlock(p_149689_2_, p_149689_3_ + 1, p_149689_4_, this, 0x8 | var7, 2);
    }
    
    @Override
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        if (p_149636_1_.isClient || p_149636_2_.getCurrentEquippedItem() == null || p_149636_2_.getCurrentEquippedItem().getItem() != Items.shears || func_149887_c(p_149636_6_) || !this.func_149886_b(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_, p_149636_2_)) {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }
    
    @Override
    public void onBlockHarvested(final World p_149681_1_, final int p_149681_2_, final int p_149681_3_, final int p_149681_4_, final int p_149681_5_, final EntityPlayer p_149681_6_) {
        if (func_149887_c(p_149681_5_)) {
            if (p_149681_1_.getBlock(p_149681_2_, p_149681_3_ - 1, p_149681_4_) == this) {
                if (!p_149681_6_.capabilities.isCreativeMode) {
                    final int var7 = p_149681_1_.getBlockMetadata(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                    final int var8 = func_149890_d(var7);
                    if (var8 != 3 && var8 != 2) {
                        p_149681_1_.func_147480_a(p_149681_2_, p_149681_3_ - 1, p_149681_4_, true);
                    }
                    else {
                        if (!p_149681_1_.isClient && p_149681_6_.getCurrentEquippedItem() != null && p_149681_6_.getCurrentEquippedItem().getItem() == Items.shears) {
                            this.func_149886_b(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, var7, p_149681_6_);
                        }
                        p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                    }
                }
                else {
                    p_149681_1_.setBlockToAir(p_149681_2_, p_149681_3_ - 1, p_149681_4_);
                }
            }
        }
        else if (p_149681_6_.capabilities.isCreativeMode && p_149681_1_.getBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_) == this) {
            p_149681_1_.setBlock(p_149681_2_, p_149681_3_ + 1, p_149681_4_, Blocks.air, 0, 2);
        }
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }
    
    private boolean func_149886_b(final World p_149886_1_, final int p_149886_2_, final int p_149886_3_, final int p_149886_4_, final int p_149886_5_, final EntityPlayer p_149886_6_) {
        final int var7 = func_149890_d(p_149886_5_);
        if (var7 != 3 && var7 != 2) {
            return false;
        }
        p_149886_6_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
        byte var8 = 1;
        if (var7 == 3) {
            var8 = 2;
        }
        this.dropBlockAsItem_do(p_149886_1_, p_149886_2_, p_149886_3_, p_149886_4_, new ItemStack(Blocks.tallgrass, 2, var8));
        return true;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_149893_M = new IIcon[BlockDoublePlant.field_149892_a.length];
        this.field_149894_N = new IIcon[BlockDoublePlant.field_149892_a.length];
        for (int var2 = 0; var2 < this.field_149893_M.length; ++var2) {
            this.field_149893_M[var2] = p_149651_1_.registerIcon("double_plant_" + BlockDoublePlant.field_149892_a[var2] + "_bottom");
            this.field_149894_N[var2] = p_149651_1_.registerIcon("double_plant_" + BlockDoublePlant.field_149892_a[var2] + "_top");
        }
        (this.field_149891_b = new IIcon[2])[0] = p_149651_1_.registerIcon("double_plant_sunflower_front");
        this.field_149891_b[1] = p_149651_1_.registerIcon("double_plant_sunflower_back");
    }
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        for (int var4 = 0; var4 < this.field_149893_M.length; ++var4) {
            p_149666_3_.add(new ItemStack(p_149666_1_, 1, var4));
        }
    }
    
    @Override
    public int getDamageValue(final World p_149643_1_, final int p_149643_2_, final int p_149643_3_, final int p_149643_4_) {
        final int var5 = p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_, p_149643_4_);
        return func_149887_c(var5) ? func_149890_d(p_149643_1_.getBlockMetadata(p_149643_2_, p_149643_3_ - 1, p_149643_4_)) : func_149890_d(var5);
    }
    
    @Override
    public boolean func_149851_a(final World p_149851_1_, final int p_149851_2_, final int p_149851_3_, final int p_149851_4_, final boolean p_149851_5_) {
        final int var6 = this.func_149885_e(p_149851_1_, p_149851_2_, p_149851_3_, p_149851_4_);
        return var6 != 2 && var6 != 3;
    }
    
    @Override
    public boolean func_149852_a(final World p_149852_1_, final Random p_149852_2_, final int p_149852_3_, final int p_149852_4_, final int p_149852_5_) {
        return true;
    }
    
    @Override
    public void func_149853_b(final World p_149853_1_, final Random p_149853_2_, final int p_149853_3_, final int p_149853_4_, final int p_149853_5_) {
        final int var6 = this.func_149885_e(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_);
        this.dropBlockAsItem_do(p_149853_1_, p_149853_3_, p_149853_4_, p_149853_5_, new ItemStack(this, 1, var6));
    }
    
    static {
        field_149892_a = new String[] { "sunflower", "syringa", "grass", "fern", "rose", "paeonia" };
    }
}
