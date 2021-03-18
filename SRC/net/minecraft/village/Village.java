package net.minecraft.village;

import net.minecraft.entity.monster.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.nbt.*;

public class Village
{
    private World worldObj;
    private final List villageDoorInfoList;
    private final ChunkCoordinates centerHelper;
    private final ChunkCoordinates center;
    private int villageRadius;
    private int lastAddDoorTimestamp;
    private int tickCounter;
    private int numVillagers;
    private int noBreedTicks;
    private TreeMap playerReputation;
    private List villageAgressors;
    private int numIronGolems;
    private static final String __OBFID = "CL_00001631";
    
    public Village() {
        this.villageDoorInfoList = new ArrayList();
        this.centerHelper = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.playerReputation = new TreeMap();
        this.villageAgressors = new ArrayList();
    }
    
    public Village(final World p_i1675_1_) {
        this.villageDoorInfoList = new ArrayList();
        this.centerHelper = new ChunkCoordinates(0, 0, 0);
        this.center = new ChunkCoordinates(0, 0, 0);
        this.playerReputation = new TreeMap();
        this.villageAgressors = new ArrayList();
        this.worldObj = p_i1675_1_;
    }
    
    public void func_82691_a(final World p_82691_1_) {
        this.worldObj = p_82691_1_;
    }
    
    public void tick(final int p_75560_1_) {
        this.tickCounter = p_75560_1_;
        this.removeDeadAndOutOfRangeDoors();
        this.removeDeadAndOldAgressors();
        if (p_75560_1_ % 20 == 0) {
            this.updateNumVillagers();
        }
        if (p_75560_1_ % 30 == 0) {
            this.updateNumIronGolems();
        }
        final int var2 = this.numVillagers / 10;
        if (this.numIronGolems < var2 && this.villageDoorInfoList.size() > 20 && this.worldObj.rand.nextInt(7000) == 0) {
            final Vec3 var3 = this.tryGetIronGolemSpawningLocation(MathHelper.floor_float((float)this.center.posX), MathHelper.floor_float((float)this.center.posY), MathHelper.floor_float((float)this.center.posZ), 2, 4, 2);
            if (var3 != null) {
                final EntityIronGolem var4 = new EntityIronGolem(this.worldObj);
                var4.setPosition(var3.xCoord, var3.yCoord, var3.zCoord);
                this.worldObj.spawnEntityInWorld(var4);
                ++this.numIronGolems;
            }
        }
    }
    
    private Vec3 tryGetIronGolemSpawningLocation(final int p_75559_1_, final int p_75559_2_, final int p_75559_3_, final int p_75559_4_, final int p_75559_5_, final int p_75559_6_) {
        for (int var7 = 0; var7 < 10; ++var7) {
            final int var8 = p_75559_1_ + this.worldObj.rand.nextInt(16) - 8;
            final int var9 = p_75559_2_ + this.worldObj.rand.nextInt(6) - 3;
            final int var10 = p_75559_3_ + this.worldObj.rand.nextInt(16) - 8;
            if (this.isInRange(var8, var9, var10) && this.isValidIronGolemSpawningLocation(var8, var9, var10, p_75559_4_, p_75559_5_, p_75559_6_)) {
                return Vec3.createVectorHelper(var8, var9, var10);
            }
        }
        return null;
    }
    
