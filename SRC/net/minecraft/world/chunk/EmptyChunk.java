package net.minecraft.world.chunk;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;

public class EmptyChunk extends Chunk
{
    private static final String __OBFID = "CL_00000372";
    
    public EmptyChunk(final World p_i1994_1_, final int p_i1994_2_, final int p_i1994_3_) {
        super(p_i1994_1_, p_i1994_2_, p_i1994_3_);
    }
    
    @Override
    public boolean isAtLocation(final int p_76600_1_, final int p_76600_2_) {
        return p_76600_1_ == this.xPosition && p_76600_2_ == this.zPosition;
    }
    
    @Override
    public int getHeightValue(final int p_76611_1_, final int p_76611_2_) {
        return 0;
    }
    
    @Override
    public void generateHeightMap() {
    }
    
    @Override
    public void generateSkylightMap() {
    }
    
    @Override
    public Block func_150810_a(final int p_150810_1_, final int p_150810_2_, final int p_150810_3_) {
        return Blocks.air;
    }
    
    @Override
    public int func_150808_b(final int p_150808_1_, final int p_150808_2_, final int p_150808_3_) {
        return 255;
    }
    
    @Override
    public boolean func_150807_a(final int p_150807_1_, final int p_150807_2_, final int p_150807_3_, final Block p_150807_4_, final int p_150807_5_) {
        return true;
    }
    
    @Override
    public int getBlockMetadata(final int p_76628_1_, final int p_76628_2_, final int p_76628_3_) {
        return 0;
    }
    
    @Override
    public boolean setBlockMetadata(final int p_76589_1_, final int p_76589_2_, final int p_76589_3_, final int p_76589_4_) {
        return false;
    }
    
    @Override
    public int getSavedLightValue(final EnumSkyBlock p_76614_1_, final int p_76614_2_, final int p_76614_3_, final int p_76614_4_) {
        return 0;
    }
    
    @Override
    public void setLightValue(final EnumSkyBlock p_76633_1_, final int p_76633_2_, final int p_76633_3_, final int p_76633_4_, final int p_76633_5_) {
    }
    
    @Override
    public int getBlockLightValue(final int p_76629_1_, final int p_76629_2_, final int p_76629_3_, final int p_76629_4_) {
        return 0;
    }
    
    @Override
    public void addEntity(final Entity p_76612_1_) {
    }
    
    @Override
    public void removeEntity(final Entity p_76622_1_) {
    }
    
    @Override
    public void removeEntityAtIndex(final Entity p_76608_1_, final int p_76608_2_) {
    }
    
    @Override
    public boolean canBlockSeeTheSky(final int p_76619_1_, final int p_76619_2_, final int p_76619_3_) {
        return false;
    }
    
    @Override
    public TileEntity func_150806_e(final int p_150806_1_, final int p_150806_2_, final int p_150806_3_) {
        return null;
    }
    
    @Override
    public void addTileEntity(final TileEntity p_150813_1_) {
    }
    
    @Override
    public void func_150812_a(final int p_150812_1_, final int p_150812_2_, final int p_150812_3_, final TileEntity p_150812_4_) {
    }
    
    @Override
    public void removeTileEntity(final int p_150805_1_, final int p_150805_2_, final int p_150805_3_) {
    }
    
    @Override
    public void onChunkLoad() {
    }
    
    @Override
    public void onChunkUnload() {
    }
    
    @Override
    public void setChunkModified() {
    }
    
    @Override
    public void getEntitiesWithinAABBForEntity(final Entity p_76588_1_, final AxisAlignedBB p_76588_2_, final List p_76588_3_, final IEntitySelector p_76588_4_) {
    }
    
    @Override
    public void getEntitiesOfTypeWithinAAAB(final Class p_76618_1_, final AxisAlignedBB p_76618_2_, final List p_76618_3_, final IEntitySelector p_76618_4_) {
    }
    
    @Override
    public boolean needsSaving(final boolean p_76601_1_) {
        return false;
    }
    
    @Override
    public Random getRandomWithSeed(final long p_76617_1_) {
        return new Random(this.worldObj.getSeed() + this.xPosition * this.xPosition * 4987142 + this.xPosition * 5947611 + this.zPosition * this.zPosition * 4392871L + this.zPosition * 389711 ^ p_76617_1_);
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public boolean getAreLevelsEmpty(final int p_76606_1_, final int p_76606_2_) {
        return true;
    }
}
