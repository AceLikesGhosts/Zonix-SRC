package net.minecraft.entity;

import java.util.*;
import net.minecraft.pathfinding.*;
import net.minecraft.world.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;

public abstract class EntityCreature extends EntityLiving
{
    public static final UUID field_110179_h;
    public static final AttributeModifier field_110181_i;
    private PathEntity pathToEntity;
    protected Entity entityToAttack;
    protected boolean hasAttacked;
    protected int fleeingTick;
    private ChunkCoordinates homePosition;
    private float maximumHomeDistance;
    private EntityAIBase field_110178_bs;
    private boolean field_110180_bt;
    private static final String __OBFID = "CL_00001558";
    
    public EntityCreature(final World p_i1602_1_) {
        super(p_i1602_1_);
        this.homePosition = new ChunkCoordinates(0, 0, 0);
        this.maximumHomeDistance = -1.0f;
        this.field_110178_bs = new EntityAIMoveTowardsRestriction(this, 1.0);
    }
    
    protected boolean isMovementCeased() {
        return false;
    }
    
    @Override
    protected void updateEntityActionState() {
        this.worldObj.theProfiler.startSection("ai");
        if (this.fleeingTick > 0 && --this.fleeingTick == 0) {
            final IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(EntityCreature.field_110181_i);
        }
        this.hasAttacked = this.isMovementCeased();
        final float var2 = 16.0f;
        if (this.entityToAttack == null) {
            this.entityToAttack = this.findPlayerToAttack();
            if (this.entityToAttack != null) {
                this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var2, true, false, false, true);
            }
        }
        else if (this.entityToAttack.isEntityAlive()) {
            final float var3 = this.entityToAttack.getDistanceToEntity(this);
            if (this.canEntityBeSeen(this.entityToAttack)) {
                this.attackEntity(this.entityToAttack, var3);
            }
        }
        else {
            this.entityToAttack = null;
        }
        if (this.entityToAttack instanceof EntityPlayerMP && ((EntityPlayerMP)this.entityToAttack).theItemInWorldManager.isCreative()) {
            this.entityToAttack = null;
        }
        this.worldObj.theProfiler.endSection();
        if (!this.hasAttacked && this.entityToAttack != null && (this.pathToEntity == null || this.rand.nextInt(20) == 0)) {
            this.pathToEntity = this.worldObj.getPathEntityToEntity(this, this.entityToAttack, var2, true, false, false, true);
        }
        else if (!this.hasAttacked && ((this.pathToEntity == null && this.rand.nextInt(180) == 0) || this.rand.nextInt(120) == 0 || this.fleeingTick > 0) && this.entityAge < 100) {
            this.updateWanderPath();
        }
        final int var4 = MathHelper.floor_double(this.boundingBox.minY + 0.5);
        final boolean var5 = this.isInWater();
        final boolean var6 = this.handleLavaMovement();
        this.rotationPitch = 0.0f;
        if (this.pathToEntity != null && this.rand.nextInt(100) != 0) {
            this.worldObj.theProfiler.startSection("followpath");
            Vec3 var7 = this.pathToEntity.getPosition(this);
            final double var8 = this.width * 2.0f;
            while (var7 != null && var7.squareDistanceTo(this.posX, var7.yCoord, this.posZ) < var8 * var8) {
                this.pathToEntity.incrementPathIndex();
                if (this.pathToEntity.isFinished()) {
                    var7 = null;
                    this.pathToEntity = null;
                }
                else {
                    var7 = this.pathToEntity.getPosition(this);
                }
            }
            this.isJumping = false;
            if (var7 != null) {
                final double var9 = var7.xCoord - this.posX;
                final double var10 = var7.zCoord - this.posZ;
                final double var11 = var7.yCoord - var4;
                final float var12 = (float)(Math.atan2(var10, var9) * 180.0 / 3.141592653589793) - 90.0f;
                float var13 = MathHelper.wrapAngleTo180_float(var12 - this.rotationYaw);
                this.moveForward = (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
                if (var13 > 30.0f) {
                    var13 = 30.0f;
                }
                if (var13 < -30.0f) {
                    var13 = -30.0f;
                }
                this.rotationYaw += var13;
                if (this.hasAttacked && this.entityToAttack != null) {
                    final double var14 = this.entityToAttack.posX - this.posX;
                    final double var15 = this.entityToAttack.posZ - this.posZ;
                    final float var16 = this.rotationYaw;
                    this.rotationYaw = (float)(Math.atan2(var15, var14) * 180.0 / 3.141592653589793) - 90.0f;
                    var13 = (var16 - this.rotationYaw + 90.0f) * 3.1415927f / 180.0f;
                    this.moveStrafing = -MathHelper.sin(var13) * this.moveForward * 1.0f;
                    this.moveForward = MathHelper.cos(var13) * this.moveForward * 1.0f;
                }
                if (var11 > 0.0) {
                    this.isJumping = true;
                }
            }
            if (this.entityToAttack != null) {
                this.faceEntity(this.entityToAttack, 30.0f, 30.0f);
            }
            if (this.isCollidedHorizontally && !this.hasPath()) {
                this.isJumping = true;
            }
            if (this.rand.nextFloat() < 0.8f && (var5 || var6)) {
                this.isJumping = true;
            }
            this.worldObj.theProfiler.endSection();
        }
        else {
            super.updateEntityActionState();
            this.pathToEntity = null;
        }
    }
    
