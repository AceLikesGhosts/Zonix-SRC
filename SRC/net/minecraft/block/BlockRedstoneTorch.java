package net.minecraft.block;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;

public class BlockRedstoneTorch extends BlockTorch
{
    private boolean field_150113_a;
    private static Map field_150112_b;
    private static final String __OBFID = "CL_00000298";
    
    private boolean func_150111_a(final World p_150111_1_, final int p_150111_2_, final int p_150111_3_, final int p_150111_4_, final boolean p_150111_5_) {
        if (!BlockRedstoneTorch.field_150112_b.containsKey(p_150111_1_)) {
            BlockRedstoneTorch.field_150112_b.put(p_150111_1_, new ArrayList());
        }
        final List var6 = BlockRedstoneTorch.field_150112_b.get(p_150111_1_);
        if (p_150111_5_) {
            var6.add(new Toggle(p_150111_2_, p_150111_3_, p_150111_4_, p_150111_1_.getTotalWorldTime()));
        }
        int var7 = 0;
        for (int var8 = 0; var8 < var6.size(); ++var8) {
            final Toggle var9 = var6.get(var8);
            if (var9.field_150847_a == p_150111_2_ && var9.field_150845_b == p_150111_3_ && var9.field_150846_c == p_150111_4_ && ++var7 >= 8) {
                return true;
            }
        }
        return false;
    }
    
