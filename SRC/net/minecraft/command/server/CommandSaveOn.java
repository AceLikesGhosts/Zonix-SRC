package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import net.minecraft.world.*;

public class CommandSaveOn extends CommandBase
{
    private static final String __OBFID = "CL_00000873";
    
    @Override
    public String getCommandName() {
        return "save-on";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.save-on.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final MinecraftServer var3 = MinecraftServer.getServer();
        boolean var4 = false;
        for (int var5 = 0; var5 < var3.worldServers.length; ++var5) {
            if (var3.worldServers[var5] != null) {
                final WorldServer var6 = var3.worldServers[var5];
                if (var6.levelSaving) {
                    var6.levelSaving = false;
                    var4 = true;
                }
            }
        }
        if (var4) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.save.enabled", new Object[0]);
            return;
        }
        throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
    }
}
