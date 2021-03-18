package net.minecraft.command.server;

import net.minecraft.command.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import java.util.*;

public class CommandListBans extends CommandBase
{
    private static final String __OBFID = "CL_00000596";
    
    @Override
    public String getCommandName() {
        return "banlist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return (MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152689_b() || MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152689_b()) && super.canCommandSenderUseCommand(p_71519_1_);
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.banlist.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 1 && p_71515_2_[0].equalsIgnoreCase("ips")) {
            p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.banlist.ips", new Object[] { MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152685_a().length }));
            p_71515_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152685_a())));
        }
        else {
            p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.banlist.players", new Object[] { MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152685_a().length }));
            p_71515_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152685_a())));
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "players", "ips") : null;
    }
}
