package net.minecraft.realms;

public class ServerPing
{
    public volatile String nrOfPlayers;
    public volatile long lastPingSnapshot;
    private static final String __OBFID = "CL_00001860";
    
    public ServerPing() {
        this.nrOfPlayers = "0";
        this.lastPingSnapshot = 0L;
    }
}
