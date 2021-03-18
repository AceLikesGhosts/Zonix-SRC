package net.minecraft.src;

import net.minecraft.client.renderer.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class DynamicLights
{
    private static Map<Integer, DynamicLight> mapDynamicLights;
    private static long timeUpdateMs;
    private static final double MAX_DIST = 7.5;
    private static final double MAX_DIST_SQ = 56.25;
    private static final int LIGHT_LEVEL_MAX = 15;
    private static final int LIGHT_LEVEL_FIRE = 15;
    private static final int LIGHT_LEVEL_BLAZE = 10;
    private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
    private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
    private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
    private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
    
    public static void entityAdded(final Entity entityIn, final RenderGlobal renderGlobal) {
    }
    
    public static void entityRemoved(final Entity entityIn, final RenderGlobal renderGlobal) {
        final Map var2 = DynamicLights.mapDynamicLights;
        synchronized (DynamicLights.mapDynamicLights) {
            final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(IntegerCache.valueOf(entityIn.getEntityId()));
            if (dynamicLight != null) {
                dynamicLight.updateLitChunks(renderGlobal);
            }
        }
    }
    
    public static void update(final RenderGlobal renderGlobal) {
        final long timeNowMs = System.currentTimeMillis();
        if (timeNowMs >= DynamicLights.timeUpdateMs + 50L) {
            DynamicLights.timeUpdateMs = timeNowMs;
            final Map var3 = DynamicLights.mapDynamicLights;
            synchronized (DynamicLights.mapDynamicLights) {
                updateMapDynamicLights(renderGlobal);
                if (DynamicLights.mapDynamicLights.size() > 0) {
                    final Collection dynamicLights = DynamicLights.mapDynamicLights.values();
                    for (final DynamicLight dynamicLight : dynamicLights) {
                        dynamicLight.update(renderGlobal);
                    }
                }
            }
        }
    }
    
    private static void updateMapDynamicLights(final RenderGlobal renderGlobal) {
        final WorldClient world = renderGlobal.theWorld;
        if (world != null) {
            final List entities = world.getLoadedEntityList();
            for (final Entity entity : entities) {
                final int lightLevel = getLightLevel(entity);
                if (lightLevel > 0) {
                    final Integer key = IntegerCache.valueOf(entity.getEntityId());
                    DynamicLight dynamicLight = DynamicLights.mapDynamicLights.get(key);
                    if (dynamicLight != null) {
                        continue;
                    }
                    dynamicLight = new DynamicLight(entity);
                    DynamicLights.mapDynamicLights.put(key, dynamicLight);
                }
                else {
                    final Integer key = IntegerCache.valueOf(entity.getEntityId());
                    final DynamicLight dynamicLight = DynamicLights.mapDynamicLights.remove(key);
                    if (dynamicLight == null) {
                        continue;
                    }
                    dynamicLight.updateLitChunks(renderGlobal);
                }
            }
        }
    }
    
    public static int getCombinedLight(final int x, final int y, final int z, int combinedLight) {
        final double lightPlayer = getLightLevel(x, y, z);
        combinedLight = getCombinedLight(lightPlayer, combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final Entity entity, int combinedLight) {
        final double lightPlayer = getLightLevel(entity);
        combinedLight = getCombinedLight(lightPlayer, combinedLight);
        return combinedLight;
    }
    
    public static int getCombinedLight(final double lightPlayer, int combinedLight) {
        if (lightPlayer > 0.0) {
            final int lightPlayerFF = (int)(lightPlayer * 16.0);
            final int lightBlockFF = combinedLight & 0xFF;
            if (lightPlayerFF > lightBlockFF) {
                combinedLight &= 0xFFFFFF00;
                combinedLight |= lightPlayerFF;
            }
        }
        return combinedLight;
    }
    
    public static double getLightLevel(final int x, final int y, final int z) {
        double lightLevelMax = 0.0;
        final Map lightPlayer = DynamicLights.mapDynamicLights;
        synchronized (DynamicLights.mapDynamicLights) {
            final Collection dynamicLights = DynamicLights.mapDynamicLights.values();
            for (final DynamicLight dynamicLight : dynamicLights) {
                int dynamicLightLevel = dynamicLight.getLastLightLevel();
                if (dynamicLightLevel > 0) {
                    final double px = dynamicLight.getLastPosX();
                    final double py = dynamicLight.getLastPosY();
                    final double pz = dynamicLight.getLastPosZ();
                    final double dx = x - px;
                    final double dy = y - py;
                    final double dz = z - pz;
                    double distSq = dx * dx + dy * dy + dz * dz;
                    if (dynamicLight.isUnderwater() && !Config.isClearWater()) {
                        dynamicLightLevel = Config.limit(dynamicLightLevel - 2, 0, 15);
                        distSq *= 2.0;
                    }
                    if (distSq > 56.25) {
                        continue;
                    }
                    final double dist = Math.sqrt(distSq);
                    final double light = 1.0 - dist / 7.5;
                    final double lightLevel = light * dynamicLightLevel;
                    if (lightLevel <= lightLevelMax) {
                        continue;
                    }
                    lightLevelMax = lightLevel;
                }
            }
        }
        final double lightPlayer2 = Config.limit(lightLevelMax, 0.0, 15.0);
        return lightPlayer2;
    }
    
    public static int getLightLevel(final ItemStack itemStack) {
        if (itemStack == null) {
            return 0;
        }
        final Item item = itemStack.getItem();
        if (item instanceof ItemBlock) {
            final ItemBlock itemBlock = (ItemBlock)item;
            final Block block = Block.getBlockFromItem(itemBlock);
            if (block != null) {
                return block.getLightValue();
            }
        }
        return (item == Items.lava_bucket) ? Blocks.lava.getLightValue() : ((item != Items.blaze_rod && item != Items.blaze_powder) ? ((item == Items.glowstone_dust) ? 8 : ((item == Items.magma_cream) ? 8 : ((item == Items.nether_star) ? (Blocks.beacon.getLightValue() / 2) : 0))) : 10);
    }
    
    public static int getLightLevel(final Entity entity) {
        if (entity == Config.getMinecraft().renderViewEntity && !Config.isDynamicHandLight()) {
            return 0;
        }
        if (entity.isBurning()) {
            return 15;
        }
        if (entity instanceof EntityFireball) {
            return 15;
        }
        if (entity instanceof EntityTNTPrimed) {
            return 15;
        }
        if (entity instanceof EntityBlaze) {
            final EntityBlaze entityItem4 = (EntityBlaze)entity;
            return entityItem4.func_70845_n() ? 15 : 10;
        }
        if (entity instanceof EntityMagmaCube) {
            final EntityMagmaCube entityItem5 = (EntityMagmaCube)entity;
            return (entityItem5.squishFactor > 0.6) ? 13 : 8;
        }
        if (entity instanceof EntityCreeper) {
            final EntityCreeper entityItem6 = (EntityCreeper)entity;
            if (entityItem6.getCreeperState() > 0) {
                return 15;
            }
        }
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityItem7 = (EntityLivingBase)entity;
            final ItemStack itemStack = entityItem7.getHeldItem();
            final int levelMain = getLightLevel(itemStack);
            final ItemStack stackHead = entityItem7.getEquipmentInSlot(4);
            final int levelHead = getLightLevel(stackHead);
            return Math.max(levelMain, levelHead);
        }
        if (entity instanceof EntityItem) {
            final EntityItem entityItem8 = (EntityItem)entity;
            final ItemStack itemStack = getItemStack(entityItem8);
            return getLightLevel(itemStack);
        }
        return 0;
    }
    
    public static void removeLights(final RenderGlobal renderGlobal) {
        final Map var1 = DynamicLights.mapDynamicLights;
        synchronized (DynamicLights.mapDynamicLights) {
            final Collection lights = DynamicLights.mapDynamicLights.values();
            final Iterator it = lights.iterator();
            while (it.hasNext()) {
                final DynamicLight dynamicLight = it.next();
                it.remove();
                dynamicLight.updateLitChunks(renderGlobal);
            }
        }
    }
    
    public static void clear() {
        final Map var0 = DynamicLights.mapDynamicLights;
        synchronized (DynamicLights.mapDynamicLights) {
            DynamicLights.mapDynamicLights.clear();
        }
    }
    
    public static int getCount() {
        final Map var0 = DynamicLights.mapDynamicLights;
        synchronized (DynamicLights.mapDynamicLights) {
            return DynamicLights.mapDynamicLights.size();
        }
    }
    
    public static ItemStack getItemStack(final EntityItem entityItem) {
        final ItemStack itemstack = entityItem.getDataWatcher().getWatchableObjectItemStack(10);
        return itemstack;
    }
    
    static {
        DynamicLights.mapDynamicLights = new HashMap<Integer, DynamicLight>();
        DynamicLights.timeUpdateMs = 0L;
    }
}
