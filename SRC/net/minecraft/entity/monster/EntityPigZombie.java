package net.minecraft.entity.monster;

import net.minecraft.entity.ai.attributes.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;

public class EntityPigZombie extends EntityZombie
{
    private static final UUID field_110189_bq;
    private static final AttributeModifier field_110190_br;
    private int angerLevel;
    private int randomSoundDelay;
    private Entity field_110191_bu;
    private static final String __OBFID = "CL_00001693";
    
    public EntityPigZombie(final World p_i1739_1_) {
        super(p_i1739_1_);
        this.isImmuneToFire = true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(EntityPigZombie.field_110186_bp).setBaseValue(0.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }
    
    @Override
    protected boolean isAIEnabled() {
        return false;
    }
    
    @Override
    public void onUpdate() {
        if (this.field_110191_bu != this.entityToAttack && !this.worldObj.isClient) {
            final IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(EntityPigZombie.field_110190_br);
            if (this.entityToAttack != null) {
                var1.applyModifier(EntityPigZombie.field_110190_br);
            }
        }
        this.field_110191_bu = this.entityToAttack;
        if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0) {
            this.playSound("mob.zombiepig.zpigangry", this.getSoundVolume() * 2.0f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 1.8f);
        }
        super.onUpdate();
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setShort("Anger", (short)this.angerLevel);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.angerLevel = p_70037_1_.getShort("Anger");
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        return (this.angerLevel == 0) ? null : super.findPlayerToAttack();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final Entity var3 = p_70097_1_.getEntity();
        if (var3 instanceof EntityPlayer) {
            final List var4 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(32.0, 32.0, 32.0));
            for (int var5 = 0; var5 < var4.size(); ++var5) {
                final Entity var6 = var4.get(var5);
                if (var6 instanceof EntityPigZombie) {
                    final EntityPigZombie var7 = (EntityPigZombie)var6;
                    var7.becomeAngryAt(var3);
                }
            }
            this.becomeAngryAt(var3);
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    private void becomeAngryAt(final Entity p_70835_1_) {
        this.entityToAttack = p_70835_1_;
        this.angerLevel = 400 + this.rand.nextInt(400);
        this.randomSoundDelay = this.rand.nextInt(40);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.zombiepig.zpig";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.zombiepig.zpighurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.zombiepig.zpigdeath";
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.rand.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.func_145779_a(Items.rotten_flesh, 1);
        }
        for (int var3 = this.rand.nextInt(2 + p_70628_2_), var4 = 0; var4 < var3; ++var4) {
            this.func_145779_a(Items.gold_nugget, 1);
        }
    }
    
    @Override
    public boolean interact(final EntityPlayer p_70085_1_) {
        return false;
    }
    
    @Override
    protected void dropRareDrop(final int p_70600_1_) {
        this.func_145779_a(Items.gold_ingot, 1);
    }
    
    @Override
    protected void addRandomArmor() {
        this.setCurrentItemOrArmor(0, new ItemStack(Items.golden_sword));
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(final IEntityLivingData p_110161_1_) {
        super.onSpawnWithEgg(p_110161_1_);
        this.setVillager(false);
        return p_110161_1_;
    }
    
    static {
        field_110189_bq = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
        field_110190_br = new AttributeModifier(EntityPigZombie.field_110189_bq, "Attacking speed boost", 0.45, 0).setSaved(false);
    }
}
