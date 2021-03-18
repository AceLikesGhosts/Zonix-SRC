package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class BlockSoulSand extends Block
{
    private static final String __OBFID = "CL_00000310";
    
    public BlockSoulSand() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(final World p_149668_1_, final int p_149668_2_, final int p_149668_3_, final int p_149668_4_) {
        final float var5 = 0.125f;
        return AxisAlignedBB.getBoundingBox(p_149668_2_, p_149668_3_, p_149668_4_, p_149668_2_ + 1, p_149668_3_ + 1 - var5, p_149668_4_ + 1);
    }
    
    @Override
    public void onEntityCollidedWithBlock(final World p_149670_1_, final int p_149670_2_, final int p_149670_3_, final int p_149670_4_, final Entity p_149670_5_) {
        p_149670_5_.motionX *= 0.4;
        p_149670_5_.motionZ *= 0.4;
    }
}
