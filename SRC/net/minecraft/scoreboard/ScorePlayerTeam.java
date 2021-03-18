package net.minecraft.scoreboard;

import java.util.*;

public class ScorePlayerTeam extends Team
{
    private final Scoreboard theScoreboard;
    private final String field_96675_b;
    private final Set membershipSet;
    private String teamNameSPT;
    private String namePrefixSPT;
    private String colorSuffix;
    private boolean allowFriendlyFire;
    private boolean canSeeFriendlyInvisibles;
    private static final String __OBFID = "CL_00000616";
    
    public ScorePlayerTeam(final Scoreboard p_i2308_1_, final String p_i2308_2_) {
        this.membershipSet = new HashSet();
        this.namePrefixSPT = "";
        this.colorSuffix = "";
        this.allowFriendlyFire = true;
        this.canSeeFriendlyInvisibles = true;
        this.theScoreboard = p_i2308_1_;
        this.field_96675_b = p_i2308_2_;
        this.teamNameSPT = p_i2308_2_;
    }
    
    @Override
    public String getRegisteredName() {
        return this.field_96675_b;
    }
    
    public String func_96669_c() {
        return this.teamNameSPT;
    }
    
    public void setTeamName(final String p_96664_1_) {
        if (p_96664_1_ == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.teamNameSPT = p_96664_1_;
        this.theScoreboard.func_96538_b(this);
    }
    
    public Collection getMembershipCollection() {
        return this.membershipSet;
    }
    
    public String getColorPrefix() {
        return this.namePrefixSPT;
    }
    
    public void setNamePrefix(final String p_96666_1_) {
        if (p_96666_1_ == null) {
            throw new IllegalArgumentException("Prefix cannot be null");
        }
        this.namePrefixSPT = p_96666_1_;
        this.theScoreboard.func_96538_b(this);
    }
    
    public String getColorSuffix() {
        return this.colorSuffix;
    }
    
    public void setNameSuffix(final String p_96662_1_) {
        if (p_96662_1_ == null) {
            throw new IllegalArgumentException("Suffix cannot be null");
        }
        this.colorSuffix = p_96662_1_;
        this.theScoreboard.func_96538_b(this);
    }
    
    @Override
    public String func_142053_d(final String p_142053_1_) {
        return this.getColorPrefix() + p_142053_1_ + this.getColorSuffix();
    }
    
    public static String formatPlayerName(final Team p_96667_0_, final String p_96667_1_) {
        return (p_96667_0_ == null) ? p_96667_1_ : p_96667_0_.func_142053_d(p_96667_1_);
    }
    
    @Override
    public boolean getAllowFriendlyFire() {
        return this.allowFriendlyFire;
    }
    
    public void setAllowFriendlyFire(final boolean p_96660_1_) {
        this.allowFriendlyFire = p_96660_1_;
        this.theScoreboard.func_96538_b(this);
    }
    
    @Override
    public boolean func_98297_h() {
        return this.canSeeFriendlyInvisibles;
    }
    
    public void setSeeFriendlyInvisiblesEnabled(final boolean p_98300_1_) {
        this.canSeeFriendlyInvisibles = p_98300_1_;
        this.theScoreboard.func_96538_b(this);
    }
    
    public int func_98299_i() {
        int var1 = 0;
        if (this.getAllowFriendlyFire()) {
            var1 |= 0x1;
        }
        if (this.func_98297_h()) {
            var1 |= 0x2;
        }
        return var1;
    }
    
    public void func_98298_a(final int p_98298_1_) {
        this.setAllowFriendlyFire((p_98298_1_ & 0x1) > 0);
        this.setSeeFriendlyInvisiblesEnabled((p_98298_1_ & 0x2) > 0);
    }
}
