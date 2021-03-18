package net.minecraft.entity.monster;

import net.minecraft.entity.passive.*;
import net.minecraft.command.*;
import net.minecraft.entity.*;

public interface IMob extends IAnimals
{
    public static final IEntitySelector mobSelector = new IEntitySelector() {
        private static final String __OBFID = "CL_00001688";
        
        @Override
        public boolean isEntityApplicable(final Entity p_82704_1_) {
            return p_82704_1_ instanceof IMob;
        }
    };
}
