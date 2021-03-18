package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandEmote extends CommandBase
{
    private static final String __OBFID = "CL_00000351";
    
    @Override
    public String getCommandName() {
        return "me";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.me.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 0) {
            final IChatComponent var3 = CommandBase.func_147176_a(p_71515_1_, p_71515_2_, 0, p_71515_1_.canCommandSenderUseCommand(1, "me"));
            MinecraftServer.getServer().getConfigurationManager().func_148539_a(new ChatComponentTranslation("chat.type.emote", new Object[] { p_71515_1_.func_145748_c_(), var3 }));
            return;
        }
        throw new WrongUsageException("commands.me.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
    }
}
