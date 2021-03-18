package net.minecraft.potion;

import com.google.common.collect.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

public class Potion
{
    public static final Potion[] potionTypes;
    public static final Potion field_76423_b;
    public static final Potion moveSpeed;
    public static final Potion moveSlowdown;
    public static final Potion digSpeed;
    public static final Potion digSlowdown;
    public static final Potion damageBoost;
    public static final Potion heal;
    public static final Potion harm;
    public static final Potion jump;
    public static final Potion confusion;
    public static final Potion regeneration;
    public static final Potion resistance;
    public static final Potion fireResistance;
    public static final Potion waterBreathing;
    public static final Potion invisibility;
    public static final Potion blindness;
    public static final Potion nightVision;
    public static final Potion hunger;
    public static final Potion weakness;
    public static final Potion poison;
    public static final Potion wither;
    public static final Potion field_76434_w;
    public static final Potion field_76444_x;
    public static final Potion field_76443_y;
    public static final Potion field_76442_z;
    public static final Potion field_76409_A;
    public static final Potion field_76410_B;
    public static final Potion field_76411_C;
    public static final Potion field_76405_D;
    public static final Potion field_76406_E;
    public static final Potion field_76407_F;
    public static final Potion field_76408_G;
    public final int id;
    private final Map field_111188_I;
    private final boolean isBadEffect;
    private final int liquidColor;
    private String name;
    private int statusIconIndex;
    private double effectiveness;
    private boolean usable;
    private static final String __OBFID = "CL_00001528";
    
    protected Potion(final int p_i1573_1_, final boolean p_i1573_2_, final int p_i1573_3_) {
        this.field_111188_I = Maps.newHashMap();
        this.name = "";
        this.statusIconIndex = -1;
        this.id = p_i1573_1_;
        Potion.potionTypes[p_i1573_1_] = this;
        this.isBadEffect = p_i1573_2_;
        if (p_i1573_2_) {
            this.effectiveness = 0.5;
        }
        else {
            this.effectiveness = 1.0;
        }
        this.liquidColor = p_i1573_3_;
    }
    
    protected Potion setIconIndex(final int p_76399_1_, final int p_76399_2_) {
        this.statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
        return this;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void performEffect(final EntityLivingBase p_76394_1_, final int p_76394_2_) {
        if (this.id == Potion.regeneration.id) {
            if (p_76394_1_.getHealth() < p_76394_1_.getMaxHealth()) {
                p_76394_1_.heal(1.0f);
            }
        }
        else if (this.id == Potion.poison.id) {
            if (p_76394_1_.getHealth() > 1.0f) {
                p_76394_1_.attackEntityFrom(DamageSource.magic, 1.0f);
            }
        }
        else if (this.id == Potion.wither.id) {
            p_76394_1_.attackEntityFrom(DamageSource.wither, 1.0f);
        }
        else if (this.id == Potion.hunger.id && p_76394_1_ instanceof EntityPlayer) {
            ((EntityPlayer)p_76394_1_).addExhaustion(0.025f * (p_76394_2_ + 1));
        }
        else if (this.id == Potion.field_76443_y.id && p_76394_1_ instanceof EntityPlayer) {
            if (!p_76394_1_.worldObj.isClient) {
                ((EntityPlayer)p_76394_1_).getFoodStats().addStats(p_76394_2_ + 1, 1.0f);
            }
        }
        else if ((this.id != Potion.heal.id || p_76394_1_.isEntityUndead()) && (this.id != Potion.harm.id || !p_76394_1_.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !p_76394_1_.isEntityUndead()) || (this.id == Potion.heal.id && p_76394_1_.isEntityUndead())) {
                p_76394_1_.attackEntityFrom(DamageSource.magic, (float)(6 << p_76394_2_));
            }
        }
        else {
            p_76394_1_.heal((float)Math.max(4 << p_76394_2_, 0));
        }
    }
    
