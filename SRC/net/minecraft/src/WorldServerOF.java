package net.minecraft.src;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;
import java.lang.reflect.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

public class WorldServerOF extends WorldServer
{
    private NextTickHashSet pendingTickListEntriesHashSet;
    private TreeSet pendingTickListEntriesTreeSet;
    private List pendingTickListEntriesThisTick;
    private int lastViewDistance;
    private boolean allChunksTicked;
    public Set setChunkCoordsToTickOnce;
    private Set limitedChunkSet;
    private static final Logger logger;
    
    public WorldServerOF(final MinecraftServer par1MinecraftServer, final ISaveHandler par2iSaveHandler, final String par3Str, final int par4, final WorldSettings par5WorldSettings, final Profiler par6Profiler) {
        super(par1MinecraftServer, par2iSaveHandler, par3Str, par4, par5WorldSettings, par6Profiler);
        this.pendingTickListEntriesThisTick = new ArrayList();
        this.lastViewDistance = 0;
        this.allChunksTicked = false;
        this.setChunkCoordsToTickOnce = new HashSet();
        this.limitedChunkSet = new HashSet();
        this.fixSetNextTicks();
    }
    
    @Override
    protected void initialize(final WorldSettings par1WorldSettings) {
        super.initialize(par1WorldSettings);
        this.fixSetNextTicks();
    }
    
    private void fixSetNextTicks() {
        try {
            final Field[] e = WorldServer.class.getDeclaredFields();
            final int posSet = this.findField(e, Set.class, 0);
            final int posTreeSet = this.findField(e, TreeSet.class, posSet);
            final int posList = this.findField(e, List.class, posTreeSet);
            if (posSet >= 0 && posTreeSet >= 0 && posList >= 0) {
                final Field fieldSet = e[posSet];
                final Field fieldTreeSet = e[posTreeSet];
                final Field fieldList = e[posList];
                fieldSet.setAccessible(true);
                fieldTreeSet.setAccessible(true);
                fieldList.setAccessible(true);
                this.pendingTickListEntriesTreeSet = (TreeSet)fieldTreeSet.get(this);
                this.pendingTickListEntriesThisTick = (List)fieldList.get(this);
                final Set oldSet = (Set)fieldSet.get(this);
                if (oldSet instanceof NextTickHashSet) {
                    return;
                }
                fieldSet.set(this, this.pendingTickListEntriesHashSet = new NextTickHashSet(oldSet));
                Config.dbg("WorldServer.nextTickSet updated");
            }
            else {
                Config.warn("Error updating WorldServer.nextTickSet");
            }
        }
        catch (Exception var9) {
            Config.warn("Error setting WorldServer.nextTickSet: " + var9.getMessage());
        }
    }
    
