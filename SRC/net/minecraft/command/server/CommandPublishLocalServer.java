package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.world.*;
import net.minecraft.command.*;

public class CommandPublishLocalServer extends CommandBase
{
    private static final String __OBFID = "CL_00000799";
    
    @Override
    public String getCommandName() {
        return "publish";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.publish.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final String var3 = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
        if (var3 != null) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.publish.started", var3);
        }
        else {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.publish.failed", new Object[0]);
        }
    }
}