    public void affectEntity(final EntityLivingBase p_76402_1_, final EntityLivingBase p_76402_2_, final int p_76402_3_, final double p_76402_4_) {
        if ((this.id != Potion.heal.id || p_76402_2_.isEntityUndead()) && (this.id != Potion.harm.id || !p_76402_2_.isEntityUndead())) {
            if ((this.id == Potion.harm.id && !p_76402_2_.isEntityUndead()) || (this.id == Potion.heal.id && p_76402_2_.isEntityUndead())) {
                final int var6 = (int)(p_76402_4_ * (6 << p_76402_3_) + 0.5);
                if (p_76402_1_ == null) {
                    p_76402_2_.attackEntityFrom(DamageSource.magic, (float)var6);
                }
                else {
                    p_76402_2_.attackEntityFrom(DamageSource.causeIndirectMagicDamage(p_76402_2_, p_76402_1_), (float)var6);
                }
            }
        }
        else {
            final int var6 = (int)(p_76402_4_ * (4 << p_76402_3_) + 0.5);
            p_76402_2_.heal((float)var6);
        }
    }
    
    public boolean isInstant() {
        return false;
    }
    
    public boolean isReady(final int p_76397_1_, final int p_76397_2_) {
        if (this.id == Potion.regeneration.id) {
            final int var3 = 50 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        if (this.id == Potion.poison.id) {
            final int var3 = 25 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        if (this.id == Potion.wither.id) {
            final int var3 = 40 >> p_76397_2_;
            return var3 <= 0 || p_76397_1_ % var3 == 0;
        }
        return this.id == Potion.hunger.id;
    }
    
    public Potion setPotionName(final String p_76390_1_) {
        this.name = p_76390_1_;
        return this;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasStatusIcon() {
        return this.statusIconIndex >= 0;
    }
    
    public int getStatusIconIndex() {
        return this.statusIconIndex;
    }
    
    public boolean isBadEffect() {
        return this.isBadEffect;
    }
    
    public static String getDurationString(final PotionEffect p_76389_0_) {
        if (p_76389_0_.getIsPotionDurationMax()) {
            return "**:**";
        }
        final int var1 = p_76389_0_.getDuration();
        return StringUtils.ticksToElapsedTime(var1);
    }
    
    protected Potion setEffectiveness(final double p_76404_1_) {
        this.effectiveness = p_76404_1_;
        return this;
    }
    
    public double getEffectiveness() {
        return this.effectiveness;
    }
    
    public boolean isUsable() {
        return this.usable;
    }
    
    public int getLiquidColor() {
        return this.liquidColor;
    }
    
    public Potion func_111184_a(final IAttribute p_111184_1_, final String p_111184_2_, final double p_111184_3_, final int p_111184_5_) {
        final AttributeModifier var6 = new AttributeModifier(UUID.fromString(p_111184_2_), this.getName(), p_111184_3_, p_111184_5_);
        this.field_111188_I.put(p_111184_1_, var6);
        return this;
    }
    
    public Map func_111186_k() {
        return this.field_111188_I;
    }
    
    public void removeAttributesModifiersFromEntity(final EntityLivingBase p_111187_1_, final BaseAttributeMap p_111187_2_, final int p_111187_3_) {
        for (final Map.Entry var5 : this.field_111188_I.entrySet()) {
            final IAttributeInstance var6 = p_111187_2_.getAttributeInstance(var5.getKey());
            if (var6 != null) {
                var6.removeModifier(var5.getValue());
            }
        }
    }
    
    public void applyAttributesModifiersToEntity(final EntityLivingBase p_111185_1_, final BaseAttributeMap p_111185_2_, final int p_111185_3_) {
        for (final Map.Entry var5 : this.field_111188_I.entrySet()) {
            final IAttributeInstance var6 = p_111185_2_.getAttributeInstance(var5.getKey());
            if (var6 != null) {
                final AttributeModifier var7 = var5.getValue();
                var6.removeModifier(var7);
                var6.applyModifier(new AttributeModifier(var7.getID(), this.getName() + " " + p_111185_3_, this.func_111183_a(p_111185_3_, var7), var7.getOperation()));
            }
        }
    }
    
    public double func_111183_a(final int p_111183_1_, final AttributeModifier p_111183_2_) {
        return p_111183_2_.getAmount() * (p_111183_1_ + 1);
    }
    
    static {
        potionTypes = new Potion[32];
        field_76423_b = null;
        moveSpeed = new Potion(1, false, 8171462).setPotionName("potion.moveSpeed").setIconIndex(0, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224, 2);
        moveSlowdown = new Potion(2, true, 5926017).setPotionName("potion.moveSlowdown").setIconIndex(1, 0).func_111184_a(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, 2);
        digSpeed = new Potion(3, false, 14270531).setPotionName("potion.digSpeed").setIconIndex(2, 0).setEffectiveness(1.5);
        digSlowdown = new Potion(4, true, 4866583).setPotionName("potion.digSlowDown").setIconIndex(3, 0);
        damageBoost = new PotionAttackDamage(5, false, 9643043).setPotionName("potion.damageBoost").setIconIndex(4, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 3.0, 2);
        heal = new PotionHealth(6, false, 16262179).setPotionName("potion.heal");
        harm = new PotionHealth(7, true, 4393481).setPotionName("potion.harm");
        jump = new Potion(8, false, 7889559).setPotionName("potion.jump").setIconIndex(2, 1);
        confusion = new Potion(9, true, 5578058).setPotionName("potion.confusion").setIconIndex(3, 1).setEffectiveness(0.25);
        regeneration = new Potion(10, false, 13458603).setPotionName("potion.regeneration").setIconIndex(7, 0).setEffectiveness(0.25);
        resistance = new Potion(11, false, 10044730).setPotionName("potion.resistance").setIconIndex(6, 1);
        fireResistance = new Potion(12, false, 14981690).setPotionName("potion.fireResistance").setIconIndex(7, 1);
        waterBreathing = new Potion(13, false, 3035801).setPotionName("potion.waterBreathing").setIconIndex(0, 2);
        invisibility = new Potion(14, false, 8356754).setPotionName("potion.invisibility").setIconIndex(0, 1);
        blindness = new Potion(15, true, 2039587).setPotionName("potion.blindness").setIconIndex(5, 1).setEffectiveness(0.25);
        nightVision = new Potion(16, false, 2039713).setPotionName("potion.nightVision").setIconIndex(4, 1);
        hunger = new Potion(17, true, 5797459).setPotionName("potion.hunger").setIconIndex(1, 1);
        weakness = new PotionAttackDamage(18, true, 4738376).setPotionName("potion.weakness").setIconIndex(5, 0).func_111184_a(SharedMonsterAttributes.attackDamage, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0, 0);
        poison = new Potion(19, true, 5149489).setPotionName("potion.poison").setIconIndex(6, 0).setEffectiveness(0.25);
        wither = new Potion(20, true, 3484199).setPotionName("potion.wither").setIconIndex(1, 2).setEffectiveness(0.25);
        field_76434_w = new PotionHealthBoost(21, false, 16284963).setPotionName("potion.healthBoost").setIconIndex(2, 2).func_111184_a(SharedMonsterAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0, 0);
        field_76444_x = new PotionAbsoption(22, false, 2445989).setPotionName("potion.absorption").setIconIndex(2, 2);
        field_76443_y = new PotionHealth(23, false, 16262179).setPotionName("potion.saturation");
        field_76442_z = null;
        field_76409_A = null;
        field_76410_B = null;
        field_76411_C = null;
        field_76405_D = null;
        field_76406_E = null;
        field_76407_F = null;
        field_76408_G = null;
    }
}
