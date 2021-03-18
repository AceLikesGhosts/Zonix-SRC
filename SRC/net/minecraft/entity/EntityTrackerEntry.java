package net.minecraft.entity;

import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.network.play.server.*;
import org.apache.logging.log4j.*;

public class EntityTrackerEntry
{
    private static final Logger logger;
    public Entity myEntity;
    public int blocksDistanceThreshold;
    public int updateFrequency;
    public int lastScaledXPosition;
    public int lastScaledYPosition;
    public int lastScaledZPosition;
    public int lastYaw;
    public int lastPitch;
    public int lastHeadMotion;
    public double motionX;
    public double motionY;
    public double motionZ;
    public int ticks;
    private double posX;
    private double posY;
    private double posZ;
    private boolean isDataInitialized;
    private boolean sendVelocityUpdates;
    private int ticksSinceLastForcedTeleport;
    private Entity field_85178_v;
    private boolean ridingEntity;
    public boolean playerEntitiesUpdated;
    public Set trackingPlayers;
    private static final String __OBFID = "CL_00001443";
    
    public EntityTrackerEntry(final Entity p_i1525_1_, final int p_i1525_2_, final int p_i1525_3_, final boolean p_i1525_4_) {
        this.trackingPlayers = new HashSet();
        this.myEntity = p_i1525_1_;
        this.blocksDistanceThreshold = p_i1525_2_;
        this.updateFrequency = p_i1525_3_;
        this.sendVelocityUpdates = p_i1525_4_;
        this.lastScaledXPosition = MathHelper.floor_double(p_i1525_1_.posX * 32.0);
        this.lastScaledYPosition = MathHelper.floor_double(p_i1525_1_.posY * 32.0);
        this.lastScaledZPosition = MathHelper.floor_double(p_i1525_1_.posZ * 32.0);
        this.lastYaw = MathHelper.floor_float(p_i1525_1_.rotationYaw * 256.0f / 360.0f);
        this.lastPitch = MathHelper.floor_float(p_i1525_1_.rotationPitch * 256.0f / 360.0f);
        this.lastHeadMotion = MathHelper.floor_float(p_i1525_1_.getRotationYawHead() * 256.0f / 360.0f);
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        return p_equals_1_ instanceof EntityTrackerEntry && ((EntityTrackerEntry)p_equals_1_).myEntity.getEntityId() == this.myEntity.getEntityId();
    }
    
    @Override
    public int hashCode() {
        return this.myEntity.getEntityId();
    }
    