    private int findField(final Field[] fields, final Class cls, final int startPos) {
        if (startPos < 0) {
            return -1;
        }
        for (int i = startPos; i < fields.length; ++i) {
            final Field field = fields[i];
            if (field.getType() == cls) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public List getPendingBlockUpdates(final Chunk par1Chunk, final boolean par2) {
        if (this.pendingTickListEntriesHashSet != null && this.pendingTickListEntriesTreeSet != null && this.pendingTickListEntriesThisTick != null) {
            ArrayList var3 = null;
            final ChunkCoordIntPair var4 = par1Chunk.getChunkCoordIntPair();
            final int var5 = (var4.chunkXPos << 4) - 2;
            final int var6 = var5 + 16 + 2;
            final int var7 = (var4.chunkZPos << 4) - 2;
            final int var8 = var7 + 16 + 2;
            for (int var9 = 0; var9 < 2; ++var9) {
                Iterator var11;
                if (var9 == 0) {
                    final TreeSet var10 = new TreeSet();
                    for (int dx = -1; dx <= 1; ++dx) {
                        for (int dz = -1; dz <= 1; ++dz) {
                            final HashSet set = this.pendingTickListEntriesHashSet.getNextTickEntriesSet(var4.chunkXPos + dx, var4.chunkZPos + dz);
                            var10.addAll(set);
                        }
                    }
                    var11 = var10.iterator();
                }
                else {
                    var11 = this.pendingTickListEntriesThisTick.iterator();
                    if (!this.pendingTickListEntriesThisTick.isEmpty()) {
                        WorldServerOF.logger.debug("toBeTicked = " + this.pendingTickListEntriesThisTick.size());
                    }
                }
                while (var11.hasNext()) {
                    final NextTickListEntry var12 = var11.next();
                    if (var12.xCoord >= var5 && var12.xCoord < var6 && var12.zCoord >= var7 && var12.zCoord < var8) {
                        if (par2) {
                            this.pendingTickListEntriesHashSet.remove(var12);
                            this.pendingTickListEntriesTreeSet.remove(var12);
                            var11.remove();
                        }
                        if (var3 == null) {
                            var3 = new ArrayList();
                        }
                        var3.add(var12);
                    }
                }
            }
            return var3;
        }
        return super.getPendingBlockUpdates(par1Chunk, par2);
    }
    
    @Override
    public void tick() {
        super.tick();
        if (Config.waterOpacityChanged) {
            Config.waterOpacityChanged = false;
            ClearWater.updateWaterOpacity(Config.getGameSettings(), this);
        }
    }
    
    @Override
    protected void updateWeather() {
        if (!Config.isWeatherEnabled()) {
            this.fixWorldWeather();
        }
        super.updateWeather();
    }
    
    @Override
    public void updateEntity(final Entity par1Entity) {
        if (this.canSkipEntityUpdate(par1Entity) && par1Entity instanceof EntityLivingBase) {
            final EntityLivingBase elb = (EntityLivingBase)par1Entity;
            int entityAge = EntityUtils.getEntityAge(elb);
            ++entityAge;
            if (elb instanceof EntityMob) {
                final float el = elb.getBrightness(1.0f);
                if (el > 0.5f) {
                    entityAge += 2;
                }
            }
            EntityUtils.setEntityAge(elb, entityAge);
            if (elb instanceof EntityLiving) {
                final EntityLiving var5 = (EntityLiving)elb;
                EntityUtils.despawnEntity(var5);
            }
        }
        else {
            super.updateEntity(par1Entity);
            if (Config.isSmoothWorld()) {
                Thread.currentThread();
                Thread.yield();
            }
        }
    }
    
    private boolean canSkipEntityUpdate(final Entity entity) {
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        final EntityLivingBase entityLiving = (EntityLivingBase)entity;
        if (entityLiving.isChild()) {
            return false;
        }
        if (entityLiving.hurtTime > 0) {
            return false;
        }
        if (entity.ticksExisted < 20) {
            return false;
        }
        if (this.playerEntities.size() != 1) {
            return false;
        }
        final Entity player = this.playerEntities.get(0);
        final double dx = Math.max(Math.abs(entity.posX - player.posX) - 16.0, 0.0);
        final double dz = Math.max(Math.abs(entity.posZ - player.posZ) - 16.0, 0.0);
        final double distSq = dx * dx + dz * dz;
        return !entity.isInRangeToRenderDist(distSq);
    }
    
    @Override
    protected void setActivePlayerChunksAndCheckLight() {
        super.setActivePlayerChunksAndCheckLight();
        this.limitedChunkSet.clear();
        final int viewDistance = this.func_152379_p();
        if (viewDistance > 10) {
            if (viewDistance != this.lastViewDistance) {
                this.lastViewDistance = viewDistance;
                this.allChunksTicked = false;
            }
            else if (!this.allChunksTicked) {
                this.allChunksTicked = true;
            }
            else {
                for (int i = 0; i < this.playerEntities.size(); ++i) {
                    final EntityPlayer player = this.playerEntities.get(i);
                    final int pcx = MathHelper.floor_double(player.posX / 16.0);
                    final int pcz = MathHelper.floor_double(player.posZ / 16.0);
                    final byte dist = 10;
                    for (int cx = -dist; cx <= dist; ++cx) {
                        for (int cz = -dist; cz <= dist; ++cz) {
                            this.limitedChunkSet.add(new ChunkCoordIntPair(cx + pcx, cz + pcz));
                        }
                    }
                }
                if (this.setChunkCoordsToTickOnce.size() > 0) {
                    this.limitedChunkSet.addAll(this.setChunkCoordsToTickOnce);
                    this.setChunkCoordsToTickOnce.clear();
                }
            }
        }
    }
    
    public void addChunkToTickOnce(final int cx, final int cz) {
        final int viewDistance = this.func_152379_p();
        if (viewDistance > 10) {
            this.setChunkCoordsToTickOnce.add(new ChunkCoordIntPair(cx, cz));
        }
    }
    
    @Override
    protected void func_147456_g() {
        final Set oldSet = this.activeChunkSet;
        if (this.limitedChunkSet.size() > 0) {
            this.activeChunkSet = this.limitedChunkSet;
        }
        super.func_147456_g();
        this.activeChunkSet = oldSet;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
