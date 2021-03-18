package net.minecraft.server.network;

import net.minecraft.network.status.*;
import net.minecraft.server.*;
import net.minecraft.util.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.*;
import net.minecraft.network.status.client.*;
import net.minecraft.network.status.server.*;

public class NetHandlerStatusServer implements INetHandlerStatusServer
{
    private final MinecraftServer field_147314_a;
    private final NetworkManager field_147313_b;
    private static final String __OBFID = "CL_00001464";
    
    public NetHandlerStatusServer(final MinecraftServer p_i45299_1_, final NetworkManager p_i45299_2_) {
        this.field_147314_a = p_i45299_1_;
        this.field_147313_b = p_i45299_2_;
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        if (p_147232_2_ != EnumConnectionState.STATUS) {
            throw new UnsupportedOperationException("Unexpected change in protocol to " + p_147232_2_);
        }
    }
    
    @Override
    public void onNetworkTick() {
    }
    
    @Override
    public void processServerQuery(final C00PacketServerQuery p_147312_1_) {
        this.field_147313_b.scheduleOutboundPacket(new S00PacketServerInfo(this.field_147314_a.func_147134_at()), new GenericFutureListener[0]);
    }
    
    @Override
    public void processPing(final C01PacketPing p_147311_1_) {
        this.field_147313_b.scheduleOutboundPacket(new S01PacketPong(p_147311_1_.func_149289_c()), new GenericFutureListener[0]);
    }
}
