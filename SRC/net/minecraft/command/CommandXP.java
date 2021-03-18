package net.minecraft.command;

import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandXP extends CommandBase
{
    private static final String __OBFID = "CL_00000398";
    
    @Override
    public String getCommandName() {
        return "xp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.xp.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length <= 0) {
            throw new WrongUsageException("commands.xp.usage", new Object[0]);
        }
        String var4 = p_71515_2_[0];
        final boolean var5 = var4.endsWith("l") || var4.endsWith("L");
        if (var5 && var4.length() > 1) {
            var4 = var4.substring(0, var4.length() - 1);
        }
        int var6 = CommandBase.parseInt(p_71515_1_, var4);
        final boolean var7 = var6 < 0;
        if (var7) {
            var6 *= -1;
        }
        EntityPlayerMP var8;
        if (p_71515_2_.length > 1) {
            var8 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[1]);
        }
        else {
            var8 = CommandBase.getCommandSenderAsPlayer(p_71515_1_);
        }
        if (var5) {
            if (var7) {
                var8.addExperienceLevel(-var6);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.xp.success.negative.levels", var6, var8.getCommandSenderName());
            }
            else {
                var8.addExperienceLevel(var6);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.xp.success.levels", var6, var8.getCommandSenderName());
            }
        }
        else {
            if (var7) {
                throw new WrongUsageException("commands.xp.failure.widthdrawXp", new Object[0]);
            }
            var8.addExperience(var6);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.xp.success", var6, var8.getCommandSenderName());
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getAllUsernames()) : null;
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 1;
    }
}
