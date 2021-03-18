package net.minecraft.command.server;

import net.minecraft.command.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;
import java.util.*;

public class CommandSummon extends CommandBase
{
    private static final String __OBFID = "CL_00001158";
    
    @Override
    public String getCommandName() {
        return "summon";
    }
    
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
    
    @Override
    public String getCommandUsage(final ICommandSender p_71518_1_) {
        return "commands.summon.usage";
    }
    
    @Override
    public void processCommand(final ICommandSender p_71515_1_, final String[] p_71515_2_) {
        if (p_71515_2_.length < 1) {
            throw new WrongUsageException("commands.summon.usage", new Object[0]);
        }
        final String var3 = p_71515_2_[0];
        double var4 = p_71515_1_.getPlayerCoordinates().posX + 0.5;
        double var5 = p_71515_1_.getPlayerCoordinates().posY;
        double var6 = p_71515_1_.getPlayerCoordinates().posZ + 0.5;
        if (p_71515_2_.length >= 4) {
            var4 = CommandBase.func_110666_a(p_71515_1_, var4, p_71515_2_[1]);
            var5 = CommandBase.func_110666_a(p_71515_1_, var5, p_71515_2_[2]);
            var6 = CommandBase.func_110666_a(p_71515_1_, var6, p_71515_2_[3]);
        }
        final World var7 = p_71515_1_.getEntityWorld();
        if (!var7.blockExists((int)var4, (int)var5, (int)var6)) {
            CommandBase.func_152373_a(p_71515_1_, this, "commands.summon.outOfWorld", new Object[0]);
        }
        else {
            NBTTagCompound var8 = new NBTTagCompound();
            boolean var9 = false;
            if (p_71515_2_.length >= 5) {
                final IChatComponent var10 = CommandBase.func_147178_a(p_71515_1_, p_71515_2_, 4);
                try {
                    final NBTBase var11 = JsonToNBT.func_150315_a(var10.getUnformattedText());
                    if (!(var11 instanceof NBTTagCompound)) {
                        CommandBase.func_152373_a(p_71515_1_, this, "commands.summon.tagError", "Not a valid tag");
                        return;
                    }
                    var8 = (NBTTagCompound)var11;
                    var9 = true;
                }
                catch (NBTException var12) {
                    CommandBase.func_152373_a(p_71515_1_, this, "commands.summon.tagError", var12.getMessage());
                    return;
                }
            }
            var8.setString("id", var3);
            final Entity var13 = EntityList.createEntityFromNBT(var8, var7);
            if (var13 == null) {
                CommandBase.func_152373_a(p_71515_1_, this, "commands.summon.failed", new Object[0]);
            }
            else {
                var13.setLocationAndAngles(var4, var5, var6, var13.rotationYaw, var13.rotationPitch);
                if (!var9 && var13 instanceof EntityLiving) {
                    ((EntityLiving)var13).onSpawnWithEgg(null);
                }
                var7.spawnEntityInWorld(var13);
                Entity var14 = var13;
                Entity var16;
                for (NBTTagCompound var15 = var8; var14 != null && var15.func_150297_b("Riding", 10); var14 = var16, var15 = var15.getCompoundTag("Riding")) {
                    var16 = EntityList.createEntityFromNBT(var15.getCompoundTag("Riding"), var7);
                    if (var16 != null) {
                        var16.setLocationAndAngles(var4, var5, var6, var16.rotationYaw, var16.rotationPitch);
                        var7.spawnEntityInWorld(var16);
                        var14.mountEntity(var16);
                    }
                }
                CommandBase.func_152373_a(p_71515_1_, this, "commands.summon.success", new Object[0]);
            }
        }
    }
    
    @Override
    public List addTabCompletionOptions(final ICommandSender p_71516_1_, final String[] p_71516_2_) {
        return (p_71516_2_.length == 1) ? CommandBase.getListOfStringsMatchingLastWord(p_71516_2_, this.func_147182_d()) : null;
    }
    
    protected String[] func_147182_d() {
        return EntityList.func_151515_b().toArray(new String[0]);
    }
}
