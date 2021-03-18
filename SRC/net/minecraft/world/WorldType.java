package net.minecraft.world;

public class WorldType
{
    public static final WorldType[] worldTypes;
    public static final WorldType DEFAULT;
    public static final WorldType FLAT;
    public static final WorldType LARGE_BIOMES;
    public static final WorldType field_151360_e;
    public static final WorldType DEFAULT_1_1;
    private final int worldTypeId;
    private final String worldType;
    private final int generatorVersion;
    private boolean canBeCreated;
    private boolean isWorldTypeVersioned;
    private boolean field_151361_l;
    private static final String __OBFID = "CL_00000150";
    
    private WorldType(final int p_i1959_1_, final String p_i1959_2_) {
        this(p_i1959_1_, p_i1959_2_, 0);
    }
    
    private WorldType(final int p_i1960_1_, final String p_i1960_2_, final int p_i1960_3_) {
        this.worldType = p_i1960_2_;
        this.generatorVersion = p_i1960_3_;
        this.canBeCreated = true;
        this.worldTypeId = p_i1960_1_;
        WorldType.worldTypes[p_i1960_1_] = this;
    }
    
    public String getWorldTypeName() {
        return this.worldType;
    }
    
    public String getTranslateName() {
        return "generator." + this.worldType;
    }
    
    public String func_151359_c() {
        return this.getTranslateName() + ".info";
    }
    
    public int getGeneratorVersion() {
        return this.generatorVersion;
    }
    
    public WorldType getWorldTypeForGeneratorVersion(final int p_77132_1_) {
        return (this == WorldType.DEFAULT && p_77132_1_ == 0) ? WorldType.DEFAULT_1_1 : this;
    }
    
    private WorldType setCanBeCreated(final boolean p_77124_1_) {
        this.canBeCreated = p_77124_1_;
        return this;
    }
    
    public boolean getCanBeCreated() {
        return this.canBeCreated;
    }
    
    private WorldType setVersioned() {
        this.isWorldTypeVersioned = true;
        return this;
    }
    
    public boolean isVersioned() {
        return this.isWorldTypeVersioned;
    }
    
    public static WorldType parseWorldType(final String p_77130_0_) {
        for (int var1 = 0; var1 < WorldType.worldTypes.length; ++var1) {
            if (WorldType.worldTypes[var1] != null && WorldType.worldTypes[var1].worldType.equalsIgnoreCase(p_77130_0_)) {
                return WorldType.worldTypes[var1];
            }
        }
        return null;
    }
    
    public int getWorldTypeID() {
        return this.worldTypeId;
    }
    
    public boolean func_151357_h() {
        return this.field_151361_l;
    }
    
    private WorldType func_151358_j() {
        this.field_151361_l = true;
        return this;
    }
    
    static {
        worldTypes = new WorldType[16];
        DEFAULT = new WorldType(0, "default", 1).setVersioned();
        FLAT = new WorldType(1, "flat");
        LARGE_BIOMES = new WorldType(2, "largeBiomes");
        field_151360_e = new WorldType(3, "amplified").func_151358_j();
        DEFAULT_1_1 = new WorldType(8, "default_1_1", 0).setCanBeCreated(false);
    }
}
