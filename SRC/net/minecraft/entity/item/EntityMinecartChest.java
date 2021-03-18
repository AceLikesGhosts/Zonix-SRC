package net.minecraft.entity.item;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;

public class EntityMinecartChest extends EntityMinecartContainer
{
    private static final String __OBFID = "CL_00001671";
    
    public EntityMinecartChest(final World p_i1714_1_) {
        super(p_i1714_1_);
    }
    
    public EntityMinecartChest(final World p_i1715_1_, final double p_i1715_2_, final double p_i1715_4_, final double p_i1715_6_) {
        super(p_i1715_1_, p_i1715_2_, p_i1715_4_, p_i1715_6_);
    }
    
    @Override
    public void killMinecart(final DamageSource p_94095_1_) {
        super.killMinecart(p_94095_1_);
        this.func_145778_a(Item.getItemFromBlock(Blocks.chest), 1, 0.0f);
    }
    
    @Override
    public int getSizeInventory() {
        return 27;
    }
    
    @Override
    public int getMinecartType() {
        return 1;
    }
    
    @Override
    public Block func_145817_o() {
        return Blocks.chest;
    }
    
    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }
}
