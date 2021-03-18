package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;

public class BlockFalling extends Block
{
    public static boolean field_149832_M;
    private static final String __OBFID = "CL_00000240";
    
    public BlockFalling() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    public BlockFalling(final Material p_i45405_1_) {
        super(p_i45405_1_);
    }
    
    @Override
    public void onBlockAdded(final World p_149726_1_, final int p_149726_2_, final int p_149726_3_, final int p_149726_4_) {
        p_149726_1_.scheduleBlockUpdate(p_149726_2_, p_149726_3_, p_149726_4_, this, this.func_149738_a(p_149726_1_));
    }
    
    @Override
    public void onNeighborBlockChange(final World p_149695_1_, final int p_149695_2_, final int p_149695_3_, final int p_149695_4_, final Block p_149695_5_) {
        p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.func_149738_a(p_149695_1_));
    }
    
    @Override
    public void updateTick(final World p_149674_1_, final int p_149674_2_, final int p_149674_3_, final int p_149674_4_, final Random p_149674_5_) {
        if (!p_149674_1_.isClient) {
            this.func_149830_m(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_);
        }
    }
    
    private void func_149830_m(final World p_149830_1_, final int p_149830_2_, int p_149830_3_, final int p_149830_4_) {
        if (func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_) && p_149830_3_ >= 0) {
            final byte var8 = 32;
            if (!BlockFalling.field_149832_M && p_149830_1_.checkChunksExist(p_149830_2_ - var8, p_149830_3_ - var8, p_149830_4_ - var8, p_149830_2_ + var8, p_149830_3_ + var8, p_149830_4_ + var8)) {
                if (!p_149830_1_.isClient) {
                    final EntityFallingBlock var9 = new EntityFallingBlock(p_149830_1_, p_149830_2_ + 0.5f, p_149830_3_ + 0.5f, p_149830_4_ + 0.5f, this, p_149830_1_.getBlockMetadata(p_149830_2_, p_149830_3_, p_149830_4_));
                    this.func_149829_a(var9);
                    p_149830_1_.spawnEntityInWorld(var9);
                }
            }
            else {
                p_149830_1_.setBlockToAir(p_149830_2_, p_149830_3_, p_149830_4_);
                while (func_149831_e(p_149830_1_, p_149830_2_, p_149830_3_ - 1, p_149830_4_) && p_149830_3_ > 0) {
                    --p_149830_3_;
                }
                if (p_149830_3_ > 0) {
                    p_149830_1_.setBlock(p_149830_2_, p_149830_3_, p_149830_4_, this);
                }
            }
        }
    }
    
    protected void func_149829_a(final EntityFallingBlock p_149829_1_) {
    }
    
    @Override
    public int func_149738_a(final World p_149738_1_) {
        return 2;
    }
    
    public static boolean func_149831_e(final World p_149831_0_, final int p_149831_1_, final int p_149831_2_, final int p_149831_3_) {
        final Block var4 = p_149831_0_.getBlock(p_149831_1_, p_149831_2_, p_149831_3_);
        if (var4.blockMaterial == Material.air) {
            return true;
        }
        if (var4 == Blocks.fire) {
            return true;
        }
        final Material var5 = var4.blockMaterial;
        return var5 == Material.water || var5 == Material.lava;
    }
    
    public void func_149828_a(final World p_149828_1_, final int p_149828_2_, final int p_149828_3_, final int p_149828_4_, final int p_149828_5_) {
    }
}
