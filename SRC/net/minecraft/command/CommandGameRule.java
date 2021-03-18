package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandGameRule extends CommandBase
{
    private static final String __OBFID = "CL_00000475";
    
    @Override
    public String getCommandName() {
        return "gamerule";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.gamerule.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length == 2) {
            final String var6 = p_71515_2_[0];
            final String var7 = p_71515_2_[1];
            final GameRules var8 = this.getGameRules();
            if (var8.hasRule(var6)) {
                var8.setOrCreateGameRule(var6, var7);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.gamerule.success", new Object[0]);
            }
            else {
                CommandBase.func_152373_a(p_71515_1_, this, "commands.gamerule.norule", var6);
            }
        }
        else if (p_71515_2_.length == 1) {
            final String var6 = p_71515_2_[0];
            final GameRules var9 = this.getGameRules();
            if (var9.hasRule(var6)) {
                final String var10 = var9.getGameRuleStringValue(var6);
                p_71515_1_.addChatMessage(new ChatComponentText(var6).appendText(" = ").appendText(var10));
            }
            else {
                CommandBase.func_152373_a(p_71515_1_, this, "commands.gamerule.norule", var6);
            }
        }
        else {
            if (p_71515_2_.length != 0) {
                throw new WrongUsageException("commands.gamerule.usage", new Object[0]);
            }
            final GameRules var11 = this.getGameRules();
            p_71515_1_.addChatMessage(new ChatComponentText(CommandBase.joinNiceString(var11.getRules())));
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getGameRules().getRules()) : ((p_71516_2_.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "true", "false") : null);
    }
    
    private GameRules getGameRules() {
        return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
    }
}
