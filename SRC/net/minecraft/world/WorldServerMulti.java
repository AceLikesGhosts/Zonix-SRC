package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.profiler.*;
import net.minecraft.world.storage.*;

public class WorldServerMulti extends WorldServer
{
    private static final String __OBFID = "CL_00001430";
    
    public WorldServerMulti(final MinecraftServer p_i45283_1_, final ISaveHandler p_i45283_2_, final String p_i45283_3_, final int p_i45283_4_, final WorldSettings p_i45283_5_, final WorldServer p_i45283_6_, final Profiler p_i45283_7_) {
        super(p_i45283_1_, p_i45283_2_, p_i45283_3_, p_i45283_4_, p_i45283_5_, p_i45283_7_);
        this.mapStorage = p_i45283_6_.mapStorage;
        this.worldScoreboard = p_i45283_6_.getScoreboard();
        this.worldInfo = new DerivedWorldInfo(p_i45283_6_.getWorldInfo());
    }
    
    @Override
    protected void saveLevel() throws MinecraftException {
    }
}
