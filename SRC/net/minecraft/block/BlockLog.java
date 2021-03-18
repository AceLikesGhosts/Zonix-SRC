package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.world.*;

public abstract class BlockLog extends BlockRotatedPillar
{
    protected IIcon[] field_150167_a;
    protected IIcon[] field_150166_b;
    private static final String __OBFID = "CL_00000266";
    
    public BlockLog() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(2.0f);
        this.setStepSound(BlockLog.soundTypeWood);
    }
    
    public static int func_150165_c(final int p_150165_0_) {
        return p_150165_0_ & 0x3;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 1;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Item.getItemFromBlock(this);
    }
    
    @Override
    public void breakBlock(final World p_149749_1_, final int p_149749_2_, final int p_149749_3_, final int p_149749_4_, final Block p_149749_5_, final int p_149749_6_) {
        final byte var7 = 4;
        final int var8 = var7 + 1;
        if (p_149749_1_.checkChunksExist(p_149749_2_ - var8, p_149749_3_ - var8, p_149749_4_ - var8, p_149749_2_ + var8, p_149749_3_ + var8, p_149749_4_ + var8)) {
            for (int var9 = -var7; var9 <= var7; ++var9) {
                for (int var10 = -var7; var10 <= var7; ++var10) {
                    for (int var11 = -var7; var11 <= var7; ++var11) {
                        if (p_149749_1_.getBlock(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11).getMaterial() == Material.leaves) {
                            final int var12 = p_149749_1_.getBlockMetadata(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11);
                            if ((var12 & 0x8) == 0x0) {
                                p_149749_1_.setBlockMetadataWithNotify(p_149749_2_ + var9, p_149749_3_ + var10, p_149749_4_ + var11, var12 | 0x8, 4);
                            }
                        }
                    }
                }
            }
        }
    }
    
    @Override
    protected IIcon func_150163_b(final int p_150163_1_) {
        return this.field_150167_a[p_150163_1_ % this.field_150167_a.length];
    }
    
    @Override
    protected IIcon func_150161_d(final int p_150161_1_) {
        return this.field_150166_b[p_150161_1_ % this.field_150166_b.length];
    }
}
