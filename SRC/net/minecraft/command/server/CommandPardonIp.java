package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.command.*;
import java.util.regex.*;
import java.util.*;

public class CommandPardonIp extends CommandBase
{
    private static final String __OBFID = "CL_00000720";
    
    @Override
    public String getCommandName() {
        return "pardon-ip";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152689_b() && super.canCommandSenderUseCommand(p_71519_1_);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.unbanip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length != 1 || p_71515_2_[0].length() <= 1) {
            throw new WrongUsageException("commands.unbanip.usage", new Object[0]);
        }
        final Matcher var3 = CommandBanIp.field_147211_a.matcher(p_71515_2_[0]);
        if (var3.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152684_c(p_71515_2_[0]);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.unbanip.success", p_71515_2_[0]);
            return;
        }
        throw new SyntaxErrorException("commands.unbanip.invalid", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152685_a()) : null;
    }
}
