package net.minecraft.dispenser;

import net.minecraft.item.*;

public interface IBehaviorDispenseItem
{
    public static final IBehaviorDispenseItem itemDispenseBehaviorProvider = new IBehaviorDispenseItem() {
        private static final String __OBFID = "CL_00001200";
        
        @Override
        public ItemStack dispense(final IBlockSource p_82482_1_, final ItemStack p_82482_2_) {
            return p_82482_2_;
        }
    };
    
    ItemStack dispense(final IBlockSource p0, final ItemStack p1);
}
