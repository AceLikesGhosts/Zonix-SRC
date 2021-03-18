package net.minecraft.world.demo;

import net.minecraft.server.*;
import net.minecraft.world.storage.*;
import net.minecraft.profiler.*;
import net.minecraft.world.*;

public class DemoWorldServer extends WorldServer
{
    private static final long demoWorldSeed;
    public static final WorldSettings demoWorldSettings;
    private static final String __OBFID = "CL_00001428";
    
    public DemoWorldServer(final MinecraftServer p_i45282_1_, final ISaveHandler p_i45282_2_, final String p_i45282_3_, final int p_i45282_4_, final Profiler p_i45282_5_) {
        super(p_i45282_1_, p_i45282_2_, p_i45282_3_, p_i45282_4_, DemoWorldServer.demoWorldSettings, p_i45282_5_);
    }
    
    static {
        demoWorldSeed = "North Carolina".hashCode();
        demoWorldSettings = new WorldSettings(DemoWorldServer.demoWorldSeed, WorldSettings.GameType.SURVIVAL, true, false, WorldType.DEFAULT).enableBonusChest();
    }
}
