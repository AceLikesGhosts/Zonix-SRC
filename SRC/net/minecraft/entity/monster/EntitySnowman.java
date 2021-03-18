package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
    private static final String __OBFID = "CL_00001650";
    
    public EntitySnowman(final World p_i1692_1_) {
        super(p_i1692_1_);
        this.setSize(0.4f, 1.8f);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0f));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0f));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLiving.class, 0, true, false, IMob.mobSelector));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224);
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        int var1 = MathHelper.floor_double(this.posX);
        int var2 = MathHelper.floor_double(this.posY);
        int var3 = MathHelper.floor_double(this.posZ);
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        if (this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) > 1.0f) {
            this.attackEntityFrom(DamageSource.onFire, 1.0f);
        }
        for (int var4 = 0; var4 < 4; ++var4) {
            var1 = MathHelper.floor_double(this.posX + (var4 % 2 * 2 - 1) * 0.25f);
            var2 = MathHelper.floor_double(this.posY);
            var3 = MathHelper.floor_double(this.posZ + (var4 / 2 % 2 * 2 - 1) * 0.25f);
            if (this.worldObj.getBlock(var1, var2, var3).getMaterial() == Material.air && this.worldObj.getBiomeGenForCoords(var1, var3).getFloatTemperature(var1, var2, var3) < 0.8f && Blocks.snow_layer.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
                this.worldObj.setBlock(var1, var2, var3, Blocks.snow_layer);
            }
        }
    }
    
    @Override
    protected Item func_146068_u() {
        return Items.snowball;
    }
    
    @Override
    protected void dropFewItems(final boolean p_70628_1_, final int p_70628_2_) {
        for (int var3 = this.rand.nextInt(16), var4 = 0; var4 < var3; ++var4) {
            this.func_145779_a(Items.snowball, 1);
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase p_82196_1_, final float p_82196_2_) {
        final EntitySnowball var3 = new EntitySnowball(this.worldObj, this);
        final double var4 = p_82196_1_.posX - this.posX;
        final double var5 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858 - var3.posY;
        final double var6 = p_82196_1_.posZ - this.posZ;
        final float var7 = MathHelper.sqrt_double(var4 * var4 + var6 * var6) * 0.2f;
        var3.setThrowableHeading(var4, var5 + var7, var6, 1.6f, 12.0f);
        this.playSound("random.bow", 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(var3);
    }
}
