package net.minecraft.command;

import net.minecraft.util.*;
import net.minecraft.world.*;

public interface ICommandSender
{
    String getCommandSenderName();
    
    IChatComponent func_145748_c_();
    
    void addChatMessage(final IChatComponent p0);
    
    boolean canCommandSenderUseCommand(final int p0, final String p1);
    
    ChunkCoordinates getPlayerCoordinates();
    
    World getEntityWorld();
}
