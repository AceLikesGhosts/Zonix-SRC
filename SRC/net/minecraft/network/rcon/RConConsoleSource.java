package net.minecraft.network.rcon;

import net.minecraft.command.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;

public class RConConsoleSource implements ICommandSender
{
    public static final RConConsoleSource field_70010_a;
    private StringBuffer field_70009_b;
    private static final String __OBFID = "CL_00001800";
    
    public RConConsoleSource() {
        this.field_70009_b = new StringBuffer();
    }
    
    @Override
    public String getCommandSenderName() {
        return "Rcon";
    }
    
    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName());
    }
    
    @Override
    public void addChatMessage(final IChatComponent p_145747_1_) {
        this.field_70009_b.append(p_145747_1_.getUnformattedText());
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
        return true;
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(0, 0, 0);
    }
    
    @Override
    public World getEntityWorld() {
        return MinecraftServer.getServer().getEntityWorld();
    }
    
    static {
        field_70010_a = new RConConsoleSource();
    }
}
