package net.minecraft.command;

import net.minecraft.server.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandServerKick extends CommandBase
{
    private static final String __OBFID = "CL_00000550";
    
    @Override
    public String getCommandName() {
        return "kick";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.kick.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length <= 0 || p_71515_2_[0].length() <= 1) {
            throw new WrongUsageException("commands.kick.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(p_71515_2_[0]);
        String var4 = "Kicked by an operator.";
        boolean var5 = false;
        if (var3 == null) {
            throw new PlayerNotFoundException();
        }
        if (p_71515_2_.length >= 2) {
            var4 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 1).getUnformattedText();
            var5 = true;
        }
        var3.playerNetServerHandler.kickPlayerFromServer(var4);
        if (var5) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.kick.success.reason", var3.getCommandSenderName(), var4);
        }
        else {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.kick.success", var3.getCommandSenderName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
