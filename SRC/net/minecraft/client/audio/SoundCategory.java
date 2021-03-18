package net.minecraft.client.audio;

import java.util.*;
import com.google.common.collect.*;

public enum SoundCategory
{
    MASTER("master", 0), 
    MUSIC("music", 1), 
    RECORDS("record", 2), 
    WEATHER("weather", 3), 
    BLOCKS("block", 4), 
    MOBS("hostile", 5), 
    ANIMALS("neutral", 6), 
    PLAYERS("player", 7), 
    AMBIENT("ambient", 8);
    
    private static final Map field_147168_j;
    private static final Map field_147169_k;
    private final String categoryName;
    private final int categoryId;
    private static final String __OBFID = "CL_00001686";
    
    private SoundCategory(final String p_i45126_3_, final int p_i45126_4_) {
        this.categoryName = p_i45126_3_;
        this.categoryId = p_i45126_4_;
    }
    
    public String getCategoryName() {
        return this.categoryName;
    }
    
    public int getCategoryId() {
        return this.categoryId;
    }
    
    public static SoundCategory func_147154_a(final String p_147154_0_) {
        return SoundCategory.field_147168_j.get(p_147154_0_);
    }
    
    static {
        field_147168_j = Maps.newHashMap();
        field_147169_k = Maps.newHashMap();
        for (final SoundCategory var4 : values()) {
            if (SoundCategory.field_147168_j.containsKey(var4.getCategoryName()) || SoundCategory.field_147169_k.containsKey(var4.getCategoryId())) {
                throw new Error("Clash in Sound Category ID & Name pools! Cannot insert " + var4);
            }
            SoundCategory.field_147168_j.put(var4.getCategoryName(), var4);
            SoundCategory.field_147169_k.put(var4.getCategoryId(), var4);
        }
    }
}
