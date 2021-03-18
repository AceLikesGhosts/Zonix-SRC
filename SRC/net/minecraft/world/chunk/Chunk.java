package net.minecraft.world.chunk;

import net.minecraft.world.chunk.storage.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import org.apache.logging.log4j.*;

public class Chunk
{
    private static final Logger logger;
    public static boolean isLit;
    private ExtendedBlockStorage[] storageArrays;
    private byte[] blockBiomeArray;
    public int[] precipitationHeightMap;
    public boolean[] updateSkylightColumns;
    public boolean isChunkLoaded;
    public World worldObj;
    public int[] heightMap;
    public final int xPosition;
    public final int zPosition;
    private boolean isGapLightingUpdated;
    public Map chunkTileEntityMap;
    public List[] entityLists;
    public boolean isTerrainPopulated;
    public boolean isLightPopulated;
    public boolean field_150815_m;
    public boolean isModified;
    public boolean hasEntities;
    public long lastSaveTime;
    public boolean sendUpdates;
    public int heightMapMinimum;
    public long inhabitedTime;
    private int queuedLightChecks;
    private static final String __OBFID = "CL_00000373";
    
    public Chunk(final World p_i1995_1_, final int p_i1995_2_, final int p_i1995_3_) {
        this.storageArrays = new ExtendedBlockStorage[16];
        this.blockBiomeArray = new byte[256];
        this.precipitationHeightMap = new int[256];
        this.updateSkylightColumns = new boolean[256];
        this.chunkTileEntityMap = new HashMap();
        this.queuedLightChecks = 4096;
        this.entityLists = new List[16];
        this.worldObj = p_i1995_1_;
        this.xPosition = p_i1995_2_;
        this.zPosition = p_i1995_3_;
        this.heightMap = new int[256];
        for (int var4 = 0; var4 < this.entityLists.length; ++var4) {
            this.entityLists[var4] = new ArrayList();
        }
        Arrays.fill(this.precipitationHeightMap, -999);
        Arrays.fill(this.blockBiomeArray, (byte)(-1));
    }
    
    public Chunk(final World p_i45446_1_, final Block[] p_i45446_2_, final int p_i45446_3_, final int p_i45446_4_) {
        this(p_i45446_1_, p_i45446_3_, p_i45446_4_);
        final int var5 = p_i45446_2_.length / 256;
        final boolean var6 = !p_i45446_1_.provider.hasNoSky;
        for (int var7 = 0; var7 < 16; ++var7) {
            for (int var8 = 0; var8 < 16; ++var8) {
                for (int var9 = 0; var9 < var5; ++var9) {
                    final Block var10 = p_i45446_2_[var7 << 11 | var8 << 7 | var9];
                    if (var10 != null && var10.getMaterial() != Material.air) {
                        final int var11 = var9 >> 4;
                        if (this.storageArrays[var11] == null) {
                            this.storageArrays[var11] = new ExtendedBlockStorage(var11 << 4, var6);
                        }
                        this.storageArrays[var11].func_150818_a(var7, var9 & 0xF, var8, var10);
                    }
                }
            }
        }
    }
    
    public Chunk(final World p_i45447_1_, final Block[] p_i45447_2_, final byte[] p_i45447_3_, final int p_i45447_4_, final int p_i45447_5_) {
        this(p_i45447_1_, p_i45447_4_, p_i45447_5_);
        final int var6 = p_i45447_2_.length / 256;
        final boolean var7 = !p_i45447_1_.provider.hasNoSky;
        for (int var8 = 0; var8 < 16; ++var8) {
            for (int var9 = 0; var9 < 16; ++var9) {
                for (int var10 = 0; var10 < var6; ++var10) {
                    final int var11 = var8 * var6 * 16 | var9 * var6 | var10;
                    final Block var12 = p_i45447_2_[var11];
                    if (var12 != null && var12 != Blocks.air) {
                        final int var13 = var10 >> 4;
                        if (this.storageArrays[var13] == null) {
                            this.storageArrays[var13] = new ExtendedBlockStorage(var13 << 4, var7);
                        }
                        this.storageArrays[var13].func_150818_a(var8, var10 & 0xF, var9, var12);
                        this.storageArrays[var13].setExtBlockMetadata(var8, var10 & 0xF, var9, p_i45447_3_[var11]);
                    }
                }
            }
        }
    }
    
    public boolean isAtLocation(final int p_76600_1_, final int p_76600_2_) {
        return p_76600_1_ == this.xPosition && p_76600_2_ == this.zPosition;
    }
    
    public int getHeightValue(final int p_76611_1_, final int p_76611_2_) {
        return this.heightMap[p_76611_2_ << 4 | p_76611_1_];
    }
    
    public int getTopFilledSegment() {
        for (int var1 = this.storageArrays.length - 1; var1 >= 0; --var1) {
            if (this.storageArrays[var1] != null) {
                return this.storageArrays[var1].getYLocation();
            }
        }
        return 0;
    }
    
    public ExtendedBlockStorage[] getBlockStorageArray() {
        return this.storageArrays;
    }
    
