package net.minecraft.client.network;

import net.minecraft.network.handshake.*;
import net.minecraft.server.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.server.network.*;
import net.minecraft.network.*;

public class NetHandlerHandshakeMemory implements INetHandlerHandshakeServer
{
    private final MinecraftServer field_147385_a;
    private final NetworkManager field_147384_b;
    private static final String __OBFID = "CL_00001445";
    
    public NetHandlerHandshakeMemory(final MinecraftServer p_i45287_1_, final NetworkManager p_i45287_2_) {
        this.field_147385_a = p_i45287_1_;
        this.field_147384_b = p_i45287_2_;
    }
    
    @Override
    public void processHandshake(final C00Handshake p_147383_1_) {
        this.field_147384_b.setConnectionState(p_147383_1_.func_149594_c());
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        Validate.validState(p_147232_2_ == EnumConnectionState.LOGIN || p_147232_2_ == EnumConnectionState.STATUS, "Unexpected protocol " + p_147232_2_, new Object[0]);
        switch (SwitchEnumConnectionState.field_151263_a[p_147232_2_.ordinal()]) {
            case 1: {
                this.field_147384_b.setNetHandler(new NetHandlerLoginServer(this.field_147385_a, this.field_147384_b));
                break;
            }
            case 2: {
                throw new UnsupportedOperationException("NYI");
            }
        }
    }
    
    @Override
    public void onNetworkTick() {
    }
    
    static final class SwitchEnumConnectionState
    {
        static final int[] field_151263_a;
        private static final String __OBFID = "CL_00001446";
        
        static {
            field_151263_a = new int[EnumConnectionState.values().length];
            try {
                SwitchEnumConnectionState.field_151263_a[EnumConnectionState.LOGIN.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchEnumConnectionState.field_151263_a[EnumConnectionState.STATUS.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
