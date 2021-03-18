package net.minecraft.server.network;

import net.minecraft.network.login.*;
import java.util.concurrent.atomic.*;
import net.minecraft.server.*;
import com.mojang.authlib.*;
import javax.crypto.*;
import io.netty.util.concurrent.*;
import net.minecraft.network.*;
import org.apache.commons.lang3.*;
import net.minecraft.network.login.server.*;
import net.minecraft.network.login.client.*;
import net.minecraft.util.*;
import java.math.*;
import java.util.*;
import com.mojang.authlib.exceptions.*;
import java.security.*;
import com.google.common.base.*;
import org.apache.logging.log4j.*;

public class NetHandlerLoginServer implements INetHandlerLoginServer
{
    private static final AtomicInteger field_147331_b;
    private static final Logger logger;
    private static final Random field_147329_d;
    private final byte[] field_147330_e;
    private final MinecraftServer field_147327_f;
    public final NetworkManager field_147333_a;
    private LoginState field_147328_g;
    private int field_147336_h;
    private GameProfile field_147337_i;
    private String field_147334_j;
    private SecretKey field_147335_k;
    private static final String __OBFID = "CL_00001458";
    
    public NetHandlerLoginServer(final MinecraftServer p_i45298_1_, final NetworkManager p_i45298_2_) {
        this.field_147330_e = new byte[4];
        this.field_147328_g = LoginState.HELLO;
        this.field_147334_j = "";
        this.field_147327_f = p_i45298_1_;
        this.field_147333_a = p_i45298_2_;
        NetHandlerLoginServer.field_147329_d.nextBytes(this.field_147330_e);
    }
    
    @Override
    public void onNetworkTick() {
        if (this.field_147328_g == LoginState.READY_TO_ACCEPT) {
            this.func_147326_c();
        }
        if (this.field_147336_h++ == 600) {
            this.func_147322_a("Took too long to log in");
        }
    }
    
    public void func_147322_a(final String p_147322_1_) {
        try {
            NetHandlerLoginServer.logger.info("Disconnecting " + this.func_147317_d() + ": " + p_147322_1_);
            final ChatComponentText var2 = new ChatComponentText(p_147322_1_);
            this.field_147333_a.scheduleOutboundPacket(new S00PacketDisconnect(var2), new GenericFutureListener[0]);
            this.field_147333_a.closeChannel(var2);
        }
        catch (Exception var3) {
            NetHandlerLoginServer.logger.error("Error whilst disconnecting player", (Throwable)var3);
        }
    }
    
    public void func_147326_c() {
        if (!this.field_147337_i.isComplete()) {
            this.field_147337_i = this.func_152506_a(this.field_147337_i);
        }
        final String var1 = this.field_147327_f.getConfigurationManager().func_148542_a(this.field_147333_a.getSocketAddress(), this.field_147337_i);
        if (var1 != null) {
            this.func_147322_a(var1);
        }
        else {
            this.field_147328_g = LoginState.ACCEPTED;
            this.field_147333_a.scheduleOutboundPacket(new S02PacketLoginSuccess(this.field_147337_i), new GenericFutureListener[0]);
            this.field_147327_f.getConfigurationManager().initializeConnectionToPlayer(this.field_147333_a, this.field_147327_f.getConfigurationManager().func_148545_a(this.field_147337_i));
        }
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
        NetHandlerLoginServer.logger.info(this.func_147317_d() + " lost connection: " + p_147231_1_.getUnformattedText());
    }
    
    public String func_147317_d() {
        return (this.field_147337_i != null) ? (this.field_147337_i.toString() + " (" + this.field_147333_a.getSocketAddress().toString() + ")") : String.valueOf(this.field_147333_a.getSocketAddress());
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        Validate.validState(this.field_147328_g == LoginState.ACCEPTED || this.field_147328_g == LoginState.HELLO, "Unexpected change in protocol", new Object[0]);
        Validate.validState(p_147232_2_ == EnumConnectionState.PLAY || p_147232_2_ == EnumConnectionState.LOGIN, "Unexpected protocol " + p_147232_2_, new Object[0]);
    }
    
    @Override
    public void processLoginStart(final C00PacketLoginStart p_147316_1_) {
        Validate.validState(this.field_147328_g == LoginState.HELLO, "Unexpected hello packet", new Object[0]);
        this.field_147337_i = p_147316_1_.func_149304_c();
        if (this.field_147327_f.isServerInOnlineMode() && !this.field_147333_a.isLocalChannel()) {
            this.field_147328_g = LoginState.KEY;
            this.field_147333_a.scheduleOutboundPacket(new S01PacketEncryptionRequest(this.field_147334_j, this.field_147327_f.getKeyPair().getPublic(), this.field_147330_e), new GenericFutureListener[0]);
        }
        else {
            this.field_147328_g = LoginState.READY_TO_ACCEPT;
        }
    }
    
