package net.minecraft.village;

import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public class VillageCollection extends WorldSavedData
{
    private World worldObj;
    private final List villagerPositionsList;
    private final List newDoors;
    private final List villageList;
    private int tickCounter;
    private static final String __OBFID = "CL_00001635";
    
    public VillageCollection(final String p_i1677_1_) {
        super(p_i1677_1_);
        this.villagerPositionsList = new ArrayList();
        this.newDoors = new ArrayList();
        this.villageList = new ArrayList();
    }
    
    public VillageCollection(final World p_i1678_1_) {
        super("villages");
        this.villagerPositionsList = new ArrayList();
        this.newDoors = new ArrayList();
        this.villageList = new ArrayList();
        this.worldObj = p_i1678_1_;
        this.markDirty();
    }
    
    public void func_82566_a(final World p_82566_1_) {
        this.worldObj = p_82566_1_;
        for (final Village var3 : this.villageList) {
            var3.func_82691_a(p_82566_1_);
        }
    }
    
    public void addVillagerPosition(final int p_75551_1_, final int p_75551_2_, final int p_75551_3_) {
        if (this.villagerPositionsList.size() <= 64 && !this.isVillagerPositionPresent(p_75551_1_, p_75551_2_, p_75551_3_)) {
            this.villagerPositionsList.add(new ChunkCoordinates(p_75551_1_, p_75551_2_, p_75551_3_));
        }
    }
    
    public void tick() {
        ++this.tickCounter;
        for (final Village var2 : this.villageList) {
            var2.tick(this.tickCounter);
        }
        this.removeAnnihilatedVillages();
        this.dropOldestVillagerPosition();
        this.addNewDoorsToVillageOrCreateVillage();
        if (this.tickCounter % 400 == 0) {
            this.markDirty();
        }
    }
    
    private void removeAnnihilatedVillages() {
        final Iterator var1 = this.villageList.iterator();
        while (var1.hasNext()) {
            final Village var2 = var1.next();
            if (var2.isAnnihilated()) {
                var1.remove();
                this.markDirty();
            }
        }
    }
    
    public List getVillageList() {
        return this.villageList;
    }
    
    public Village findNearestVillage(final int p_75550_1_, final int p_75550_2_, final int p_75550_3_, final int p_75550_4_) {
        Village var5 = null;
        float var6 = Float.MAX_VALUE;
        for (final Village var8 : this.villageList) {
            final float var9 = var8.getCenter().getDistanceSquared(p_75550_1_, p_75550_2_, p_75550_3_);
            if (var9 < var6) {
                final float var10 = (float)(p_75550_4_ + var8.getVillageRadius());
                if (var9 > var10 * var10) {
                    continue;
                }
                var5 = var8;
                var6 = var9;
            }
        }
        return var5;
    }
    
    private void dropOldestVillagerPosition() {
        if (!this.villagerPositionsList.isEmpty()) {
            this.addUnassignedWoodenDoorsAroundToNewDoorsList(this.villagerPositionsList.remove(0));
        }
    }
    
    private void addNewDoorsToVillageOrCreateVillage() {
        for (int var1 = 0; var1 < this.newDoors.size(); ++var1) {
            final VillageDoorInfo var2 = this.newDoors.get(var1);
            boolean var3 = false;
            for (final Village var5 : this.villageList) {
                final int var6 = (int)var5.getCenter().getDistanceSquared(var2.posX, var2.posY, var2.posZ);
                final int var7 = 32 + var5.getVillageRadius();
                if (var6 > var7 * var7) {
                    continue;
                }
                var5.addVillageDoorInfo(var2);
                var3 = true;
                break;
            }
            if (!var3) {
                final Village var8 = new Village(this.worldObj);
                var8.addVillageDoorInfo(var2);
                this.villageList.add(var8);
                this.markDirty();
            }
        }
        this.newDoors.clear();
    }
    
    private void addUnassignedWoodenDoorsAroundToNewDoorsList(final ChunkCoordinates p_75546_1_) {
        final byte var2 = 16;
        final byte var3 = 4;
        final byte var4 = 16;
        for (int var5 = p_75546_1_.posX - var2; var5 < p_75546_1_.posX + var2; ++var5) {
            for (int var6 = p_75546_1_.posY - var3; var6 < p_75546_1_.posY + var3; ++var6) {
                for (int var7 = p_75546_1_.posZ - var4; var7 < p_75546_1_.posZ + var4; ++var7) {
                    if (this.isWoodenDoorAt(var5, var6, var7)) {
                        final VillageDoorInfo var8 = this.getVillageDoorAt(var5, var6, var7);
                        if (var8 == null) {
                            this.addDoorToNewListIfAppropriate(var5, var6, var7);
                        }
                        else {
                            var8.lastActivityTimestamp = this.tickCounter;
                        }
                    }
                }
            }
        }
    }
    
    private VillageDoorInfo getVillageDoorAt(final int p_75547_1_, final int p_75547_2_, final int p_75547_3_) {
        for (final VillageDoorInfo var5 : this.newDoors) {
            if (var5.posX == p_75547_1_ && var5.posZ == p_75547_3_ && Math.abs(var5.posY - p_75547_2_) <= 1) {
                return var5;
            }
        }
        for (final Village var6 : this.villageList) {
            final VillageDoorInfo var7 = var6.getVillageDoorAt(p_75547_1_, p_75547_2_, p_75547_3_);
            if (var7 != null) {
                return var7;
            }
        }
        return null;
    }
    
    private void addDoorToNewListIfAppropriate(final int p_75542_1_, final int p_75542_2_, final int p_75542_3_) {
        final int var4 = ((BlockDoor)Blocks.wooden_door).func_150013_e(this.worldObj, p_75542_1_, p_75542_2_, p_75542_3_);
        if (var4 != 0 && var4 != 2) {
            int var5 = 0;
            for (int var6 = -5; var6 < 0; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(p_75542_1_, p_75542_2_, p_75542_3_ + var6)) {
                    --var5;
                }
            }
            for (int var6 = 1; var6 <= 5; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(p_75542_1_, p_75542_2_, p_75542_3_ + var6)) {
                    ++var5;
                }
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(p_75542_1_, p_75542_2_, p_75542_3_, 0, (var5 > 0) ? -2 : 2, this.tickCounter));
            }
        }
        else {
            int var5 = 0;
            for (int var6 = -5; var6 < 0; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(p_75542_1_ + var6, p_75542_2_, p_75542_3_)) {
                    --var5;
                }
            }
            for (int var6 = 1; var6 <= 5; ++var6) {
                if (this.worldObj.canBlockSeeTheSky(p_75542_1_ + var6, p_75542_2_, p_75542_3_)) {
                    ++var5;
                }
            }
            if (var5 != 0) {
                this.newDoors.add(new VillageDoorInfo(p_75542_1_, p_75542_2_, p_75542_3_, (var5 > 0) ? -2 : 2, 0, this.tickCounter));
            }
        }
    }
    
    private boolean isVillagerPositionPresent(final int p_75548_1_, final int p_75548_2_, final int p_75548_3_) {
        for (final ChunkCoordinates var5 : this.villagerPositionsList) {
            if (var5.posX == p_75548_1_ && var5.posY == p_75548_2_ && var5.posZ == p_75548_3_) {
                return true;
            }
        }
        return false;
    }
    
    private boolean isWoodenDoorAt(final int p_75541_1_, final int p_75541_2_, final int p_75541_3_) {
        return this.worldObj.getBlock(p_75541_1_, p_75541_2_, p_75541_3_) == Blocks.wooden_door;
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_76184_1_) {
        this.tickCounter = p_76184_1_.getInteger("Tick");
        final NBTTagList var2 = p_76184_1_.getTagList("Villages", 10);
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
            final Village var5 = new Village();
            var5.readVillageDataFromNBT(var4);
            this.villageList.add(var5);
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_76187_1_) {
        p_76187_1_.setInteger("Tick", this.tickCounter);
        final NBTTagList var2 = new NBTTagList();
        for (final Village var4 : this.villageList) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var4.writeVillageDataToNBT(var5);
            var2.appendTag(var5);
        }
        p_76187_1_.setTag("Villages", var2);
    }
}
