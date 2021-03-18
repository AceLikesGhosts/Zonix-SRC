package net.minecraft.command;

import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandEnchant extends CommandBase
{
    private static final String __OBFID = "CL_00000377";
    
    @Override
    public String getCommandName() {
        return "enchant";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.enchant.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException("commands.enchant.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        final int var4 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[1], 0, Enchantment.enchantmentsList.length - 1);
        int var5 = 1;
        final ItemStack var6 = var3.getCurrentEquippedItem();
        if (var6 == null) {
            throw new CommandException("commands.enchant.noItem", new Object[0]);
        }
        final Enchantment var7 = Enchantment.enchantmentsList[var4];
        if (var7 == null) {
            throw new NumberInvalidException("commands.enchant.notFound", new Object[] { var4 });
        }
        if (!var7.canApply(var6)) {
            throw new CommandException("commands.enchant.cantEnchant", new Object[0]);
        }
        if (p_71515_2_.length >= 3) {
            var5 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[2], var7.getMinLevel(), var7.getMaxLevel());
        }
        if (var6.hasTagCompound()) {
            final NBTTagList var8 = var6.getEnchantmentTagList();
            if (var8 != null) {
                for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
                    final short var10 = var8.getCompoundTagAt(var9).getShort("id");
                    if (Enchantment.enchantmentsList[var10] != null) {
                        final Enchantment var11 = Enchantment.enchantmentsList[var10];
                        if (!var11.canApplyTogether(var7)) {
                            throw new CommandException("commands.enchant.cantCombine", new Object[] { var7.getTranslatedName(var5), var11.getTranslatedName(var8.getCompoundTagAt(var9).getShort("lvl")) });
                        }
                    }
                }
            }
        }
        var6.addEnchantment(var7, var5);
        CommandBase.func_152373_a(p_71515_1_, this, "commands.enchant.success", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getListOfPlayers()) : null;
    }
    
    protected String[] getListOfPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
