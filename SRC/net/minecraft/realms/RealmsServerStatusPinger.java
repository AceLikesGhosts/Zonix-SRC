package net.minecraft.realms;

import java.net.*;
import net.minecraft.network.status.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.status.server.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import net.minecraft.network.status.client.*;
import java.io.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class RealmsServerStatusPinger
{
    private static final Logger LOGGER;
    private final List connections;
    private static final String __OBFID = "CL_00001854";
    
    public RealmsServerStatusPinger() {
        this.connections = Collections.synchronizedList(new ArrayList<Object>());
    }
    
    public void pingServer(final String p_pingServer_1_, final ServerPing p_pingServer_2_) throws IOException {
        if (p_pingServer_1_ != null && !p_pingServer_1_.startsWith("0.0.0.0") && !p_pingServer_1_.isEmpty()) {
            final RealmsServerAddress var3 = RealmsServerAddress.parseString(p_pingServer_1_);
            final NetworkManager var4 = NetworkManager.provideLanClient(InetAddress.getByName(var3.getHost()), var3.getPort());
            this.connections.add(var4);
            var4.setNetHandler(new INetHandlerStatusClient() {
                private boolean field_154345_e = false;
                private static final String __OBFID = "CL_00001807";
                
                @Override
                public void handleServerInfo(final S00PacketServerInfo p_147397_1_) {
                    final ServerStatusResponse var2 = p_147397_1_.func_149294_c();
                    if (var2.func_151318_b() != null) {
                        p_pingServer_2_.nrOfPlayers = String.valueOf(var2.func_151318_b().func_151333_b());
                    }
                    var4.scheduleOutboundPacket(new C01PacketPing(Realms.currentTimeMillis()), new GenericFutureListener[0]);
                    this.field_154345_e = true;
                }
                
                @Override
                public void handlePong(final S01PacketPong p_147398_1_) {
                    var4.closeChannel(new ChatComponentText("Finished"));
                }
                
                @Override
                public void onDisconnect(final IChatComponent p_147231_1_) {
                    if (!this.field_154345_e) {
                        RealmsServerStatusPinger.LOGGER.error("Can't ping " + p_pingServer_1_ + ": " + p_147231_1_.getUnformattedText());
                    }
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
            });
            try {
                var4.scheduleOutboundPacket(new C00Handshake(RealmsSharedConstants.NETWORK_PROTOCOL_VERSION, var3.getHost(), var3.getPort(), EnumConnectionState.STATUS), new GenericFutureListener[0]);
                var4.scheduleOutboundPacket(new C00PacketServerQuery(), new GenericFutureListener[0]);
            }
            catch (Throwable var5) {
                RealmsServerStatusPinger.LOGGER.error((Object)var5);
            }
        }
    }
    
    public void tick() {
        final List var1 = this.connections;
        synchronized (this.connections) {
            final Iterator var2 = this.connections.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var3.processReceivedPackets();
                }
                else {
                    var2.remove();
                    if (var3.getExitMessage() == null) {
                        continue;
                    }
                    var3.getNetHandler().onDisconnect(var3.getExitMessage());
                }
            }
        }
    }
    
    public void removeAll() {
        final List var1 = this.connections;
        synchronized (this.connections) {
            final Iterator var2 = this.connections.iterator();
            while (var2.hasNext()) {
                final NetworkManager var3 = var2.next();
                if (var3.isChannelOpen()) {
                    var2.remove();
                    var3.closeChannel(new ChatComponentText("Cancelled"));
                }
            }
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}
