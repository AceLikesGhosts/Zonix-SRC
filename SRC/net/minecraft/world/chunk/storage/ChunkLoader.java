package net.minecraft.world.chunk.storage;

import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.nbt.*;

public class ChunkLoader
{
    private static final String __OBFID = "CL_00000379";
    
    public static AnvilConverterData load(final NBTTagCompound p_76691_0_) {
        final int var1 = p_76691_0_.getInteger("xPos");
        final int var2 = p_76691_0_.getInteger("zPos");
        final AnvilConverterData var3 = new AnvilConverterData(var1, var2);
        var3.blocks = p_76691_0_.getByteArray("Blocks");
        var3.data = new NibbleArrayReader(p_76691_0_.getByteArray("Data"), 7);
        var3.skyLight = new NibbleArrayReader(p_76691_0_.getByteArray("SkyLight"), 7);
        var3.blockLight = new NibbleArrayReader(p_76691_0_.getByteArray("BlockLight"), 7);
        var3.heightmap = p_76691_0_.getByteArray("HeightMap");
        var3.terrainPopulated = p_76691_0_.getBoolean("TerrainPopulated");
        var3.entities = p_76691_0_.getTagList("Entities", 10);
        var3.field_151564_i = p_76691_0_.getTagList("TileEntities", 10);
        var3.field_151563_j = p_76691_0_.getTagList("TileTicks", 10);
        try {
            var3.lastUpdated = p_76691_0_.getLong("LastUpdate");
        }
        catch (ClassCastException var4) {
            var3.lastUpdated = p_76691_0_.getInteger("LastUpdate");
        }
        return var3;
    }
    
    public static void convertToAnvilFormat(final AnvilConverterData p_76690_0_, final NBTTagCompound p_76690_1_, final WorldChunkManager p_76690_2_) {
        p_76690_1_.setInteger("xPos", p_76690_0_.x);
        p_76690_1_.setInteger("zPos", p_76690_0_.z);
        p_76690_1_.setLong("LastUpdate", p_76690_0_.lastUpdated);
        final int[] var3 = new int[p_76690_0_.heightmap.length];
        for (int var4 = 0; var4 < p_76690_0_.heightmap.length; ++var4) {
            var3[var4] = p_76690_0_.heightmap[var4];
        }
        p_76690_1_.setIntArray("HeightMap", var3);
        p_76690_1_.setBoolean("TerrainPopulated", p_76690_0_.terrainPopulated);
        final NBTTagList var5 = new NBTTagList();
        for (int var6 = 0; var6 < 8; ++var6) {
            boolean var7 = true;
            for (int var8 = 0; var8 < 16 && var7; ++var8) {
                for (int var9 = 0; var9 < 16 && var7; ++var9) {
                    for (int var10 = 0; var10 < 16; ++var10) {
                        final int var11 = var8 << 11 | var10 << 7 | var9 + (var6 << 4);
                        final byte var12 = p_76690_0_.blocks[var11];
                        if (var12 != 0) {
                            var7 = false;
                            break;
                        }
                    }
                }
            }
            if (!var7) {
                final byte[] var13 = new byte[4096];
                final NibbleArray var14 = new NibbleArray(var13.length, 4);
                final NibbleArray var15 = new NibbleArray(var13.length, 4);
                final NibbleArray var16 = new NibbleArray(var13.length, 4);
                for (int var17 = 0; var17 < 16; ++var17) {
                    for (int var18 = 0; var18 < 16; ++var18) {
                        for (int var19 = 0; var19 < 16; ++var19) {
                            final int var20 = var17 << 11 | var19 << 7 | var18 + (var6 << 4);
                            final byte var21 = p_76690_0_.blocks[var20];
                            var13[var18 << 8 | var19 << 4 | var17] = (byte)(var21 & 0xFF);
                            var14.set(var17, var18, var19, p_76690_0_.data.get(var17, var18 + (var6 << 4), var19));
                            var15.set(var17, var18, var19, p_76690_0_.skyLight.get(var17, var18 + (var6 << 4), var19));
                            var16.set(var17, var18, var19, p_76690_0_.blockLight.get(var17, var18 + (var6 << 4), var19));
                        }
                    }
                }
                final NBTTagCompound var22 = new NBTTagCompound();
                var22.setByte("Y", (byte)(var6 & 0xFF));
                var22.setByteArray("Blocks", var13);
                var22.setByteArray("Data", var14.data);
                var22.setByteArray("SkyLight", var15.data);
                var22.setByteArray("BlockLight", var16.data);
                var5.appendTag(var22);
            }
        }
        p_76690_1_.setTag("Sections", var5);
        final byte[] var23 = new byte[256];
        for (int var24 = 0; var24 < 16; ++var24) {
            for (int var8 = 0; var8 < 16; ++var8) {
                var23[var8 << 4 | var24] = (byte)(p_76690_2_.getBiomeGenAt(p_76690_0_.x << 4 | var24, p_76690_0_.z << 4 | var8).biomeID & 0xFF);
            }
        }
        p_76690_1_.setByteArray("Biomes", var23);
        p_76690_1_.setTag("Entities", p_76690_0_.entities);
        p_76690_1_.setTag("TileEntities", p_76690_0_.field_151564_i);
        if (p_76690_0_.field_151563_j != null) {
            p_76690_1_.setTag("TileTicks", p_76690_0_.field_151563_j);
        }
    }
    
    public static class AnvilConverterData
    {
        public long lastUpdated;
        public boolean terrainPopulated;
        public byte[] heightmap;
        public NibbleArrayReader blockLight;
        public NibbleArrayReader skyLight;
        public NibbleArrayReader data;
        public byte[] blocks;
        public NBTTagList entities;
        public NBTTagList field_151564_i;
        public NBTTagList field_151563_j;
        public final int x;
        public final int z;
        private static final String __OBFID = "CL_00000380";
        
        public AnvilConverterData(final int p_i1999_1_, final int p_i1999_2_) {
            this.x = p_i1999_1_;
            this.z = p_i1999_2_;
        }
    }
}
