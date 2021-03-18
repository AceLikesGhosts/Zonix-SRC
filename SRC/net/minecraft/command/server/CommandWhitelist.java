package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import com.mojang.authlib.*;
import java.util.*;

public class CommandWhitelist extends CommandBase
{
    private static final String __OBFID = "CL_00001186";
    
    @Override
    public String getCommandName() {
        return "whitelist";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 3;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.whitelist.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 1) {
            final MinecraftServer var3 = MinecraftServer.getServer();
            if (p_71515_2_[0].equals("on")) {
                var3.getConfigurationManager().setWhiteListEnabled(true);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.whitelist.enabled", new Object[0]);
                return;
            }
            if (p_71515_2_[0].equals("off")) {
                var3.getConfigurationManager().setWhiteListEnabled(false);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.whitelist.disabled", new Object[0]);
                return;
            }
            if (p_71515_2_[0].equals("list")) {
                p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.whitelist.list", new Object[] { var3.getConfigurationManager().func_152598_l().length, var3.getConfigurationManager().getAvailablePlayerDat().length }));
                final String[] var4 = var3.getConfigurationManager().func_152598_l();
                p_71515_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(var4)));
                return;
            }
            if (p_71515_2_[0].equals("add")) {
                if (p_71515_2_.length < 2) {
                    throw new WrongUsageException("commands.whitelist.add.usage", new Object[0]);
                }
                final GameProfile var5 = var3.func_152358_ax().func_152655_a(p_71515_2_[1]);
                if (var5 == null) {
                    throw new CommandException("commands.whitelist.add.failed", new Object[] { p_71515_2_[1] });
                }
                var3.getConfigurationManager().func_152601_d(var5);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.whitelist.add.success", p_71515_2_[1]);
                return;
            }
            else if (p_71515_2_[0].equals("remove")) {
                if (p_71515_2_.length < 2) {
                    throw new WrongUsageException("commands.whitelist.remove.usage", new Object[0]);
                }
                final GameProfile var5 = var3.getConfigurationManager().func_152599_k().func_152706_a(p_71515_2_[1]);
                if (var5 == null) {
                    throw new CommandException("commands.whitelist.remove.failed", new Object[] { p_71515_2_[1] });
                }
                var3.getConfigurationManager().func_152597_c(var5);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.whitelist.remove.success", p_71515_2_[1]);
                return;
            }
            else if (p_71515_2_[0].equals("reload")) {
                var3.getConfigurationManager().loadWhiteList();
                CommandBase.func_152373_a(p_71515_1_, this, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }
        throw new WrongUsageException("commands.whitelist.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        if (p_71516_2_.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "on", "off", "list", "add", "remove", "reload");
        }
        if (p_71516_2_.length == 2) {
            if (p_71516_2_[0].equals("remove")) {
                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getConfigurationManager().func_152598_l());
            }
            if (p_71516_2_[0].equals("add")) {
                return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().func_152358_ax().func_152654_a());
            }
        }
        return null;
    }
}
