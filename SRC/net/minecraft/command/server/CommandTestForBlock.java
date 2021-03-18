package net.minecraft.command.server;

import net.minecraft.block.*;
import net.minecraft.command.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import java.util.*;

public class CommandTestForBlock extends CommandBase
{
    private static final String __OBFID = "CL_00001181";
    
    @Override
    public String getCommandName() {
        return "testforblock";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.testforblock.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 4) {
            throw new WrongUsageException("commands.testforblock.usage", new Object[0]);
        }
        int var3 = p_71515_1_.getPlayerCoordinates().posX;
        int var4 = p_71515_1_.getPlayerCoordinates().posY;
        int var5 = p_71515_1_.getPlayerCoordinates().posZ;
        var3 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var3, p_71515_2_[0]));
        var4 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var4, p_71515_2_[1]));
        var5 = MathHelper.floor_double(CommandBase.func_110666_a(p_71515_1_, var5, p_71515_2_[2]));
        final Block var6 = Block.getBlockFromName(p_71515_2_[3]);
        if (var6 == null) {
            throw new NumberInvalidException("commands.setblock.notFound", new Object[] { p_71515_2_[3] });
        }
        int var7 = -1;
        if (p_71515_2_.length >= 5) {
            var7 = CommandBase.parseIntBounded(p_71515_1_, p_71515_2_[4], -1, 15);
        }
        final World var8 = p_71515_1_.getEntityWorld();
        if (!var8.blockExists(var3, var4, var5)) {
            throw new CommandException("commands.testforblock.outOfWorld", new Object[0]);
        }
        NBTTagCompound var9 = new NBTTagCompound();
        boolean var10 = false;
        if (p_71515_2_.length >= 6 && var6.hasTileEntity()) {
            final String var11 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 5).getUnformattedText();
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
        final Block var14 = var8.getBlock(var3, var4, var5);
        if (var14 != var6) {
            throw new CommandException("commands.testforblock.failed.tile", new Object[] { var3, var4, var5, var14.getLocalizedName(), var6.getLocalizedName() });
        }
        if (var7 > -1) {
            final int var15 = var8.getBlockMetadata(var3, var4, var5);
            if (var15 != var7) {
                throw new CommandException("commands.testforblock.failed.data", new Object[] { var3, var4, var5, var15, var7 });
            }
        }
        if (var10) {
            final TileEntity var16 = var8.getTileEntity(var3, var4, var5);
            if (var16 == null) {
                throw new CommandException("commands.testforblock.failed.tileEntity", new Object[] { var3, var4, var5 });
            }
            final NBTTagCompound var17 = new NBTTagCompound();
            var16.writeToNBT(var17);
            if (!this.func_147181_a(var9, var17)) {
                throw new CommandException("commands.testforblock.failed.nbt", new Object[] { var3, var4, var5 });
            }
        }
        p_71515_1_.addChatMessage(new ChatComponentTranslation("commands.testforblock.success", new Object[] { var3, var4, var5 }));
    }
    
    public boolean func_147181_a(final NBTBase p_147181_1_, final NBTBase p_147181_2_) {
        if (p_147181_1_ == p_147181_2_) {
            return true;
        }
        if (p_147181_1_ == null) {
            return true;
        }
        if (p_147181_2_ == null) {
            return false;
        }
        if (!p_147181_1_.getClass().equals(p_147181_2_.getClass())) {
            return false;
        }
        if (p_147181_1_ instanceof NBTTagCompound) {
            final NBTTagCompound var3 = (NBTTagCompound)p_147181_1_;
            final NBTTagCompound var4 = (NBTTagCompound)p_147181_2_;
            for (final String var6 : var3.func_150296_c()) {
                final NBTBase var7 = var3.getTag(var6);
                if (!this.func_147181_a(var7, var4.getTag(var6))) {
                    return false;
                }
            }
            return true;
        }
        return p_147181_1_.equals(p_147181_2_);
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 4) ? CommandBase.getListOfStringsFromIterableMatchingLastWord(p_71516_2_, Block.blockRegistry.getKeys()) : null;
    }
}