    public void sendLocationToAllClients(final List p_73122_1_) {
        this.playerEntitiesUpdated = false;
        if (!this.isDataInitialized || this.myEntity.getDistanceSq(this.posX, this.posY, this.posZ) > 16.0) {
            this.posX = this.myEntity.posX;
            this.posY = this.myEntity.posY;
            this.posZ = this.myEntity.posZ;
            this.isDataInitialized = true;
            this.playerEntitiesUpdated = true;
            this.sendEventsToPlayers(p_73122_1_);
        }
        if (this.field_85178_v != this.myEntity.ridingEntity || (this.myEntity.ridingEntity != null && this.ticks % 60 == 0)) {
            this.field_85178_v = this.myEntity.ridingEntity;
            this.func_151259_a(new S1BPacketEntityAttach(0, this.myEntity, this.myEntity.ridingEntity));
        }
        if (this.myEntity instanceof EntityItemFrame && this.ticks % 10 == 0) {
            final EntityItemFrame var23 = (EntityItemFrame)this.myEntity;
            final ItemStack var24 = var23.getDisplayedItem();
            if (var24 != null && var24.getItem() instanceof ItemMap) {
                final MapData var25 = Items.filled_map.getMapData(var24, this.myEntity.worldObj);
                for (final EntityPlayer var27 : p_73122_1_) {
                    final EntityPlayerMP var28 = (EntityPlayerMP)var27;
                    var25.updateVisiblePlayers(var28, var24);
                    final Packet var29 = Items.filled_map.func_150911_c(var24, this.myEntity.worldObj, var28);
                    if (var29 != null) {
                        var28.playerNetServerHandler.sendPacket(var29);
                    }
                }
            }
            this.func_111190_b();
        }
        else if (this.ticks % this.updateFrequency == 0 || this.myEntity.isAirBorne || this.myEntity.getDataWatcher().hasChanges()) {
            if (this.myEntity.ridingEntity == null) {
                ++this.ticksSinceLastForcedTeleport;
                final int var30 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                final int var31 = MathHelper.floor_double(this.myEntity.posY * 32.0);
                final int var32 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                final int var33 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0f / 360.0f);
                final int var34 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0f / 360.0f);
                final int var35 = var30 - this.lastScaledXPosition;
                final int var36 = var31 - this.lastScaledYPosition;
                final int var37 = var32 - this.lastScaledZPosition;
                Object var38 = null;
                final boolean var39 = Math.abs(var35) >= 4 || Math.abs(var36) >= 4 || Math.abs(var37) >= 4 || this.ticks % 60 == 0;
                final boolean var40 = Math.abs(var33 - this.lastYaw) >= 4 || Math.abs(var34 - this.lastPitch) >= 4;
                if (this.ticks > 0 || this.myEntity instanceof EntityArrow) {
                    if (var35 >= -128 && var35 < 128 && var36 >= -128 && var36 < 128 && var37 >= -128 && var37 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity) {
                        if (var39 && var40) {
                            var38 = new S14PacketEntity.S17PacketEntityLookMove(this.myEntity.getEntityId(), (byte)var35, (byte)var36, (byte)var37, (byte)var33, (byte)var34);
                        }
                        else if (var39) {
                            var38 = new S14PacketEntity.S15PacketEntityRelMove(this.myEntity.getEntityId(), (byte)var35, (byte)var36, (byte)var37);
                        }
                        else if (var40) {
                            var38 = new S14PacketEntity.S16PacketEntityLook(this.myEntity.getEntityId(), (byte)var33, (byte)var34);
                        }
                    }
                    else {
                        this.ticksSinceLastForcedTeleport = 0;
                        var38 = new S18PacketEntityTeleport(this.myEntity.getEntityId(), var30, var31, var32, (byte)var33, (byte)var34);
                    }
                }
                if (this.sendVelocityUpdates) {
                    final double var41 = this.myEntity.motionX - this.motionX;
                    final double var42 = this.myEntity.motionY - this.motionY;
                    final double var43 = this.myEntity.motionZ - this.motionZ;
                    final double var44 = 0.02;
                    final double var45 = var41 * var41 + var42 * var42 + var43 * var43;
                    if (var45 > var44 * var44 || (var45 > 0.0 && this.myEntity.motionX == 0.0 && this.myEntity.motionY == 0.0 && this.myEntity.motionZ == 0.0)) {
                        this.motionX = this.myEntity.motionX;
                        this.motionY = this.myEntity.motionY;
                        this.motionZ = this.myEntity.motionZ;
                        this.func_151259_a(new S12PacketEntityVelocity(this.myEntity.getEntityId(), this.motionX, this.motionY, this.motionZ));
                    }
                }
                if (var38 != null) {
                    this.func_151259_a((Packet)var38);
                }
                this.func_111190_b();
                if (var39) {
                    this.lastScaledXPosition = var30;
                    this.lastScaledYPosition = var31;
                    this.lastScaledZPosition = var32;
                }
                if (var40) {
                    this.lastYaw = var33;
                    this.lastPitch = var34;
                }
                this.ridingEntity = false;
            }
            else {
                final int var30 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0f / 360.0f);
                final int var31 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0f / 360.0f);
                final boolean var46 = Math.abs(var30 - this.lastYaw) >= 4 || Math.abs(var31 - this.lastPitch) >= 4;
                if (var46) {
                    this.func_151259_a(new S14PacketEntity.S16PacketEntityLook(this.myEntity.getEntityId(), (byte)var30, (byte)var31));
                    this.lastYaw = var30;
                    this.lastPitch = var31;
                }
                this.lastScaledXPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                this.lastScaledYPosition = MathHelper.floor_double(this.myEntity.posY * 32.0);
                this.lastScaledZPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                this.func_111190_b();
                this.ridingEntity = true;
            }
            final int var30 = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(var30 - this.lastHeadMotion) >= 4) {
                this.func_151259_a(new S19PacketEntityHeadLook(this.myEntity, (byte)var30));
                this.lastHeadMotion = var30;
            }
            this.myEntity.isAirBorne = false;
        }
        ++this.ticks;
        if (this.myEntity.velocityChanged) {
            this.func_151261_b(new S12PacketEntityVelocity(this.myEntity));
            this.myEntity.velocityChanged = false;
        }
    }
    
    private void func_111190_b() {
        final DataWatcher var1 = this.myEntity.getDataWatcher();
        if (var1.hasChanges()) {
            this.func_151261_b(new S1CPacketEntityMetadata(this.myEntity.getEntityId(), var1, false));
        }
        if (this.myEntity instanceof EntityLivingBase) {
            final ServersideAttributeMap var2 = (ServersideAttributeMap)((EntityLivingBase)this.myEntity).getAttributeMap();
            final Set var3 = var2.getAttributeInstanceSet();
            if (!var3.isEmpty()) {
                this.func_151261_b(new S20PacketEntityProperties(this.myEntity.getEntityId(), var3));
            }
            var3.clear();
        }
    }
    
    public void func_151259_a(final Packet p_151259_1_) {
        for (final EntityPlayerMP var3 : this.trackingPlayers) {
            var3.playerNetServerHandler.sendPacket(p_151259_1_);
        }
    }
    
    public void func_151261_b(final Packet p_151261_1_) {
        this.func_151259_a(p_151261_1_);
        if (this.myEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.myEntity).playerNetServerHandler.sendPacket(p_151261_1_);
        }
    }
    
    public void informAllAssociatedPlayersOfItemDestruction() {
        for (final EntityPlayerMP var2 : this.trackingPlayers) {
            var2.func_152339_d(this.myEntity);
        }
    }
    
    public void removeFromWatchingList(final EntityPlayerMP p_73118_1_) {
        if (this.trackingPlayers.contains(p_73118_1_)) {
            p_73118_1_.func_152339_d(this.myEntity);
            this.trackingPlayers.remove(p_73118_1_);
        }
    }
    
    public void tryStartWachingThis(final EntityPlayerMP p_73117_1_) {
        if (p_73117_1_ != this.myEntity) {
            final double var2 = p_73117_1_.posX - this.lastScaledXPosition / 32;
            final double var3 = p_73117_1_.posZ - this.lastScaledZPosition / 32;
            if (var2 >= -this.blocksDistanceThreshold && var2 <= this.blocksDistanceThreshold && var3 >= -this.blocksDistanceThreshold && var3 <= this.blocksDistanceThreshold) {
                if (!this.trackingPlayers.contains(p_73117_1_) && (this.isPlayerWatchingThisChunk(p_73117_1_) || this.myEntity.forceSpawn)) {
                    this.trackingPlayers.add(p_73117_1_);
                    final Packet var4 = this.func_151260_c();
                    p_73117_1_.playerNetServerHandler.sendPacket(var4);
                    if (!this.myEntity.getDataWatcher().getIsBlank()) {
                        p_73117_1_.playerNetServerHandler.sendPacket(new S1CPacketEntityMetadata(this.myEntity.getEntityId(), this.myEntity.getDataWatcher(), true));
                    }
                    if (this.myEntity instanceof EntityLivingBase) {
                        final ServersideAttributeMap var5 = (ServersideAttributeMap)((EntityLivingBase)this.myEntity).getAttributeMap();
                        final Collection var6 = var5.getWatchedAttributes();
                        if (!var6.isEmpty()) {
                            p_73117_1_.playerNetServerHandler.sendPacket(new S20PacketEntityProperties(this.myEntity.getEntityId(), var6));
                        }
                    }
                    this.motionX = this.myEntity.motionX;
                    this.motionY = this.myEntity.motionY;
                    this.motionZ = this.myEntity.motionZ;
                    if (this.sendVelocityUpdates && !(var4 instanceof S0FPacketSpawnMob)) {
                        p_73117_1_.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(this.myEntity.getEntityId(), this.myEntity.motionX, this.myEntity.motionY, this.myEntity.motionZ));
                    }
                    if (this.myEntity.ridingEntity != null) {
                        p_73117_1_.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this.myEntity, this.myEntity.ridingEntity));
                    }
                    if (this.myEntity instanceof EntityLiving && ((EntityLiving)this.myEntity).getLeashedToEntity() != null) {
                        p_73117_1_.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(1, this.myEntity, ((EntityLiving)this.myEntity).getLeashedToEntity()));
                    }
                    if (this.myEntity instanceof EntityLivingBase) {
                        for (int var7 = 0; var7 < 5; ++var7) {
                            final ItemStack var8 = ((EntityLivingBase)this.myEntity).getEquipmentInSlot(var7);
                            if (var8 != null) {
                                p_73117_1_.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(this.myEntity.getEntityId(), var7, var8));
                            }
                        }
                    }
                    if (this.myEntity instanceof EntityPlayer) {
                        final EntityPlayer var9 = (EntityPlayer)this.myEntity;
                        if (var9.isPlayerSleeping()) {
                            p_73117_1_.playerNetServerHandler.sendPacket(new S0APacketUseBed(var9, MathHelper.floor_double(this.myEntity.posX), MathHelper.floor_double(this.myEntity.posY), MathHelper.floor_double(this.myEntity.posZ)));
                        }
                    }
                    if (this.myEntity instanceof EntityLivingBase) {
                        final EntityLivingBase var10 = (EntityLivingBase)this.myEntity;
                        for (final PotionEffect var12 : var10.getActivePotionEffects()) {
                            p_73117_1_.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.myEntity.getEntityId(), var12));
                        }
                    }
                }
            }
            else if (this.trackingPlayers.contains(p_73117_1_)) {
                this.trackingPlayers.remove(p_73117_1_);
                p_73117_1_.func_152339_d(this.myEntity);
            }
        }
    }
    
    private boolean isPlayerWatchingThisChunk(final EntityPlayerMP p_73121_1_) {
        return p_73121_1_.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(p_73121_1_, this.myEntity.chunkCoordX, this.myEntity.chunkCoordZ);
    }
    
    public void sendEventsToPlayers(final List p_73125_1_) {
        for (int var2 = 0; var2 < p_73125_1_.size(); ++var2) {
            this.tryStartWachingThis(p_73125_1_.get(var2));
        }
    }
    
    private Packet func_151260_c() {
        if (this.myEntity.isDead) {
            EntityTrackerEntry.logger.warn("Fetching addPacket for removed entity");
        }
        if (this.myEntity instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.myEntity, 2, 1);
        }
        if (this.myEntity instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer)this.myEntity);
        }
        if (this.myEntity instanceof EntityMinecart) {
            final EntityMinecart var9 = (EntityMinecart)this.myEntity;
            return new S0EPacketSpawnObject(this.myEntity, 10, var9.getMinecartType());
        }
        if (this.myEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.myEntity, 1);
        }
        if (this.myEntity instanceof IAnimals || this.myEntity instanceof EntityDragon) {
            this.lastHeadMotion = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0f / 360.0f);
            return new S0FPacketSpawnMob((EntityLivingBase)this.myEntity);
        }
        if (this.myEntity instanceof EntityFishHook) {
            final EntityPlayer var10 = ((EntityFishHook)this.myEntity).field_146042_b;
            return new S0EPacketSpawnObject(this.myEntity, 90, (var10 != null) ? var10.getEntityId() : this.myEntity.getEntityId());
        }
        if (this.myEntity instanceof EntityArrow) {
            final Entity var11 = ((EntityArrow)this.myEntity).shootingEntity;
            return new S0EPacketSpawnObject(this.myEntity, 60, (var11 != null) ? var11.getEntityId() : this.myEntity.getEntityId());
        }
        if (this.myEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.myEntity, 61);
        }
        if (this.myEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.myEntity, 73, ((EntityPotion)this.myEntity).getPotionDamage());
        }
        if (this.myEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.myEntity, 75);
        }
        if (this.myEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.myEntity, 65);
        }
        if (this.myEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.myEntity, 72);
        }
        if (this.myEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.myEntity, 76);
        }
        if (this.myEntity instanceof EntityFireball) {
            final EntityFireball var12 = (EntityFireball)this.myEntity;
            S0EPacketSpawnObject var13 = null;
            byte var14 = 63;
            if (this.myEntity instanceof EntitySmallFireball) {
                var14 = 64;
            }
            else if (this.myEntity instanceof EntityWitherSkull) {
                var14 = 66;
            }
            if (var12.shootingEntity != null) {
                var13 = new S0EPacketSpawnObject(this.myEntity, var14, ((EntityFireball)this.myEntity).shootingEntity.getEntityId());
            }
            else {
                var13 = new S0EPacketSpawnObject(this.myEntity, var14, 0);
            }
            var13.func_149003_d((int)(var12.accelerationX * 8000.0));
            var13.func_149000_e((int)(var12.accelerationY * 8000.0));
            var13.func_149007_f((int)(var12.accelerationZ * 8000.0));
            return var13;
        }
        if (this.myEntity instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.myEntity, 62);
        }
        if (this.myEntity instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.myEntity, 50);
        }
        if (this.myEntity instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.myEntity, 51);
        }
        if (this.myEntity instanceof EntityFallingBlock) {
            final EntityFallingBlock var15 = (EntityFallingBlock)this.myEntity;
            return new S0EPacketSpawnObject(this.myEntity, 70, Block.getIdFromBlock(var15.func_145805_f()) | var15.field_145814_a << 16);
        }
        if (this.myEntity instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.myEntity);
        }
        if (this.myEntity instanceof EntityItemFrame) {
            final EntityItemFrame var16 = (EntityItemFrame)this.myEntity;
            final S0EPacketSpawnObject var13 = new S0EPacketSpawnObject(this.myEntity, 71, var16.hangingDirection);
            var13.func_148996_a(MathHelper.floor_float((float)(var16.field_146063_b * 32)));
            var13.func_148995_b(MathHelper.floor_float((float)(var16.field_146064_c * 32)));
            var13.func_149005_c(MathHelper.floor_float((float)(var16.field_146062_d * 32)));
            return var13;
        }
        if (this.myEntity instanceof EntityLeashKnot) {
            final EntityLeashKnot var17 = (EntityLeashKnot)this.myEntity;
            final S0EPacketSpawnObject var13 = new S0EPacketSpawnObject(this.myEntity, 77);
            var13.func_148996_a(MathHelper.floor_float((float)(var17.field_146063_b * 32)));
            var13.func_148995_b(MathHelper.floor_float((float)(var17.field_146064_c * 32)));
            var13.func_149005_c(MathHelper.floor_float((float)(var17.field_146062_d * 32)));
            return var13;
        }
        if (this.myEntity instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.myEntity);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.myEntity.getClass() + "!");
    }
    
    public void removePlayerFromTracker(final EntityPlayerMP p_73123_1_) {
        if (this.trackingPlayers.contains(p_73123_1_)) {
            this.trackingPlayers.remove(p_73123_1_);
            p_73123_1_.func_152339_d(this.myEntity);
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
