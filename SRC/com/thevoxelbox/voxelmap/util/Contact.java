package com.thevoxelbox.voxelmap.util;

import net.minecraft.entity.*;
import net.minecraft.item.*;

public class Contact
{
    public double x;
    public double z;
    public int y;
    public int yFudge;
    public float angle;
    public double distance;
    public float brightness;
    public EnumMobs type;
    public String name;
    public String skinURL;
    public Entity entity;
    public int armorValue;
    public int armorColor;
    public Item helmet;
    public int blockOnHead;
    public int blockOnHeadMetadata;
    public int[] refs;
    
    public Contact(final Entity entity, final EnumMobs type) {
        this.yFudge = 0;
        this.name = "_";
        this.skinURL = "";
        this.entity = null;
        this.armorValue = -1;
        this.armorColor = -1;
        this.helmet = null;
        this.blockOnHead = -1;
        this.blockOnHeadMetadata = -1;
        this.entity = entity;
        this.type = type;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setBlockOnHead(final int blockOnHead) {
        this.blockOnHead = blockOnHead;
    }
    
    public void setBlockOnHeadMetadata(final int blockOnHeadMetadata) {
        this.blockOnHeadMetadata = blockOnHeadMetadata;
    }
    
    public void setArmor(final int armorValue) {
        this.armorValue = armorValue;
    }
    
    public void setArmorColor(final int armorColor) {
        this.armorColor = armorColor;
    }
    
    public void updateLocation() {
        this.x = this.entity.posX;
        this.y = (int)this.entity.posY + this.yFudge;
        this.z = this.entity.posZ;
    }
}
