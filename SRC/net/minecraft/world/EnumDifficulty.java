package net.minecraft.world;

public enum EnumDifficulty
{
    PEACEFUL(0, "options.difficulty.peaceful"), 
    EASY(1, "options.difficulty.easy"), 
    NORMAL(2, "options.difficulty.normal"), 
    HARD(3, "options.difficulty.hard");
    
    private static final EnumDifficulty[] difficultyEnums;
    private final int difficultyId;
    private final String difficultyResourceKey;
    private static final String __OBFID = "CL_00001510";
    
    private EnumDifficulty(final int p_i45312_3_, final String p_i45312_4_) {
        this.difficultyId = p_i45312_3_;
        this.difficultyResourceKey = p_i45312_4_;
    }
    
    public int getDifficultyId() {
        return this.difficultyId;
    }
    
    public static EnumDifficulty getDifficultyEnum(final int p_151523_0_) {
        return EnumDifficulty.difficultyEnums[p_151523_0_ % EnumDifficulty.difficultyEnums.length];
    }
    
    public String getDifficultyResourceKey() {
        return this.difficultyResourceKey;
    }
    
    static {
        difficultyEnums = new EnumDifficulty[values().length];
        for (final EnumDifficulty var4 : values()) {
            EnumDifficulty.difficultyEnums[var4.difficultyId] = var4;
        }
    }
}
