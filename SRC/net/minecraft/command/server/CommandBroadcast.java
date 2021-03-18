package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandBroadcast extends CommandBase
{
    private static final String __OBFID = "CL_00000191";
    
    @Override
    public String getCommandName() {
        return "say";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.say.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 0 && p_71515_2_[0].length() > 0) {
            final IChatComponent var3 = CommandBase.func_147176_a(p_71515_1_, p_71515_2_, 0, true);
            MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.announcement", new Object[] { p_71515_1_.getCommandSenderName(), var3 }));
            return;
        }
        throw new WrongUsageException("commands.say.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
