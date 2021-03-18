package net.minecraft.block;

import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.util.*;

public class BlockOldLog extends BlockLog
{
    public static final String[] field_150168_M;
    private static final String __OBFID = "CL_00000281";
    
    @Override
    public void getSubBlocks(final Item p_149666_1_, final CreativeTabs p_149666_2_, final List p_149666_3_) {
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 0));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 1));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 2));
        p_149666_3_.add(new ItemStack(p_149666_1_, 1, 3));
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.field_150167_a = new IIcon[BlockOldLog.field_150168_M.length];
        this.field_150166_b = new IIcon[BlockOldLog.field_150168_M.length];
        for (int var2 = 0; var2 < this.field_150167_a.length; ++var2) {
            this.field_150167_a[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockOldLog.field_150168_M[var2]);
            this.field_150166_b[var2] = p_149651_1_.registerIcon(this.getTextureName() + "_" + BlockOldLog.field_150168_M[var2] + "_top");
        }
    }
    
    static {
        field_150168_M = new String[] { "oak", "spruce", "birch", "jungle" };
    }
}
