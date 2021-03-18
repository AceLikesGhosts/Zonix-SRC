package net.minecraft.src;

import net.minecraft.entity.*;

public class EntityUtils
{
    private static boolean directEntityAge;
    private static boolean directDespawnEntity;
    
    public static int getEntityAge(final EntityLivingBase elb) {
        return elb.entityAge;
    }
    
    public static void setEntityAge(final EntityLivingBase elb, final int age) {
        elb.entityAge = age;
    }
    
    public static void despawnEntity(final EntityLiving el) {
        el.despawnEntity();
    }
    
    static {
        EntityUtils.directEntityAge = true;
        EntityUtils.directDespawnEntity = true;
    }
}
