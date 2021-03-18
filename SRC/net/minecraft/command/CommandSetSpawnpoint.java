package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandSetSpawnpoint extends CommandBase
{
    private static final String __OBFID = "CL_00001026";
    
    @Override
    public String getCommandName() {
        return "spawnpoint";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.spawnpoint.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        final EntityPlayerMP var3 = (p_71515_2_.length == 0) ? CommandBase.getCommandSenderAsPlayer(p_71515_1_) : CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        if (p_71515_2_.length == 4) {
            if (var3.worldObj != null) {
                final byte var4 = 1;
                final int var5 = 30000000;
                int var6 = var4 + 1;
                final int var7 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var4], -var5, var5);
                final int var8 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var6++], 0, 256);
                final int var9 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[var6++], -var5, var5);
                var3.setSpawnChunk(new ChunkCoordinates(var7, var8, var9), true);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.spawnpoint.success", var3.getCommandSenderName(), var7, var8, var9);
            }
        }
        else {
            if (p_71515_2_.length > 1) {
                throw new WrongUsageException("commands.spawnpoint.usage", new Object[0]);
            }
            final ChunkCoordinates var10 = var3.getPlayerCoordinates();
            var3.setSpawnChunk(var10, true);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.spawnpoint.success", var3.getCommandSenderName(), var10.posX, var10.posY, var10.posZ);
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length != 1 && p_71516_2_.length != 2) ? null : CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, MinecraftServer.getServer().getAllUsernames());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
