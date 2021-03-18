package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class CommandBanPlayer extends CommandBase
{
    private static final String __OBFID = "CL_00000165";
    
    @Override
    public String getCommandName() {
        return "ban";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.ban.usage";
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender p_71519_1_) {
        return MinecraftServer.getServer().getConfigurationManager().func_152608_h().func_152689_b() && super.canCommandSenderUseCommand(p_71519_1_);
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 1 || p_71515_2_[0].length() <= 0) {
            throw new WrongUsageException("commands.ban.usage", new Object[0]);
        }
        final MinecraftServer var3 = MinecraftServer.getServer();
        final GameProfile var4 = var3.func_152358_ax().func_152655_a(p_71515_2_[0]);
        if (var4 == null) {
            throw new CommandException("commands.ban.failed", new Object[] { p_71515_2_[0] });
        }
        String var5 = null;
        if (p_71515_2_.length >= 2) {
            var5 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 1).getUnformattedText();
        }
        final UserListBansEntry var6 = new UserListBansEntry(var4, null, p_71515_1_.getCommandSenderName(), null, var5);
        var3.getConfigurationManager().func_152608_h().func_152687_a(var6);
        final EntityPlayerMP var7 = var3.getConfigurationManager().func_152612_a(p_71515_2_[0]);
        if (var7 != null) {
            var7.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
        }
        CommandBase.func_152373_a(p_71515_1_, this, "commands.ban.success", p_71515_2_[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length >= 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
}
