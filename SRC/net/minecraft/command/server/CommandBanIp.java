package net.minecraft.command.server;

import net.minecraft.server.*;
import java.util.regex.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.management.*;
import net.minecraft.command.*;
import java.util.*;

public class CommandBanIp extends CommandBase
{
    public static final Pattern field_147211_a;
    private static final String __OBFID = "CL_00000139";
    
    @Override
    public String getCommandName() {
        return "ban-ip";
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
        return "commands.banip.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 1 && p_71515_2_[0].length() > 1) {
            final Matcher var3 = CommandBanIp.field_147211_a.matcher(p_71515_2_[0]);
            IChatComponent var4 = null;
            if (p_71515_2_.length >= 2) {
                var4 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 1);
            }
            if (var3.matches()) {
                this.func_147210_a(p_71515_1_, p_71515_2_[0], (var4 == null) ? null : var4.getUnformattedText());
            }
            else {
                final EntityPlayerMP var5 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(p_71515_2_[0]);
                if (var5 == null) {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }
                this.func_147210_a(p_71515_1_, var5.getPlayerIP(), (var4 == null) ? null : var4.getUnformattedText());
            }
            return;
        }
        throw new WrongUsageException("commands.banip.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
    }
    
    protected void func_147210_a(final ICommandSender p_147210_1_, final String p_147210_2_, final String p_147210_3_) {
        final IPBanEntry var4 = new IPBanEntry(p_147210_2_, null, p_147210_1_.getCommandSenderName(), null, p_147210_3_);
        MinecraftServer.getServer().getConfigurationManager().getBannedIPs().func_152687_a(var4);
        final List var5 = MinecraftServer.getServer().getConfigurationManager().getPlayerList(p_147210_2_);
        final String[] var6 = new String[var5.size()];
        int var7 = 0;
        for (final EntityPlayerMP var9 : var5) {
            var9.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
            var6[var7++] = var9.getCommandSenderName();
        }
        if (var5.isEmpty()) {
            CommandBase.func_152373_a(p_147210_1_, this, "commands.banip.success", p_147210_2_);
        }
        else {
            CommandBase.func_152373_a(p_147210_1_, this, "commands.banip.success.players", p_147210_2_, CommandBase.joinNiceString(var6));
        }
    }
    
    static {
        field_147211_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    }
}
