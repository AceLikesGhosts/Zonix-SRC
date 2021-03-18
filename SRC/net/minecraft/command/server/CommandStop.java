package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;

public class CommandStop extends CommandBase
{
    private static final String __OBFID = "CL_00001132";
    
    @Override
    public String getCommandName() {
        return "stop";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.stop.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (MinecraftServer.getServer().worldServers != null) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.stop.start", new Object[0]);
        }
        MinecraftServer.getServer().initiateShutdown();
    }
}