    protected void updateWanderPath() {
        this.worldObj.theProfiler.startSection("stroll");
        boolean var1 = false;
        int var2 = -1;
        int var3 = -1;
        int var4 = -1;
        float var5 = -99999.0f;
        for (int var6 = 0; var6 < 10; ++var6) {
            final int var7 = MathHelper.floor_double(this.posX + this.rand.nextInt(13) - 6.0);
            final int var8 = MathHelper.floor_double(this.posY + this.rand.nextInt(7) - 3.0);
            final int var9 = MathHelper.floor_double(this.posZ + this.rand.nextInt(13) - 6.0);
            final float var10 = this.getBlockPathWeight(var7, var8, var9);
            if (var10 > var5) {
                var5 = var10;
                var2 = var7;
                var3 = var8;
                var4 = var9;
                var1 = true;
            }
        }
        if (var1) {
            this.pathToEntity = this.worldObj.getEntityPathToXYZ(this, var2, var3, var4, 10.0f, true, false, false, true);
        }
        this.worldObj.theProfiler.endSection();
    }
    
    protected void attackEntity(final Entity p_70785_1_, final float p_70785_2_) {
    }
    
    public float getBlockPathWeight(final int p_70783_1_, final int p_70783_2_, final int p_70783_3_) {
        return 0.0f;
    }
    
    protected Entity findPlayerToAttack() {
        return null;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final int var1 = MathHelper.floor_double(this.posX);
        final int var2 = MathHelper.floor_double(this.boundingBox.minY);
        final int var3 = MathHelper.floor_double(this.posZ);
        return super.getCanSpawnHere() && this.getBlockPathWeight(var1, var2, var3) >= 0.0f;
    }
    
    public boolean hasPath() {
        return this.pathToEntity != null;
    }
    
    public void setPathToEntity(final PathEntity p_70778_1_) {
        this.pathToEntity = p_70778_1_;
    }
    
    public Entity getEntityToAttack() {
        return this.entityToAttack;
    }
    
    public void setTarget(final Entity p_70784_1_) {
        this.entityToAttack = p_70784_1_;
    }
    
    public boolean isWithinHomeDistanceCurrentPosition() {
        return this.isWithinHomeDistance(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
    }
    
    public boolean isWithinHomeDistance(final int p_110176_1_, final int p_110176_2_, final int p_110176_3_) {
        return this.maximumHomeDistance == -1.0f || this.homePosition.getDistanceSquared(p_110176_1_, p_110176_2_, p_110176_3_) < this.maximumHomeDistance * this.maximumHomeDistance;
    }
    
    public void setHomeArea(final int p_110171_1_, final int p_110171_2_, final int p_110171_3_, final int p_110171_4_) {
        this.homePosition.set(p_110171_1_, p_110171_2_, p_110171_3_);
        this.maximumHomeDistance = (float)p_110171_4_;
    }
    
    public ChunkCoordinates getHomePosition() {
        return this.homePosition;
    }
    
    public float func_110174_bM() {
        return this.maximumHomeDistance;
    }
    
    public void detachHome() {
        this.maximumHomeDistance = -1.0f;
    }
    
    public boolean hasHome() {
        return this.maximumHomeDistance != -1.0f;
    }
    
    @Override
    protected void updateLeashedState() {
        super.updateLeashedState();
        if (this.getLeashed() && this.getLeashedToEntity() != null && this.getLeashedToEntity().worldObj == this.worldObj) {
            final Entity var1 = this.getLeashedToEntity();
            this.setHomeArea((int)var1.posX, (int)var1.posY, (int)var1.posZ, 5);
            final float var2 = this.getDistanceToEntity(var1);
            if (this instanceof EntityTameable && ((EntityTameable)this).isSitting()) {
                if (var2 > 10.0f) {
                    this.clearLeashed(true, true);
                }
                return;
            }
            if (!this.field_110180_bt) {
                this.tasks.addTask(2, this.field_110178_bs);
                this.getNavigator().setAvoidsWater(false);
                this.field_110180_bt = true;
            }
            this.func_142017_o(var2);
            if (var2 > 4.0f) {
                this.getNavigator().tryMoveToEntityLiving(var1, 1.0);
            }
            if (var2 > 6.0f) {
                final double var3 = (var1.posX - this.posX) / var2;
                final double var4 = (var1.posY - this.posY) / var2;
                final double var5 = (var1.posZ - this.posZ) / var2;
                this.motionX += var3 * Math.abs(var3) * 0.4;
                this.motionY += var4 * Math.abs(var4) * 0.4;
                this.motionZ += var5 * Math.abs(var5) * 0.4;
            }
            if (var2 > 10.0f) {
                this.clearLeashed(true, true);
            }
        }
        else if (!this.getLeashed() && this.field_110180_bt) {
            this.field_110180_bt = false;
            this.tasks.removeTask(this.field_110178_bs);
            this.getNavigator().setAvoidsWater(true);
            this.detachHome();
        }
    }
    
    protected void func_142017_o(final float p_142017_1_) {
    }
    
    static {
        field_110179_h = UUID.fromString("E199AD21-BA8A-4C53-8D13-6182D5C69D3A");
        field_110181_i = new AttributeModifier(EntityCreature.field_110179_h, "Fleeing speed bonus", 2.0, 2).setSaved(false);
    }
}
