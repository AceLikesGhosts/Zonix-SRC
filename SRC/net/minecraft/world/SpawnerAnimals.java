package net.minecraft.world;

import net.minecraft.world.chunk.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public final class SpawnerAnimals
{
    private HashMap eligibleChunksForSpawning;
    private static final String __OBFID = "CL_00000152";
    
    public SpawnerAnimals() {
        this.eligibleChunksForSpawning = new HashMap();
    }
    
    protected static ChunkPosition func_151350_a(final World p_151350_0_, final int p_151350_1_, final int p_151350_2_) {
        final Chunk var3 = p_151350_0_.getChunkFromChunkCoords(p_151350_1_, p_151350_2_);
        final int var4 = p_151350_1_ * 16 + p_151350_0_.rand.nextInt(16);
        final int var5 = p_151350_2_ * 16 + p_151350_0_.rand.nextInt(16);
        final int var6 = p_151350_0_.rand.nextInt((var3 == null) ? p_151350_0_.getActualHeight() : (var3.getTopFilledSegment() + 16 - 1));
        return new ChunkPosition(var4, var6, var5);
    }
    
    public int findChunksForSpawning(final WorldServer p_77192_1_, final boolean p_77192_2_, final boolean p_77192_3_, final boolean p_77192_4_) {
        if (!p_77192_2_ && !p_77192_3_) {
            return 0;
        }
        this.eligibleChunksForSpawning.clear();
        for (int var5 = 0; var5 < p_77192_1_.playerEntities.size(); ++var5) {
            final EntityPlayer var6 = p_77192_1_.playerEntities.get(var5);
            final int var7 = MathHelper.floor_double(var6.posX / 16.0);
            final int var8 = MathHelper.floor_double(var6.posZ / 16.0);
            final byte var9 = 8;
            for (int var10 = -var9; var10 <= var9; ++var10) {
                for (int var11 = -var9; var11 <= var9; ++var11) {
                    final boolean var12 = var10 == -var9 || var10 == var9 || var11 == -var9 || var11 == var9;
                    final ChunkCoordIntPair var13 = new ChunkCoordIntPair(var10 + var7, var11 + var8);
                    if (!var12) {
                        this.eligibleChunksForSpawning.put(var13, false);
                    }
                    else if (!this.eligibleChunksForSpawning.containsKey(var13)) {
                        this.eligibleChunksForSpawning.put(var13, true);
                    }
                }
            }
        }
        int var5 = 0;
        final ChunkCoordinates var14 = p_77192_1_.getSpawnPoint();
        final EnumCreatureType[] var15 = EnumCreatureType.values();
        final int var8 = var15.length;
    Label_0344_Outer:
        for (final EnumCreatureType var17 : var15) {
            if ((!var17.getPeacefulCreature() || p_77192_3_) && (var17.getPeacefulCreature() || p_77192_2_) && (!var17.getAnimal() || p_77192_4_) && p_77192_1_.countEntities(var17.getCreatureClass()) <= var17.getMaxNumberOfCreature() * this.eligibleChunksForSpawning.size() / 256) {
            Label_0344:
                while (true) {
                    for (final ChunkCoordIntPair var19 : this.eligibleChunksForSpawning.keySet()) {
                        if (!this.eligibleChunksForSpawning.get(var19)) {
                            final ChunkPosition var20 = func_151350_a(p_77192_1_, var19.chunkXPos, var19.chunkZPos);
                            final int var21 = var20.field_151329_a;
                            final int var22 = var20.field_151327_b;
                            final int var23 = var20.field_151328_c;
                            if (p_77192_1_.getBlock(var21, var22, var23).isNormalCube() || p_77192_1_.getBlock(var21, var22, var23).getMaterial() != var17.getCreatureMaterial()) {
                                continue Label_0344_Outer;
                            }
                            int var24 = 0;
                            for (int var25 = 0; var25 < 3; ++var25) {
                                int var26 = var21;
                                int var27 = var22;
                                int var28 = var23;
                                final byte var29 = 6;
                                BiomeGenBase.SpawnListEntry var30 = null;
                                IEntityLivingData var31 = null;
                                for (int var32 = 0; var32 < 4; ++var32) {
                                    var26 += p_77192_1_.rand.nextInt(var29) - p_77192_1_.rand.nextInt(var29);
                                    var27 += p_77192_1_.rand.nextInt(1) - p_77192_1_.rand.nextInt(1);
                                    var28 += p_77192_1_.rand.nextInt(var29) - p_77192_1_.rand.nextInt(var29);
                                    if (canCreatureTypeSpawnAtLocation(var17, p_77192_1_, var26, var27, var28)) {
                                        final float var33 = var26 + 0.5f;
                                        final float var34 = (float)var27;
                                        final float var35 = var28 + 0.5f;
                                        if (p_77192_1_.getClosestPlayer(var33, var34, var35, 24.0) == null) {
                                            final float var36 = var33 - var14.posX;
                                            final float var37 = var34 - var14.posY;
                                            final float var38 = var35 - var14.posZ;
                                            final float var39 = var36 * var36 + var37 * var37 + var38 * var38;
                                            if (var39 >= 576.0f) {
                                                if (var30 == null) {
                                                    var30 = p_77192_1_.spawnRandomCreature(var17, var26, var27, var28);
                                                    if (var30 == null) {
                                                        break;
                                                    }
                                                }
                                                EntityLiving var40;
                                                try {
                                                    var40 = var30.entityClass.getConstructor(World.class).newInstance(p_77192_1_);
                                                }
                                                catch (Exception var41) {
                                                    var41.printStackTrace();
                                                    return var5;
                                                }
                                                var40.setLocationAndAngles(var33, var34, var35, p_77192_1_.rand.nextFloat() * 360.0f, 0.0f);
                                                if (var40.getCanSpawnHere()) {
                                                    ++var24;
                                                    p_77192_1_.spawnEntityInWorld(var40);
                                                    var31 = var40.onSpawnWithEgg(var31);
                                                    if (var24 >= var40.getMaxSpawnedInChunk()) {
                                                        continue Label_0344;
                                                    }
                                                }
                                                var5 += var24;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return var5;
    }
    
    public static boolean canCreatureTypeSpawnAtLocation(final EnumCreatureType p_77190_0_, final World p_77190_1_, final int p_77190_2_, final int p_77190_3_, final int p_77190_4_) {
        if (p_77190_0_.getCreatureMaterial() == Material.water) {
            return p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).getMaterial().isLiquid() && p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_).getMaterial().isLiquid() && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
        }
        if (!World.doesBlockHaveSolidTopSurface(p_77190_1_, p_77190_2_, p_77190_3_ - 1, p_77190_4_)) {
            return false;
        }
        final Block var5 = p_77190_1_.getBlock(p_77190_2_, p_77190_3_ - 1, p_77190_4_);
        return var5 != Blocks.bedrock && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).isNormalCube() && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_, p_77190_4_).getMaterial().isLiquid() && !p_77190_1_.getBlock(p_77190_2_, p_77190_3_ + 1, p_77190_4_).isNormalCube();
    }
    
    public static void performWorldGenSpawning(final World p_77191_0_, final BiomeGenBase p_77191_1_, final int p_77191_2_, final int p_77191_3_, final int p_77191_4_, final int p_77191_5_, final Random p_77191_6_) {
        final List var7 = p_77191_1_.getSpawnableList(EnumCreatureType.creature);
        if (!var7.isEmpty()) {
            while (p_77191_6_.nextFloat() < p_77191_1_.getSpawningChance()) {
                final BiomeGenBase.SpawnListEntry var8 = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(p_77191_0_.rand, var7);
                IEntityLivingData var9 = null;
                final int var10 = var8.minGroupCount + p_77191_6_.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
                int var11 = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
                int var12 = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
                final int var13 = var11;
                final int var14 = var12;
                for (int var15 = 0; var15 < var10; ++var15) {
                    boolean var16 = false;
                    for (int var17 = 0; !var16 && var17 < 4; ++var17) {
                        final int var18 = p_77191_0_.getTopSolidOrLiquidBlock(var11, var12);
                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, p_77191_0_, var11, var18, var12)) {
                            final float var19 = var11 + 0.5f;
                            final float var20 = (float)var18;
                            final float var21 = var12 + 0.5f;
                            EntityLiving var22;
                            try {
                                var22 = var8.entityClass.getConstructor(World.class).newInstance(p_77191_0_);
                            }
                            catch (Exception var23) {
                                var23.printStackTrace();
                                continue;
                            }
                            var22.setLocationAndAngles(var19, var20, var21, p_77191_6_.nextFloat() * 360.0f, 0.0f);
                            p_77191_0_.spawnEntityInWorld(var22);
                            var9 = var22.onSpawnWithEgg(var9);
                            var16 = true;
                        }
                        for (var11 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5), var12 += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); var11 < p_77191_2_ || var11 >= p_77191_2_ + p_77191_4_ || var12 < p_77191_3_ || var12 >= p_77191_3_ + p_77191_4_; var11 = var13 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5), var12 = var14 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5)) {}
                    }
                }
            }
        }
    }
}