    protected BlockRedstoneTorch(final boolean p_i45423_1_) {
        this.field_150113_a = p_i45423_1_;
        this.setTickRandomly(true);
        this.setCreativeTab(null);
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 2;
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        if (p_149726_1_.getBlockMetadata(p_149726_2_, p_149726_3_, p_149726_4_) == 0) {
            super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        }
        if (this.field_150113_a) {
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ - 1, p_149726_4_, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_ + 1, p_149726_4_, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_ - 1, p_149726_3_, p_149726_4_, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_ + 1, p_149726_3_, p_149726_4_, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_, p_149726_4_ - 1, this);
            p_149726_1_.notifyBlocksOfNeighborChange(p_149726_2_, p_149726_3_, p_149726_4_ + 1, this);
        }
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        if (this.field_150113_a) {
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ - 1, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_ + 1, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ - 1, p_149749_3_, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_ + 1, p_149749_3_, p_149749_4_, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ - 1, this);
            p_149749_1_.notifyBlocksOfNeighborChange(p_149749_2_, p_149749_3_, p_149749_4_ + 1, this);
        }
    }
    
    @Override
    public int isProvidingWeakPower(final IBlockAccess p_149709_1_, final int p_149709_2_, final int p_149709_3_, final int p_149709_4_, final int p_149709_5_) {
        if (!this.field_150113_a) {
            return 0;
        }
        final int var6 = p_149709_1_.getBlockMetadata(p_149709_2_, p_149709_3_, p_149709_4_);
        return (var6 == 5 && p_149709_5_ == 1) ? 0 : ((var6 == 3 && p_149709_5_ == 3) ? 0 : ((var6 == 4 && p_149709_5_ == 2) ? 0 : ((var6 == 1 && p_149709_5_ == 5) ? 0 : ((var6 == 2 && p_149709_5_ == 4) ? 0 : 15))));
    }
    
    private boolean func_150110_m(final World p_150110_1_, final int p_150110_2_, final int p_150110_3_, final int p_150110_4_) {
        final int var5 = p_150110_1_.getBlockMetadata(p_150110_2_, p_150110_3_, p_150110_4_);
        return (var5 == 5 && p_150110_1_.getIndirectPowerOutput(p_150110_2_, p_150110_3_ - 1, p_150110_4_, 0)) || (var5 == 3 && p_150110_1_.getIndirectPowerOutput(p_150110_2_, p_150110_3_, p_150110_4_ - 1, 2)) || (var5 == 4 && p_150110_1_.getIndirectPowerOutput(p_150110_2_, p_150110_3_, p_150110_4_ + 1, 3)) || (var5 == 1 && p_150110_1_.getIndirectPowerOutput(p_150110_2_ - 1, p_150110_3_, p_150110_4_, 4)) || (var5 == 2 && p_150110_1_.getIndirectPowerOutput(p_150110_2_ + 1, p_150110_3_, p_150110_4_, 5));
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        final boolean var6 = this.func_150110_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        final List var7 = BlockRedstoneTorch.field_150112_b.get(p_149674_1_);
        while (var7 != null && !var7.isEmpty() && p_149674_1_.getTotalWorldTime() - var7.get(0).field_150844_d > 60L) {
            var7.remove(0);
        }
        if (this.field_150113_a) {
            if (var6) {
                p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.unlit_redstone_torch, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 3);
                if (this.func_150111_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, true)) {
                    p_149674_1_.playSoundEffect(p_149674_2_ + 0.5f, p_149674_3_ + 0.5f, p_149674_4_ + 0.5f, "random.fizz", 0.5f, 2.6f + (p_149674_1_.rand.nextFloat() - p_149674_1_.rand.nextFloat()) * 0.8f);
                    for (int var8 = 0; var8 < 5; ++var8) {
                        final double var9 = p_149674_2_ + p_149674_5_.nextDouble() * 0.6 + 0.2;
                        final double var10 = p_149674_3_ + p_149674_5_.nextDouble() * 0.6 + 0.2;
                        final double var11 = p_149674_4_ + p_149674_5_.nextDouble() * 0.6 + 0.2;
                        p_149674_1_.spawnParticle("smoke", var9, var10, var11, 0.0, 0.0, 0.0);
                    }
                }
            }
        }
        else if (!var6 && !this.func_150111_a(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, false)) {
            p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, Blocks.redstone_torch, p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_), 3);
        }
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        if (!this.func_150108_b(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_)) {
            final boolean var6 = this.func_150110_m(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
            if ((this.field_150113_a && var6) || (!this.field_150113_a && !var6)) {
                p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.func_149738_a(p_149695_1_));
            }
        }
    }
    
    @Override
    public int isProvidingStrongPower(final IBlockAccess p_149748_1_, final int p_149748_2_, final int p_149748_3_, final int p_149748_4_, final int p_149748_5_) {
        return (p_149748_5_ == 0) ? this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public boolean canProvidePower() {
        return true;
    }
    
    @Override
    public void randomDisplayTick(final World p_149734_1_, final int p_149734_2_, final int p_149734_3_, final int p_149734_4_, final Random p_149734_5_) {
        if (this.field_150113_a) {
            final int var6 = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            final double var7 = p_149734_2_ + 0.5f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            final double var8 = p_149734_3_ + 0.7f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            final double var9 = p_149734_4_ + 0.5f + (p_149734_5_.nextFloat() - 0.5f) * 0.2;
            final double var10 = 0.2199999988079071;
            final double var11 = 0.27000001072883606;
            if (var6 == 1) {
                p_149734_1_.spawnParticle("reddust", var7 - var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            }
            else if (var6 == 2) {
                p_149734_1_.spawnParticle("reddust", var7 + var11, var8 + var10, var9, 0.0, 0.0, 0.0);
            }
            else if (var6 == 3) {
                p_149734_1_.spawnParticle("reddust", var7, var8 + var10, var9 - var11, 0.0, 0.0, 0.0);
            }
            else if (var6 == 4) {
                p_149734_1_.spawnParticle("reddust", var7, var8 + var10, var9 + var11, 0.0, 0.0, 0.0);
            }
            else {
                p_149734_1_.spawnParticle("reddust", var7, var8, var9, 0.0, 0.0, 0.0);
            }
        }
    }
    
    @Override
    public Item getItem(final World p_149694_1_, final int p_149694_2_, final int p_149694_3_, final int p_149694_4_) {
        return Item.getItemFromBlock(Blocks.redstone_torch);
    }
    
    @Override
    public boolean func_149667_c(final Block p_149667_1_) {
        return p_149667_1_ == Blocks.unlit_redstone_torch || p_149667_1_ == Blocks.redstone_torch;
    }
    
    static {
        BlockRedstoneTorch.field_150112_b = new HashMap();
    }
    
    static class Toggle
    {
        int field_150847_a;
        int field_150845_b;
        int field_150846_c;
        long field_150844_d;
        private static final String __OBFID = "CL_00000299";
        
        public Toggle(final int p_i45422_1_, final int p_i45422_2_, final int p_i45422_3_, final long p_i45422_4_) {
            this.field_150847_a = p_i45422_1_;
            this.field_150845_b = p_i45422_2_;
            this.field_150846_c = p_i45422_3_;
            this.field_150844_d = p_i45422_4_;
        }
    }
}
