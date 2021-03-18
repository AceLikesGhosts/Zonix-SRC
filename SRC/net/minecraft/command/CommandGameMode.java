package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandGameMode extends CommandBase
{
    private static final String __OBFID = "CL_00000448";
    
    @Override
    public String getCommandName() {
        return "gamemode";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.gamemode.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length > 0) {
            final WorldSettings.GameType var3 = this.getGameModeFromCommand(p_71515_1_, p_71515_2_[0]);
            final EntityPlayerMP var4 = (p_71515_2_.length >= 2) ? CommandBase.getPlayer(p_71515_1_, p_71515_2_[1]) : CommandBase.getCommandSenderAsPlayer(p_71515_1_);
            var4.setGameType(var3);
            var4.fallDistance = 0.0f;
            final ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.getName(), new Object[0]);
            if (var4 != p_71515_1_) {
                CommandBase.func_152374_a(p_71515_1_, this, 1, "commands.gamemode.success.other", var4.getCommandSenderName(), var5);
            }
            else {
                CommandBase.func_152374_a(p_71515_1_, this, 1, "commands.gamemode.success.self", var5);
            }
            return;
        }
        throw new WrongUsageException("commands.gamemode.usage", new Object[0]);
    }
    
    protected WorldSettings.GameType getGameModeFromCommand(final ICommandSender p_71539_1_, final String p_71539_2_) {
        return (!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) && !p_71539_2_.equalsIgnoreCase("s")) ? ((!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) && !p_71539_2_.equalsIgnoreCase("c")) ? ((!p_71539_2_.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) && !p_71539_2_.equalsIgnoreCase("a")) ? WorldSettings.getGameTypeById(CommandBase.parseIntBounded(p_71539_1_, p_71539_2_, 0, WorldSettings.GameType.values().length - 2)) : WorldSettings.GameType.ADVENTURE) : WorldSettings.GameType.CREATIVE) : WorldSettings.GameType.SURVIVAL;
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "survival", "creative", "adventure") : ((p_71516_2_.length == 2) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getListOfPlayerUsernames()) : null);
    }
    
    protected String[] getListOfPlayerUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 1;
    }
}
