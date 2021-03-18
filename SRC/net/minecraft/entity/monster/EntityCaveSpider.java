package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityCaveSpider extends EntitySpider
{
    private static final String __OBFID = "CL_00001683";
    
    public EntityCaveSpider(final World p_i1732_1_) {
        super(p_i1732_1_);
        this.setSize(0.7f, 0.5f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0);
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            if (p_70652_1_ instanceof EntityLivingBase) {
                byte var2 = 0;
                if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL) {
                    var2 = 7;
                }
                else if (this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                    var2 = 15;
                }
                if (var2 > 0) {
                    ((EntityLivingBase)p_70652_1_).addPotionEffect(new PotionEffect(Potion.poison.id, var2 * 20, 0));
                }
            }
            return true;
        }
        return false;
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        return p_110161_1_;
    }
}
