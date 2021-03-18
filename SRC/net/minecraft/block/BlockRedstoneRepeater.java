package net.minecraft.block;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;

public class BlockRedstoneRepeater extends BlockRedstoneDiode
{
    public static final double[] field_149973_b;
    private static final int[] field_149974_M;
    private static final String __OBFID = "CL_00000301";
    
    protected BlockRedstoneRepeater(final boolean p_i45424_1_) {
        super(p_i45424_1_);
    }
    
    @Override
    public boolean onBlockActivated(final World p_149727_1_, final int p_149727_2_, final int p_149727_3_, final int p_149727_4_, final EntityPlayer p_149727_5_, final int p_149727_6_, final float p_149727_7_, final float p_149727_8_, final float p_149727_9_) {
        final int var10 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
        int var11 = (var10 & 0xC) >> 2;
        var11 = (var11 + 1 << 2 & 0xC);
        p_149727_1_.setBlockMetadataWithNotify(p_149727_2_, p_149727_3_, p_149727_4_, var11 | (var10 & 0x3), 3);
        return true;
    }
    
    @Override
    protected int func_149901_b(final int p_149901_1_) {
        return BlockRedstoneRepeater.field_149974_M[(p_149901_1_ & 0xC) >> 2] * 2;
    }
    
    @Override
    protected BlockRedstoneDiode func_149906_e() {
        return Blocks.powered_repeater;
    }
    
    @Override
    protected BlockRedstoneDiode func_149898_i() {
        return Blocks.unpowered_repeater;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.repeater;
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Items.repeater;
    }
    
    @Override
    public int getRenderType() {
        return 15;
    }
    
    @Override
    public boolean func_149910_g(final IBlockAccess p_149910_1_, final int p_149910_2_, final int p_149910_3_, final int p_149910_4_, final int p_149910_5_) {
        return this.func_149902_h(p_149910_1_, p_149910_2_, p_149910_3_, p_149910_4_, p_149910_5_) > 0;
    }
    
    @Override
    protected boolean func_149908_a(final Block p_149908_1_) {
        return BlockRedstoneDiode.func_149909_d(p_149908_1_);
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (this.field_149914_a) {
            final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            final int var7 = BlockDirectional.func_149895_l(var6);
            final double var8 = p_149734_2_ + 0.5f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            final double var9 = p_149734_3_ + 0.4f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            final double var10 = p_149734_4_ + 0.5f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            double var11 = 0.0;
            double var12 = 0.0;
            if (p_149734_5_.nextInt(2) == 0) {
                switch (var7) {
                    case 0: {
                        var12 = -0.3125;
                        break;
                    }
                    case 1: {
                        var11 = 0.3125;
                        break;
                    }
                    case 2: {
                        var12 = 0.3125;
                        break;
                    }
                    case 3: {
                        var11 = -0.3125;
                        break;
                    }
                }
            }
            else {
                final int var13 = (var6 & 0xC) >> 2;
                switch (var7) {
                    case 0: {
                        var12 = BlockRedstoneRepeater.field_149973_b[var13];
                        break;
                    }
                    case 1: {
                        var11 = -BlockRedstoneRepeater.field_149973_b[var13];
                        break;
                    }
                    case 2: {
                        var12 = -BlockRedstoneRepeater.field_149973_b[var13];
                        break;
                    }
                    case 3: {
                        var11 = BlockRedstoneRepeater.field_149973_b[var13];
                        break;
                    }
                }
            }
            p_149734_1_.spawnParticle("reddust", var8 + var11, var9, var10 + var12, 0.0, 0.0, 0.0);
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        this.func_149911_e(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_);
    }
    
    static {
        field_149973_b = new double[] { -0.0625, 0.0625, 0.1875, 0.3125 };
        field_149974_M = new int[] { 1, 2, 3, 4 };
    }
}
