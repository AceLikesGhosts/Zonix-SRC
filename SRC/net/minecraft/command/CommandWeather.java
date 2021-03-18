package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.world.storage.*;
import java.util.*;

public class CommandWeather extends CommandBase
{
    private static final String __OBFID = "CL_00001185";
    
    @Override
    public String getCommandName() {
        return "weather";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.weather.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 1 && p_71515_2_.length <= 2) {
            int var3 = (300 + new Random().nextInt(600)) * 20;
            if (p_71515_2_.length >= 2) {
                var3 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[1], 1, 1000000) * 20;
            }
            final WorldServer var4 = MinecraftServer.getServer().worldServers[0];
            final WorldInfo var5 = var4.getWorldInfo();
            if ("clear".equalsIgnoreCase(p_71515_2_[0])) {
                var5.setRainTime(0);
                var5.setThunderTime(0);
                var5.setRaining(false);
                var5.setThundering(false);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.weather.clear", new Object[0]);
            }
            else if ("rain".equalsIgnoreCase(p_71515_2_[0])) {
                var5.setRainTime(var3);
                var5.setRaining(true);
                var5.setThundering(false);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.weather.rain", new Object[0]);
            }
            else {
                if (!"thunder".equalsIgnoreCase(p_71515_2_[0])) {
                    throw new WrongUsageException("commands.weather.usage", new Object[0]);
                }
                var5.setRainTime(var3);
                var5.setThunderTime(var3);
                var5.setRaining(true);
                var5.setThundering(true);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.weather.thunder", new Object[0]);
            }
            return;
        }
        throw new WrongUsageException("commands.weather.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "clear", "rain", "thunder") : null;
    }
}
