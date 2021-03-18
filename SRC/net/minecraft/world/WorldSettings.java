package net.minecraft.world;

import net.minecraft.world.storage.*;
import net.minecraft.entity.player.*;

public final class WorldSettings
{
    private final long seed;
    private final GameType theGameType;
    private final boolean mapFeaturesEnabled;
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    private boolean commandsAllowed;
    private boolean bonusChestEnabled;
    private String field_82751_h;
    private static final String __OBFID = "CL_00000147";
    
    public WorldSettings(final long p_i1957_1_, final GameType p_i1957_3_, final boolean p_i1957_4_, final boolean p_i1957_5_, final WorldType p_i1957_6_) {
        this.field_82751_h = "";
        this.seed = p_i1957_1_;
        this.theGameType = p_i1957_3_;
        this.mapFeaturesEnabled = p_i1957_4_;
        this.hardcoreEnabled = p_i1957_5_;
        this.terrainType = p_i1957_6_;
    }
    
    public WorldSettings(final WorldInfo p_i1958_1_) {
        this(p_i1958_1_.getSeed(), p_i1958_1_.getGameType(), p_i1958_1_.isMapFeaturesEnabled(), p_i1958_1_.isHardcoreModeEnabled(), p_i1958_1_.getTerrainType());
    }
    
    public WorldSettings enableBonusChest() {
        this.bonusChestEnabled = true;
        return this;
    }
    
    public WorldSettings enableCommands() {
        this.commandsAllowed = true;
        return this;
    }
    
    public WorldSettings func_82750_a(final String p_82750_1_) {
        this.field_82751_h = p_82750_1_;
        return this;
    }
    
    public boolean isBonusChestEnabled() {
        return this.bonusChestEnabled;
    }
    
    public long getSeed() {
        return this.seed;
    }
    
    public GameType getGameType() {
        return this.theGameType;
    }
    
    public boolean getHardcoreEnabled() {
        return this.hardcoreEnabled;
    }
    
    public boolean isMapFeaturesEnabled() {
        return this.mapFeaturesEnabled;
    }
    
    public WorldType getTerrainType() {
        return this.terrainType;
    }
    
    public boolean areCommandsAllowed() {
        return this.commandsAllowed;
    }
    
    public static GameType getGameTypeById(final int p_77161_0_) {
        return GameType.getByID(p_77161_0_);
    }
    
    public String func_82749_j() {
        return this.field_82751_h;
    }
    
    public enum GameType
    {
        NOT_SET("NOT_SET", 0, -1, ""), 
        SURVIVAL("SURVIVAL", 1, 0, "survival"), 
        CREATIVE("CREATIVE", 2, 1, "creative"), 
        ADVENTURE("ADVENTURE", 3, 2, "adventure");
        
        int id;
        String name;
        private static final GameType[] $VALUES;
        private static final String __OBFID = "CL_00000148";
        
        private GameType(final String p_i1956_1_, final int p_i1956_2_, final int p_i1956_3_, final String p_i1956_4_) {
            this.id = p_i1956_3_;
            this.name = p_i1956_4_;
        }
        
        public int getID() {
            return this.id;
        }
        
        public String getName() {
            return this.name;
        }
        
        public void configurePlayerCapabilities(final PlayerCapabilities p_77147_1_) {
            if (this == GameType.CREATIVE) {
                p_77147_1_.allowFlying = true;
                p_77147_1_.isCreativeMode = true;
                p_77147_1_.disableDamage = true;
            }
            else {
                p_77147_1_.allowFlying = false;
                p_77147_1_.isCreativeMode = false;
                p_77147_1_.disableDamage = false;
                p_77147_1_.isFlying = false;
            }
            p_77147_1_.allowEdit = !this.isAdventure();
        }
        
        public boolean isAdventure() {
            return this == GameType.ADVENTURE;
        }
        
        public boolean isCreative() {
            return this == GameType.CREATIVE;
        }
        
        public boolean isSurvivalOrAdventure() {
            return this == GameType.SURVIVAL || this == GameType.ADVENTURE;
        }
        
        public static GameType getByID(final int p_77146_0_) {
            for (final GameType var4 : values()) {
                if (var4.id == p_77146_0_) {
                    return var4;
                }
            }
            return GameType.SURVIVAL;
        }
        
        public static GameType getByName(final String p_77142_0_) {
            for (final GameType var4 : values()) {
                if (var4.name.equals(p_77142_0_)) {
                    return var4;
                }
            }
            return GameType.SURVIVAL;
        }
        
        static {
            $VALUES = new GameType[] { GameType.NOT_SET, GameType.SURVIVAL, GameType.CREATIVE, GameType.ADVENTURE };
        }
    }
}
