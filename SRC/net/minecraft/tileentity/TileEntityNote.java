package net.minecraft.tileentity;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class TileEntityNote extends TileEntity
{
    public byte field_145879_a;
    public boolean field_145880_i;
    private static final String __OBFID = "CL_00000362";
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setByte("note", this.field_145879_a);
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_145879_a = p_145839_1_.getByte("note");
        if (this.field_145879_a < 0) {
            this.field_145879_a = 0;
        }
        if (this.field_145879_a > 24) {
            this.field_145879_a = 24;
        }
    }
    
    public void func_145877_a() {
        this.field_145879_a = (byte)((this.field_145879_a + 1) % 25);
        this.onInventoryChanged();
    }
    
    public void func_145878_a(final World p_145878_1_, final int p_145878_2_, final int p_145878_3_, final int p_145878_4_) {
        if (p_145878_1_.getBlock(p_145878_2_, p_145878_3_ + 1, p_145878_4_).getMaterial() == Material.air) {
            final Material var5 = p_145878_1_.getBlock(p_145878_2_, p_145878_3_ - 1, p_145878_4_).getMaterial();
            byte var6 = 0;
            if (var5 == Material.rock) {
                var6 = 1;
            }
            if (var5 == Material.sand) {
                var6 = 2;
            }
            if (var5 == Material.glass) {
                var6 = 3;
            }
            if (var5 == Material.wood) {
                var6 = 4;
            }
            p_145878_1_.func_147452_c(p_145878_2_, p_145878_3_, p_145878_4_, Blocks.noteblock, var6, this.field_145879_a);
        }
    }
}
