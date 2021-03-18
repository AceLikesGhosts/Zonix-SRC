package net.minecraft.world;

import net.minecraft.nbt.*;
import java.util.*;

public class GameRules
{
    private TreeMap theGameRules;
    private static final String __OBFID = "CL_00000136";
    
    public GameRules() {
        this.theGameRules = new TreeMap();
        this.addGameRule("doFireTick", "true");
        this.addGameRule("mobGriefing", "true");
        this.addGameRule("keepInventory", "false");
        this.addGameRule("doMobSpawning", "true");
        this.addGameRule("doMobLoot", "true");
        this.addGameRule("doTileDrops", "true");
        this.addGameRule("commandBlockOutput", "true");
        this.addGameRule("naturalRegeneration", "true");
        this.addGameRule("doDaylightCycle", "true");
    }
    
    public void addGameRule(final String p_82769_1_, final String p_82769_2_) {
        this.theGameRules.put(p_82769_1_, new Value(p_82769_2_));
    }
    
    public void setOrCreateGameRule(final String p_82764_1_, final String p_82764_2_) {
        final Value var3 = this.theGameRules.get(p_82764_1_);
        if (var3 != null) {
            var3.setValue(p_82764_2_);
        }
        else {
            this.addGameRule(p_82764_1_, p_82764_2_);
        }
    }
    
    public String getGameRuleStringValue(final String p_82767_1_) {
        final Value var2 = this.theGameRules.get(p_82767_1_);
        return (var2 != null) ? var2.getGameRuleStringValue() : "";
    }
    
    public boolean getGameRuleBooleanValue(final String p_82766_1_) {
        final Value var2 = this.theGameRules.get(p_82766_1_);
        return var2 != null && var2.getGameRuleBooleanValue();
    }
    
    public NBTTagCompound writeGameRulesToNBT() {
        final NBTTagCompound var1 = new NBTTagCompound();
        for (final String var3 : this.theGameRules.keySet()) {
            final Value var4 = this.theGameRules.get(var3);
            var1.setString(var3, var4.getGameRuleStringValue());
        }
        return var1;
    }
    
    public void readGameRulesFromNBT(final NBTTagCompound p_82768_1_) {
        final Set var2 = p_82768_1_.func_150296_c();
        for (final String var4 : var2) {
            final String var5 = p_82768_1_.getString(var4);
            this.setOrCreateGameRule(var4, var5);
        }
    }
    
    public String[] getRules() {
        return (String[])this.theGameRules.keySet().toArray(new String[0]);
    }
    
    public boolean hasRule(final String p_82765_1_) {
        return this.theGameRules.containsKey(p_82765_1_);
    }
    
    static class Value
    {
        private String valueString;
        private boolean valueBoolean;
        private int valueInteger;
        private double valueDouble;
        private static final String __OBFID = "CL_00000137";
        
        public Value(final String p_i1949_1_) {
            this.setValue(p_i1949_1_);
        }
        
        public void setValue(final String p_82757_1_) {
            this.valueString = p_82757_1_;
            this.valueBoolean = Boolean.parseBoolean(p_82757_1_);
            try {
                this.valueInteger = Integer.parseInt(p_82757_1_);
            }
            catch (NumberFormatException ex) {}
            try {
                this.valueDouble = Double.parseDouble(p_82757_1_);
            }
            catch (NumberFormatException ex2) {}
        }
        
        public String getGameRuleStringValue() {
            return this.valueString;
        }
        
        public boolean getGameRuleBooleanValue() {
            return this.valueBoolean;
        }
    }
}
