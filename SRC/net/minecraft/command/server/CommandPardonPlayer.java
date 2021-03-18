package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import java.util.*;

public class CommandPardonPlayer extends CommandBase
{
    private static final String __OBFID = "CL_00000747";
    
    @Override
    public String getCommandName() {
        return "pardon";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.unban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152689_b() && super.canCommandSenderUseCommand(p_71519_1_);
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length != 1 || p_71515_2_[0].length() <= 0) {
            throw new WrongUsageException("commands.unban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.getConfigurationManager().func_152608_h().func_152703_a(p_71515_2_[0]);
        if (var4 == null) {
            throw new CommandException("commands.unban.failed", new Object[] { p_71515_2_[0] });
        }
        var3.getConfigurationManager().func_152608_h().func_152684_c(var4);
        CommandBase.func_152373_a(p_71515_1_, this, "commands.unban.success", p_71515_2_[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152685_a()) : null;
    }
}
