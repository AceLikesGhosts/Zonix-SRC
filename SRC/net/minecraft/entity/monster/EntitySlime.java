package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;

public class EntitySlime extends EntityLiving implements IMob
{
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private int slimeJumpDelay;
    private static final String __OBFID = "CL_00001698";
    
    public EntitySlime(final World p_i1742_1_) {
        super(p_i1742_1_);
        final int var2 = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0f;
        this.slimeJumpDelay = this.rand.nextInt(20) + 10;
        this.setSlimeSize(var2);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)1));
    }
    
    protected void setSlimeSize(final int p_70799_1_) {
        this.dataWatcher.updateObject(16, new Byte((byte)p_70799_1_));
        this.setSize(0.6f * p_70799_1_, 0.6f * p_70799_1_);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(p_70799_1_ * p_70799_1_);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = p_70799_1_;
    }
    
    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("Size", this.getSlimeSize() - 1);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        int var2 = p_70037_1_.getInteger("Size");
        if (var2 < 0) {
            var2 = 0;
        }
        this.setSlimeSize(var2 + 1);
    }
    
    protected String getSlimeParticle() {
        return "slime";
    }
    
    protected String getJumpSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isClient && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getSlimeSize() > 0) {
            this.isDead = true;
        }
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5f;
        this.prevSquishFactor = this.squishFactor;
        final boolean var1 = this.onGround;
        super.onUpdate();
        if (this.onGround && !var1) {
            for (int var2 = this.getSlimeSize(), var3 = 0; var3 < var2 * 8; ++var3) {
                final float var4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float var5 = this.rand.nextFloat() * 0.5f + 0.5f;
                final float var6 = MathHelper.sin(var4) * var2 * 0.5f * var5;
                final float var7 = MathHelper.cos(var4) * var2 * 0.5f * var5;
                this.worldObj.spawnParticle(this.getSlimeParticle(), this.posX + var6, this.boundingBox.minY, this.posZ + var7, 0.0, 0.0, 0.0);
            }
            if (this.makesSoundOnLand()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.squishAmount = -0.5f;
        }
        else if (!this.onGround && var1) {
            this.squishAmount = 1.0f;
        }
        this.alterSquishAmount();
        if (this.worldObj.isClient) {
            final int var2 = this.getSlimeSize();
            this.setSize(0.6f * var2, 0.6f * var2);
        }
    }
    
    @Override
    protected void updateEntityActionState() {
        this.despawnEntity();
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        if (var1 != null) {
            this.faceEntity(var1, 10.0f, 20.0f);
        }
        if (this.onGround && this.slimeJumpDelay-- <= 0) {
            this.slimeJumpDelay = this.getJumpDelay();
            if (var1 != null) {
                this.slimeJumpDelay /= 3;
            }
            this.isJumping = true;
            if (this.makesSoundOnJump()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) * 0.8f);
            }
            this.moveStrafing = 1.0f - this.rand.nextFloat() * 2.0f;
            this.moveForward = (float)(1 * this.getSlimeSize());
        }
        else {
            this.isJumping = false;
            if (this.onGround) {
                final float n = 0.0f;
                this.moveForward = n;
                this.moveStrafing = n;
            }
        }
    }
    
    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }
    
    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }
    
    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }
    
    @Override
    public void setDead() {
        final int var1 = this.getSlimeSize();
        if (!this.worldObj.isClient && var1 > 1 && this.getHealth() <= 0.0f) {
            for (int var2 = 2 + this.rand.nextInt(3), var3 = 0; var3 < var2; ++var3) {
                final float var4 = (var3 % 2 - 0.5f) * var1 / 4.0f;
                final float var5 = (var3 / 2 - 0.5f) * var1 / 4.0f;
                final EntitySlime var6 = this.createInstance();
                var6.setSlimeSize(var1 / 2);
                var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5, this.posZ + var5, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(var6);
            }
        }
        super.setDead();
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer p_70100_1_) {
        if (this.canDamagePlayer()) {
            final int var2 = this.getSlimeSize();
            if (this.canEntityBeSeen(p_70100_1_) && this.getDistanceSqToEntity(p_70100_1_) < 0.6 * var2 * 0.6 * var2 && p_70100_1_.attackEntityFrom(DamageSource.causeMobDamage(this), (float)this.getAttackStrength())) {
                this.playSound("mob.attack", 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
        }
    }
    
    protected boolean canDamagePlayer() {
        return this.getSlimeSize() > 1;
    }
    
    protected int getAttackStrength() {
        return this.getSlimeSize();
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.slime." + ((this.getSlimeSize() > 1) ? "big" : "small");
    }
    
    @Override
    protected Item func_146068_u() {
        return (this.getSlimeSize() == 1) ? Items.slime_ball : Item.getItemById(0);
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final Chunk var1 = this.worldObj.getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1) {
            return false;
        }
        if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            final BiomeGenBase var2 = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (var2 == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(10) == 0 && var1.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return false;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f * this.getSlimeSize();
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 0;
    }
    
    protected boolean makesSoundOnJump() {
        return this.getSlimeSize() > 0;
    }
    
    protected boolean makesSoundOnLand() {
        return this.getSlimeSize() > 2;
    }
}
