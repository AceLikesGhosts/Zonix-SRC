package net.minecraft.entity.monster;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import java.util.*;

public class EntitySpider extends EntityMob
{
    private static final String __OBFID = "CL_00001699";
    
    public EntitySpider(final World p_i1743_1_) {
        super(p_i1743_1_);
        this.setSize(1.4f, 0.9f);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isClient) {
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
        }
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929);
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final float var1 = this.getBrightness(1.0f);
        if (var1 < 0.5f) {
            final double var2 = 16.0;
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, var2);
        }
        return null;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.spider.say";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.spider.death";
    }
    
    @Override
    protected void func_145780_a(final int p_145780_1_, final int p_145780_2_, final int p_145780_3_, final Block p_145780_4_) {
        this.playSound("mob.spider.step", 0.15f, 1.0f);
    }
    
    @Override
    protected void attackEntity(final Entity p_70785_1_, final float p_70785_2_) {
        final float var3 = this.getBrightness(1.0f);
        if (var3 > 0.5f && this.rand.nextInt(100) == 0) {
            this.entityToAttack = null;
        }
        else if (p_70785_2_ > 2.0f && p_70785_2_ < 6.0f && this.rand.nextInt(10) == 0) {
            if (this.onGround) {
                final double var4 = p_70785_1_.posX - this.posX;
                final double var5 = p_70785_1_.posZ - this.posZ;
                final float var6 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
                this.motionX = var4 / var6 * 0.5 * 0.800000011920929 + this.motionX * 0.20000000298023224;
                this.motionZ = var5 / var6 * 0.5 * 0.800000011920929 + this.motionZ * 0.20000000298023224;
                this.motionY = 0.4000000059604645;
            }
        }
        else {
            super.attackEntity(p_70785_1_, p_70785_2_);
        }
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.string;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        super.dropFewItems(p_70628_1_, p_70628_2_);
        if (p_70628_1_ && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + p_70628_2_) > 0)) {
            this.func_145779_a(Items.spider_eye, 1);
        }
    }
    
    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    public boolean isPotionApplicable(final PotionEffect p_70687_1_) {
        return p_70687_1_.getPotionID() != Potion.poison.id && super.isPotionApplicable(p_70687_1_);
    }
    
    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0x0;
    }
    
    public void setBesideClimbableBlock(final boolean p_70839_1_) {
        byte var2 = this.dataWatcher.getWatchableObjectByte(16);
        if (p_70839_1_) {
            var2 |= 0x1;
        }
        else {
            var2 &= 0xFFFFFFFE;
        }
        this.dataWatcher.updateObject(16, var2);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        Object p_110161_1_2 = super.onSpawnWithEgg(p_110161_1_);
        if (this.worldObj.rand.nextInt(100) == 0) {
            final EntitySkeleton var2 = new EntitySkeleton(this.worldObj);
            var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
            var2.onSpawnWithEgg(null);
            this.worldObj.spawnEntityInWorld(var2);
            var2.mountEntity(this);
        }
        if (p_110161_1_2 == null) {
            p_110161_1_2 = new GroupData();
            if (this.worldObj.difficultySetting == EnumDifficulty.HARD && this.worldObj.rand.nextFloat() < 0.1f * this.worldObj.func_147462_b(this.posX, this.posY, this.posZ)) {
                ((GroupData)p_110161_1_2).func_111104_a(this.worldObj.rand);
            }
        }
        if (p_110161_1_2 instanceof GroupData) {
            final int var3 = ((GroupData)p_110161_1_2).field_111105_a;
            if (var3 > 0 && Potion.potionTypes[var3] != null) {
                this.addPotionEffect(new PotionEffect(var3, Integer.MAX_VALUE));
            }
        }
        return (IEntityLivingData)p_110161_1_2;
    }
    
    public static class GroupData implements IEntityLivingData
    {
        public int field_111105_a;
        private static final String __OBFID = "CL_00001700";
        
        public void func_111104_a(final Random p_111104_1_) {
            final int var2 = p_111104_1_.nextInt(5);
            if (var2 <= 1) {
                this.field_111105_a = Potion.moveSpeed.id;
            }
            else if (var2 <= 2) {
                this.field_111105_a = Potion.damageBoost.id;
            }
            else if (var2 <= 3) {
                this.field_111105_a = Potion.regeneration.id;
            }
            else if (var2 <= 4) {
                this.field_111105_a = Potion.invisibility.id;
            }
        }
    }
}
