package net.minecraft.scoreboard;

public abstract class Team
{
    private static final String __OBFID = "CL_00000621";
    
    public boolean isSameTeam(final Team p_142054_1_) {
        return p_142054_1_ != null && this == p_142054_1_;
    }
    
    public abstract String getRegisteredName();
    
    public abstract String func_142053_d(final String p0);
    
    public abstract boolean func_98297_h();
    
    public abstract boolean getAllowFriendlyFire();
}
