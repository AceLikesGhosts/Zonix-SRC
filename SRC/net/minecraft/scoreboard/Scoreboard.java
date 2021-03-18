package net.minecraft.scoreboard;

import java.util.*;

public class Scoreboard
{
    private final Map scoreObjectives;
    private final Map scoreObjectiveCriterias;
    public final Map field_96544_c;
    private final ScoreObjective[] field_96541_d;
    private final Map teams;
    private final Map teamMemberships;
    private static final String __OBFID = "CL_00000619";
    
    public Scoreboard() {
        this.scoreObjectives = new HashMap();
        this.scoreObjectiveCriterias = new HashMap();
        this.field_96544_c = new HashMap();
        this.field_96541_d = new ScoreObjective[3];
        this.teams = new HashMap();
        this.teamMemberships = new HashMap();
    }
    
    public ScoreObjective getObjective(final String p_96518_1_) {
        return this.scoreObjectives.get(p_96518_1_);
    }
    
    public ScoreObjective addScoreObjective(final String p_96535_1_, final IScoreObjectiveCriteria p_96535_2_) {
        ScoreObjective var3 = this.getObjective(p_96535_1_);
        if (var3 != null) {
            throw new IllegalArgumentException("An objective with the name '" + p_96535_1_ + "' already exists!");
        }
        var3 = new ScoreObjective(this, p_96535_1_, p_96535_2_);
        Object var4 = this.scoreObjectiveCriterias.get(p_96535_2_);
        if (var4 == null) {
            var4 = new ArrayList();
            this.scoreObjectiveCriterias.put(p_96535_2_, var4);
        }
        ((List)var4).add(var3);
        this.scoreObjectives.put(p_96535_1_, var3);
        this.func_96522_a(var3);
        return var3;
    }
    
    public Collection func_96520_a(final IScoreObjectiveCriteria p_96520_1_) {
        final Collection var2 = this.scoreObjectiveCriterias.get(p_96520_1_);
        return (var2 == null) ? new ArrayList() : new ArrayList(var2);
    }
    
    public Score func_96529_a(final String p_96529_1_, final ScoreObjective p_96529_2_) {
        Object var3 = this.field_96544_c.get(p_96529_1_);
        if (var3 == null) {
            var3 = new HashMap();
            this.field_96544_c.put(p_96529_1_, var3);
        }
        Score var4 = ((Map)var3).get(p_96529_2_);
        if (var4 == null) {
            var4 = new Score(this, p_96529_2_, p_96529_1_);
            ((Map)var3).put(p_96529_2_, var4);
        }
        return var4;
    }
    
    public Collection func_96534_i(final ScoreObjective p_96534_1_) {
        final ArrayList var2 = new ArrayList();
        for (final Map var4 : this.field_96544_c.values()) {
            final Score var5 = var4.get(p_96534_1_);
            if (var5 != null) {
                var2.add(var5);
            }
        }
        Collections.sort((List<Object>)var2, Score.field_96658_a);
        return var2;
    }
    
    public Collection getScoreObjectives() {
        return this.scoreObjectives.values();
    }
    
    public Collection getObjectiveNames() {
        return this.field_96544_c.keySet();
    }
    
    public void func_96515_c(final String p_96515_1_) {
        final Map var2 = this.field_96544_c.remove(p_96515_1_);
        if (var2 != null) {
            this.func_96516_a(p_96515_1_);
        }
    }
    
    public Collection func_96528_e() {
        final Collection var1 = this.field_96544_c.values();
        final ArrayList var2 = new ArrayList();
        for (final Map var4 : var1) {
            var2.addAll(var4.values());
        }
        return var2;
    }
    
    public Map func_96510_d(final String p_96510_1_) {
        Object var2 = this.field_96544_c.get(p_96510_1_);
        if (var2 == null) {
            var2 = new HashMap();
        }
        return (Map)var2;
    }
    
    public void func_96519_k(final ScoreObjective p_96519_1_) {
        this.scoreObjectives.remove(p_96519_1_.getName());
        for (int var2 = 0; var2 < 3; ++var2) {
            if (this.func_96539_a(var2) == p_96519_1_) {
                this.func_96530_a(var2, null);
            }
        }
        final List var3 = this.scoreObjectiveCriterias.get(p_96519_1_.getCriteria());
        if (var3 != null) {
            var3.remove(p_96519_1_);
        }
        for (final Map var5 : this.field_96544_c.values()) {
            var5.remove(p_96519_1_);
        }
        this.func_96533_c(p_96519_1_);
    }
    
