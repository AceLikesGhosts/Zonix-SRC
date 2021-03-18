package net.minecraft.world.gen.structure;

import net.minecraft.world.gen.*;
import net.minecraft.block.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;

public abstract class MapGenStructure extends MapGenBase
{
    private MapGenStructureData field_143029_e;
    protected Map structureMap;
    private static final String __OBFID = "CL_00000505";
    
    public MapGenStructure() {
        this.structureMap = new HashMap();
    }
    
    public abstract String func_143025_a();
    
    @Override
    protected final void func_151538_a(final World p_151538_1_, final int p_151538_2_, final int p_151538_3_, final int p_151538_4_, final int p_151538_5_, final Block[] p_151538_6_) {
        this.func_143027_a(p_151538_1_);
        if (!this.structureMap.containsKey(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_))) {
            this.rand.nextInt();
            try {
                if (this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_)) {
                    final StructureStart var7 = this.getStructureStart(p_151538_2_, p_151538_3_);
                    this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_), var7);
                    this.func_143026_a(p_151538_2_, p_151538_3_, var7);
                }
            }
            catch (Throwable var9) {
                final CrashReport var8 = CrashReport.makeCrashReport(var9, "Exception preparing structure feature");
                final CrashReportCategory var10 = var8.makeCategory("Feature being prepared");
                var10.addCrashSectionCallable("Is feature chunk", new Callable() {
                    private static final String __OBFID = "CL_00000506";
                    
                    @Override
                    public String call() {
                        return MapGenStructure.this.canSpawnStructureAtCoords(p_151538_2_, p_151538_3_) ? "True" : "False";
                    }
                });
                var10.addCrashSection("Chunk location", String.format("%d,%d", p_151538_2_, p_151538_3_));
                var10.addCrashSectionCallable("Chunk pos hash", new Callable() {
                    private static final String __OBFID = "CL_00000507";
                    
                    @Override
                    public String call() {
                        return String.valueOf(ChunkCoordIntPair.chunkXZ2Int(p_151538_2_, p_151538_3_));
                    }
                });
                var10.addCrashSectionCallable("Structure type", new Callable() {
                    private static final String __OBFID = "CL_00000508";
                    
                    @Override
                    public String call() {
                        return MapGenStructure.this.getClass().getCanonicalName();
                    }
                });
                throw new ReportedException(var8);
            }
        }
    }
    
    public boolean generateStructuresInChunk(final World p_75051_1_, final Random p_75051_2_, final int p_75051_3_, final int p_75051_4_) {
        this.func_143027_a(p_75051_1_);
        final int var5 = (p_75051_3_ << 4) + 8;
        final int var6 = (p_75051_4_ << 4) + 8;
        boolean var7 = false;
        for (final StructureStart var9 : this.structureMap.values()) {
            if (var9.isSizeableStructure() && var9.getBoundingBox().intersectsWith(var5, var6, var5 + 15, var6 + 15)) {
                var9.generateStructure(p_75051_1_, p_75051_2_, new StructureBoundingBox(var5, var6, var5 + 15, var6 + 15));
                var7 = true;
                this.func_143026_a(var9.func_143019_e(), var9.func_143018_f(), var9);
            }
        }
        return var7;
    }
    
    public boolean hasStructureAt(final int p_75048_1_, final int p_75048_2_, final int p_75048_3_) {
        this.func_143027_a(this.worldObj);
        return this.func_143028_c(p_75048_1_, p_75048_2_, p_75048_3_) != null;
    }
    
    protected StructureStart func_143028_c(final int p_143028_1_, final int p_143028_2_, final int p_143028_3_) {
        for (final StructureStart var5 : this.structureMap.values()) {
            if (var5.isSizeableStructure() && var5.getBoundingBox().intersectsWith(p_143028_1_, p_143028_3_, p_143028_1_, p_143028_3_)) {
                for (final StructureComponent var7 : var5.getComponents()) {
                    if (var7.getBoundingBox().isVecInside(p_143028_1_, p_143028_2_, p_143028_3_)) {
                        return var5;
                    }
                }
            }
        }
        return null;
    }
    
    public boolean func_142038_b(final int p_142038_1_, final int p_142038_2_, final int p_142038_3_) {
        this.func_143027_a(this.worldObj);
        for (final StructureStart var5 : this.structureMap.values()) {
            if (var5.isSizeableStructure()) {
                return var5.getBoundingBox().intersectsWith(p_142038_1_, p_142038_3_, p_142038_1_, p_142038_3_);
            }
        }
        return false;
    }
    
    public ChunkPosition func_151545_a(final World p_151545_1_, final int p_151545_2_, final int p_151545_3_, final int p_151545_4_) {
        this.func_143027_a(this.worldObj = p_151545_1_);
        this.rand.setSeed(p_151545_1_.getSeed());
        final long var5 = this.rand.nextLong();
        final long var6 = this.rand.nextLong();
        final long var7 = (p_151545_2_ >> 4) * var5;
        final long var8 = (p_151545_4_ >> 4) * var6;
        this.rand.setSeed(var7 ^ var8 ^ p_151545_1_.getSeed());
        this.func_151538_a(p_151545_1_, p_151545_2_ >> 4, p_151545_4_ >> 4, 0, 0, null);
        double var9 = Double.MAX_VALUE;
        ChunkPosition var10 = null;
        for (final StructureStart var12 : this.structureMap.values()) {
            if (var12.isSizeableStructure()) {
                final StructureComponent var13 = var12.getComponents().get(0);
                final ChunkPosition var14 = var13.func_151553_a();
                final int var15 = var14.field_151329_a - p_151545_2_;
                final int var16 = var14.field_151327_b - p_151545_3_;
                final int var17 = var14.field_151328_c - p_151545_4_;
                final double var18 = var15 * var15 + var16 * var16 + var17 * var17;
                if (var18 >= var9) {
                    continue;
                }
                var9 = var18;
                var10 = var14;
            }
        }
        if (var10 != null) {
            return var10;
        }
        final List var19 = this.getCoordList();
        if (var19 != null) {
            ChunkPosition var20 = null;
            for (final ChunkPosition var14 : var19) {
                final int var15 = var14.field_151329_a - p_151545_2_;
                final int var16 = var14.field_151327_b - p_151545_3_;
                final int var17 = var14.field_151328_c - p_151545_4_;
                final double var18 = var15 * var15 + var16 * var16 + var17 * var17;
                if (var18 < var9) {
                    var9 = var18;
                    var20 = var14;
                }
            }
            return var20;
        }
        return null;
    }
    
    protected List getCoordList() {
        return null;
    }
    
    private void func_143027_a(final World p_143027_1_) {
        if (this.field_143029_e == null) {
            this.field_143029_e = (MapGenStructureData)p_143027_1_.loadItemData(MapGenStructureData.class, this.func_143025_a());
            if (this.field_143029_e == null) {
                this.field_143029_e = new MapGenStructureData(this.func_143025_a());
                p_143027_1_.setItemData(this.func_143025_a(), this.field_143029_e);
            }
            else {
                final NBTTagCompound var2 = this.field_143029_e.func_143041_a();
                for (final String var4 : var2.func_150296_c()) {
                    final NBTBase var5 = var2.getTag(var4);
                    if (var5.getId() == 10) {
                        final NBTTagCompound var6 = (NBTTagCompound)var5;
                        if (!var6.hasKey("ChunkX") || !var6.hasKey("ChunkZ")) {
                            continue;
                        }
                        final int var7 = var6.getInteger("ChunkX");
                        final int var8 = var6.getInteger("ChunkZ");
                        final StructureStart var9 = MapGenStructureIO.func_143035_a(var6, p_143027_1_);
                        if (var9 == null) {
                            continue;
                        }
                        this.structureMap.put(ChunkCoordIntPair.chunkXZ2Int(var7, var8), var9);
                    }
                }
            }
        }
    }
    
    private void func_143026_a(final int p_143026_1_, final int p_143026_2_, final StructureStart p_143026_3_) {
        this.field_143029_e.func_143043_a(p_143026_3_.func_143021_a(p_143026_1_, p_143026_2_), p_143026_1_, p_143026_2_);
        this.field_143029_e.markDirty();
    }
    
    protected abstract boolean canSpawnStructureAtCoords(final int p0, final int p1);
    
    protected abstract StructureStart getStructureStart(final int p0, final int p1);
}
