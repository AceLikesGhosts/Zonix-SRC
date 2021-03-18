package net.minecraft.tileentity;

import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;

public abstract class MobSpawnerBaseLogic
{
    public int spawnDelay;
    private String mobID;
    private List minecartToSpawn;
    private WeightedRandomMinecart randomMinecart;
    public double field_98287_c;
    public double field_98284_d;
    private int minSpawnDelay;
    private int maxSpawnDelay;
    private int spawnCount;
    private Entity field_98291_j;
    private int maxNearbyEntities;
    private int activatingRangeFromPlayer;
    private int spawnRange;
    private static final String __OBFID = "CL_00000129";
    
    public MobSpawnerBaseLogic() {
        this.spawnDelay = 20;
        this.mobID = "Pig";
        this.minSpawnDelay = 200;
        this.maxSpawnDelay = 800;
        this.spawnCount = 4;
        this.maxNearbyEntities = 6;
        this.activatingRangeFromPlayer = 16;
        this.spawnRange = 4;
    }
    
    public String getEntityNameToSpawn() {
        if (this.getRandomMinecart() == null) {
            if (this.mobID.equals("Minecart")) {
                this.mobID = "MinecartRideable";
            }
            return this.mobID;
        }
        return this.getRandomMinecart().minecartName;
    }
    
    public void setMobID(final String p_98272_1_) {
        this.mobID = p_98272_1_;
    }
    
    public boolean canRun() {
        return this.getSpawnerWorld().getClosestPlayer(this.getSpawnerX() + 0.5, this.getSpawnerY() + 0.5, this.getSpawnerZ() + 0.5, this.activatingRangeFromPlayer) != null;
    }
    
