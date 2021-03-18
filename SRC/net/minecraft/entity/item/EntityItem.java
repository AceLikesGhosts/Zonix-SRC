package net.minecraft.entity.item;

import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class EntityItem extends Entity
{
    private static final Logger logger;
    public int age;
    public int delayBeforeCanPickup;
    private int health;
    private String field_145801_f;
    private String field_145802_g;
    public float hoverStart;
    private static final String __OBFID = "CL_00001669";
    
    public EntityItem(final World p_i1709_1_, final double p_i1709_2_, final double p_i1709_4_, final double p_i1709_6_) {
        super(p_i1709_1_);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
        this.setPosition(p_i1709_2_, p_i1709_4_, p_i1709_6_);
        this.rotationYaw = (float)(Math.random() * 360.0);
        this.motionX = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
        this.motionY = 0.20000000298023224;
        this.motionZ = (float)(Math.random() * 0.20000000298023224 - 0.10000000149011612);
    }
    
    public EntityItem(final World p_i1710_1_, final double p_i1710_2_, final double p_i1710_4_, final double p_i1710_6_, final ItemStack p_i1710_8_) {
        this(p_i1710_1_, p_i1710_2_, p_i1710_4_, p_i1710_6_);
        this.setEntityItemStack(p_i1710_8_);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
    
    public EntityItem(final World p_i1711_1_) {
        super(p_i1711_1_);
        this.health = 5;
        this.hoverStart = (float)(Math.random() * 3.141592653589793 * 2.0);
        this.setSize(0.25f, 0.25f);
        this.yOffset = this.height / 2.0f;
    }
    
    @Override
    protected void entityInit() {
        this.getDataWatcher().addObjectByDataType(10, 5);
    }
    
    @Override
    public void onUpdate() {
        if (this.getEntityItem() == null) {
            this.setDead();
        }
        else {
            super.onUpdate();
            if (this.delayBeforeCanPickup > 0) {
                --this.delayBeforeCanPickup;
            }
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
            this.motionY -= 0.03999999910593033;
            this.noClip = this.func_145771_j(this.posX, (this.boundingBox.minY + this.boundingBox.maxY) / 2.0, this.posZ);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            final boolean var1 = (int)this.prevPosX != (int)this.posX || (int)this.prevPosY != (int)this.posY || (int)this.prevPosZ != (int)this.posZ;
            if (var1 || this.ticksExisted % 25 == 0) {
                if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.lava) {
                    this.motionY = 0.20000000298023224;
                    this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f;
                    this.playSound("random.fizz", 0.4f, 2.0f + this.rand.nextFloat() * 0.4f);
                }
                if (!this.worldObj.isClient) {
                    this.searchForOtherItemsNearby();
                }
            }
            float var2 = 0.98f;
            if (this.onGround) {
                var2 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ)).slipperiness * 0.98f;
            }
            this.motionX *= var2;
            this.motionY *= 0.9800000190734863;
            this.motionZ *= var2;
            if (this.onGround) {
                this.motionY *= -0.5;
            }
            ++this.age;
            if (!this.worldObj.isClient && this.age >= 6000) {
                this.setDead();
            }
        }
    }
    
    private void searchForOtherItemsNearby() {
        for (final EntityItem var2 : this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(0.5, 0.0, 0.5))) {
            this.combineItems(var2);
        }
    }
    
    public boolean combineItems(final EntityItem p_70289_1_) {
        if (p_70289_1_ == this) {
            return false;
        }
        if (!p_70289_1_.isEntityAlive() || !this.isEntityAlive()) {
            return false;
        }
        final ItemStack var2 = this.getEntityItem();
        final ItemStack var3 = p_70289_1_.getEntityItem();
        if (var3.getItem() != var2.getItem()) {
            return false;
        }
        if (var3.hasTagCompound() ^ var2.hasTagCompound()) {
            return false;
        }
        if (var3.hasTagCompound() && !var3.getTagCompound().equals(var2.getTagCompound())) {
            return false;
        }
        if (var3.getItem() == null) {
            return false;
        }
        if (var3.getItem().getHasSubtypes() && var3.getItemDamage() != var2.getItemDamage()) {
            return false;
        }
        if (var3.stackSize < var2.stackSize) {
            return p_70289_1_.combineItems(this);
        }
        if (var3.stackSize + var2.stackSize > var3.getMaxStackSize()) {
            return false;
        }
        final ItemStack itemStack = var3;
        itemStack.stackSize += var2.stackSize;
        p_70289_1_.delayBeforeCanPickup = Math.max(p_70289_1_.delayBeforeCanPickup, this.delayBeforeCanPickup);
        p_70289_1_.age = Math.min(p_70289_1_.age, this.age);
        p_70289_1_.setEntityItemStack(var3);
        this.setDead();
        return true;
    }
    
    public void setAgeToCreativeDespawnTime() {
        this.age = 4800;
    }
    
    @Override
    public boolean handleWaterMovement() {
        return this.worldObj.handleMaterialAcceleration(this.boundingBox, Material.water, this);
    }
    
    @Override
    protected void dealFireDamage(final int p_70081_1_) {
        this.attackEntityFrom(DamageSource.inFire, (float)p_70081_1_);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource p_70097_1_, final float p_70097_2_) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.getEntityItem() != null && this.getEntityItem().getItem() == Items.nether_star && p_70097_1_.isExplosion()) {
            return false;
        }
        this.setBeenAttacked();
        this.health -= (int)p_70097_2_;
        if (this.health <= 0) {
            this.setDead();
        }
        return false;
    }
    
    public void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
        p_70014_1_.setShort("Health", (byte)this.health);
        p_70014_1_.setShort("Age", (short)this.age);
        if (this.func_145800_j() != null) {
            p_70014_1_.setString("Thrower", this.field_145801_f);
        }
        if (this.func_145798_i() != null) {
            p_70014_1_.setString("Owner", this.field_145802_g);
        }
        if (this.getEntityItem() != null) {
            p_70014_1_.setTag("Item", this.getEntityItem().writeToNBT(new NBTTagCompound()));
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
        this.health = (p_70037_1_.getShort("Health") & 0xFF);
        this.age = p_70037_1_.getShort("Age");
        if (p_70037_1_.hasKey("Owner")) {
            this.field_145802_g = p_70037_1_.getString("Owner");
        }
        if (p_70037_1_.hasKey("Thrower")) {
            this.field_145801_f = p_70037_1_.getString("Thrower");
        }
        final NBTTagCompound var2 = p_70037_1_.getCompoundTag("Item");
        this.setEntityItemStack(ItemStack.loadItemStackFromNBT(var2));
        if (this.getEntityItem() == null) {
            this.setDead();
        }
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer p_70100_1_) {
        if (!this.worldObj.isClient) {
            final ItemStack var2 = this.getEntityItem();
            final int var3 = var2.stackSize;
            if (this.delayBeforeCanPickup == 0 && (this.field_145802_g == null || 6000 - this.age <= 200 || this.field_145802_g.equals(p_70100_1_.getCommandSenderName())) && p_70100_1_.inventory.addItemStackToInventory(var2)) {
                if (var2.getItem() == Item.getItemFromBlock(Blocks.log)) {
                    p_70100_1_.triggerAchievement(AchievementList.mineWood);
                }
                if (var2.getItem() == Item.getItemFromBlock(Blocks.log2)) {
                    p_70100_1_.triggerAchievement(AchievementList.mineWood);
                }
                if (var2.getItem() == Items.leather) {
                    p_70100_1_.triggerAchievement(AchievementList.killCow);
                }
                if (var2.getItem() == Items.diamond) {
                    p_70100_1_.triggerAchievement(AchievementList.diamonds);
                }
                if (var2.getItem() == Items.blaze_rod) {
                    p_70100_1_.triggerAchievement(AchievementList.blazeRod);
                }
                if (var2.getItem() == Items.diamond && this.func_145800_j() != null) {
                    final EntityPlayer var4 = this.worldObj.getPlayerEntityByName(this.func_145800_j());
                    if (var4 != null && var4 != p_70100_1_) {
                        var4.triggerAchievement(AchievementList.field_150966_x);
                    }
                }
                this.worldObj.playSoundAtEntity(p_70100_1_, "random.pop", 0.2f, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
                p_70100_1_.onItemPickup(this, var3);
                if (var2.stackSize <= 0) {
                    this.setDead();
                }
            }
        }
    }
    
    @Override
    public String getCommandSenderName() {
        return StatCollector.translateToLocal("item." + this.getEntityItem().getUnlocalizedName());
    }
    
    @Override
    public boolean canAttackWithItem() {
        return false;
    }
    
    @Override
    public void travelToDimension(final int p_71027_1_) {
        super.travelToDimension(p_71027_1_);
        if (!this.worldObj.isClient) {
            this.searchForOtherItemsNearby();
        }
    }
    
    public ItemStack getEntityItem() {
        final ItemStack var1 = this.getDataWatcher().getWatchableObjectItemStack(10);
        return (var1 == null) ? new ItemStack(Blocks.stone) : var1;
    }
    
    public void setEntityItemStack(final ItemStack p_92058_1_) {
        this.getDataWatcher().updateObject(10, p_92058_1_);
        this.getDataWatcher().setObjectWatched(10);
    }
    
    public String func_145798_i() {
        return this.field_145802_g;
    }
    
    public void func_145797_a(final String p_145797_1_) {
        this.field_145802_g = p_145797_1_;
    }
    
    public String func_145800_j() {
        return this.field_145801_f;
    }
    
    public void func_145799_b(final String p_145799_1_) {
        this.field_145801_f = p_145799_1_;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
