package net.minecraft.entity.effect;

import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.nbt.*;

public class EntityLightningBolt extends EntityWeatherEffect
{
    private int lightningState;
    public long boltVertex;
    private int boltLivingTime;
    private static final String __OBFID = "CL_00001666";
    
    public EntityLightningBolt(final World p_i1703_1_, final double p_i1703_2_, final double p_i1703_4_, final double p_i1703_6_) {
        super(p_i1703_1_);
        this.setLocationAndAngles(p_i1703_2_, p_i1703_4_, p_i1703_6_, 0.0f, 0.0f);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        if (!p_i1703_1_.isClient && p_i1703_1_.getGameRules().getGameRuleBooleanValue("doFireTick") && (p_i1703_1_.difficultySetting == EnumDifficulty.NORMAL || p_i1703_1_.difficultySetting == EnumDifficulty.HARD) && p_i1703_1_.doChunksNearChunkExist(MathHelper.floor_double(p_i1703_2_), MathHelper.floor_double(p_i1703_4_), MathHelper.floor_double(p_i1703_6_), 10)) {
            int var8 = MathHelper.floor_double(p_i1703_2_);
            int var9 = MathHelper.floor_double(p_i1703_4_);
            int var10 = MathHelper.floor_double(p_i1703_6_);
            if (p_i1703_1_.getBlock(var8, var9, var10).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(p_i1703_1_, var8, var9, var10)) {
                p_i1703_1_.setBlock(var8, var9, var10, Blocks.fire);
            }
            for (var8 = 0; var8 < 4; ++var8) {
                var9 = MathHelper.floor_double(p_i1703_2_) + this.rand.nextInt(3) - 1;
                var10 = MathHelper.floor_double(p_i1703_4_) + this.rand.nextInt(3) - 1;
                final int var11 = MathHelper.floor_double(p_i1703_6_) + this.rand.nextInt(3) - 1;
                if (p_i1703_1_.getBlock(var9, var10, var11).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(p_i1703_1_, var9, var10, var11)) {
                    p_i1703_1_.setBlock(var9, var10, var11, Blocks.fire);
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.lightningState == 2) {
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "ambient.weather.thunder", 10000.0f, 0.8f + this.rand.nextFloat() * 0.2f);
            this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.explode", 2.0f, 0.5f + this.rand.nextFloat() * 0.2f);
        }
        --this.lightningState;
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
            }
            else if (this.lightningState < -this.rand.nextInt(10)) {
                --this.boltLivingTime;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                if (!this.worldObj.isClient && this.worldObj.getGameRules().getGameRuleBooleanValue("doFireTick") && this.worldObj.doChunksNearChunkExist(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 10)) {
                    final int var1 = MathHelper.floor_double(this.posX);
                    final int var2 = MathHelper.floor_double(this.posY);
                    final int var3 = MathHelper.floor_double(this.posZ);
                    if (this.worldObj.getBlock(var1, var2, var3).getMaterial() == Material.air && Blocks.fire.canPlaceBlockAt(this.worldObj, var1, var2, var3)) {
                        this.worldObj.setBlock(var1, var2, var3, Blocks.fire);
                    }
                }
            }
        }
        if (this.lightningState >= 0) {
            if (this.worldObj.isClient) {
                this.worldObj.lastLightningBolt = 2;
            }
            else {
                final double var4 = 3.0;
                final List var5 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, AxisAlignedBB.getBoundingBox(this.posX - var4, this.posY - var4, this.posZ - var4, this.posX + var4, this.posY + 6.0 + var4, this.posZ + var4));
                for (int var6 = 0; var6 < var5.size(); ++var6) {
                    final Entity var7 = var5.get(var6);
                    var7.onStruckByLightning(this);
                }
            }
        }
    }
    
    @Override
    protected void entityInit() {
    }
    
    @Override
    protected void readEntityFromNBT(final NBTTagCompound p_70037_1_) {
    }
    
    @Override
    protected void writeEntityToNBT(final NBTTagCompound p_70014_1_) {
    }
}
