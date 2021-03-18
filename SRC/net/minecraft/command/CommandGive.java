package net.minecraft.command;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.server.*;

public class CommandGive extends CommandBase
{
    private static final String __OBFID = "CL_00000502";
    
    @Override
    public String getCommandName() {
        return "give";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.give.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 2) {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
        final EntityPlayerMP var3 = CommandBase.getPlayer(p_71515_1_, p_71515_2_[0]);
        final Item var4 = CommandBase.getItemByText(p_71515_1_, p_71515_2_[1]);
        int var5 = 1;
        int var6 = 0;
        if (p_71515_2_.length >= 3) {
            var5 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[2], 1, 64);
        }
        if (p_71515_2_.length >= 4) {
            var6 = CommandBase.parseInt(p_71515_1_, p_71515_2_[3]);
        }
        final ItemStack var7 = new ItemStack(var4, var5, var6);
        if (p_71515_2_.length >= 5) {
            final String var8 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 4).getUnformattedText();
            try {
                final NBTBase var9 = JsonToNBT.func_150315_a(var8);
                if (!(var9 instanceof NBTTagCompound)) {
                    CommandBase.func_152373_a(p_71515_1_, this, "commands.give.tagError", "Not a valid tag");
                    return;
                }
                var7.setTagCompound((NBTTagCompound)var9);
            }
            catch (NBTException var10) {
                CommandBase.func_152373_a(p_71515_1_, this, "commands.give.tagError", var10.getMessage());
                return;
            }
        }
        final EntityItem var11 = var3.dropPlayerItemWithRandomChoice(var7, false);
        var11.delayBeforeCanPickup = 0;
        var11.func_145797_a(var3.getCommandSenderName());
        CommandBase.func_152373_a(p_71515_1_, this, "commands.give.success", var7.func_151000_E(), var5, var3.getCommandSenderName());
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.getPlayers()) : ((p_71516_2_.length == 2) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, Item.itemRegistry.getKeys()) : null);
    }
    
    protected String[] getPlayers() {
        return MinecraftServer.getServer().getAllUsernames();
    }
    
    @Override
    public boolean isUsernameIndex(final String[] p_82358_1_, final int p_82358_2_) {
        return p_82358_2_ == 0;
    }
}