    private boolean isValidIronGolemSpawningLocation(final int p_75563_1_, final int p_75563_2_, final int p_75563_3_, final int p_75563_4_, final int p_75563_5_, final int p_75563_6_) {
        if (!World.doesBlockHaveSolidTopSurface(this.worldObj, p_75563_1_, p_75563_2_ - 1, p_75563_3_)) {
            return false;
        }
        final int var7 = p_75563_1_ - p_75563_4_ / 2;
        final int var8 = p_75563_3_ - p_75563_6_ / 2;
        for (int var9 = var7; var9 < var7 + p_75563_4_; ++var9) {
            for (int var10 = p_75563_2_; var10 < p_75563_2_ + p_75563_5_; ++var10) {
                for (int var11 = var8; var11 < var8 + p_75563_6_; ++var11) {
                    if (this.worldObj.getBlock(var9, var10, var11).isNormalCube()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void updateNumIronGolems() {
        final List var1 = this.worldObj.getEntitiesWithinAABB(EntityIronGolem.class, AxisAlignedBB.getBoundingBox(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numIronGolems = var1.size();
    }
    
    private void updateNumVillagers() {
        final List var1 = this.worldObj.getEntitiesWithinAABB(EntityVillager.class, AxisAlignedBB.getBoundingBox(this.center.posX - this.villageRadius, this.center.posY - 4, this.center.posZ - this.villageRadius, this.center.posX + this.villageRadius, this.center.posY + 4, this.center.posZ + this.villageRadius));
        this.numVillagers = var1.size();
        if (this.numVillagers == 0) {
            this.playerReputation.clear();
        }
    }
    
    public ChunkCoordinates getCenter() {
        return this.center;
    }
    
    public int getVillageRadius() {
        return this.villageRadius;
    }
    
    public int getNumVillageDoors() {
        return this.villageDoorInfoList.size();
    }
    
    public int getTicksSinceLastDoorAdding() {
        return this.tickCounter - this.lastAddDoorTimestamp;
    }
    
    public int getNumVillagers() {
        return this.numVillagers;
    }
    
    public boolean isInRange(final int p_75570_1_, final int p_75570_2_, final int p_75570_3_) {
        return this.center.getDistanceSquared(p_75570_1_, p_75570_2_, p_75570_3_) < this.villageRadius * this.villageRadius;
    }
    
    public List getVillageDoorInfoList() {
        return this.villageDoorInfoList;
    }
    
    public VillageDoorInfo findNearestDoor(final int p_75564_1_, final int p_75564_2_, final int p_75564_3_) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var7 : this.villageDoorInfoList) {
            final int var8 = var7.getDistanceSquared(p_75564_1_, p_75564_2_, p_75564_3_);
            if (var8 < var5) {
                var4 = var7;
                var5 = var8;
            }
        }
        return var4;
    }
    
    public VillageDoorInfo findNearestDoorUnrestricted(final int p_75569_1_, final int p_75569_2_, final int p_75569_3_) {
        VillageDoorInfo var4 = null;
        int var5 = Integer.MAX_VALUE;
        for (final VillageDoorInfo var7 : this.villageDoorInfoList) {
            int var8 = var7.getDistanceSquared(p_75569_1_, p_75569_2_, p_75569_3_);
            if (var8 > 256) {
                var8 *= 1000;
            }
            else {
                var8 = var7.getDoorOpeningRestrictionCounter();
            }
            if (var8 < var5) {
                var4 = var7;
                var5 = var8;
            }
        }
        return var4;
    }
    
    public VillageDoorInfo getVillageDoorAt(final int p_75578_1_, final int p_75578_2_, final int p_75578_3_) {
        if (this.center.getDistanceSquared(p_75578_1_, p_75578_2_, p_75578_3_) > this.villageRadius * this.villageRadius) {
            return null;
        }
        for (final VillageDoorInfo var5 : this.villageDoorInfoList) {
            if (var5.posX == p_75578_1_ && var5.posZ == p_75578_3_ && Math.abs(var5.posY - p_75578_2_) <= 1) {
                return var5;
            }
        }
        return null;
    }
    
    public void addVillageDoorInfo(final VillageDoorInfo p_75576_1_) {
        this.villageDoorInfoList.add(p_75576_1_);
        final ChunkCoordinates centerHelper = this.centerHelper;
        centerHelper.posX += p_75576_1_.posX;
        final ChunkCoordinates centerHelper2 = this.centerHelper;
        centerHelper2.posY += p_75576_1_.posY;
        final ChunkCoordinates centerHelper3 = this.centerHelper;
        centerHelper3.posZ += p_75576_1_.posZ;
        this.updateVillageRadiusAndCenter();
        this.lastAddDoorTimestamp = p_75576_1_.lastActivityTimestamp;
    }
    
    public boolean isAnnihilated() {
        return this.villageDoorInfoList.isEmpty();
    }
    
    public void addOrRenewAgressor(final EntityLivingBase p_75575_1_) {
        for (final VillageAgressor var3 : this.villageAgressors) {
            if (var3.agressor == p_75575_1_) {
                var3.agressionTime = this.tickCounter;
                return;
            }
        }
        this.villageAgressors.add(new VillageAgressor(p_75575_1_, this.tickCounter));
    }
    
    public EntityLivingBase findNearestVillageAggressor(final EntityLivingBase p_75571_1_) {
        double var2 = Double.MAX_VALUE;
        VillageAgressor var3 = null;
        for (int var4 = 0; var4 < this.villageAgressors.size(); ++var4) {
            final VillageAgressor var5 = this.villageAgressors.get(var4);
            final double var6 = var5.agressor.getDistanceSqToEntity(p_75571_1_);
            if (var6 <= var2) {
                var3 = var5;
                var2 = var6;
            }
        }
        return (var3 != null) ? var3.agressor : null;
    }
    
    public EntityPlayer func_82685_c(final EntityLivingBase p_82685_1_) {
        double var2 = Double.MAX_VALUE;
        EntityPlayer var3 = null;
        for (final String var5 : this.playerReputation.keySet()) {
            if (this.isPlayerReputationTooLow(var5)) {
                final EntityPlayer var6 = this.worldObj.getPlayerEntityByName(var5);
                if (var6 == null) {
                    continue;
                }
                final double var7 = var6.getDistanceSqToEntity(p_82685_1_);
                if (var7 > var2) {
                    continue;
                }
                var3 = var6;
                var2 = var7;
            }
        }
        return var3;
    }
    
    private void removeDeadAndOldAgressors() {
        final Iterator var1 = this.villageAgressors.iterator();
        while (var1.hasNext()) {
            final VillageAgressor var2 = var1.next();
            if (!var2.agressor.isEntityAlive() || Math.abs(this.tickCounter - var2.agressionTime) > 300) {
                var1.remove();
            }
        }
    }
    
    private void removeDeadAndOutOfRangeDoors() {
        boolean var1 = false;
        final boolean var2 = this.worldObj.rand.nextInt(50) == 0;
        final Iterator var3 = this.villageDoorInfoList.iterator();
        while (var3.hasNext()) {
            final VillageDoorInfo var4 = var3.next();
            if (var2) {
                var4.resetDoorOpeningRestrictionCounter();
            }
            if (!this.isBlockDoor(var4.posX, var4.posY, var4.posZ) || Math.abs(this.tickCounter - var4.lastActivityTimestamp) > 1200) {
                final ChunkCoordinates centerHelper = this.centerHelper;
                centerHelper.posX -= var4.posX;
                final ChunkCoordinates centerHelper2 = this.centerHelper;
                centerHelper2.posY -= var4.posY;
                final ChunkCoordinates centerHelper3 = this.centerHelper;
                centerHelper3.posZ -= var4.posZ;
                var1 = true;
                var4.isDetachedFromVillageFlag = true;
                var3.remove();
            }
        }
        if (var1) {
            this.updateVillageRadiusAndCenter();
        }
    }
    
    private boolean isBlockDoor(final int p_75574_1_, final int p_75574_2_, final int p_75574_3_) {
        return this.worldObj.getBlock(p_75574_1_, p_75574_2_, p_75574_3_) == Blocks.wooden_door;
    }
    
    private void updateVillageRadiusAndCenter() {
        final int var1 = this.villageDoorInfoList.size();
        if (var1 == 0) {
            this.center.set(0, 0, 0);
            this.villageRadius = 0;
        }
        else {
            this.center.set(this.centerHelper.posX / var1, this.centerHelper.posY / var1, this.centerHelper.posZ / var1);
            int var2 = 0;
            for (final VillageDoorInfo var4 : this.villageDoorInfoList) {
                var2 = Math.max(var4.getDistanceSquared(this.center.posX, this.center.posY, this.center.posZ), var2);
            }
            this.villageRadius = Math.max(32, (int)Math.sqrt(var2) + 1);
        }
    }
    
    public int getReputationForPlayer(final String p_82684_1_) {
        final Integer var2 = this.playerReputation.get(p_82684_1_);
        return (var2 != null) ? var2 : 0;
    }
    
    public int setReputationForPlayer(final String p_82688_1_, final int p_82688_2_) {
        final int var3 = this.getReputationForPlayer(p_82688_1_);
        final int var4 = MathHelper.clamp_int(var3 + p_82688_2_, -30, 10);
        this.playerReputation.put(p_82688_1_, var4);
        return var4;
    }
    
    public boolean isPlayerReputationTooLow(final String p_82687_1_) {
        return this.getReputationForPlayer(p_82687_1_) <= -15;
    }
    
    public void readVillageDataFromNBT(final NBTTagCompound p_82690_1_) {
        this.numVillagers = p_82690_1_.getInteger("PopSize");
        this.villageRadius = p_82690_1_.getInteger("Radius");
        this.numIronGolems = p_82690_1_.getInteger("Golems");
        this.lastAddDoorTimestamp = p_82690_1_.getInteger("Stable");
        this.tickCounter = p_82690_1_.getInteger("Tick");
        this.noBreedTicks = p_82690_1_.getInteger("MTick");
        this.center.posX = p_82690_1_.getInteger("CX");
        this.center.posY = p_82690_1_.getInteger("CY");
        this.center.posZ = p_82690_1_.getInteger("CZ");
        this.centerHelper.posX = p_82690_1_.getInteger("ACX");
        this.centerHelper.posY = p_82690_1_.getInteger("ACY");
        this.centerHelper.posZ = p_82690_1_.getInteger("ACZ");
        final NBTTagList var2 = p_82690_1_.getTagList("Doors", 10);
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final VillageDoorInfo var5 = new VillageDoorInfo(var4.getInteger("X"), var4.getInteger("Y"), var4.getInteger("Z"), var4.getInteger("IDX"), var4.getInteger("IDZ"), var4.getInteger("TS"));
            this.villageDoorInfoList.add(var5);
        }
        final NBTTagList var6 = p_82690_1_.getTagList("Players", 10);
        for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
            final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
            this.playerReputation.put(var8.getString("Name"), var8.getInteger("S"));
        }
    }
    
    public void writeVillageDataToNBT(final NBTTagCompound p_82689_1_) {
        p_82689_1_.setInteger("PopSize", this.numVillagers);
        p_82689_1_.setInteger("Radius", this.villageRadius);
        p_82689_1_.setInteger("Golems", this.numIronGolems);
        p_82689_1_.setInteger("Stable", this.lastAddDoorTimestamp);
        p_82689_1_.setInteger("Tick", this.tickCounter);
        p_82689_1_.setInteger("MTick", this.noBreedTicks);
        p_82689_1_.setInteger("CX", this.center.posX);
        p_82689_1_.setInteger("CY", this.center.posY);
        p_82689_1_.setInteger("CZ", this.center.posZ);
        p_82689_1_.setInteger("ACX", this.centerHelper.posX);
        p_82689_1_.setInteger("ACY", this.centerHelper.posY);
        p_82689_1_.setInteger("ACZ", this.centerHelper.posZ);
        final NBTTagList var2 = new NBTTagList();
        for (final VillageDoorInfo var4 : this.villageDoorInfoList) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setInteger("X", var4.posX);
            var5.setInteger("Y", var4.posY);
            var5.setInteger("Z", var4.posZ);
            var5.setInteger("IDX", var4.insideDirectionX);
            var5.setInteger("IDZ", var4.insideDirectionZ);
            var5.setInteger("TS", var4.lastActivityTimestamp);
            var2.appendTag(var5);
        }
        p_82689_1_.setTag("Doors", var2);
        final NBTTagList var6 = new NBTTagList();
        for (final String var8 : this.playerReputation.keySet()) {
            final NBTTagCompound var9 = new NBTTagCompound();
            var9.setString("Name", var8);
            var9.setInteger("S", this.playerReputation.get(var8));
            var6.appendTag(var9);
        }
        p_82689_1_.setTag("Players", var6);
    }
    
    public void endMatingSeason() {
        this.noBreedTicks = this.tickCounter;
    }
    
    public boolean isMatingSeason() {
        return this.noBreedTicks == 0 || this.tickCounter - this.noBreedTicks >= 3600;
    }
    
    public void setDefaultPlayerReputation(final int p_82683_1_) {
        for (final String var3 : this.playerReputation.keySet()) {
            this.setReputationForPlayer(var3, p_82683_1_);
        }
    }
    
    class VillageAgressor
    {
        public EntityLivingBase agressor;
        public int agressionTime;
        private static final String __OBFID = "CL_00001632";
        
        VillageAgressor(final EntityLivingBase p_i1674_2_, final int p_i1674_3_) {
            this.agressor = p_i1674_2_;
            this.agressionTime = p_i1674_3_;
        }
    }
}
