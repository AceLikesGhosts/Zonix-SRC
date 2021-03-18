package net.minecraft.scoreboard;

import java.util.*;

public interface IScoreObjectiveCriteria
{
    public static final Map field_96643_a = new HashMap();
    public static final IScoreObjectiveCriteria field_96641_b = new ScoreDummyCriteria("dummy");
    public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
    public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
    public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
    public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
    
    String func_96636_a();
    
    int func_96635_a(final List p0);
    
    boolean isReadOnly();
}
