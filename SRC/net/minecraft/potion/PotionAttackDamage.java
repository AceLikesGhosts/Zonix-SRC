package net.minecraft.potion;

import net.minecraft.entity.ai.attributes.*;

public class PotionAttackDamage extends Potion
{
    private static final String __OBFID = "CL_00001525";
    
    protected PotionAttackDamage(final int p_i1570_1_, final boolean p_i1570_2_, final int p_i1570_3_) {
        super(p_i1570_1_, p_i1570_2_, p_i1570_3_);
    }
    
    @Override
    public double func_111183_a(final int p_111183_1_, final AttributeModifier p_111183_2_) {
        return (this.id == Potion.weakness.id) ? (-0.5f * (p_111183_1_ + 1)) : (1.3 * (p_111183_1_ + 1));
    }
}