    public void generateHeightMap() {
        final int var1 = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16 - 1;
                while (var4 > 0) {
                    final Block var5 = this.func_150810_a(var2, var4 - 1, var3);
                    if (var5.getLightOpacity() == 0) {
                        --var4;
                    }
                    else {
                        if ((this.heightMap[var3 << 4 | var2] = var4) < this.heightMapMinimum) {
                            this.heightMapMinimum = var4;
                            break;
                        }
                        break;
                    }
                }
            }
        }
        this.isModified = true;
    }
    
    public void generateSkylightMap() {
        final int var1 = this.getTopFilledSegment();
        this.heightMapMinimum = Integer.MAX_VALUE;
        for (int var2 = 0; var2 < 16; ++var2) {
            for (int var3 = 0; var3 < 16; ++var3) {
                this.precipitationHeightMap[var2 + (var3 << 4)] = -999;
                int var4 = var1 + 16 - 1;
                while (var4 > 0) {
                    if (this.func_150808_b(var2, var4 - 1, var3) == 0) {
                        --var4;
                    }
                    else {
                        if ((this.heightMap[var3 << 4 | var2] = var4) < this.heightMapMinimum) {
                            this.heightMapMinimum = var4;
                            break;
                        }
                        break;
                    }
                }
                if (!this.worldObj.provider.hasNoSky) {
                    var4 = 15;
                    int var5 = var1 + 16 - 1;
                    do {
                        int var6 = this.func_150808_b(var2, var5, var3);
                        if (var6 == 0 && var4 != 15) {
                            var6 = 1;
                        }
                        var4 -= var6;
                        if (var4 > 0) {
                            final ExtendedBlockStorage var7 = this.storageArrays[var5 >> 4];
                            if (var7 == null) {
                                continue;
                            }
                            var7.setExtSkylightValue(var2, var5 & 0xF, var3, var4);
                            this.worldObj.func_147479_m((this.xPosition << 4) + var2, var5, (this.zPosition << 4) + var3);
                        }
                    } while (--var5 > 0 && var4 > 0);
                }
            }
        }
        this.isModified = true;
    }
    
    private void propagateSkylightOcclusion(final int p_76595_1_, final int p_76595_2_) {
        this.updateSkylightColumns[p_76595_1_ + p_76595_2_ * 16] = true;
        this.isGapLightingUpdated = true;
    }
    
    private void recheckGaps(final boolean p_150803_1_) {
        this.worldObj.theProfiler.startSection("recheckGaps");
        if (this.worldObj.doChunksNearChunkExist(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8, 16)) {
            for (int var2 = 0; var2 < 16; ++var2) {
                for (int var3 = 0; var3 < 16; ++var3) {
                    if (this.updateSkylightColumns[var2 + var3 * 16]) {
                        this.updateSkylightColumns[var2 + var3 * 16] = false;
                        final int var4 = this.getHeightValue(var2, var3);
                        final int var5 = this.xPosition * 16 + var2;
                        final int var6 = this.zPosition * 16 + var3;
                        int var7 = this.worldObj.getChunkHeightMapMinimum(var5 - 1, var6);
                        final int var8 = this.worldObj.getChunkHeightMapMinimum(var5 + 1, var6);
                        final int var9 = this.worldObj.getChunkHeightMapMinimum(var5, var6 - 1);
                        final int var10 = this.worldObj.getChunkHeightMapMinimum(var5, var6 + 1);
                        if (var8 < var7) {
                            var7 = var8;
                        }
                        if (var9 < var7) {
                            var7 = var9;
                        }
                        if (var10 < var7) {
                            var7 = var10;
                        }
                        this.checkSkylightNeighborHeight(var5, var6, var7);
                        this.checkSkylightNeighborHeight(var5 - 1, var6, var4);
                        this.checkSkylightNeighborHeight(var5 + 1, var6, var4);
                        this.checkSkylightNeighborHeight(var5, var6 - 1, var4);
                        this.checkSkylightNeighborHeight(var5, var6 + 1, var4);
                        if (p_150803_1_) {
                            this.worldObj.theProfiler.endSection();
                            return;
                        }
                    }
                }
            }
            this.isGapLightingUpdated = false;
        }
        this.worldObj.theProfiler.endSection();
    }
    
    private void checkSkylightNeighborHeight(final int p_76599_1_, final int p_76599_2_, final int p_76599_3_) {
        final int var4 = this.worldObj.getHeightValue(p_76599_1_, p_76599_2_);
        if (var4 > p_76599_3_) {
            this.updateSkylightNeighborHeight(p_76599_1_, p_76599_2_, p_76599_3_, var4 + 1);
        }
        else if (var4 < p_76599_3_) {
            this.updateSkylightNeighborHeight(p_76599_1_, p_76599_2_, var4, p_76599_3_ + 1);
        }
    }
    
    private void updateSkylightNeighborHeight(final int p_76609_1_, final int p_76609_2_, final int p_76609_3_, final int p_76609_4_) {
        if (p_76609_4_ > p_76609_3_ && this.worldObj.doChunksNearChunkExist(p_76609_1_, 0, p_76609_2_, 16)) {
            for (int var5 = p_76609_3_; var5 < p_76609_4_; ++var5) {
                this.worldObj.updateLightByType(EnumSkyBlock.Sky, p_76609_1_, var5, p_76609_2_);
            }
            this.isModified = true;
        }
    }
    
    private void relightBlock(final int p_76615_1_, final int p_76615_2_, final int p_76615_3_) {
        int var5;
        final int var4 = var5 = (this.heightMap[p_76615_3_ << 4 | p_76615_1_] & 0xFF);
        if (p_76615_2_ > var4) {
            var5 = p_76615_2_;
        }
        while (var5 > 0 && this.func_150808_b(p_76615_1_, var5 - 1, p_76615_3_) == 0) {
            --var5;
        }
        if (var5 != var4) {
            this.worldObj.markBlocksDirtyVertical(p_76615_1_ + this.xPosition * 16, p_76615_3_ + this.zPosition * 16, var5, var4);
            this.heightMap[p_76615_3_ << 4 | p_76615_1_] = var5;
            final int var6 = this.xPosition * 16 + p_76615_1_;
            final int var7 = this.zPosition * 16 + p_76615_3_;
            if (!this.worldObj.provider.hasNoSky) {
                if (var5 < var4) {
                    for (int var8 = var5; var8 < var4; ++var8) {
                        final ExtendedBlockStorage var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(p_76615_1_, var8 & 0xF, p_76615_3_, 15);
                            this.worldObj.func_147479_m((this.xPosition << 4) + p_76615_1_, var8, (this.zPosition << 4) + p_76615_3_);
                        }
                    }
                }
                else {
                    for (int var8 = var4; var8 < var5; ++var8) {
                        final ExtendedBlockStorage var9 = this.storageArrays[var8 >> 4];
                        if (var9 != null) {
                            var9.setExtSkylightValue(p_76615_1_, var8 & 0xF, p_76615_3_, 0);
                            this.worldObj.func_147479_m((this.xPosition << 4) + p_76615_1_, var8, (this.zPosition << 4) + p_76615_3_);
                        }
                    }
                }
                int var8 = 15;
                while (var5 > 0 && var8 > 0) {
                    --var5;
                    int var10 = this.func_150808_b(p_76615_1_, var5, p_76615_3_);
                    if (var10 == 0) {
                        var10 = 1;
                    }
                    var8 -= var10;
                    if (var8 < 0) {
                        var8 = 0;
                    }
                    final ExtendedBlockStorage var11 = this.storageArrays[var5 >> 4];
                    if (var11 != null) {
                        var11.setExtSkylightValue(p_76615_1_, var5 & 0xF, p_76615_3_, var8);
                    }
                }
            }
            int var8 = this.heightMap[p_76615_3_ << 4 | p_76615_1_];
            int var10;
            int var12;
            if ((var12 = var8) < (var10 = var4)) {
                var10 = var8;
                var12 = var4;
            }
            if (var8 < this.heightMapMinimum) {
                this.heightMapMinimum = var8;
            }
            if (!this.worldObj.provider.hasNoSky) {
                this.updateSkylightNeighborHeight(var6 - 1, var7, var10, var12);
                this.updateSkylightNeighborHeight(var6 + 1, var7, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7 - 1, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7 + 1, var10, var12);
                this.updateSkylightNeighborHeight(var6, var7, var10, var12);
            }
            this.isModified = true;
        }
    }
    
    public int func_150808_b(final int p_150808_1_, final int p_150808_2_, final int p_150808_3_) {
        return this.func_150810_a(p_150808_1_, p_150808_2_, p_150808_3_).getLightOpacity();
    }
    
    public Block func_150810_a(final int p_150810_1_, final int p_150810_2_, final int p_150810_3_) {
        Block var4 = Blocks.air;
        if (p_150810_2_ >> 4 < this.storageArrays.length) {
            final ExtendedBlockStorage var5 = this.storageArrays[p_150810_2_ >> 4];
            if (var5 != null) {
                try {
                    var4 = var5.func_150819_a(p_150810_1_, p_150810_2_ & 0xF, p_150810_3_);
                }
                catch (Throwable var7) {
                    final CrashReport var6 = CrashReport.makeCrashReport(var7, "Getting block");
                    final CrashReportCategory var8 = var6.makeCategory("Block being got");
                    var8.addCrashSectionCallable("Location", new Callable() {
                        private static final String __OBFID = "CL_00000374";
                        
                        @Override
                        public String call() {
                            return CrashReportCategory.getLocationInfo(p_150810_1_, p_150810_2_, p_150810_3_);
                        }
                    });
                    throw new ReportedException(var6);
                }
            }
        }
        return var4;
    }
    
    public int getBlockMetadata(final int p_76628_1_, final int p_76628_2_, final int p_76628_3_) {
        if (p_76628_2_ >> 4 >= this.storageArrays.length) {
            return 0;
        }
        final ExtendedBlockStorage var4 = this.storageArrays[p_76628_2_ >> 4];
        return (var4 != null) ? var4.getExtBlockMetadata(p_76628_1_, p_76628_2_ & 0xF, p_76628_3_) : 0;
    }
    
    public boolean func_150807_a(final int p_150807_1_, final int p_150807_2_, final int p_150807_3_, final Block p_150807_4_, final int p_150807_5_) {
        final int var6 = p_150807_3_ << 4 | p_150807_1_;
        if (p_150807_2_ >= this.precipitationHeightMap[var6] - 1) {
            this.precipitationHeightMap[var6] = -999;
        }
        final int var7 = this.heightMap[var6];
        final Block var8 = this.func_150810_a(p_150807_1_, p_150807_2_, p_150807_3_);
        final int var9 = this.getBlockMetadata(p_150807_1_, p_150807_2_, p_150807_3_);
        if (var8 == p_150807_4_ && var9 == p_150807_5_) {
            return false;
        }
        ExtendedBlockStorage var10 = this.storageArrays[p_150807_2_ >> 4];
        boolean var11 = false;
        if (var10 == null) {
            if (p_150807_4_ == Blocks.air) {
                return false;
            }
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n = p_150807_2_ >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(p_150807_2_ >> 4 << 4, !this.worldObj.provider.hasNoSky);
            storageArrays[n] = extendedBlockStorage;
            var10 = extendedBlockStorage;
            var11 = (p_150807_2_ >= var7);
        }
        final int var12 = this.xPosition * 16 + p_150807_1_;
        final int var13 = this.zPosition * 16 + p_150807_3_;
        if (!this.worldObj.isClient) {
            var8.onBlockPreDestroy(this.worldObj, var12, p_150807_2_, var13, var9);
        }
        var10.func_150818_a(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_, p_150807_4_);
        if (!this.worldObj.isClient) {
            var8.breakBlock(this.worldObj, var12, p_150807_2_, var13, var8, var9);
        }
        else if (var8 instanceof ITileEntityProvider && var8 != p_150807_4_) {
            this.worldObj.removeTileEntity(var12, p_150807_2_, var13);
        }
        if (var10.func_150819_a(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_) != p_150807_4_) {
            return false;
        }
        var10.setExtBlockMetadata(p_150807_1_, p_150807_2_ & 0xF, p_150807_3_, p_150807_5_);
        if (var11) {
            this.generateSkylightMap();
        }
        else {
            final int var14 = p_150807_4_.getLightOpacity();
            final int var15 = var8.getLightOpacity();
            if (var14 > 0) {
                if (p_150807_2_ >= var7) {
                    this.relightBlock(p_150807_1_, p_150807_2_ + 1, p_150807_3_);
                }
            }
            else if (p_150807_2_ == var7 - 1) {
                this.relightBlock(p_150807_1_, p_150807_2_, p_150807_3_);
            }
            if (var14 != var15 && (var14 < var15 || this.getSavedLightValue(EnumSkyBlock.Sky, p_150807_1_, p_150807_2_, p_150807_3_) > 0 || this.getSavedLightValue(EnumSkyBlock.Block, p_150807_1_, p_150807_2_, p_150807_3_) > 0)) {
                this.propagateSkylightOcclusion(p_150807_1_, p_150807_3_);
            }
        }
        if (var8 instanceof ITileEntityProvider) {
            final TileEntity var16 = this.func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_);
            if (var16 != null) {
                var16.updateContainingBlockInfo();
            }
        }
        if (!this.worldObj.isClient) {
            p_150807_4_.onBlockAdded(this.worldObj, var12, p_150807_2_, var13);
        }
        if (p_150807_4_ instanceof ITileEntityProvider) {
            TileEntity var16 = this.func_150806_e(p_150807_1_, p_150807_2_, p_150807_3_);
            if (var16 == null) {
                var16 = ((ITileEntityProvider)p_150807_4_).createNewTileEntity(this.worldObj, p_150807_5_);
                this.worldObj.setTileEntity(var12, p_150807_2_, var13, var16);
            }
            if (var16 != null) {
                var16.updateContainingBlockInfo();
            }
        }
        return this.isModified = true;
    }
    
    public boolean setBlockMetadata(final int p_76589_1_, final int p_76589_2_, final int p_76589_3_, final int p_76589_4_) {
        final ExtendedBlockStorage var5 = this.storageArrays[p_76589_2_ >> 4];
        if (var5 == null) {
            return false;
        }
        final int var6 = var5.getExtBlockMetadata(p_76589_1_, p_76589_2_ & 0xF, p_76589_3_);
        if (var6 == p_76589_4_) {
            return false;
        }
        this.isModified = true;
        var5.setExtBlockMetadata(p_76589_1_, p_76589_2_ & 0xF, p_76589_3_, p_76589_4_);
        if (var5.func_150819_a(p_76589_1_, p_76589_2_ & 0xF, p_76589_3_) instanceof ITileEntityProvider) {
            final TileEntity var7 = this.func_150806_e(p_76589_1_, p_76589_2_, p_76589_3_);
            if (var7 != null) {
                var7.updateContainingBlockInfo();
                var7.blockMetadata = p_76589_4_;
            }
        }
        return true;
    }
    
    public int getSavedLightValue(final EnumSkyBlock p_76614_1_, final int p_76614_2_, final int p_76614_3_, final int p_76614_4_) {
        final ExtendedBlockStorage var5 = this.storageArrays[p_76614_3_ >> 4];
        return (var5 == null) ? (this.canBlockSeeTheSky(p_76614_2_, p_76614_3_, p_76614_4_) ? p_76614_1_.defaultLightValue : 0) : ((p_76614_1_ == EnumSkyBlock.Sky) ? (this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(p_76614_2_, p_76614_3_ & 0xF, p_76614_4_)) : ((p_76614_1_ == EnumSkyBlock.Block) ? var5.getExtBlocklightValue(p_76614_2_, p_76614_3_ & 0xF, p_76614_4_) : p_76614_1_.defaultLightValue));
    }
    
    public void setLightValue(final EnumSkyBlock p_76633_1_, final int p_76633_2_, final int p_76633_3_, final int p_76633_4_, final int p_76633_5_) {
        ExtendedBlockStorage var6 = this.storageArrays[p_76633_3_ >> 4];
        if (var6 == null) {
            final ExtendedBlockStorage[] storageArrays = this.storageArrays;
            final int n = p_76633_3_ >> 4;
            final ExtendedBlockStorage extendedBlockStorage = new ExtendedBlockStorage(p_76633_3_ >> 4 << 4, !this.worldObj.provider.hasNoSky);
            storageArrays[n] = extendedBlockStorage;
            var6 = extendedBlockStorage;
            this.generateSkylightMap();
        }
        this.isModified = true;
        if (p_76633_1_ == EnumSkyBlock.Sky) {
            if (!this.worldObj.provider.hasNoSky) {
                var6.setExtSkylightValue(p_76633_2_, p_76633_3_ & 0xF, p_76633_4_, p_76633_5_);
            }
        }
        else if (p_76633_1_ == EnumSkyBlock.Block) {
            var6.setExtBlocklightValue(p_76633_2_, p_76633_3_ & 0xF, p_76633_4_, p_76633_5_);
        }
    }
    
    public int getBlockLightValue(final int p_76629_1_, final int p_76629_2_, final int p_76629_3_, final int p_76629_4_) {
        final ExtendedBlockStorage var5 = this.storageArrays[p_76629_2_ >> 4];
        if (var5 == null) {
            return (!this.worldObj.provider.hasNoSky && p_76629_4_ < EnumSkyBlock.Sky.defaultLightValue) ? (EnumSkyBlock.Sky.defaultLightValue - p_76629_4_) : 0;
        }
        int var6 = this.worldObj.provider.hasNoSky ? 0 : var5.getExtSkylightValue(p_76629_1_, p_76629_2_ & 0xF, p_76629_3_);
        if (var6 > 0) {
            Chunk.isLit = true;
        }
        var6 -= p_76629_4_;
        final int var7 = var5.getExtBlocklightValue(p_76629_1_, p_76629_2_ & 0xF, p_76629_3_);
        if (var7 > var6) {
            var6 = var7;
        }
        return var6;
    }
    
    public void addEntity(final Entity p_76612_1_) {
        this.hasEntities = true;
        final int var2 = MathHelper.floor_double(p_76612_1_.posX / 16.0);
        final int var3 = MathHelper.floor_double(p_76612_1_.posZ / 16.0);
        if (var2 != this.xPosition || var3 != this.zPosition) {
            Chunk.logger.warn("Wrong location! " + p_76612_1_ + " (at " + var2 + ", " + var3 + " instead of " + this.xPosition + ", " + this.zPosition + ")");
            Thread.dumpStack();
        }
        int var4 = MathHelper.floor_double(p_76612_1_.posY / 16.0);
        if (var4 < 0) {
            var4 = 0;
        }
        if (var4 >= this.entityLists.length) {
            var4 = this.entityLists.length - 1;
        }
        p_76612_1_.addedToChunk = true;
        p_76612_1_.chunkCoordX = this.xPosition;
        p_76612_1_.chunkCoordY = var4;
        p_76612_1_.chunkCoordZ = this.zPosition;
        this.entityLists[var4].add(p_76612_1_);
    }
    
    public void removeEntity(final Entity p_76622_1_) {
        this.removeEntityAtIndex(p_76622_1_, p_76622_1_.chunkCoordY);
    }
    
    public void removeEntityAtIndex(final Entity p_76608_1_, int p_76608_2_) {
        if (p_76608_2_ < 0) {
            p_76608_2_ = 0;
        }
        if (p_76608_2_ >= this.entityLists.length) {
            p_76608_2_ = this.entityLists.length - 1;
        }
        this.entityLists[p_76608_2_].remove(p_76608_1_);
    }
    
    public boolean canBlockSeeTheSky(final int p_76619_1_, final int p_76619_2_, final int p_76619_3_) {
        return p_76619_2_ >= this.heightMap[p_76619_3_ << 4 | p_76619_1_];
    }
    
    public TileEntity func_150806_e(final int p_150806_1_, final int p_150806_2_, final int p_150806_3_) {
        final ChunkPosition var4 = new ChunkPosition(p_150806_1_, p_150806_2_, p_150806_3_);
        TileEntity var5 = this.chunkTileEntityMap.get(var4);
        if (var5 == null) {
            final Block var6 = this.func_150810_a(p_150806_1_, p_150806_2_, p_150806_3_);
            if (!var6.hasTileEntity()) {
                return null;
            }
            var5 = ((ITileEntityProvider)var6).createNewTileEntity(this.worldObj, this.getBlockMetadata(p_150806_1_, p_150806_2_, p_150806_3_));
            this.worldObj.setTileEntity(this.xPosition * 16 + p_150806_1_, p_150806_2_, this.zPosition * 16 + p_150806_3_, var5);
        }
        if (var5 != null && var5.isInvalid()) {
            this.chunkTileEntityMap.remove(var4);
            return null;
        }
        return var5;
    }
    
    public void addTileEntity(final TileEntity p_150813_1_) {
        final int var2 = p_150813_1_.field_145851_c - this.xPosition * 16;
        final int var3 = p_150813_1_.field_145848_d;
        final int var4 = p_150813_1_.field_145849_e - this.zPosition * 16;
        this.func_150812_a(var2, var3, var4, p_150813_1_);
        if (this.isChunkLoaded) {
            this.worldObj.field_147482_g.add(p_150813_1_);
        }
    }
    
    public void func_150812_a(final int p_150812_1_, final int p_150812_2_, final int p_150812_3_, final TileEntity p_150812_4_) {
        final ChunkPosition var5 = new ChunkPosition(p_150812_1_, p_150812_2_, p_150812_3_);
        p_150812_4_.setWorldObj(this.worldObj);
        p_150812_4_.field_145851_c = this.xPosition * 16 + p_150812_1_;
        p_150812_4_.field_145848_d = p_150812_2_;
        p_150812_4_.field_145849_e = this.zPosition * 16 + p_150812_3_;
        if (this.func_150810_a(p_150812_1_, p_150812_2_, p_150812_3_) instanceof ITileEntityProvider) {
            if (this.chunkTileEntityMap.containsKey(var5)) {
                this.chunkTileEntityMap.get(var5).invalidate();
            }
            p_150812_4_.validate();
            this.chunkTileEntityMap.put(var5, p_150812_4_);
        }
    }
    
    public void removeTileEntity(final int p_150805_1_, final int p_150805_2_, final int p_150805_3_) {
        final ChunkPosition var4 = new ChunkPosition(p_150805_1_, p_150805_2_, p_150805_3_);
        if (this.isChunkLoaded) {
            final TileEntity var5 = this.chunkTileEntityMap.remove(var4);
            if (var5 != null) {
                var5.invalidate();
            }
        }
    }
    
    public void onChunkLoad() {
        this.isChunkLoaded = true;
        this.worldObj.func_147448_a(this.chunkTileEntityMap.values());
        for (int var1 = 0; var1 < this.entityLists.length; ++var1) {
            for (final Entity var3 : this.entityLists[var1]) {
                var3.onChunkLoad();
            }
            this.worldObj.addLoadedEntities(this.entityLists[var1]);
        }
    }
    
    public void onChunkUnload() {
        this.isChunkLoaded = false;
        for (final TileEntity var2 : this.chunkTileEntityMap.values()) {
            this.worldObj.func_147457_a(var2);
        }
        for (int var3 = 0; var3 < this.entityLists.length; ++var3) {
            this.worldObj.unloadEntities(this.entityLists[var3]);
        }
    }
    
    public void setChunkModified() {
        this.isModified = true;
    }
    
    public void getEntitiesWithinAABBForEntity(final Entity p_76588_1_, final AxisAlignedBB p_76588_2_, final List p_76588_3_, final IEntitySelector p_76588_4_) {
        int var5 = MathHelper.floor_double((p_76588_2_.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((p_76588_2_.maxY + 2.0) / 16.0);
        var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
        var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
        for (int var7 = var5; var7 <= var6; ++var7) {
            final List var8 = this.entityLists[var7];
            for (int var9 = 0; var9 < var8.size(); ++var9) {
                Entity var10 = var8.get(var9);
                if (var10 != p_76588_1_ && var10.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(var10))) {
                    p_76588_3_.add(var10);
                    final Entity[] var11 = var10.getParts();
                    if (var11 != null) {
                        for (int var12 = 0; var12 < var11.length; ++var12) {
                            var10 = var11[var12];
                            if (var10 != p_76588_1_ && var10.boundingBox.intersectsWith(p_76588_2_) && (p_76588_4_ == null || p_76588_4_.isEntityApplicable(var10))) {
                                p_76588_3_.add(var10);
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void getEntitiesOfTypeWithinAAAB(final Class p_76618_1_, final AxisAlignedBB p_76618_2_, final List p_76618_3_, final IEntitySelector p_76618_4_) {
        int var5 = MathHelper.floor_double((p_76618_2_.minY - 2.0) / 16.0);
        int var6 = MathHelper.floor_double((p_76618_2_.maxY + 2.0) / 16.0);
        var5 = MathHelper.clamp_int(var5, 0, this.entityLists.length - 1);
        var6 = MathHelper.clamp_int(var6, 0, this.entityLists.length - 1);
        for (int var7 = var5; var7 <= var6; ++var7) {
            final List var8 = this.entityLists[var7];
            for (int var9 = 0; var9 < var8.size(); ++var9) {
                final Entity var10 = var8.get(var9);
                if (p_76618_1_.isAssignableFrom(var10.getClass()) && var10.boundingBox.intersectsWith(p_76618_2_) && (p_76618_4_ == null || p_76618_4_.isEntityApplicable(var10))) {
                    p_76618_3_.add(var10);
                }
            }
        }
    }
    
    public boolean needsSaving(final boolean p_76601_1_) {
        if (p_76601_1_) {
            if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified) {
                return true;
            }
        }
        else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
            return true;
        }
        return this.isModified;
    }
    
    public Random getRandomWithSeed(final long p_76617_1_) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ p_76617_1_);
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public void populateChunk(final IChunkProvider p_76624_1_, final IChunkProvider p_76624_2_, final int p_76624_3_, final int p_76624_4_) {
        if (!this.isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_)) {
            p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_);
        }
        if (p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_) && !p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ + 1) && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ + 1)) {
            p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_);
        }
        if (p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1) && !p_76624_1_.provideChunk(p_76624_3_, p_76624_4_ - 1).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ + 1, p_76624_4_)) {
            p_76624_1_.populate(p_76624_2_, p_76624_3_, p_76624_4_ - 1);
        }
        if (p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_ - 1) && !p_76624_1_.provideChunk(p_76624_3_ - 1, p_76624_4_ - 1).isTerrainPopulated && p_76624_1_.chunkExists(p_76624_3_, p_76624_4_ - 1) && p_76624_1_.chunkExists(p_76624_3_ - 1, p_76624_4_)) {
            p_76624_1_.populate(p_76624_2_, p_76624_3_ - 1, p_76624_4_ - 1);
        }
    }
    
    public int getPrecipitationHeight(final int p_76626_1_, final int p_76626_2_) {
        final int var3 = p_76626_1_ | p_76626_2_ << 4;
        int var4 = this.precipitationHeightMap[var3];
        if (var4 == -999) {
            int var5 = this.getTopFilledSegment() + 15;
            var4 = -1;
            while (var5 > 0 && var4 == -1) {
                final Block var6 = this.func_150810_a(p_76626_1_, var5, p_76626_2_);
                final Material var7 = var6.getMaterial();
                if (!var7.blocksMovement() && !var7.isLiquid()) {
                    --var5;
                }
                else {
                    var4 = var5 + 1;
                }
            }
            this.precipitationHeightMap[var3] = var4;
        }
        return var4;
    }
    
    public void func_150804_b(final boolean p_150804_1_) {
        if (this.isGapLightingUpdated && !this.worldObj.provider.hasNoSky && !p_150804_1_) {
            this.recheckGaps(this.worldObj.isClient);
        }
        this.field_150815_m = true;
        if (!this.isLightPopulated && this.isTerrainPopulated) {
            this.func_150809_p();
        }
    }
    
    public boolean func_150802_k() {
        return this.field_150815_m && this.isTerrainPopulated && this.isLightPopulated;
    }
    
    public ChunkCoordIntPair getChunkCoordIntPair() {
        return new ChunkCoordIntPair(this.xPosition, this.zPosition);
    }
    
    public boolean getAreLevelsEmpty(int p_76606_1_, int p_76606_2_) {
        if (p_76606_1_ < 0) {
            p_76606_1_ = 0;
        }
        if (p_76606_2_ >= 256) {
            p_76606_2_ = 255;
        }
        for (int var3 = p_76606_1_; var3 <= p_76606_2_; var3 += 16) {
            final ExtendedBlockStorage var4 = this.storageArrays[var3 >> 4];
            if (var4 != null && !var4.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public void setStorageArrays(final ExtendedBlockStorage[] p_76602_1_) {
        this.storageArrays = p_76602_1_;
    }
    
    public void fillChunk(final byte[] p_76607_1_, final int p_76607_2_, final int p_76607_3_, final boolean p_76607_4_) {
        int var5 = 0;
        final boolean var6 = !this.worldObj.provider.hasNoSky;
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((p_76607_2_ & 1 << var7) != 0x0) {
                if (this.storageArrays[var7] == null) {
                    this.storageArrays[var7] = new ExtendedBlockStorage(var7 << 4, var6);
                }
                final byte[] var8 = this.storageArrays[var7].getBlockLSBArray();
                System.arraycopy(p_76607_1_, var5, var8, 0, var8.length);
                var5 += var8.length;
            }
            else if (p_76607_4_ && this.storageArrays[var7] != null) {
                this.storageArrays[var7] = null;
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((p_76607_2_ & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                final NibbleArray var9 = this.storageArrays[var7].getMetadataArray();
                System.arraycopy(p_76607_1_, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((p_76607_2_ & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                final NibbleArray var9 = this.storageArrays[var7].getBlocklightArray();
                System.arraycopy(p_76607_1_, var5, var9.data, 0, var9.data.length);
                var5 += var9.data.length;
            }
        }
        if (var6) {
            for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
                if ((p_76607_2_ & 1 << var7) != 0x0 && this.storageArrays[var7] != null) {
                    final NibbleArray var9 = this.storageArrays[var7].getSkylightArray();
                    System.arraycopy(p_76607_1_, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
            }
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if ((p_76607_3_ & 1 << var7) != 0x0) {
                if (this.storageArrays[var7] == null) {
                    var5 += 2048;
                }
                else {
                    NibbleArray var9 = this.storageArrays[var7].getBlockMSBArray();
                    if (var9 == null) {
                        var9 = this.storageArrays[var7].createBlockMSBArray();
                    }
                    System.arraycopy(p_76607_1_, var5, var9.data, 0, var9.data.length);
                    var5 += var9.data.length;
                }
            }
            else if (p_76607_4_ && this.storageArrays[var7] != null && this.storageArrays[var7].getBlockMSBArray() != null) {
                this.storageArrays[var7].clearMSBArray();
            }
        }
        if (p_76607_4_) {
            System.arraycopy(p_76607_1_, var5, this.blockBiomeArray, 0, this.blockBiomeArray.length);
            final int n = var5 + this.blockBiomeArray.length;
        }
        for (int var7 = 0; var7 < this.storageArrays.length; ++var7) {
            if (this.storageArrays[var7] != null && (p_76607_2_ & 1 << var7) != 0x0) {
                this.storageArrays[var7].removeInvalidBlocks();
            }
        }
        this.isLightPopulated = true;
        this.isTerrainPopulated = true;
        this.generateHeightMap();
        for (final TileEntity var11 : this.chunkTileEntityMap.values()) {
            var11.updateContainingBlockInfo();
        }
    }
    
    public BiomeGenBase getBiomeGenForWorldCoords(final int p_76591_1_, final int p_76591_2_, final WorldChunkManager p_76591_3_) {
        int var4 = this.blockBiomeArray[p_76591_2_ << 4 | p_76591_1_] & 0xFF;
        if (var4 == 255) {
            final BiomeGenBase var5 = p_76591_3_.getBiomeGenAt((this.xPosition << 4) + p_76591_1_, (this.zPosition << 4) + p_76591_2_);
            var4 = var5.biomeID;
            this.blockBiomeArray[p_76591_2_ << 4 | p_76591_1_] = (byte)(var4 & 0xFF);
        }
        return (BiomeGenBase.func_150568_d(var4) == null) ? BiomeGenBase.plains : BiomeGenBase.func_150568_d(var4);
    }
    
    public byte[] getBiomeArray() {
        return this.blockBiomeArray;
    }
    
    public void setBiomeArray(final byte[] p_76616_1_) {
        this.blockBiomeArray = p_76616_1_;
    }
    
    public void resetRelightChecks() {
        this.queuedLightChecks = 0;
    }
    
    public void enqueueRelightChecks() {
        for (int var1 = 0; var1 < 8; ++var1) {
            if (this.queuedLightChecks >= 4096) {
                return;
            }
            final int var2 = this.queuedLightChecks % 16;
            final int var3 = this.queuedLightChecks / 16 % 16;
            final int var4 = this.queuedLightChecks / 256;
            ++this.queuedLightChecks;
            final int var5 = (this.xPosition << 4) + var3;
            final int var6 = (this.zPosition << 4) + var4;
            for (int var7 = 0; var7 < 16; ++var7) {
                final int var8 = (var2 << 4) + var7;
                if ((this.storageArrays[var2] == null && (var7 == 0 || var7 == 15 || var3 == 0 || var3 == 15 || var4 == 0 || var4 == 15)) || (this.storageArrays[var2] != null && this.storageArrays[var2].func_150819_a(var3, var7, var4).getMaterial() == Material.air)) {
                    if (this.worldObj.getBlock(var5, var8 - 1, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8 - 1, var6);
                    }
                    if (this.worldObj.getBlock(var5, var8 + 1, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8 + 1, var6);
                    }
                    if (this.worldObj.getBlock(var5 - 1, var8, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5 - 1, var8, var6);
                    }
                    if (this.worldObj.getBlock(var5 + 1, var8, var6).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5 + 1, var8, var6);
                    }
                    if (this.worldObj.getBlock(var5, var8, var6 - 1).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8, var6 - 1);
                    }
                    if (this.worldObj.getBlock(var5, var8, var6 + 1).getLightValue() > 0) {
                        this.worldObj.func_147451_t(var5, var8, var6 + 1);
                    }
                    this.worldObj.func_147451_t(var5, var8, var6);
                }
            }
        }
    }
    
    public void func_150809_p() {
        this.isTerrainPopulated = true;
        this.isLightPopulated = true;
        if (!this.worldObj.provider.hasNoSky) {
            if (this.worldObj.checkChunksExist(this.xPosition * 16 - 1, 0, this.zPosition * 16 - 1, this.xPosition * 16 + 1, 63, this.zPosition * 16 + 1)) {
                for (int var1 = 0; var1 < 16; ++var1) {
                    for (int var2 = 0; var2 < 16; ++var2) {
                        if (!this.func_150811_f(var1, var2)) {
                            this.isLightPopulated = false;
                            break;
                        }
                    }
                }
                if (this.isLightPopulated) {
                    Chunk var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 - 1, this.zPosition * 16);
                    var3.func_150801_a(3);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16 + 16, this.zPosition * 16);
                    var3.func_150801_a(1);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 - 1);
                    var3.func_150801_a(0);
                    var3 = this.worldObj.getChunkFromBlockCoords(this.xPosition * 16, this.zPosition * 16 + 16);
                    var3.func_150801_a(2);
                }
            }
            else {
                this.isLightPopulated = false;
            }
        }
    }
    
    private void func_150801_a(final int p_150801_1_) {
        if (this.isTerrainPopulated) {
            if (p_150801_1_ == 3) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.func_150811_f(15, var2);
                }
            }
            else if (p_150801_1_ == 1) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.func_150811_f(0, var2);
                }
            }
            else if (p_150801_1_ == 0) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.func_150811_f(var2, 15);
                }
            }
            else if (p_150801_1_ == 2) {
                for (int var2 = 0; var2 < 16; ++var2) {
                    this.func_150811_f(var2, 0);
                }
            }
        }
    }
    
    private boolean func_150811_f(final int p_150811_1_, final int p_150811_2_) {
        final int var3 = this.getTopFilledSegment();
        boolean var4 = false;
        boolean var5;
        int var6;
        int var7;
        for (var5 = false, var6 = var3 + 16 - 1; var6 > 63 || (var6 > 0 && !var5); --var6) {
            var7 = this.func_150808_b(p_150811_1_, var6, p_150811_2_);
            if (var7 == 255 && var6 < 63) {
                var5 = true;
            }
            if (!var4 && var7 > 0) {
                var4 = true;
            }
            else if (var4 && var7 == 0 && !this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_)) {
                return false;
            }
        }
        while (var6 > 0) {
            if (this.func_150810_a(p_150811_1_, var6, p_150811_2_).getLightValue() > 0) {
                this.worldObj.func_147451_t(this.xPosition * 16 + p_150811_1_, var6, this.zPosition * 16 + p_150811_2_);
            }
            --var6;
        }
        return true;
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
