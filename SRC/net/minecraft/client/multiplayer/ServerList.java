package net.minecraft.client.multiplayer;

import net.minecraft.client.*;
import java.io.*;
import net.minecraft.nbt.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ServerList
{
    private static final Logger logger;
    private final Minecraft mc;
    public final List<ServerData> servers;
    private static final String __OBFID = "CL_00000891";
    public final List<ServerData> pinnedServers;
    
    public ServerList(final Minecraft p_i1194_1_) {
        this.servers = new ArrayList<ServerData>();
        this.pinnedServers = Arrays.asList(new ServerData("Zonix US", "zonix.us", -1449640900), new ServerData("Zonix EU", "eu.zonix.us", -1455646628), new ServerData("Zonix SA", "sa.zonix.us", -1455659112), new ServerData("Zonix AS", "as.zonix.us", -1449640809));
        this.mc = p_i1194_1_;
        this.loadServerList();
    }
    
    public void loadServerList() {
        try {
            this.servers.clear();
            final NBTTagCompound var1 = CompressedStreamTools.read(new File(this.mc.mcDataDir, "servers.dat"));
            if (var1 == null) {
                return;
            }
            final NBTTagList var2 = var1.getTagList("servers", 10);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                this.servers.add(ServerData.getServerDataFromNBTCompound(var2.getCompoundTagAt(var3)));
            }
        }
        catch (Exception var4) {
            ServerList.logger.error("Couldn't load server list", (Throwable)var4);
        }
    }
    
    public void saveServerList() {
        try {
            final NBTTagList var1 = new NBTTagList();
            for (final ServerData var3 : this.servers) {
                var1.appendTag(var3.getNBTCompound());
            }
            final NBTTagCompound var4 = new NBTTagCompound();
            var4.setTag("servers", var1);
            CompressedStreamTools.safeWrite(var4, new File(this.mc.mcDataDir, "servers.dat"));
        }
        catch (Exception var5) {
            ServerList.logger.error("Couldn't save server list", (Throwable)var5);
        }
    }
    
    public ServerData getServerData(final int p_78850_1_) {
        return this.servers.get(p_78850_1_);
    }
    
    public void removeServerData(final int p_78851_1_) {
        this.servers.remove(p_78851_1_);
    }
    
    public void addServerData(final ServerData p_78849_1_) {
        this.servers.add(p_78849_1_);
    }
    
    public int countServers() {
        return this.servers.size();
    }
    
    public void swapServers(final int p_78857_1_, final int p_78857_2_) {
        final ServerData var3 = this.getServerData(p_78857_1_);
        this.servers.set(p_78857_1_, this.getServerData(p_78857_2_));
        this.servers.set(p_78857_2_, var3);
        this.saveServerList();
    }
    
    public void func_147413_a(final int p_147413_1_, final ServerData p_147413_2_) {
        this.servers.set(p_147413_1_, p_147413_2_);
    }
    
    public static void func_147414_b(final ServerData p_147414_0_) {
        final ServerList var1 = new ServerList(Minecraft.getMinecraft());
        var1.loadServerList();
        for (int var2 = 0; var2 < var1.countServers(); ++var2) {
            final ServerData var3 = var1.getServerData(var2);
            if (var3.serverName.equals(p_147414_0_.serverName) && var3.serverIP.equals(p_147414_0_.serverIP)) {
                var1.func_147413_a(var2, p_147414_0_);
                break;
            }
        }
        var1.saveServerList();
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
