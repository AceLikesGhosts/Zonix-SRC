package net.minecraft.command.server;

import net.minecraft.stats.*;
import com.google.common.collect.*;
import net.minecraft.command.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandAchievement extends CommandBase
{
    private static final String __OBFID = "CL_00000113";
    
    @Override
    public String getCommandName() {
        return "achievement";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.achievement.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length >= 2) {
            final StatBase var3 = StatList.func_151177_a(p_71515_2_[1]);
            if (var3 == null && !p_71515_2_[1].equals("*")) {
                throw new CommandException("commands.achievement.unknownAchievement", new Object[] { p_71515_2_[1] });
            }
            EntityPlayerMP var4;
            if (p_71515_2_.length >= 3) {
                var4 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[2]);
            }
            else {
                var4 = CommandBase.getCommandSenderAsPlayer(p_71515_1_);
            }
            if (p_71515_2_[0].equalsIgnoreCase("give")) {
                if (var3 == null) {
                    for (final Achievement var6 : AchievementList.achievementList) {
                        var4.triggerAchievement(var6);
                    }
                    CommandBase.func_152373_a(p_71515_1_, this, "commands.achievement.give.success.all", var4.getCommandSenderName());
                }
                else {
                    if (var3 instanceof Achievement) {
                        Achievement var7 = (Achievement)var3;
                        final ArrayList var8 = Lists.newArrayList();
                        while (var7.parentAchievement != null && !var4.func_147099_x().hasAchievementUnlocked(var7.parentAchievement)) {
                            var8.add(var7.parentAchievement);
                            var7 = var7.parentAchievement;
                        }
                        for (final Achievement var10 : Lists.reverse((List)var8)) {
                            var4.triggerAchievement(var10);
                        }
                    }
                    var4.triggerAchievement(var3);
                    CommandBase.func_152373_a(p_71515_1_, this, "commands.achievement.give.success.one", var4.getCommandSenderName(), var3.func_150955_j());
                }
                return;
            }
        }
        throw new WrongUsageException("commands.achievement.usage", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        if (p_71516_2_.length == 1) {
            return CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "give");
        }
        if (p_71516_2_.length != 2) {
            return (p_71516_2_.length == 3) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames()) : null;
        }
        final ArrayList var3 = Lists.newArrayList();
        for (final StatBase var5 : StatList.allStats) {
            var3.add(var5.statId);
        }
        return CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, var3);
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 2;
    }
}
