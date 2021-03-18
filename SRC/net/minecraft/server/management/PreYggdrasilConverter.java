package net.minecraft.server.management;

import java.io.*;
import net.minecraft.server.*;
import com.google.common.base.*;
import net.minecraft.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class PreYggdrasilConverter
{
    private static final Logger field_152732_e;
    public static final File field_152728_a;
    public static final File field_152729_b;
    public static final File field_152730_c;
    public static final File field_152731_d;
    private static final String __OBFID = "CL_00001882";
    
    private static void func_152717_a(final MinecraftServer p_152717_0_, final Collection p_152717_1_, final ProfileLookupCallback p_152717_2_) {
        final String[] var3 = (String[])Iterators.toArray((Iterator)Iterators.filter((Iterator)p_152717_1_.iterator(), (Predicate)new Predicate() {
            private static final String __OBFID = "CL_00001881";
            
            public boolean func_152733_a(final String p_152733_1_) {
                return !StringUtils.isNullOrEmpty(p_152733_1_);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_152733_a((String)p_apply_1_);
            }
        }), (Class)String.class);
        if (p_152717_0_.isServerInOnlineMode()) {
            p_152717_0_.func_152359_aw().findProfilesByNames(var3, Agent.MINECRAFT, p_152717_2_);
        }
        else {
            final String[] var4 = var3;
            for (int var5 = var3.length, var6 = 0; var6 < var5; ++var6) {
                final String var7 = var4[var6];
                final UUID var8 = EntityPlayer.func_146094_a(new GameProfile((UUID)null, var7));
                final GameProfile var9 = new GameProfile(var8, var7);
                p_152717_2_.onProfileLookupSucceeded(var9);
            }
        }
    }
    
    public static String func_152719_a(final String p_152719_0_) {
        if (StringUtils.isNullOrEmpty(p_152719_0_) || p_152719_0_.length() > 16) {
            return p_152719_0_;
        }
        final MinecraftServer var1 = MinecraftServer.getServer();
        final GameProfile var2 = var1.func_152358_ax().func_152655_a(p_152719_0_);
        if (var2 != null && var2.getId() != null) {
            return var2.getId().toString();
        }
        if (!var1.isSinglePlayer() && var1.isServerInOnlineMode()) {
            final ArrayList var3 = Lists.newArrayList();
            final ProfileLookupCallback var4 = (ProfileLookupCallback)new ProfileLookupCallback() {
                private static final String __OBFID = "CL_00001880";
                
                public void onProfileLookupSucceeded(final GameProfile p_onProfileLookupSucceeded_1_) {
                    var1.func_152358_ax().func_152649_a(p_onProfileLookupSucceeded_1_);
                    var3.add(p_onProfileLookupSucceeded_1_);
                }
                
                public void onProfileLookupFailed(final GameProfile p_onProfileLookupFailed_1_, final Exception p_onProfileLookupFailed_2_) {
                    PreYggdrasilConverter.field_152732_e.warn("Could not lookup user whitelist entry for " + p_onProfileLookupFailed_1_.getName(), (Throwable)p_onProfileLookupFailed_2_);
                }
            };
            func_152717_a(var1, Lists.newArrayList((Object[])new String[] { p_152719_0_ }), var4);
            return (var3.size() > 0 && var3.get(0).getId() != null) ? var3.get(0).getId().toString() : "";
        }
        return EntityPlayer.func_146094_a(new GameProfile((UUID)null, p_152719_0_)).toString();
    }
    
    static {
        field_152732_e = LogManager.getLogger();
        field_152728_a = new File("banned-ips.txt");
        field_152729_b = new File("banned-players.txt");
        field_152730_c = new File("ops.txt");
        field_152731_d = new File("white-list.txt");
    }
}