    public void func_96530_a(final int p_96530_1_, final ScoreObjective p_96530_2_) {
        this.field_96541_d[p_96530_1_] = p_96530_2_;
    }
    
    public ScoreObjective func_96539_a(final int p_96539_1_) {
        return this.field_96541_d[p_96539_1_];
    }
    
    public ScorePlayerTeam getTeam(final String p_96508_1_) {
        return this.teams.get(p_96508_1_);
    }
    
    public ScorePlayerTeam createTeam(final String p_96527_1_) {
        ScorePlayerTeam var2 = this.getTeam(p_96527_1_);
        if (var2 != null) {
            throw new IllegalArgumentException("A team with the name '" + p_96527_1_ + "' already exists!");
        }
        var2 = new ScorePlayerTeam(this, p_96527_1_);
        this.teams.put(p_96527_1_, var2);
        this.func_96523_a(var2);
        return var2;
    }
    
    public void removeTeam(final ScorePlayerTeam p_96511_1_) {
        this.teams.remove(p_96511_1_.getRegisteredName());
        for (final String var3 : p_96511_1_.getMembershipCollection()) {
            this.teamMemberships.remove(var3);
        }
        this.func_96513_c(p_96511_1_);
    }
    
    public boolean func_151392_a(final String p_151392_1_, final String p_151392_2_) {
        if (!this.teams.containsKey(p_151392_2_)) {
            return false;
        }
        final ScorePlayerTeam var3 = this.getTeam(p_151392_2_);
        if (this.getPlayersTeam(p_151392_1_) != null) {
            this.func_96524_g(p_151392_1_);
        }
        this.teamMemberships.put(p_151392_1_, var3);
        var3.getMembershipCollection().add(p_151392_1_);
        return true;
    }
    
    public boolean func_96524_g(final String p_96524_1_) {
        final ScorePlayerTeam var2 = this.getPlayersTeam(p_96524_1_);
        if (var2 != null) {
            this.removePlayerFromTeam(p_96524_1_, var2);
            return true;
        }
        return false;
    }
    
    public void removePlayerFromTeam(final String p_96512_1_, final ScorePlayerTeam p_96512_2_) {
        if (this.getPlayersTeam(p_96512_1_) != p_96512_2_) {
            throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_96512_2_.getRegisteredName() + "'.");
        }
        this.teamMemberships.remove(p_96512_1_);
        p_96512_2_.getMembershipCollection().remove(p_96512_1_);
    }
    
    public Collection getTeamNames() {
        return this.teams.keySet();
    }
    
    public Collection getTeams() {
        return this.teams.values();
    }
    
    public ScorePlayerTeam getPlayersTeam(final String p_96509_1_) {
        return this.teamMemberships.get(p_96509_1_);
    }
    
    public void func_96522_a(final ScoreObjective p_96522_1_) {
    }
    
    public void func_96532_b(final ScoreObjective p_96532_1_) {
    }
    
    public void func_96533_c(final ScoreObjective p_96533_1_) {
    }
    
    public void func_96536_a(final Score p_96536_1_) {
    }
    
    public void func_96516_a(final String p_96516_1_) {
    }
    
    public void func_96523_a(final ScorePlayerTeam p_96523_1_) {
    }
    
    public void func_96538_b(final ScorePlayerTeam p_96538_1_) {
    }
    
    public void func_96513_c(final ScorePlayerTeam p_96513_1_) {
    }
    
    public static String getObjectiveDisplaySlot(final int p_96517_0_) {
        switch (p_96517_0_) {
            case 0: {
                return "list";
            }
            case 1: {
                return "sidebar";
            }
            case 2: {
                return "belowName";
            }
            default: {
                return null;
            }
        }
    }
    
    public static int getObjectiveDisplaySlotNumber(final String p_96537_0_) {
        return p_96537_0_.equalsIgnoreCase("list") ? 0 : (p_96537_0_.equalsIgnoreCase("sidebar") ? 1 : (p_96537_0_.equalsIgnoreCase("belowName") ? 2 : -1));
    }
}
