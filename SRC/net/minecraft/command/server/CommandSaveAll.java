package net.minecraft.command.server;

import net.minecraft.server.*;
import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.world.*;

public class CommandSaveAll extends CommandBase
{
    private static final String __OBFID = "CL_00000826";
    
    @Override
    public String getCommandName() {
        return "save-all";
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.save.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final MinecraftServer var3 = MinecraftServer.getServer();
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.save.start", new Object[0]));
        if (var3.getConfigurationManager() != null) {
            var3.getConfigurationManager().saveAllPlayerData();
        }
        try {
            for (int var4 = 0; var4 < var3.worldServers.length; ++var4) {
                if (var3.worldServers[var4] != null) {
                    final WorldServer var5 = var3.worldServers[var4];
                    final boolean var6 = var5.levelSaving;
                    var5.levelSaving = false;
                    var5.saveAllChunks(true, null);
                    var5.levelSaving = var6;
                }
            }
            if (p_71515_2_.length > 0 && "flush".equals(p_71515_2_[0])) {
                p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.save.flushStart", new Object[0]));
                for (int var4 = 0; var4 < var3.worldServers.length; ++var4) {
                    if (var3.worldServers[var4] != null) {
                        final WorldServer var5 = var3.worldServers[var4];
                        final boolean var6 = var5.levelSaving;
                        var5.levelSaving = false;
                        var5.saveChunkData();
                        var5.levelSaving = var6;
                    }
                }
                p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (MinecraftException var7) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.save.failed", var7.getMessage());
            return;
        }
        CommandBase.func_152373_a(p_71515_1_, this, "commands.save.success", new Object[0]);
    }
}
