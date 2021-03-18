package us.zonix.client.social.friend;

import java.util.*;

public final class Friend
{
    private final UUID uniqueId;
    private final String name;
    private boolean online;
    
    public Friend(final UUID uniqueId, final String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isOnline() {
        return this.online;
    }
    
    public void setOnline(final boolean online) {
        this.online = online;
    }
}
