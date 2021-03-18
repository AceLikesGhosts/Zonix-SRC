package net.minecraft.world.storage;

import net.minecraft.nbt.*;
import net.minecraft.world.*;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo theWorldInfo;
    private static final String __OBFID = "CL_00000584";
    
    public DerivedWorldInfo(final WorldInfo p_i2145_1_) {
        this.theWorldInfo = p_i2145_1_;
    }
    
    @Override
    public NBTTagCompound getNBTTagCompound() {
        return this.theWorldInfo.getNBTTagCompound();
    }
    
    @Override
    public NBTTagCompound cloneNBTCompound(final NBTTagCompound p_76082_1_) {
        return this.theWorldInfo.cloneNBTCompound(p_76082_1_);
    }
    
    @Override
    public long getSeed() {
        return this.theWorldInfo.getSeed();
    }
    
    @Override
    public int getSpawnX() {
        return this.theWorldInfo.getSpawnX();
    }
    
    @Override
    public int getSpawnY() {
        return this.theWorldInfo.getSpawnY();
    }
    
    @Override
    public int getSpawnZ() {
        return this.theWorldInfo.getSpawnZ();
    }
    
    @Override
    public long getWorldTotalTime() {
        return this.theWorldInfo.getWorldTotalTime();
    }
    
    @Override
    public long getWorldTime() {
        return this.theWorldInfo.getWorldTime();
    }
    
    @Override
    public long getSizeOnDisk() {
        return this.theWorldInfo.getSizeOnDisk();
    }
    
    @Override
    public NBTTagCompound getPlayerNBTTagCompound() {
        return this.theWorldInfo.getPlayerNBTTagCompound();
    }
    
    @Override
    public int getVanillaDimension() {
        return this.theWorldInfo.getVanillaDimension();
    }
    
    @Override
    public String getWorldName() {
        return this.theWorldInfo.getWorldName();
    }
    
    @Override
    public int getSaveVersion() {
        return this.theWorldInfo.getSaveVersion();
    }
    
    @Override
    public long getLastTimePlayed() {
        return this.theWorldInfo.getLastTimePlayed();
    }
    
    @Override
    public boolean isThundering() {
        return this.theWorldInfo.isThundering();
    }
    
    @Override
    public int getThunderTime() {
        return this.theWorldInfo.getThunderTime();
    }
    
    @Override
    public boolean isRaining() {
        return this.theWorldInfo.isRaining();
    }
    
    @Override
    public int getRainTime() {
        return this.theWorldInfo.getRainTime();
    }
    
    @Override
    public WorldSettings.GameType getGameType() {
        return this.theWorldInfo.getGameType();
    }
    
    @Override
    public void setSpawnX(final int p_76058_1_) {
    }
    
    @Override
    public void setSpawnY(final int p_76056_1_) {
    }
    
    @Override
    public void setSpawnZ(final int p_76087_1_) {
    }
    
    @Override
    public void incrementTotalWorldTime(final long p_82572_1_) {
    }
    
    @Override
    public void setWorldTime(final long p_76068_1_) {
    }
    
    @Override
    public void setSpawnPosition(final int p_76081_1_, final int p_76081_2_, final int p_76081_3_) {
    }
    
    @Override
    public void setWorldName(final String p_76062_1_) {
    }
    
    @Override
    public void setSaveVersion(final int p_76078_1_) {
    }
    
    @Override
    public void setThundering(final boolean p_76069_1_) {
    }
    
    @Override
    public void setThunderTime(final int p_76090_1_) {
    }
    
    @Override
    public void setRaining(final boolean p_76084_1_) {
    }
    
    @Override
    public void setRainTime(final int p_76080_1_) {
    }
    
    @Override
    public boolean isMapFeaturesEnabled() {
        return this.theWorldInfo.isMapFeaturesEnabled();
    }
    
    @Override
    public boolean isHardcoreModeEnabled() {
        return this.theWorldInfo.isHardcoreModeEnabled();
    }
    
    @Override
    public WorldType getTerrainType() {
        return this.theWorldInfo.getTerrainType();
    }
    
    @Override
    public void setTerrainType(final WorldType p_76085_1_) {
    }
    
    @Override
    public boolean areCommandsAllowed() {
        return this.theWorldInfo.areCommandsAllowed();
    }
    
    @Override
    public boolean isInitialized() {
        return this.theWorldInfo.isInitialized();
    }
    
    @Override
    public void setServerInitialized(final boolean p_76091_1_) {
    }
    
    @Override
    public GameRules getGameRulesInstance() {
        return this.theWorldInfo.getGameRulesInstance();
    }
}
