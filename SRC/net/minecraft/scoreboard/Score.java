package net.minecraft.scoreboard;

import java.util.*;

public class Score
{
    public static final Comparator field_96658_a;
    private final Scoreboard theScoreboard;
    private final ScoreObjective theScoreObjective;
    private final String field_96654_d;
    private int field_96655_e;
    private static final String __OBFID = "CL_00000617";
    
    public Score(final Scoreboard p_i2309_1_, final ScoreObjective p_i2309_2_, final String p_i2309_3_) {
        this.theScoreboard = p_i2309_1_;
        this.theScoreObjective = p_i2309_2_;
        this.field_96654_d = p_i2309_3_;
    }
    
    public void func_96649_a(final int p_96649_1_) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.func_96647_c(this.getScorePoints() + p_96649_1_);
    }
    
    public void func_96646_b(final int p_96646_1_) {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.func_96647_c(this.getScorePoints() - p_96646_1_);
    }
    
    public void func_96648_a() {
        if (this.theScoreObjective.getCriteria().isReadOnly()) {
            throw new IllegalStateException("Cannot modify read-only score");
        }
        this.func_96649_a(1);
    }
    
    public int getScorePoints() {
        return this.field_96655_e;
    }
    
    public void func_96647_c(final int p_96647_1_) {
        final int var2 = this.field_96655_e;
        this.field_96655_e = p_96647_1_;
        if (var2 != p_96647_1_) {
            this.func_96650_f().func_96536_a(this);
        }
    }
    
    public ScoreObjective func_96645_d() {
        return this.theScoreObjective;
    }
    
    public String getPlayerName() {
        return this.field_96654_d;
    }
    
    public Scoreboard func_96650_f() {
        return this.theScoreboard;
    }
    
    public void func_96651_a(final List p_96651_1_) {
        this.func_96647_c(this.theScoreObjective.getCriteria().func_96635_a(p_96651_1_));
    }
    
    static {
        field_96658_a = new Comparator() {
            private static final String __OBFID = "CL_00000618";
            
            public int compare(final Score p_compare_1_, final Score p_compare_2_) {
                return (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints()) ? 1 : ((p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints()) ? -1 : 0);
            }
            
            @Override
            public int compare(final Object p_compare_1_, final Object p_compare_2_) {
                return this.compare((Score)p_compare_1_, (Score)p_compare_2_);
            }
        };
    }
}
