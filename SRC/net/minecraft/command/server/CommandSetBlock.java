package net.minecraft.command.server;

import net.minecraft.util.*;
import net.minecraft.command.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandSetBlock extends CommandBase
{
    private static final String __OBFID = "CL_00000949";
    
    @Override
    public String getCommandName() {
        return "setblock";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.setblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 4) {
            throw new WrongUsageException("commands.setblock.usage", new Object[0]);
        }
        int var3 = p_71515_1_.getPlayerCoordinates().posX;
        int var4 = p_71515_1_.getPlayerCoordinates().posY;
        int var5 = p_71515_1_.getPlayerCoordinates().posZ;
        var3 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var3, p_71515_2_[0]));
        var4 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var4, p_71515_2_[1]));
        var5 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var5, p_71515_2_[2]));
        final Block var6 = CommandBase.getBlockByText(p_71515_1_, p_71515_2_[3]);
        int var7 = 0;
        if (p_71515_2_.length >= 5) {
            var7 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[4], 0, 15);
        }
        final World var8 = p_71515_1_.getEntityWorld();
        if (!var8.blockExists(var3, var4, var5)) {
            throw new CommandException("commands.setblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound var9 = new NBTTagCompound();
        boolean var10 = false;
        if (p_71515_2_.length >= 7 && var6.hasTileEntity()) {
            final String var11 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 6).getUnformattedText();
            try {
                final NBTBase var12 = JsonToNBT.func_150315_a(var11);
                if (!(var12 instanceof NBTTagCompound)) {
                    throw new CommandException("commands.setblock.tagError", new Object[] { "Not a valid tag" });
                }
                var9 = (NBTTagCompound)var12;
                var10 = true;
            }
            catch (NBTException var13) {
                throw new CommandException("commands.setblock.tagError", new Object[] { var13.getMessage() });
            }
        }
        if (p_71515_2_.length >= 6) {
            if (p_71515_2_[5].equals("destroy")) {
                var8.func_147480_a(var3, var4, var5, true);
            }
            else if (p_71515_2_[5].equals("keep") && !var8.isAirBlock(var3, var4, var5)) {
                throw new CommandException("commands.setblock.noChange", new Object[0]);
            }
        }
        if (!var8.setBlock(var3, var4, var5, var6, var7, 3)) {
            throw new CommandException("commands.setblock.noChange", new Object[0]);
        }
        if (var10) {
            final TileEntity var14 = var8.getTileEntity(var3, var4, var5);
            if (var14 != null) {
                var9.setInteger("x", var3);
                var9.setInteger("y", var4);
                var9.setInteger("z", var5);
                var14.readFromNBT(var9);
            }
        }
        CommandBase.func_152373_a(p_71515_1_, this, "commands.setblock.success", new Object[0]);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 4) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, Block.blockRegistry.getKeys()) : ((p_71516_2_.length == 6) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, "replace", "destroy", "keep") : null);
    }
}
