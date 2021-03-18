package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.util.*;

public class EntityWitch extends EntityMob implements IRangedAttackMob
{
    private static final UUID field_110184_bp;
    private static final AttributeModifier field_110185_bq;
    private static final Item[] witchDrops;
    private int witchAttackTimer;
    private static final String __OBFID = "CL_00001701";
    
    public EntityWitch(final World p_i1744_1_) {
        super(p_i1744_1_);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 60, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(3, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(21, 0);
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.witch.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.witch.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.witch.death";
    }
    
    public void setAggressive(final boolean p_82197_1_) {
        this.getDataWatcher().updateObject(21, (byte)(byte)(p_82197_1_ ? 1 : 0));
    }
    
    public boolean getAggressive() {
        return this.getDataWatcher().getWatchableObjectByte(21) == 1;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(26.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isClient) {
            if (this.getAggressive()) {
                if (this.witchAttackTimer-- <= 0) {
                    this.setAggressive(false);
                    final ItemStack var1 = this.getHeldItem();
                    this.setCurrentItemOrArmor(0, null);
                    if (var1 != null && var1.getItem() == Items.potionitem) {
                        final List var2 = Items.potionitem.getEffects(var1);
                        if (var2 != null) {
                            for (final PotionEffect var4 : var2) {
                                this.addPotionEffect(new PotionEffect(var4));
                            }
                        }
                    }
                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).removeModifier(EntityWitch.field_110185_bq);
                }
            }
            else {
                short var5 = -1;
                if (this.rand.nextFloat() < 0.15f && this.isInsideOfMaterial(Material.water) && !this.isPotionActive(Potion.waterBreathing)) {
                    var5 = 8237;
                }
                else if (this.rand.nextFloat() < 0.15f && this.isBurning() && !this.isPotionActive(Potion.fireResistance)) {
                    var5 = 16307;
                }
                else if (this.rand.nextFloat() < 0.05f && this.getHealth() < this.getMaxHealth()) {
                    var5 = 16341;
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                }
                else if (this.rand.nextFloat() < 0.25f && this.getAttackTarget() != null && !this.isPotionActive(Potion.moveSpeed) && this.getAttackTarget().getDistanceSqToEntity(this) > 121.0) {
                    var5 = 16274;
                }
                if (var5 > -1) {
                    this.setCurrentItemOrArmor(0, new ItemStack(Items.potionitem, 1, var5));
                    this.witchAttackTimer = this.getHeldItem().getMaxItemUseDuration();
                    this.setAggressive(true);
                    final IAttributeInstance var6 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
                    var6.removeModifier(EntityWitch.field_110185_bq);
                    var6.applyModifier(EntityWitch.field_110185_bq);
                }
            }
            if (this.rand.nextFloat() < 7.5E-4f) {
                this.worldObj.setEntityState(this, (byte)15);
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    public void handleHealthUpdate(final byte p_70103_1_) {
        if (p_70103_1_ == 15) {
            for (int var2 = 0; var2 < this.rand.nextInt(35) + 10; ++var2) {
                this.worldObj.spawnParticle("witchMagic", this.posX + this.rand.nextGaussian() * 0.12999999523162842, this.boundingBox.maxY + 0.5 + this.rand.nextGaussian() * 0.12999999523162842, this.posZ + this.rand.nextGaussian() * 0.12999999523162842, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    @Override
    protected float applyPotionDamageCalculations(final DamageSource p_70672_1_, float p_70672_2_) {
        p_70672_2_ = super.applyPotionDamageCalculations(p_70672_1_, p_70672_2_);
        if (p_70672_1_.getEntity() == this) {
            p_70672_2_ = 0.0f;
        }
        if (p_70672_1_.isMagicDamage()) {
            p_70672_2_ *= (float)0.15;
        }
        return p_70672_2_;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.rand.nextInt(3) + 1, var4 = 0; var4 < var3; ++var4) {
            int var5 = this.rand.nextInt(3);
            final Item var6 = EntityWitch.witchDrops[this.rand.nextInt(EntityWitch.witchDrops.length)];
            if (p_70628_2_ > 0) {
                var5 += this.rand.nextInt(p_70628_2_ + 1);
            }
            for (int var7 = 0; var7 < var5; ++var7) {
                this.func_145779_a(var6, 1);
            }
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        if (!this.getAggressive()) {
            final EntityPotion entityPotion;
            final EntityPotion var3 = entityPotion = new EntityPotion(this.worldObj, this, 32732);
            entityPotion.rotationPitch += 20.0f;
            final double var4 = p_82196_1_.posX + p_82196_1_.motionX - this.posX;
            final double var5 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858 - this.posY;
            final double var6 = p_82196_1_.posZ + p_82196_1_.motionZ - this.posZ;
            final float var7 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);
            if (var7 >= 8.0f && !p_82196_1_.isPotionActive(Potion.moveSlowdown)) {
                var3.setPotionDamage(32698);
            }
            else if (p_82196_1_.getHealth() >= 8.0f && !p_82196_1_.isPotionActive(Potion.poison)) {
                var3.setPotionDamage(32660);
            }
            else if (var7 <= 3.0f && !p_82196_1_.isPotionActive(Potion.weakness) && this.rand.nextFloat() < 0.25f) {
                var3.setPotionDamage(32696);
            }
            var3.setThrowableHeading(var4, var5 + var7 * 0.2f, var6, 0.75f, 8.0f);
            this.worldObj.spawnEntityInWorld(var3);
        }
    }
    
    static {
        field_110184_bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
        field_110185_bq = new AttributeModifier(EntityWitch.field_110184_bp, "Drinking speed penalty", -0.25, 0).setSaved(false);
        witchDrops = new Item[] { Items.glowstone_dust, Items.sugar, Items.redstone, Items.spider_eye, Items.glass_bottle, Items.gunpowder, Items.stick, Items.stick };
    }
}
