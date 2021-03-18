package net.minecraft.block;

import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;

public class BlockDeadBush extends BlockBush
{
    private static final String __OBFID = "CL_00000224";
    
    protected BlockDeadBush() {
        super(Material.vine);
        final float var1 = 0.4f;
        this.setBlockBounds(0.5f - var1, 0.0f, 0.5f - var1, 0.5f + var1, 0.8f, 0.5f + var1);
    }
    
    @Override
    protected boolean func_149854_a(final Block p_149854_1_) {
        return p_149854_1_ == Blocks.sand || p_149854_1_ == Blocks.hardened_clay || p_149854_1_ == Blocks.stained_hardened_clay || p_149854_1_ == Blocks.dirt;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return null;
    }
    
    @Override
    public void harvestBlock(final World p_149636_1_, final EntityPlayer p_149636_2_, final int p_149636_3_, final int p_149636_4_, final int p_149636_5_, final int p_149636_6_) {
        if (!p_149636_1_.isClient && p_149636_2_.getCurrentEquippedItem() != null && p_149636_2_.getCurrentEquippedItem().getItem() == Items.shears) {
            p_149636_2_.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
            this.dropBlockAsItem_do(p_149636_1_, p_149636_3_, p_149636_4_, p_149636_5_, new ItemStack(Blocks.deadbush, 1, p_149636_6_));
        }
        else {
            super.harvestBlock(p_149636_1_, p_149636_2_, p_149636_3_, p_149636_4_, p_149636_5_, p_149636_6_);
        }
    }
}
