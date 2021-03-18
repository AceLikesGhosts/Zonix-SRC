package net.minecraft.client.network;

import net.minecraft.network.login.*;
import net.minecraft.client.*;
import java.math.*;
import net.minecraft.util.*;
import com.mojang.authlib.exceptions.*;
import net.minecraft.network.login.client.*;
import javax.crypto.*;
import io.netty.util.concurrent.*;
import java.security.*;
import com.mojang.authlib.minecraft.*;
import java.util.*;
import com.mojang.authlib.yggdrasil.*;
import net.minecraft.client.gui.*;
import net.minecraft.network.*;
import net.minecraft.network.login.server.*;
import org.apache.logging.log4j.*;

public class NetHandlerLoginClient implements INetHandlerLoginClient
{
    private static final Logger logger;
    private final Minecraft field_147394_b;
    private final GuiScreen field_147395_c;
    private final NetworkManager field_147393_d;
    private static final String __OBFID = "CL_00000876";
    
    public NetHandlerLoginClient(final NetworkManager p_i45059_1_, final Minecraft p_i45059_2_, final GuiScreen p_i45059_3_) {
        this.field_147393_d = p_i45059_1_;
        this.field_147394_b = p_i45059_2_;
        this.field_147395_c = p_i45059_3_;
    }
    
    @Override
    public void handleEncryptionRequest(final S01PacketEncryptionRequest p_147389_1_) {
        final SecretKey var2 = CryptManager.createNewSharedKey();
        final String var3 = p_147389_1_.func_149609_c();
        final PublicKey var4 = p_147389_1_.func_149608_d();
        final String var5 = new BigInteger(CryptManager.getServerIdHash(var3, var4, var2)).toString(16);
        final boolean var6 = this.field_147394_b.func_147104_D() == null || !this.field_147394_b.func_147104_D().func_152585_d();
        try {
            this.func_147391_c().joinServer(this.field_147394_b.getSession().func_148256_e(), this.field_147394_b.getSession().getToken(), var5);
        }
        catch (AuthenticationUnavailableException var8) {
            if (var6) {
                this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.serversUnavailable", new Object[0]) }));
                return;
            }
        }
        catch (InvalidCredentialsException var9) {
            if (var6) {
                this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { new ChatComponentTranslation("disconnect.loginFailedInfo.invalidSession", new Object[0]) }));
                return;
            }
        }
        catch (AuthenticationException var7) {
            if (var6) {
                this.field_147393_d.closeChannel(new ChatComponentTranslation("disconnect.loginFailedInfo", new Object[] { var7.getMessage() }));
                return;
            }
        }
        this.field_147393_d.scheduleOutboundPacket(new C01PacketEncryptionResponse(var2, var4, p_147389_1_.func_149607_e()), (GenericFutureListener)new GenericFutureListener() {
            private static final String __OBFID = "CL_00000877";
            
            public void operationComplete(final Future p_operationComplete_1_) {
                NetHandlerLoginClient.this.field_147393_d.enableEncryption(var2);
            }
        });
    }
    
    private MinecraftSessionService func_147391_c() {
        return new YggdrasilAuthenticationService(this.field_147394_b.getProxy(), UUID.randomUUID().toString()).createMinecraftSessionService();
    }
    
    @Override
    public void handleLoginSuccess(final S02PacketLoginSuccess p_147390_1_) {
        this.field_147393_d.setConnectionState(EnumConnectionState.PLAY);
    }
    
    @Override
    public void onDisconnect(final IChatComponent p_147231_1_) {
        this.field_147394_b.displayGuiScreen(new GuiDisconnected(this.field_147395_c, "connect.failed", p_147231_1_));
    }
    
    @Override
    public void onConnectionStateTransition(final EnumConnectionState p_147232_1_, final EnumConnectionState p_147232_2_) {
        NetHandlerLoginClient.logger.debug("Switching protocol from " + p_147232_1_ + " to " + p_147232_2_);
        if (p_147232_2_ == EnumConnectionState.PLAY) {
            this.field_147393_d.setNetHandler(new NetHandlerPlayClient(this.field_147394_b, this.field_147395_c, this.field_147393_d));
        }
    }
    
    @Override
    public void onNetworkTick() {
    }
    
    @Override
    public void handleDisconnect(final S00PacketDisconnect p_147388_1_) {
        this.field_147393_d.closeChannel(p_147388_1_.func_149603_c());
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
