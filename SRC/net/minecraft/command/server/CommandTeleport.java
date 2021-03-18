package net.minecraft.command.server;

import net.minecraft.command.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandTeleport extends CommandBase
{
    private static final String __OBFID = "CL_00001180";
    
    @Override
    public String getCommandName() {
        return "tp";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.tp.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 1) {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
        EntityPlayerMP var3;
        if (p_71515_2_.length != 2 && p_71515_2_.length != 4) {
            var3 = CommandBase.getCommandSenderAsPlayer(p_71515_1_);
        }
        else {
            var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
            if (var3 == null) {
                throw new PlayerNotFoundException();
            }
        }
        if (p_71515_2_.length != 3 && p_71515_2_.length != 4) {
            if (p_71515_2_.length == 1 || p_71515_2_.length == 2) {
                final EntityPlayerMP var4 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[p_71515_2_.length - 1]);
                if (var4 == null) {
                    throw new PlayerNotFoundException();
                }
                if (var4.worldObj != var3.worldObj) {
                    CommandBase.func_152373_a(p_71515_1_, this, "commands.tp.notSameDimension", new Object[0]);
                    return;
                }
                var3.mountEntity(null);
                var3.playerNetServerHandler.setPlayerLocation(var4.posX, var4.posY, var4.posZ, var4.rotationYaw, var4.rotationPitch);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.tp.success", var3.getCommandSenderName(), var4.getCommandSenderName());
            }
        }
        else if (var3.worldObj != null) {
            int var5 = p_71515_2_.length - 3;
            final double var6 = CommandBase.func_110666_a(p_71515_1_, var3.posX, p_71515_2_[var5++]);
            final double var7 = CommandBase.func_110665_a(p_71515_1_, var3.posY, p_71515_2_[var5++], 0, 0);
            final double var8 = CommandBase.func_110666_a(p_71515_1_, var3.posZ, p_71515_2_[var5++]);
            var3.mountEntity(null);
            var3.setPositionAndUpdate(var6, var7, var8);
            CommandBase.func_152373_a(p_71515_1_, this, "commands.tp.success.coordinates", var3.getCommandSenderName(), var6, var7, var8);
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
