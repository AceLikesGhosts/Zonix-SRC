package us.zonix.client.social;

import java.util.*;

public final class PartyManager
{
    private final Set<String> partyMembers;
    private boolean leader;
    
    public PartyManager() {
        (this.partyMembers = new HashSet<String>()).add("Erouax");
        this.partyMembers.add("Manthe");
    }
    
    public Set<String> getPartyMembers() {
        this.partyMembers.clear();
        this.partyMembers.add("Erouax");
        this.partyMembers.add("Manthe");
        this.partyMembers.add("Hitler");
        return this.partyMembers;
    }
    
    public boolean isLeader() {
        return this.leader;
    }
    
    public boolean isInParty() {
        return true;
    }
}
