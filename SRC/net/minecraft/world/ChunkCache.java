package net.minecraft.world;

import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.biome.*;
import net.minecraft.block.material.*;

public class ChunkCache implements IBlockAccess
{
    private int chunkX;
    private int chunkZ;
    private Chunk[][] chunkArray;
    private boolean isEmpty;
    private World worldObj;
    private static final String __OBFID = "CL_00000155";
    
    public ChunkCache(final World p_i1964_1_, final int p_i1964_2_, final int p_i1964_3_, final int p_i1964_4_, final int p_i1964_5_, final int p_i1964_6_, final int p_i1964_7_, final int p_i1964_8_) {
        this.worldObj = p_i1964_1_;
        this.chunkX = p_i1964_2_ - p_i1964_8_ >> 4;
        this.chunkZ = p_i1964_4_ - p_i1964_8_ >> 4;
        final int var9 = p_i1964_5_ + p_i1964_8_ >> 4;
        final int var10 = p_i1964_7_ + p_i1964_8_ >> 4;
        this.chunkArray = new Chunk[var9 - this.chunkX + 1][var10 - this.chunkZ + 1];
        this.isEmpty = true;
        for (int var11 = this.chunkX; var11 <= var9; ++var11) {
            for (int var12 = this.chunkZ; var12 <= var10; ++var12) {
                final Chunk var13 = p_i1964_1_.getChunkFromChunkCoords(var11, var12);
                if (var13 != null) {
                    this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ] = var13;
                }
            }
        }
        for (int var11 = p_i1964_2_ >> 4; var11 <= p_i1964_5_ >> 4; ++var11) {
            for (int var12 = p_i1964_4_ >> 4; var12 <= p_i1964_7_ >> 4; ++var12) {
                final Chunk var13 = this.chunkArray[var11 - this.chunkX][var12 - this.chunkZ];
                if (var13 != null && !var13.getAreLevelsEmpty(p_i1964_3_, p_i1964_6_)) {
                    this.isEmpty = false;
                }
            }
        }
    }
    
    @Override
    public boolean extendedLevelsInChunkCache() {
        return this.isEmpty;
    }
    
    @Override
    public Block getBlock(final int p_147439_1_, final int p_147439_2_, final int p_147439_3_) {
        Block var4 = Blocks.air;
        if (p_147439_2_ >= 0 && p_147439_2_ < 256) {
            final int var5 = (p_147439_1_ >> 4) - this.chunkX;
            final int var6 = (p_147439_3_ >> 4) - this.chunkZ;
            if (var5 >= 0 && var5 < this.chunkArray.length && var6 >= 0 && var6 < this.chunkArray[var5].length) {
                final Chunk var7 = this.chunkArray[var5][var6];
                if (var7 != null) {
                    var4 = var7.func_150810_a(p_147439_1_ & 0xF, p_147439_2_, p_147439_3_ & 0xF);
                }
            }
        }
        return var4;
    }
    
    @Override
    public TileEntity getTileEntity(final int p_147438_1_, final int p_147438_2_, final int p_147438_3_) {
        final int var4 = (p_147438_1_ >> 4) - this.chunkX;
        final int var5 = (p_147438_3_ >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].func_150806_e(p_147438_1_ & 0xF, p_147438_2_, p_147438_3_ & 0xF);
    }
    
    @Override
    public int getLightBrightnessForSkyBlocks(final int p_72802_1_, final int p_72802_2_, final int p_72802_3_, final int p_72802_4_) {
        final int var5 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Sky, p_72802_1_, p_72802_2_, p_72802_3_);
        int var6 = this.getSkyBlockTypeBrightness(EnumSkyBlock.Block, p_72802_1_, p_72802_2_, p_72802_3_);
        if (var6 < p_72802_4_) {
            var6 = p_72802_4_;
        }
        return var5 << 20 | var6 << 4;
    }
    
    @Override
    public int getBlockMetadata(final int p_72805_1_, final int p_72805_2_, final int p_72805_3_) {
        if (p_72805_2_ < 0) {
            return 0;
        }
        if (p_72805_2_ >= 256) {
            return 0;
        }
        final int var4 = (p_72805_1_ >> 4) - this.chunkX;
        final int var5 = (p_72805_3_ >> 4) - this.chunkZ;
        return this.chunkArray[var4][var5].getBlockMetadata(p_72805_1_ & 0xF, p_72805_2_, p_72805_3_ & 0xF);
    }
    
    @Override
    public BiomeGenBase getBiomeGenForCoords(final int p_72807_1_, final int p_72807_2_) {
        return this.worldObj.getBiomeGenForCoords(p_72807_1_, p_72807_2_);
    }
    
    @Override
    public boolean isAirBlock(final int p_147437_1_, final int p_147437_2_, final int p_147437_3_) {
        return this.getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air;
    }
    
    public int getSkyBlockTypeBrightness(final EnumSkyBlock p_72810_1_, final int p_72810_2_, int p_72810_3_, final int p_72810_4_) {
        if (p_72810_3_ < 0) {
            p_72810_3_ = 0;
        }
        if (p_72810_3_ >= 256) {
            p_72810_3_ = 255;
        }
        if (p_72810_3_ < 0 || p_72810_3_ >= 256 || p_72810_2_ < -30000000 || p_72810_4_ < -30000000 || p_72810_2_ >= 30000000 || p_72810_4_ > 30000000) {
            return p_72810_1_.defaultLightValue;
        }
        if (p_72810_1_ == EnumSkyBlock.Sky && this.worldObj.provider.hasNoSky) {
            return 0;
        }
        if (this.getBlock(p_72810_2_, p_72810_3_, p_72810_4_).func_149710_n()) {
            int var5 = this.getSpecialBlockBrightness(p_72810_1_, p_72810_2_, p_72810_3_ + 1, p_72810_4_);
            final int var6 = this.getSpecialBlockBrightness(p_72810_1_, p_72810_2_ + 1, p_72810_3_, p_72810_4_);
            final int var7 = this.getSpecialBlockBrightness(p_72810_1_, p_72810_2_ - 1, p_72810_3_, p_72810_4_);
            final int var8 = this.getSpecialBlockBrightness(p_72810_1_, p_72810_2_, p_72810_3_, p_72810_4_ + 1);
            final int var9 = this.getSpecialBlockBrightness(p_72810_1_, p_72810_2_, p_72810_3_, p_72810_4_ - 1);
            if (var6 > var5) {
                var5 = var6;
            }
            if (var7 > var5) {
                var5 = var7;
            }
            if (var8 > var5) {
                var5 = var8;
            }
            if (var9 > var5) {
                var5 = var9;
            }
            return var5;
        }
        int var5 = (p_72810_2_ >> 4) - this.chunkX;
        final int var6 = (p_72810_4_ >> 4) - this.chunkZ;
        return this.chunkArray[var5][var6].getSavedLightValue(p_72810_1_, p_72810_2_ & 0xF, p_72810_3_, p_72810_4_ & 0xF);
    }
    
    public int getSpecialBlockBrightness(final EnumSkyBlock p_72812_1_, final int p_72812_2_, int p_72812_3_, final int p_72812_4_) {
        if (p_72812_3_ < 0) {
            p_72812_3_ = 0;
        }
        if (p_72812_3_ >= 256) {
            p_72812_3_ = 255;
        }
        if (p_72812_3_ >= 0 && p_72812_3_ < 256 && p_72812_2_ >= -30000000 && p_72812_4_ >= -30000000 && p_72812_2_ < 30000000 && p_72812_4_ <= 30000000) {
            final int var5 = (p_72812_2_ >> 4) - this.chunkX;
            final int var6 = (p_72812_4_ >> 4) - this.chunkZ;
            return this.chunkArray[var5][var6].getSavedLightValue(p_72812_1_, p_72812_2_ & 0xF, p_72812_3_, p_72812_4_ & 0xF);
        }
        return p_72812_1_.defaultLightValue;
    }
    
    @Override
    public int getHeight() {
        return 256;
    }
    
    @Override
    public int isBlockProvidingPowerTo(final int p_72879_1_, final int p_72879_2_, final int p_72879_3_, final int p_72879_4_) {
        return this.getBlock(p_72879_1_, p_72879_2_, p_72879_3_).isProvidingStrongPower(this, p_72879_1_, p_72879_2_, p_72879_3_, p_72879_4_);
    }
}
