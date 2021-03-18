package net.minecraft.entity.monster;

import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.init.*;
import net.minecraft.util.*;

public class EntityEnderman extends EntityMob
{
    private static final UUID attackingSpeedBoostModifierUUID;
    private static final AttributeModifier attackingSpeedBoostModifier;
    private static boolean[] carriableBlocks;
    private int teleportDelay;
    private int stareTimer;
    private Entity lastEntityToAttack;
    private boolean isAggressive;
    private static final String __OBFID = "CL_00001685";
    
    public EntityEnderman(final World p_i1734_1_) {
        super(p_i1734_1_);
        this.setSize(0.6f, 2.9f);
        this.stepHeight = 1.0f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setShort("carried", (short)Block.getIdFromBlock(this.func_146080_bZ()));
        p_70014_1_.setShort("carriedData", (short)this.getCarryingData());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.func_146081_a(Block.getBlockById(p_70037_1_.getShort("carried")));
        this.setCarryingData(p_70037_1_.getShort("carriedData"));
    }
    
    @Override
    protected Entity findPlayerToAttack() {
        final EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 64.0);
        if (var1 != null) {
            if (this.shouldAttackPlayer(var1)) {
                this.isAggressive = true;
                if (this.stareTimer == 0) {
                    this.worldObj.playSoundEffect(var1.posX, var1.posY, var1.posZ, "mob.endermen.stare", 1.0f, 1.0f);
                }
                if (this.stareTimer++ == 5) {
                    this.stareTimer = 0;
                    this.setScreaming(true);
                    return var1;
                }
            }
            else {
                this.stareTimer = 0;
            }
        }
        return null;
    }
    
    private boolean shouldAttackPlayer(final EntityPlayer p_70821_1_) {
        final ItemStack var2 = p_70821_1_.inventory.armorInventory[3];
        if (var2 != null && var2.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            return false;
        }
        final Vec3 var3 = p_70821_1_.getLook(1.0f).normalize();
        Vec3 var4 = Vec3.createVectorHelper(this.posX - p_70821_1_.posX, this.boundingBox.minY + this.height / 2.0f - (p_70821_1_.posY + p_70821_1_.getEyeHeight()), this.posZ - p_70821_1_.posZ);
        final double var5 = var4.lengthVector();
        var4 = var4.normalize();
        final double var6 = var3.dotProduct(var4);
        return var6 > 1.0 - 0.025 / var5 && p_70821_1_.canEntityBeSeen(this);
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this.lastEntityToAttack != this.entityToAttack) {
            final IAttributeInstance var1 = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            var1.removeModifier(EntityEnderman.attackingSpeedBoostModifier);
            if (this.entityToAttack != null) {
                var1.applyModifier(EntityEnderman.attackingSpeedBoostModifier);
            }
        }
        this.lastEntityToAttack = this.entityToAttack;
        if (!this.worldObj.isClient && this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
            if (this.func_146080_bZ().getMaterial() == Material.air) {
                if (this.rand.nextInt(20) == 0) {
                    final int var2 = MathHelper.floor_double(this.posX - 2.0 + this.rand.nextDouble() * 4.0);
                    final int var3 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 3.0);
                    final int var4 = MathHelper.floor_double(this.posZ - 2.0 + this.rand.nextDouble() * 4.0);
                    final Block var5 = this.worldObj.getBlock(var2, var3, var4);
                    if (EntityEnderman.carriableBlocks[Block.getIdFromBlock(var5)]) {
                        this.func_146081_a(var5);
                        this.setCarryingData(this.worldObj.getBlockMetadata(var2, var3, var4));
                        this.worldObj.setBlock(var2, var3, var4, Blocks.air);
                    }
                }
            }
            else if (this.rand.nextInt(2000) == 0) {
                final int var2 = MathHelper.floor_double(this.posX - 1.0 + this.rand.nextDouble() * 2.0);
                final int var3 = MathHelper.floor_double(this.posY + this.rand.nextDouble() * 2.0);
                final int var4 = MathHelper.floor_double(this.posZ - 1.0 + this.rand.nextDouble() * 2.0);
                final Block var5 = this.worldObj.getBlock(var2, var3, var4);
                final Block var6 = this.worldObj.getBlock(var2, var3 - 1, var4);
                if (var5.getMaterial() == Material.air && var6.getMaterial() != Material.air && var6.renderAsNormalBlock()) {
                    this.worldObj.setBlock(var2, var3, var4, this.func_146080_bZ(), this.getCarryingData(), 3);
                    this.func_146081_a(Blocks.air);
                }
            }
        }
        for (int var2 = 0; var2 < 2; ++var2) {
            this.worldObj.spawnParticle("portal", this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, (this.rand.nextDouble() - 0.5) * 2.0, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5) * 2.0);
        }
        if (this.worldObj.isDaytime() && !this.worldObj.isClient) {
            final float var7 = this.getBrightness(1.0f);
            if (var7 > 0.5f && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0f < (var7 - 0.4f) * 2.0f) {
                this.entityToAttack = null;
                this.setScreaming(false);
                this.isAggressive = false;
                this.teleportRandomly();
            }
        }
        if (this.isWet() || this.isBurning()) {
            this.entityToAttack = null;
            this.setScreaming(false);
            this.isAggressive = false;
            this.teleportRandomly();
        }
        if (this.isScreaming() && !this.isAggressive && this.rand.nextInt(100) == 0) {
            this.setScreaming(false);
        }
        this.isJumping = false;
        if (this.entityToAttack != null) {
            this.faceEntity(this.entityToAttack, 100.0f, 100.0f);
        }
        if (!this.worldObj.isClient && this.isEntityAlive()) {
            if (this.entityToAttack != null) {
                if (this.entityToAttack instanceof EntityPlayer && this.shouldAttackPlayer((EntityPlayer)this.entityToAttack)) {
                    if (this.entityToAttack.getDistanceSqToEntity(this) < 16.0) {
                        this.teleportRandomly();
                    }
                    this.teleportDelay = 0;
                }
                else if (this.entityToAttack.getDistanceSqToEntity(this) > 256.0 && this.teleportDelay++ >= 30 && this.teleportToEntity(this.entityToAttack)) {
                    this.teleportDelay = 0;
                }
            }
            else {
                this.setScreaming(false);
                this.teleportDelay = 0;
            }
        }
        super.onLivingUpdate();
    }
    
    protected boolean teleportRandomly() {
        final double var1 = this.posX + (this.rand.nextDouble() - 0.5) * 64.0;
        final double var2 = this.posY + (this.rand.nextInt(64) - 32);
        final double var3 = this.posZ + (this.rand.nextDouble() - 0.5) * 64.0;
        return this.teleportTo(var1, var2, var3);
    }
    
    protected boolean teleportToEntity(final Entity p_70816_1_) {
        Vec3 var2 = Vec3.createVectorHelper(this.posX - p_70816_1_.posX, this.boundingBox.minY + this.height / 2.0f - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
        var2 = var2.normalize();
        final double var3 = 16.0;
        final double var4 = this.posX + (this.rand.nextDouble() - 0.5) * 8.0 - var2.xCoord * var3;
        final double var5 = this.posY + (this.rand.nextInt(16) - 8) - var2.yCoord * var3;
        final double var6 = this.posZ + (this.rand.nextDouble() - 0.5) * 8.0 - var2.zCoord * var3;
        return this.teleportTo(var4, var5, var6);
    }
    
    protected boolean teleportTo(final double p_70825_1_, final double p_70825_3_, final double p_70825_5_) {
        final double var7 = this.posX;
        final double var8 = this.posY;
        final double var9 = this.posZ;
        this.posX = p_70825_1_;
        this.posY = p_70825_3_;
        this.posZ = p_70825_5_;
        boolean var10 = false;
        final int var11 = MathHelper.floor_double(this.posX);
        int var12 = MathHelper.floor_double(this.posY);
        final int var13 = MathHelper.floor_double(this.posZ);
        if (this.worldObj.blockExists(var11, var12, var13)) {
            boolean var14 = false;
            while (!var14 && var12 > 0) {
                final Block var15 = this.worldObj.getBlock(var11, var12 - 1, var13);
                if (var15.getMaterial().blocksMovement()) {
                    var14 = true;
                }
                else {
                    --this.posY;
                    --var12;
                }
            }
            if (var14) {
                this.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox)) {
                    var10 = true;
                }
            }
        }
        if (!var10) {
            this.setPosition(var7, var8, var9);
            return false;
        }
        final short var16 = 128;
        for (int var17 = 0; var17 < var16; ++var17) {
            final double var18 = var17 / (var16 - 1.0);
            final float var19 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final float var20 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final float var21 = (this.rand.nextFloat() - 0.5f) * 0.2f;
            final double var22 = var7 + (this.posX - var7) * var18 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            final double var23 = var8 + (this.posY - var8) * var18 + this.rand.nextDouble() * this.height;
            final double var24 = var9 + (this.posZ - var9) * var18 + (this.rand.nextDouble() - 0.5) * this.width * 2.0;
            this.worldObj.spawnParticle("portal", var22, var23, var24, var19, var20, var21);
        }
        this.worldObj.playSoundEffect(var7, var8, var9, "mob.endermen.portal", 1.0f, 1.0f);
        this.playSound("mob.endermen.portal", 1.0f, 1.0f);
        return true;
    }
    
    @Override
    protected String getLivingSound() {
        return this.isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.endermen.hit";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.endermen.death";
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.ender_pearl;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        final Item var3 = this.func_146068_u();
        if (var3 != null) {
            for (int var4 = this.rand.nextInt(2 + p_70628_2_), var5 = 0; var5 < var4; ++var5) {
                this.func_145779_a(var3, 1);
            }
        }
    }
    
    public void func_146081_a(final Block p_146081_1_) {
        this.dataWatcher.updateObject(16, (byte)(Block.getIdFromBlock(p_146081_1_) & 0xFF));
    }
    
    public Block func_146080_bZ() {
        return Block.getBlockById(this.dataWatcher.getWatchableObjectByte(16));
    }
    
    public void setCarryingData(final int p_70817_1_) {
        this.dataWatcher.updateObject(17, (byte)(p_70817_1_ & 0xFF));
    }
    
    public int getCarryingData() {
        return this.dataWatcher.getWatchableObjectByte(17);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        this.setScreaming(true);
        if (p_70097_1_ instanceof EntityDamageSource && p_70097_1_.getEntity() instanceof EntityPlayer) {
            this.isAggressive = true;
        }
        if (p_70097_1_ instanceof EntityDamageSourceIndirect) {
            this.isAggressive = false;
            for (int var3 = 0; var3 < 64; ++var3) {
                if (this.teleportRandomly()) {
                    return true;
                }
            }
            return false;
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }
    
    public boolean isScreaming() {
        return this.dataWatcher.getWatchableObjectByte(18) > 0;
    }
    
    public void setScreaming(final boolean p_70819_1_) {
        this.dataWatcher.updateObject(18, (byte)(byte)(p_70819_1_ ? 1 : 0));
    }
    
    static {
        attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
        attackingSpeedBoostModifier = new AttributeModifier(EntityEnderman.attackingSpeedBoostModifierUUID, "Attacking speed boost", 6.199999809265137, 0).setSaved(false);
        (EntityEnderman.carriableBlocks = new boolean[256])[Block.getIdFromBlock(Blocks.grass)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.dirt)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.sand)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.gravel)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.yellow_flower)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.red_flower)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.brown_mushroom)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.red_mushroom)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.tnt)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.cactus)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.clay)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.pumpkin)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.melon_block)] = true;
        EntityEnderman.carriableBlocks[Block.getIdFromBlock(Blocks.mycelium)] = true;
    }
}
