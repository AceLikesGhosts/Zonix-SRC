package us.zonix.client.social.friend;

import java.util.*;

public final class FriendManager
{
    private final Set<Friend> friends;
    
    public FriendManager() {
        this.friends = new HashSet<Friend>();
    }
    
    public Set<Friend> getFriends() {
        return this.friends;
    }
    
    public Set<Friend> getOnlineFriends() {
        final Set<Friend> friends = new HashSet<Friend>();
        for (final Friend friend : this.friends) {
            if (friend.isOnline()) {
                friends.add(friend);
            }
        }
        return friends;
    }
}
