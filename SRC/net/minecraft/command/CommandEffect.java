package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.player.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandEffect extends CommandBase
{
    private static final String __OBFID = "CL_00000323";
    
    @Override
    public String getCommandName() {
        return "effect";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.effect.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException("commands.effect.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        if (p_71515_2_[1].equals("clear")) {
            if (var3.getActivePotionEffects().isEmpty()) {
                throw new CommandException("commands.effect.failure.notActive.all", new Object[] { var3.getCommandSenderName() });
            }
            var3.clearActivePotions();
            CommandBase.func_152373_a(p_71515_1_, this, "commands.effect.success.removed.all", var3.getCommandSenderName());
        }
        else {
            final int var4 = CommandBase.parseIntWithMin(p_71515_1_, p_71515_2_[1], 1);
            int var5 = 600;
            int var6 = 30;
            int var7 = 0;
            if (var4 < 0 || var4 >= Potion.potionTypes.length || Potion.potionTypes[var4] == null) {
                throw new NumberInvalidException("commands.effect.notFound", new Object[] { var4 });
            }
            if (p_71515_2_.length >= 3) {
                var6 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[2], 0, 1000000);
                if (Potion.potionTypes[var4].isInstant()) {
                    var5 = var6;
                }
                else {
                    var5 = var6 * 20;
                }
            }
            else if (Potion.potionTypes[var4].isInstant()) {
                var5 = 1;
            }
            if (p_71515_2_.length >= 4) {
                var7 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[3], 0, 255);
            }
            if (var6 == 0) {
                if (!var3.isPotionActive(var4)) {
                    throw new CommandException("commands.effect.failure.notActive", new Object[] { new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName() });
                }
                var3.removePotionEffect(var4);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.effect.success.removed", new ChatComponentTranslation(Potion.potionTypes[var4].getName(), new Object[0]), var3.getCommandSenderName());
            }
            else {
                final PotionEffect var8 = new PotionEffect(var4, var5, var7);
                var3.addPotionEffect(var8);
                CommandBase.func_152373_a(p_71515_1_, this, "commands.effect.success", new ChatComponentTranslation(var8.getEffectName(), new Object[0]), var4, var7, var3.getCommandSenderName(), var6);
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getAllUsernames()) : null;
    }
    
    protected String[] getAllUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
