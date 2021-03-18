package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    private float[] field_82220_d;
    private float[] field_82221_e;
    private float[] field_82217_f;
    private float[] field_82218_g;
    private int[] field_82223_h;
    private int[] field_82224_i;
    private int field_82222_j;
    private static final IEntitySelector attackEntitySelector;
    private static final String __OBFID = "CL_00001661";
    
    public EntityWither(final World p_i1701_1_) {
        super(p_i1701_1_);
        this.field_82220_d = new float[2];
        this.field_82221_e = new float[2];
        this.field_82217_f = new float[2];
        this.field_82218_g = new float[2];
        this.field_82223_h = new int[2];
        this.field_82224_i = new int[2];
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 4.0f);
        this.isImmuneToFire = true;
        this.getNavigator().setCanSwim(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIArrowAttack(this, 1.0, 40, 20.0f));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, false, false, EntityWither.attackEntitySelector));
        this.experienceValue = 50;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(17, new Integer(0));
        this.dataWatcher.addObject(18, new Integer(0));
        this.dataWatcher.addObject(19, new Integer(0));
        this.dataWatcher.addObject(20, new Integer(0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Invul", this.func_82212_n());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.func_82215_s(p_70037_1_.getInteger("Invul"));
    }
    
    @Override
    public float getShadowSize() {
        return this.height / 8.0f;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.wither.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.wither.hurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.wither.death";
    }
    
    @Override
    public void onLivingUpdate() {
        this.motionY *= 0.6000000238418579;
        if (!this.worldObj.isClient && this.getWatchedTargetId(0) > 0) {
            final Entity var1 = this.worldObj.getEntityByID(this.getWatchedTargetId(0));
            if (var1 != null) {
                if (this.posY < var1.posY || (!this.isArmored() && this.posY < var1.posY + 5.0)) {
                    if (this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }
                    this.motionY += (0.5 - this.motionY) * 0.6000000238418579;
                }
                final double var2 = var1.posX - this.posX;
                final double var3 = var1.posZ - this.posZ;
                final double var4 = var2 * var2 + var3 * var3;
                if (var4 > 9.0) {
                    final double var5 = MathHelper.sqrt_double(var4);
                    this.motionX += (var2 / var5 * 0.5 - this.motionX) * 0.6000000238418579;
                    this.motionZ += (var3 / var5 * 0.5 - this.motionZ) * 0.6000000238418579;
                }
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806) {
            this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776f - 90.0f;
        }
        super.onLivingUpdate();
        for (int var6 = 0; var6 < 2; ++var6) {
            this.field_82218_g[var6] = this.field_82221_e[var6];
            this.field_82217_f[var6] = this.field_82220_d[var6];
        }
        for (int var6 = 0; var6 < 2; ++var6) {
            final int var7 = this.getWatchedTargetId(var6 + 1);
            Entity var8 = null;
            if (var7 > 0) {
                var8 = this.worldObj.getEntityByID(var7);
            }
            if (var8 != null) {
                final double var3 = this.func_82214_u(var6 + 1);
                final double var4 = this.func_82208_v(var6 + 1);
                final double var5 = this.func_82213_w(var6 + 1);
                final double var9 = var8.posX - var3;
                final double var10 = var8.posY + var8.getEyeHeight() - var4;
                final double var11 = var8.posZ - var5;
                final double var12 = MathHelper.sqrt_double(var9 * var9 + var11 * var11);
                final float var13 = (float)(Math.atan2(var11, var9) * 180.0 / 3.141592653589793) - 90.0f;
                final float var14 = (float)(-(Math.atan2(var10, var12) * 180.0 / 3.141592653589793));
                this.field_82220_d[var6] = this.func_82204_b(this.field_82220_d[var6], var14, 40.0f);
                this.field_82221_e[var6] = this.func_82204_b(this.field_82221_e[var6], var13, 10.0f);
            }
            else {
                this.field_82221_e[var6] = this.func_82204_b(this.field_82221_e[var6], this.renderYawOffset, 10.0f);
            }
        }
        final boolean var15 = this.isArmored();
        for (int var7 = 0; var7 < 3; ++var7) {
            final double var16 = this.func_82214_u(var7);
            final double var17 = this.func_82208_v(var7);
            final double var18 = this.func_82213_w(var7);
            this.worldObj.spawnParticle("smoke", var16 + this.rand.nextGaussian() * 0.30000001192092896, var17 + this.rand.nextGaussian() * 0.30000001192092896, var18 + this.rand.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0);
            if (var15 && this.worldObj.rand.nextInt(4) == 0) {
                this.worldObj.spawnParticle("mobSpell", var16 + this.rand.nextGaussian() * 0.30000001192092896, var17 + this.rand.nextGaussian() * 0.30000001192092896, var18 + this.rand.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5);
            }
        }
        if (this.func_82212_n() > 0) {
            for (int var7 = 0; var7 < 3; ++var7) {
                this.worldObj.spawnParticle("mobSpell", this.posX + this.rand.nextGaussian() * 1.0, this.posY + this.rand.nextFloat() * 3.3f, this.posZ + this.rand.nextGaussian() * 1.0, 0.699999988079071, 0.699999988079071, 0.8999999761581421);
            }
        }
    }
    
    @Override
    protected void updateAITasks() {
        if (this.func_82212_n() > 0) {
            final int var1 = this.func_82212_n() - 1;
            if (var1 <= 0) {
                this.worldObj.newExplosion(this, this.posX, this.posY + this.getEyeHeight(), this.posZ, 7.0f, false, this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                this.worldObj.playBroadcastSound(1013, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
            }
            this.func_82215_s(var1);
            if (this.ticksExisted % 10 == 0) {
                this.heal(10.0f);
            }
        }
        else {
            super.updateAITasks();
            for (int var1 = 1; var1 < 3; ++var1) {
                if (this.ticksExisted >= this.field_82223_h[var1 - 1]) {
                    this.field_82223_h[var1 - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
                    if (this.worldObj.difficultySetting == EnumDifficulty.NORMAL || this.worldObj.difficultySetting == EnumDifficulty.HARD) {
                        final int var2 = var1 - 1;
                        final int var3 = this.field_82224_i[var1 - 1];
                        this.field_82224_i[var2] = this.field_82224_i[var1 - 1] + 1;
                        if (var3 > 15) {
                            final float var4 = 10.0f;
                            final float var5 = 5.0f;
                            final double var6 = MathHelper.getRandomDoubleInRange(this.rand, this.posX - var4, this.posX + var4);
                            final double var7 = MathHelper.getRandomDoubleInRange(this.rand, this.posY - var5, this.posY + var5);
                            final double var8 = MathHelper.getRandomDoubleInRange(this.rand, this.posZ - var4, this.posZ + var4);
                            this.func_82209_a(var1 + 1, var6, var7, var8, true);
                            this.field_82224_i[var1 - 1] = 0;
                        }
                    }
                    final int var9 = this.getWatchedTargetId(var1);
                    if (var9 > 0) {
                        final Entity var10 = this.worldObj.getEntityByID(var9);
                        if (var10 != null && var10.isEntityAlive() && this.getDistanceSqToEntity(var10) <= 900.0 && this.canEntityBeSeen(var10)) {
                            this.func_82216_a(var1 + 1, (EntityLivingBase)var10);
                            this.field_82223_h[var1 - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
                            this.field_82224_i[var1 - 1] = 0;
                        }
                        else {
                            this.func_82211_c(var1, 0);
                        }
                    }
                    else {
                        final List var11 = this.worldObj.selectEntitiesWithinAABB(EntityLivingBase.class, this.boundingBox.expand(20.0, 8.0, 20.0), EntityWither.attackEntitySelector);
                        int var12 = 0;
                        while (var12 < 10 && !var11.isEmpty()) {
                            final EntityLivingBase var13 = var11.get(this.rand.nextInt(var11.size()));
                            if (var13 != this && var13.isEntityAlive() && this.canEntityBeSeen(var13)) {
                                if (!(var13 instanceof EntityPlayer)) {
                                    this.func_82211_c(var1, var13.getEntityId());
                                    break;
                                }
                                if (!((EntityPlayer)var13).capabilities.disableDamage) {
                                    this.func_82211_c(var1, var13.getEntityId());
                                    break;
                                }
                                break;
                            }
                            else {
                                var11.remove(var13);
                                ++var12;
                            }
                        }
                    }
                }
            }
            if (this.getAttackTarget() != null) {
                this.func_82211_c(0, this.getAttackTarget().getEntityId());
            }
            else {
                this.func_82211_c(0, 0);
            }
            if (this.field_82222_j > 0) {
                --this.field_82222_j;
                if (this.field_82222_j == 0 && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    final int var1 = MathHelper.floor_double(this.posY);
                    final int var9 = MathHelper.floor_double(this.posX);
                    final int var14 = MathHelper.floor_double(this.posZ);
                    boolean var15 = false;
                    for (int var16 = -1; var16 <= 1; ++var16) {
                        for (int var17 = -1; var17 <= 1; ++var17) {
                            for (int var18 = 0; var18 <= 3; ++var18) {
                                final int var19 = var9 + var16;
                                final int var20 = var1 + var18;
                                final int var21 = var14 + var17;
                                final Block var22 = this.worldObj.getBlock(var19, var20, var21);
                                if (var22.getMaterial() != Material.air && var22 != Blocks.bedrock && var22 != Blocks.end_portal && var22 != Blocks.end_portal_frame && var22 != Blocks.command_block) {
                                    var15 = (this.worldObj.func_147480_a(var19, var20, var21, true) || var15);
                                }
                            }
                        }
                    }
                    if (var15) {
                        this.worldObj.playAuxSFXAtEntity(null, 1012, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
                    }
                }
            }
            if (this.ticksExisted % 20 == 0) {
                this.heal(1.0f);
            }
        }
    }
    
    public void func_82206_m() {
        this.func_82215_s(220);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }
    
    @Override
    public void setInWeb() {
    }
    
    @Override
    public int getTotalArmorValue() {
        return 4;
    }
    
    private double func_82214_u(final int p_82214_1_) {
        if (p_82214_1_ <= 0) {
            return this.posX;
        }
        final float var2 = (this.renderYawOffset + 180 * (p_82214_1_ - 1)) / 180.0f * 3.1415927f;
        final float var3 = MathHelper.cos(var2);
        return this.posX + var3 * 1.3;
    }
    
    private double func_82208_v(final int p_82208_1_) {
        return (p_82208_1_ <= 0) ? (this.posY + 3.0) : (this.posY + 2.2);
    }
    
    private double func_82213_w(final int p_82213_1_) {
        if (p_82213_1_ <= 0) {
            return this.posZ;
        }
        final float var2 = (this.renderYawOffset + 180 * (p_82213_1_ - 1)) / 180.0f * 3.1415927f;
        final float var3 = MathHelper.sin(var2);
        return this.posZ + var3 * 1.3;
    }
    
    private float func_82204_b(final float p_82204_1_, final float p_82204_2_, final float p_82204_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_82204_2_ - p_82204_1_);
        if (var4 > p_82204_3_) {
            var4 = p_82204_3_;
        }
        if (var4 < -p_82204_3_) {
            var4 = -p_82204_3_;
        }
        return p_82204_1_ + var4;
    }
    
    private void func_82216_a(final int p_82216_1_, final EntityLivingBase p_82216_2_) {
        this.func_82209_a(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5, p_82216_2_.posZ, p_82216_1_ == 0 && this.rand.nextFloat() < 0.001f);
    }
    
    private void func_82209_a(final int p_82209_1_, final double p_82209_2_, final double p_82209_4_, final double p_82209_6_, final boolean p_82209_8_) {
        this.worldObj.playAuxSFXAtEntity(null, 1014, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
        final double var9 = this.func_82214_u(p_82209_1_);
        final double var10 = this.func_82208_v(p_82209_1_);
        final double var11 = this.func_82213_w(p_82209_1_);
        final double var12 = p_82209_2_ - var9;
        final double var13 = p_82209_4_ - var10;
        final double var14 = p_82209_6_ - var11;
        final EntityWitherSkull var15 = new EntityWitherSkull(this.worldObj, this, var12, var13, var14);
        if (p_82209_8_) {
            var15.setInvulnerable(true);
        }
        var15.posY = var10;
        var15.posX = var9;
        var15.posZ = var11;
        this.worldObj.spawnEntityInWorld(var15);
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        this.func_82216_a(0, p_82196_1_);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (p_70097_1_ == DamageSource.drown) {
            return false;
        }
        if (this.func_82212_n() > 0) {
            return false;
        }
        if (this.isArmored()) {
            final Entity var3 = p_70097_1_.getSourceOfDamage();
            if (var3 instanceof EntityArrow) {
                return false;
            }
        }
        final Entity var3 = p_70097_1_.getEntity();
        if (var3 != null && !(var3 instanceof EntityPlayer) && var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getCreatureAttribute() == this.getCreatureAttribute()) {
            return false;
        }
        if (this.field_82222_j <= 0) {
            this.field_82222_j = 20;
        }
        for (int var4 = 0; var4 < this.field_82224_i.length; ++var4) {
            final int[] field_82224_i = this.field_82224_i;
            final int n = var4;
            field_82224_i[n] += 3;
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        this.func_145779_a(Items.nether_star, 1);
        if (!this.worldObj.isClient) {
            for (final EntityPlayer var4 : this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(50.0, 100.0, 50.0))) {
                var4.triggerAchievement(AchievementList.field_150964_J);
            }
        }
    }
    
    @Override
    public void despawnEntity() {
        this.entityAge = 0;
    }
    
    @Override
    public int getBrightnessForRender(final float p_70070_1_) {
        return 15728880;
    }
    
    @Override
    protected void fall(final float p_70069_1_) {
    }
    
    @Override
    public void addPotionEffect(final PotionEffect p_70690_1_) {
    }
    
    @Override
    protected boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }
    
    public float func_82207_a(final int p_82207_1_) {
        return this.field_82221_e[p_82207_1_];
    }
    
    public float func_82210_r(final int p_82210_1_) {
        return this.field_82220_d[p_82210_1_];
    }
    
    public int func_82212_n() {
        return this.dataWatcher.getWatchableObjectInt(20);
    }
    
    public void func_82215_s(final int p_82215_1_) {
        this.dataWatcher.updateObject(20, p_82215_1_);
    }
    
    public int getWatchedTargetId(final int p_82203_1_) {
        return this.dataWatcher.getWatchableObjectInt(17 + p_82203_1_);
    }
    
    public void func_82211_c(final int p_82211_1_, final int p_82211_2_) {
        this.dataWatcher.updateObject(17 + p_82211_1_, p_82211_2_);
    }
    
    public boolean isArmored() {
        return this.getHealth() <= this.getMaxHealth() / 2.0f;
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void mountEntity(final Entity p_70078_1_) {
        this.ridingEntity = null;
    }
    
    static {
        attackEntitySelector = new IEntitySelector() {
            private static final String __OBFID = "CL_00001662";
            
            @Override
            public boolean isEntityApplicable(final Entity p_82704_1_) {
                return p_82704_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_82704_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD;
            }
        };
    }
}