    @Override
    public void processEncryptionResponse(final C01PacketEncryptionResponse p_147315_1_) {
        Validate.validState(this.field_147328_g == LoginState.KEY, "Unexpected key packet", new Object[0]);
        final PrivateKey var2 = this.field_147327_f.getKeyPair().getPrivate();
        if (!Arrays.equals(this.field_147330_e, p_147315_1_.func_149299_b(var2))) {
            throw new IllegalStateException("Invalid nonce!");
        }
        this.field_147335_k = p_147315_1_.func_149300_a(var2);
        this.field_147328_g = LoginState.AUTHENTICATING;
        this.field_147333_a.enableEncryption(this.field_147335_k);
        new Thread("User Authenticator #" + NetHandlerLoginServer.field_147331_b.incrementAndGet()) {
            private static final String __OBFID = "CL_00001459";
            
            @Override
            public void run() {
                final GameProfile var1 = NetHandlerLoginServer.this.field_147337_i;
                try {
                    final String var2 = new BigInteger(CryptManager.getServerIdHash(NetHandlerLoginServer.this.field_147334_j, NetHandlerLoginServer.this.field_147327_f.getKeyPair().getPublic(), NetHandlerLoginServer.this.field_147335_k)).toString(16);
                    NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.field_147327_f.func_147130_as().hasJoinedServer(new GameProfile((UUID)null, var1.getName()), var2);
                    if (NetHandlerLoginServer.this.field_147337_i != null) {
                        NetHandlerLoginServer.logger.info("UUID of player " + NetHandlerLoginServer.this.field_147337_i.getName() + " is " + NetHandlerLoginServer.this.field_147337_i.getId());
                        NetHandlerLoginServer.this.field_147328_g = LoginState.READY_TO_ACCEPT;
                    }
                    else if (NetHandlerLoginServer.this.field_147327_f.isSinglePlayer()) {
                        NetHandlerLoginServer.logger.warn("Failed to verify username but will let them in anyway!");
                        NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.func_152506_a(var1);
                        NetHandlerLoginServer.this.field_147328_g = LoginState.READY_TO_ACCEPT;
                    }
                    else {
                        NetHandlerLoginServer.this.func_147322_a("Failed to verify username!");
                        NetHandlerLoginServer.logger.error("Username '" + NetHandlerLoginServer.this.field_147337_i.getName() + "' tried to join with an invalid session");
                    }
                }
                catch (AuthenticationUnavailableException var3) {
                    if (NetHandlerLoginServer.this.field_147327_f.isSinglePlayer()) {
                        NetHandlerLoginServer.logger.warn("Authentication servers are down but will let them in anyway!");
                        NetHandlerLoginServer.this.field_147337_i = NetHandlerLoginServer.this.func_152506_a(var1);
                        NetHandlerLoginServer.this.field_147328_g = LoginState.READY_TO_ACCEPT;
                    }
                    else {
                        NetHandlerLoginServer.this.func_147322_a("Authentication servers are down. Please try again later, sorry!");
                        NetHandlerLoginServer.logger.error("Couldn't verify username because servers are unavailable");
                    }
                }
            }
        }.start();
    }
    
    protected GameProfile func_152506_a(final GameProfile p_152506_1_) {
        final UUID var2 = UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_152506_1_.getName()).getBytes(Charsets.UTF_8));
        return new GameProfile(var2, p_152506_1_.getName());
    }
    
    static {
        field_147331_b = new AtomicInteger(0);
        logger = LogManager.getLogger();
        field_147329_d = new Random();
    }
    
    enum LoginState
    {
        HELLO("HELLO", 0), 
        KEY("KEY", 1), 
        AUTHENTICATING("AUTHENTICATING", 2), 
        READY_TO_ACCEPT("READY_TO_ACCEPT", 3), 
        ACCEPTED("ACCEPTED", 4);
        
        private static final LoginState[] $VALUES;
        private static final String __OBFID = "CL_00001463";
        
        private LoginState(final String p_i45297_1_, final int p_i45297_2_) {
        }
        
        static {
            $VALUES = new LoginState[] { LoginState.HELLO, LoginState.KEY, LoginState.AUTHENTICATING, LoginState.READY_TO_ACCEPT, LoginState.ACCEPTED };
        }
    }
}
