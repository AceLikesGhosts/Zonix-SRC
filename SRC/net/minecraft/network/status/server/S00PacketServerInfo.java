package net.minecraft.network.status.server;

import java.io.*;
import net.minecraft.network.status.*;
import net.minecraft.network.*;
import java.lang.reflect.*;
import net.minecraft.util.*;
import com.google.gson.*;

public class S00PacketServerInfo extends Packet
{
    private static final Gson field_149297_a;
    private ServerStatusResponse field_149296_b;
    private static final String __OBFID = "CL_00001384";
    
    public S00PacketServerInfo() {
    }
    
    public S00PacketServerInfo(final ServerStatusResponse p_i45273_1_) {
        this.field_149296_b = p_i45273_1_;
    }
    
    @Override
    public void readPacketData(final PacketBuffer p_148837_1_) throws IOException {
        this.field_149296_b = (ServerStatusResponse)S00PacketServerInfo.field_149297_a.fromJson(p_148837_1_.readStringFromBuffer(32767), (Class)ServerStatusResponse.class);
    }
    
    @Override
    public void writePacketData(final PacketBuffer p_148840_1_) throws IOException {
        p_148840_1_.writeStringToBuffer(S00PacketServerInfo.field_149297_a.toJson((Object)this.field_149296_b));
    }
    
    public void processPacket(final INetHandlerStatusClient p_148833_1_) {
        p_148833_1_.handleServerInfo(this);
    }
    
    public ServerStatusResponse func_149294_c() {
        return this.field_149296_b;
    }
    
    @Override
    public boolean hasPriority() {
        return true;
    }
    
    @Override
    public void processPacket(final INetHandler p_148833_1_) {
        this.processPacket((INetHandlerStatusClient)p_148833_1_);
    }
    
    static {
        field_149297_a = new GsonBuilder().registerTypeAdapter((Type)ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, (Object)new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.PlayerCountData.class, (Object)new ServerStatusResponse.PlayerCountData.Serializer()).registerTypeAdapter((Type)ServerStatusResponse.class, (Object)new ServerStatusResponse.Serializer()).registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new IChatComponent.Serializer()).registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer()).registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory()).create();
    }
}
