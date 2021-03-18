package net.minecraft.network;

import net.minecraft.util.*;

public interface INetHandler
{
    void onDisconnect(final IChatComponent p0);
    
    void onConnectionStateTransition(final EnumConnectionState p0, final EnumConnectionState p1);
    
    void onNetworkTick();
}
