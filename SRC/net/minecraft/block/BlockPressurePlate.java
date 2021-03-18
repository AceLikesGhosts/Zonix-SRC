package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class BlockPressurePlate extends BlockBasePressurePlate
{
    private Sensitivity field_150069_a;
    private static final String __OBFID = "CL_00000289";
    
    protected BlockPressurePlate(final String p_i45418_1_, final Material p_i45418_2_, final Sensitivity p_i45418_3_) {
        super(p_i45418_1_, p_i45418_2_);
        this.field_150069_a = p_i45418_3_;
    }
    
    @Override
    protected int func_150066_d(final int p_150066_1_) {
        return (p_150066_1_ > 0) ? 1 : 0;
    }
    
    @Override
    protected int func_150060_c(final int p_150060_1_) {
        return (p_150060_1_ == 1) ? 15 : 0;
    }
    
    @Override
    protected int func_150065_e(final World p_150065_1_, final int p_150065_2_, final int p_150065_3_, final int p_150065_4_) {
        List var5 = null;
        if (this.field_150069_a == Sensitivity.everything) {
            var5 = p_150065_1_.getEntitiesWithinAABBExcludingEntity(null, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (this.field_150069_a == Sensitivity.mobs) {
            var5 = p_150065_1_.getEntitiesWithinAABB(EntityLivingBase.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (this.field_150069_a == Sensitivity.players) {
            var5 = p_150065_1_.getEntitiesWithinAABB(EntityPlayer.class, this.func_150061_a(p_150065_2_, p_150065_3_, p_150065_4_));
        }
        if (var5 != null && !var5.isEmpty()) {
            for (final Entity var7 : var5) {
                if (!var7.doesEntityNotTriggerPressurePlate()) {
                    return 15;
                }
            }
        }
        return 0;
    }
    
    public enum Sensitivity
    {
        everything("everything", 0), 
        mobs("mobs", 1), 
        players("players", 2);
        
        private static final Sensitivity[] $VALUES;
        private static final String __OBFID = "CL_00000290";
        
        private Sensitivity(final String p_i45417_1_, final int p_i45417_2_) {
        }
        
        static {
            $VALUES = new Sensitivity[] { Sensitivity.everything, Sensitivity.mobs, Sensitivity.players };
        }
    }
}
