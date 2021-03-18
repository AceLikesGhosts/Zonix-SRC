package net.minecraft.item;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.creativetab.*;
import java.util.*;
import com.google.common.collect.*;

public class ItemFishFood extends ItemFood
{
    private final boolean field_150907_b;
    private static final String __OBFID = "CL_00000032";
    
    public ItemFishFood(final boolean p_i45338_1_) {
        super(0, 0.0f, false);
        this.field_150907_b = p_i45338_1_;
    }
    
    @Override
    public int func_150905_g(final ItemStack p_150905_1_) {
        final FishType var2 = FishType.func_150978_a(p_150905_1_);
        return (this.field_150907_b && var2.func_150973_i()) ? var2.func_150970_e() : var2.func_150975_c();
    }
    
    @Override
    public float func_150906_h(final ItemStack p_150906_1_) {
        final FishType var2 = FishType.func_150978_a(p_150906_1_);
        return (this.field_150907_b && var2.func_150973_i()) ? var2.func_150977_f() : var2.func_150967_d();
    }
    
    @Override
    public String getPotionEffect(final ItemStack p_150896_1_) {
        return (FishType.func_150978_a(p_150896_1_) == FishType.PUFFERFISH) ? PotionHelper.field_151423_m : null;
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        for (final FishType var5 : FishType.values()) {
            var5.func_150968_a(p_94581_1_);
        }
    }
    
    @Override
    protected void onFoodEaten(final ItemStack p_77849_1_, final World p_77849_2_, final EntityPlayer p_77849_3_) {
        final FishType var4 = FishType.func_150978_a(p_77849_1_);
        if (var4 == FishType.PUFFERFISH) {
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.poison.id, 1200, 3));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.hunger.id, 300, 2));
            p_77849_3_.addPotionEffect(new PotionEffect(Potion.confusion.id, 300, 1));
        }
        super.onFoodEaten(p_77849_1_, p_77849_2_, p_77849_3_);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        final FishType var2 = FishType.func_150974_a(p_77617_1_);
        return (this.field_150907_b && var2.func_150973_i()) ? var2.func_150979_h() : var2.func_150971_g();
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        for (final FishType var7 : FishType.values()) {
            if (!this.field_150907_b || var7.func_150973_i()) {
                p_150895_3_.add(new ItemStack(this, 1, var7.func_150976_a()));
            }
        }
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        final FishType var2 = FishType.func_150978_a(p_77667_1_);
        return this.getUnlocalizedName() + "." + var2.func_150972_b() + "." + ((this.field_150907_b && var2.func_150973_i()) ? "cooked" : "raw");
    }
    
    public enum FishType
    {
        COD("COD", 0, 0, "cod", 2, 0.1f, 5, 0.6f), 
        SALMON("SALMON", 1, 1, "salmon", 2, 0.1f, 6, 0.8f), 
        CLOWNFISH("CLOWNFISH", 2, 2, "clownfish", 1, 0.1f), 
        PUFFERFISH("PUFFERFISH", 3, 3, "pufferfish", 1, 0.1f);
        
        private static final Map field_150983_e;
        private final int field_150980_f;
        private final String field_150981_g;
        private IIcon field_150993_h;
        private IIcon field_150994_i;
        private final int field_150991_j;
        private final float field_150992_k;
        private final int field_150989_l;
        private final float field_150990_m;
        private boolean field_150987_n;
        private static final FishType[] $VALUES;
        private static final String __OBFID = "CL_00000033";
        
        private FishType(final String p_i45336_1_, final int p_i45336_2_, final int p_i45336_3_, final String p_i45336_4_, final int p_i45336_5_, final float p_i45336_6_, final int p_i45336_7_, final float p_i45336_8_) {
            this.field_150987_n = false;
            this.field_150980_f = p_i45336_3_;
            this.field_150981_g = p_i45336_4_;
            this.field_150991_j = p_i45336_5_;
            this.field_150992_k = p_i45336_6_;
            this.field_150989_l = p_i45336_7_;
            this.field_150990_m = p_i45336_8_;
            this.field_150987_n = true;
        }
        
        private FishType(final String p_i45337_1_, final int p_i45337_2_, final int p_i45337_3_, final String p_i45337_4_, final int p_i45337_5_, final float p_i45337_6_) {
            this.field_150987_n = false;
            this.field_150980_f = p_i45337_3_;
            this.field_150981_g = p_i45337_4_;
            this.field_150991_j = p_i45337_5_;
            this.field_150992_k = p_i45337_6_;
            this.field_150989_l = 0;
            this.field_150990_m = 0.0f;
            this.field_150987_n = false;
        }
        
        public int func_150976_a() {
            return this.field_150980_f;
        }
        
        public String func_150972_b() {
            return this.field_150981_g;
        }
        
        public int func_150975_c() {
            return this.field_150991_j;
        }
        
        public float func_150967_d() {
            return this.field_150992_k;
        }
        
        public int func_150970_e() {
            return this.field_150989_l;
        }
        
        public float func_150977_f() {
            return this.field_150990_m;
        }
        
        public void func_150968_a(final IIconRegister p_150968_1_) {
            this.field_150993_h = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_raw");
            if (this.field_150987_n) {
                this.field_150994_i = p_150968_1_.registerIcon("fish_" + this.field_150981_g + "_cooked");
            }
        }
        
        public IIcon func_150971_g() {
            return this.field_150993_h;
        }
        
        public IIcon func_150979_h() {
            return this.field_150994_i;
        }
        
        public boolean func_150973_i() {
            return this.field_150987_n;
        }
        
        public static FishType func_150974_a(final int p_150974_0_) {
            final FishType var1 = FishType.field_150983_e.get(p_150974_0_);
            return (var1 == null) ? FishType.COD : var1;
        }
        
        public static FishType func_150978_a(final ItemStack p_150978_0_) {
            return (p_150978_0_.getItem() instanceof ItemFishFood) ? func_150974_a(p_150978_0_.getItemDamage()) : FishType.COD;
        }
        
        static {
            field_150983_e = Maps.newHashMap();
            $VALUES = new FishType[] { FishType.COD, FishType.SALMON, FishType.CLOWNFISH, FishType.PUFFERFISH };
            for (final FishType var4 : values()) {
                FishType.field_150983_e.put(var4.func_150976_a(), var4);
            }
        }
    }
}