    public void updateSpawner() {
        if (this.canRun()) {
            if (this.getSpawnerWorld().isClient) {
                final double var1 = this.getSpawnerX() + this.getSpawnerWorld().rand.nextFloat();
                final double var2 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextFloat();
                final double var3 = this.getSpawnerZ() + this.getSpawnerWorld().rand.nextFloat();
                this.getSpawnerWorld().spawnParticle("smoke", var1, var2, var3, 0.0, 0.0, 0.0);
                this.getSpawnerWorld().spawnParticle("flame", var1, var2, var3, 0.0, 0.0, 0.0);
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                }
                this.field_98284_d = this.field_98287_c;
                this.field_98287_c = (this.field_98287_c + 1000.0f / (this.spawnDelay + 200.0f)) % 360.0;
            }
            else {
                if (this.spawnDelay == -1) {
                    this.resetTimer();
                }
                if (this.spawnDelay > 0) {
                    --this.spawnDelay;
                    return;
                }
                boolean var4 = false;
                for (int var5 = 0; var5 < this.spawnCount; ++var5) {
                    final Entity var6 = EntityList.createEntityByName(this.getEntityNameToSpawn(), this.getSpawnerWorld());
                    if (var6 == null) {
                        return;
                    }
                    final int var7 = this.getSpawnerWorld().getEntitiesWithinAABB(var6.getClass(), AxisAlignedBB.getBoundingBox(this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), this.getSpawnerX() + 1, this.getSpawnerY() + 1, this.getSpawnerZ() + 1).expand(this.spawnRange * 2, 4.0, this.spawnRange * 2)).size();
                    if (var7 >= this.maxNearbyEntities) {
                        this.resetTimer();
                        return;
                    }
                    final double var3 = this.getSpawnerX() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
                    final double var8 = this.getSpawnerY() + this.getSpawnerWorld().rand.nextInt(3) - 1;
                    final double var9 = this.getSpawnerZ() + (this.getSpawnerWorld().rand.nextDouble() - this.getSpawnerWorld().rand.nextDouble()) * this.spawnRange;
                    final EntityLiving var10 = (var6 instanceof EntityLiving) ? ((EntityLiving)var6) : null;
                    var6.setLocationAndAngles(var3, var8, var9, this.getSpawnerWorld().rand.nextFloat() * 360.0f, 0.0f);
                    if (var10 == null || var10.getCanSpawnHere()) {
                        this.func_98265_a(var6);
                        this.getSpawnerWorld().playAuxSFX(2004, this.getSpawnerX(), this.getSpawnerY(), this.getSpawnerZ(), 0);
                        if (var10 != null) {
                            var10.spawnExplosionParticle();
                        }
                        var4 = true;
                    }
                }
                if (var4) {
                    this.resetTimer();
                }
            }
        }
    }
    
    public Entity func_98265_a(final Entity p_98265_1_) {
        if (this.getRandomMinecart() != null) {
            NBTTagCompound var2 = new NBTTagCompound();
            p_98265_1_.writeToNBTOptional(var2);
            for (final String var4 : this.getRandomMinecart().field_98222_b.func_150296_c()) {
                final NBTBase var5 = this.getRandomMinecart().field_98222_b.getTag(var4);
                var2.setTag(var4, var5.copy());
            }
            p_98265_1_.readFromNBT(var2);
            if (p_98265_1_.worldObj != null) {
                p_98265_1_.worldObj.spawnEntityInWorld(p_98265_1_);
            }
            Entity var6 = p_98265_1_;
            while (var2.func_150297_b("Riding", 10)) {
                final NBTTagCompound var7 = var2.getCompoundTag("Riding");
                final Entity var8 = EntityList.createEntityByName(var7.getString("id"), p_98265_1_.worldObj);
                if (var8 != null) {
                    final NBTTagCompound var9 = new NBTTagCompound();
                    var8.writeToNBTOptional(var9);
                    for (final String var11 : var7.func_150296_c()) {
                        final NBTBase var12 = var7.getTag(var11);
                        var9.setTag(var11, var12.copy());
                    }
                    var8.readFromNBT(var9);
                    var8.setLocationAndAngles(var6.posX, var6.posY, var6.posZ, var6.rotationYaw, var6.rotationPitch);
                    if (p_98265_1_.worldObj != null) {
                        p_98265_1_.worldObj.spawnEntityInWorld(var8);
                    }
                    var6.mountEntity(var8);
                }
                var6 = var8;
                var2 = var7;
            }
        }
        else if (p_98265_1_ instanceof EntityLivingBase && p_98265_1_.worldObj != null) {
            ((EntityLiving)p_98265_1_).onSpawnWithEgg(null);
            this.getSpawnerWorld().spawnEntityInWorld(p_98265_1_);
        }
        return p_98265_1_;
    }
    
    private void resetTimer() {
        if (this.maxSpawnDelay <= this.minSpawnDelay) {
            this.spawnDelay = this.minSpawnDelay;
        }
        else {
            final int var10003 = this.maxSpawnDelay - this.minSpawnDelay;
            this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(var10003);
        }
        if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0) {
            this.setRandomMinecart((WeightedRandomMinecart)WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.minecartToSpawn));
        }
        this.func_98267_a(1);
    }
    
    public void readFromNBT(final NBTTagCompound p_98270_1_) {
        this.mobID = p_98270_1_.getString("EntityId");
        this.spawnDelay = p_98270_1_.getShort("Delay");
        if (p_98270_1_.func_150297_b("SpawnPotentials", 9)) {
            this.minecartToSpawn = new ArrayList();
            final NBTTagList var2 = p_98270_1_.getTagList("SpawnPotentials", 10);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.minecartToSpawn.add(new WeightedRandomMinecart(var2.getCompoundTagAt(var3)));
            }
        }
        else {
            this.minecartToSpawn = null;
        }
        if (p_98270_1_.func_150297_b("SpawnData", 10)) {
            this.setRandomMinecart(new WeightedRandomMinecart(p_98270_1_.getCompoundTag("SpawnData"), this.mobID));
        }
        else {
            this.setRandomMinecart(null);
        }
        if (p_98270_1_.func_150297_b("MinSpawnDelay", 99)) {
            this.minSpawnDelay = p_98270_1_.getShort("MinSpawnDelay");
            this.maxSpawnDelay = p_98270_1_.getShort("MaxSpawnDelay");
            this.spawnCount = p_98270_1_.getShort("SpawnCount");
        }
        if (p_98270_1_.func_150297_b("MaxNearbyEntities", 99)) {
            this.maxNearbyEntities = p_98270_1_.getShort("MaxNearbyEntities");
            this.activatingRangeFromPlayer = p_98270_1_.getShort("RequiredPlayerRange");
        }
        if (p_98270_1_.func_150297_b("SpawnRange", 99)) {
            this.spawnRange = p_98270_1_.getShort("SpawnRange");
        }
        if (this.getSpawnerWorld() != null && this.getSpawnerWorld().isClient) {
            this.field_98291_j = null;
        }
    }
    
    public void writeToNBT(final NBTTagCompound p_98280_1_) {
        p_98280_1_.setString("EntityId", this.getEntityNameToSpawn());
        p_98280_1_.setShort("Delay", (short)this.spawnDelay);
        p_98280_1_.setShort("MinSpawnDelay", (short)this.minSpawnDelay);
        p_98280_1_.setShort("MaxSpawnDelay", (short)this.maxSpawnDelay);
        p_98280_1_.setShort("SpawnCount", (short)this.spawnCount);
        p_98280_1_.setShort("MaxNearbyEntities", (short)this.maxNearbyEntities);
        p_98280_1_.setShort("RequiredPlayerRange", (short)this.activatingRangeFromPlayer);
        p_98280_1_.setShort("SpawnRange", (short)this.spawnRange);
        if (this.getRandomMinecart() != null) {
            p_98280_1_.setTag("SpawnData", this.getRandomMinecart().field_98222_b.copy());
        }
        if (this.getRandomMinecart() != null || (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0)) {
            final NBTTagList var2 = new NBTTagList();
            if (this.minecartToSpawn != null && this.minecartToSpawn.size() > 0) {
                for (final WeightedRandomMinecart var4 : this.minecartToSpawn) {
                    var2.appendTag(var4.func_98220_a());
                }
            }
            else {
                var2.appendTag(this.getRandomMinecart().func_98220_a());
            }
            p_98280_1_.setTag("SpawnPotentials", var2);
        }
    }
    
    public Entity func_98281_h() {
        if (this.field_98291_j == null) {
            Entity var1 = EntityList.createEntityByName(this.getEntityNameToSpawn(), null);
            var1 = this.func_98265_a(var1);
            this.field_98291_j = var1;
        }
        return this.field_98291_j;
    }
    
    public boolean setDelayToMin(final int p_98268_1_) {
        if (p_98268_1_ == 1 && this.getSpawnerWorld().isClient) {
            this.spawnDelay = this.minSpawnDelay;
            return true;
        }
        return false;
    }
    
    public WeightedRandomMinecart getRandomMinecart() {
        return this.randomMinecart;
    }
    
    public void setRandomMinecart(final WeightedRandomMinecart p_98277_1_) {
        this.randomMinecart = p_98277_1_;
    }
    
    public abstract void func_98267_a(final int p0);
    
    public abstract World getSpawnerWorld();
    
    public abstract int getSpawnerX();
    
    public abstract int getSpawnerY();
    
    public abstract int getSpawnerZ();
    
    public class WeightedRandomMinecart extends WeightedRandom.Item
    {
        public final NBTTagCompound field_98222_b;
        public final String minecartName;
        private static final String __OBFID = "CL_00000130";
        
        public WeightedRandomMinecart(final NBTTagCompound p_i1945_2_) {
            super(p_i1945_2_.getInteger("Weight"));
            final NBTTagCompound var3 = p_i1945_2_.getCompoundTag("Properties");
            String var4 = p_i1945_2_.getString("Type");
            if (var4.equals("Minecart")) {
                if (var3 != null) {
                    switch (var3.getInteger("Type")) {
                        case 0: {
                            var4 = "MinecartRideable";
                            break;
                        }
                        case 1: {
                            var4 = "MinecartChest";
                            break;
                        }
                        case 2: {
                            var4 = "MinecartFurnace";
                            break;
                        }
                    }
                }
                else {
                    var4 = "MinecartRideable";
                }
            }
            this.field_98222_b = var3;
            this.minecartName = var4;
        }
        
        public WeightedRandomMinecart(final NBTTagCompound p_i1946_2_, String p_i1946_3_) {
            super(1);
            if (p_i1946_3_.equals("Minecart")) {
                if (p_i1946_2_ != null) {
                    switch (p_i1946_2_.getInteger("Type")) {
                        case 0: {
                            p_i1946_3_ = "MinecartRideable";
                            break;
                        }
                        case 1: {
                            p_i1946_3_ = "MinecartChest";
                            break;
                        }
                        case 2: {
                            p_i1946_3_ = "MinecartFurnace";
                            break;
                        }
                    }
                }
                else {
                    p_i1946_3_ = "MinecartRideable";
                }
            }
            this.field_98222_b = p_i1946_2_;
            this.minecartName = p_i1946_3_;
        }
        
        public NBTTagCompound func_98220_a() {
            final NBTTagCompound var1 = new NBTTagCompound();
            var1.setTag("Properties", this.field_98222_b);
            var1.setString("Type", this.minecartName);
            var1.setInteger("Weight", this.itemWeight);
            return var1;
        }
    }
}
