package net.minecraft.command;

import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;

public class CommandPlaySound extends CommandBase
{
    private static final String __OBFID = "CL_00000774";
    
    @Override
    public String getCommandName() {
        return "playsound";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.playsound.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException(this.getCommandUsage(p_71515_1_), new Object[0]);
        }
        final byte var3 = 0;
        int var4 = var3 + 1;
        final String var5 = p_71515_2_[var3];
        final EntityPlayerMP var6 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[var4++]);
        double var7 = var6.getPlayerCoordinates().posX;
        double var8 = var6.getPlayerCoordinates().posY;
        double var9 = var6.getPlayerCoordinates().posZ;
        double var10 = 1.0;
        double var11 = 1.0;
        double var12 = 0.0;
        if (p_71515_2_.length > var4) {
            var7 = CommandBase.func_110666_a(p_71515_1_, var7, p_71515_2_[var4++]);
        }
        if (p_71515_2_.length > var4) {
            var8 = CommandBase.func_110665_a(p_71515_1_, var8, p_71515_2_[var4++], 0, 0);
        }
        if (p_71515_2_.length > var4) {
            var9 = CommandBase.func_110666_a(p_71515_1_, var9, p_71515_2_[var4++]);
        }
        if (p_71515_2_.length > var4) {
            var10 = CommandBase.parseDoubleBounded(p_71515_1_, p_71515_2_[var4++], 0.0, 3.4028234663852886E38);
        }
        if (p_71515_2_.length > var4) {
            var11 = CommandBase.parseDoubleBounded(p_71515_1_, p_71515_2_[var4++], 0.0, 2.0);
        }
        if (p_71515_2_.length > var4) {
            var12 = CommandBase.parseDoubleBounded(p_71515_1_, p_71515_2_[var4++], 0.0, 1.0);
        }
        final double var13 = (var10 > 1.0) ? (var10 * 16.0) : 16.0;
        final double var14 = var6.getDistance(var7, var8, var9);
        if (var14 > var13) {
            if (var12 <= 0.0) {
                throw new CommandException("commands.playsound.playerTooFar", new Object[] { var6.getCommandSenderName() });
            }
            final double var15 = var7 - var6.posX;
            final double var16 = var8 - var6.posY;
            final double var17 = var9 - var6.posZ;
            final double var18 = Math.sqrt(var15 * var15 + var16 * var16 + var17 * var17);
            double var19 = var6.posX;
            double var20 = var6.posY;
            double var21 = var6.posZ;
            if (var18 > 0.0) {
                var19 += var15 / var18 * 2.0;
                var20 += var16 / var18 * 2.0;
                var21 += var17 / var18 * 2.0;
            }
            var6.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var5, var19, var20, var21, (float)var12, (float)var11));
        }
        else {
            var6.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var5, var7, var8, var9, (float)var10, (float)var11));
        }
        CommandBase.func_152373_a(p_71515_1_, this, "commands.playsound.success", var5, var6.getCommandSenderName());
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 1;
    }
}
