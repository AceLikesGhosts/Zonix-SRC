package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.potion.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;

public class ItemPotion extends Item
{
    private HashMap effectCache;
    private static final Map field_77835_b;
    private IIcon field_94591_c;
    private IIcon field_94590_d;
    private IIcon field_94592_ct;
    private static final String __OBFID = "CL_00000055";
    
    public ItemPotion() {
        this.effectCache = new HashMap();
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabBrewing);
    }
    
    public List getEffects(final ItemStack p_77832_1_) {
        if (p_77832_1_.hasTagCompound() && p_77832_1_.getTagCompound().func_150297_b("CustomPotionEffects", 9)) {
            final ArrayList var7 = new ArrayList();
            final NBTTagList var8 = p_77832_1_.getTagCompound().getTagList("CustomPotionEffects", 10);
            for (int var9 = 0; var9 < var8.tagCount(); ++var9) {
                final NBTTagCompound var10 = var8.getCompoundTagAt(var9);
                final PotionEffect var11 = PotionEffect.readCustomPotionEffectFromNBT(var10);
                if (var11 != null) {
                    var7.add(var11);
                }
            }
            return var7;
        }
        List var12 = this.effectCache.get(p_77832_1_.getItemDamage());
        if (var12 == null) {
            var12 = PotionHelper.getPotionEffects(p_77832_1_.getItemDamage(), false);
            this.effectCache.put(p_77832_1_.getItemDamage(), var12);
        }
        return var12;
    }
    
    public List getEffects(final int p_77834_1_) {
        List var2 = this.effectCache.get(p_77834_1_);
        if (var2 == null) {
            var2 = PotionHelper.getPotionEffects(p_77834_1_, false);
            this.effectCache.put(p_77834_1_, var2);
        }
        return var2;
    }
    
    @Override
    public ItemStack onEaten(final ItemStack p_77654_1_, final World p_77654_2_, final EntityPlayer p_77654_3_) {
        if (!p_77654_3_.capabilities.isCreativeMode) {
            --p_77654_1_.stackSize;
        }
        if (!p_77654_2_.isClient) {
            final List var4 = this.getEffects(p_77654_1_);
            if (var4 != null) {
                for (final PotionEffect var6 : var4) {
                    p_77654_3_.addPotionEffect(new PotionEffect(var6));
                }
            }
        }
        if (!p_77654_3_.capabilities.isCreativeMode) {
            if (p_77654_1_.stackSize <= 0) {
                return new ItemStack(Items.glass_bottle);
            }
            p_77654_3_.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
        }
        return p_77654_1_;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack p_77626_1_) {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack p_77661_1_) {
        return EnumAction.drink;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        if (isSplash(p_77659_1_.getItemDamage())) {
            if (!p_77659_3_.capabilities.isCreativeMode) {
                --p_77659_1_.stackSize;
            }
            p_77659_2_.playSoundAtEntity(p_77659_3_, "random.bow", 0.5f, 0.4f / (ItemPotion.itemRand.nextFloat() * 0.4f + 0.8f));
            if (!p_77659_2_.isClient) {
                p_77659_2_.spawnEntityInWorld(new EntityPotion(p_77659_2_, p_77659_3_, p_77659_1_));
            }
            return p_77659_1_;
        }
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        return false;
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return isSplash(p_77617_1_) ? this.field_94591_c : this.field_94590_d;
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return (p_77618_2_ == 0) ? this.field_94592_ct : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    public static boolean isSplash(final int p_77831_0_) {
        return (p_77831_0_ & 0x4000) != 0x0;
    }
    
    public int getColorFromDamage(final int p_77620_1_) {
        return PotionHelper.func_77915_a(p_77620_1_, false);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        return (p_82790_2_ > 0) ? 16777215 : this.getColorFromDamage(p_82790_1_.getItemDamage());
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public boolean isEffectInstant(final int p_77833_1_) {
        final List var2 = this.getEffects(p_77833_1_);
        if (var2 != null && !var2.isEmpty()) {
            for (final PotionEffect var4 : var2) {
                if (Potion.potionTypes[var4.getPotionID()].isInstant()) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack p_77653_1_) {
        if (p_77653_1_.getItemDamage() == 0) {
            return StatCollector.translateToLocal("item.emptyPotion.name").trim();
        }
        String var2 = "";
        if (isSplash(p_77653_1_.getItemDamage())) {
            var2 = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
        }
        final List var3 = Items.potionitem.getEffects(p_77653_1_);
        if (var3 != null && !var3.isEmpty()) {
            String var4 = var3.get(0).getEffectName();
            var4 += ".postfix";
            return var2 + StatCollector.translateToLocal(var4).trim();
        }
        String var4 = PotionHelper.func_77905_c(p_77653_1_.getItemDamage());
        return StatCollector.translateToLocal(var4).trim() + " " + super.getItemStackDisplayName(p_77653_1_);
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        if (p_77624_1_.getItemDamage() != 0) {
            final List var5 = Items.potionitem.getEffects(p_77624_1_);
            final HashMultimap var6 = HashMultimap.create();
            if (var5 != null && !var5.isEmpty()) {
                for (final PotionEffect var8 : var5) {
                    String var9 = StatCollector.translateToLocal(var8.getEffectName()).trim();
                    final Potion var10 = Potion.potionTypes[var8.getPotionID()];
                    final Map var11 = var10.func_111186_k();
                    if (var11 != null && var11.size() > 0) {
                        for (final Map.Entry var13 : var11.entrySet()) {
                            final AttributeModifier var14 = var13.getValue();
                            final AttributeModifier var15 = new AttributeModifier(var14.getName(), var10.func_111183_a(var8.getAmplifier(), var14), var14.getOperation());
                            var6.put((Object)var13.getKey().getAttributeUnlocalizedName(), (Object)var15);
                        }
                    }
                    if (var8.getAmplifier() > 0) {
                        var9 = var9 + " " + StatCollector.translateToLocal("potion.potency." + var8.getAmplifier()).trim();
                    }
                    if (var8.getDuration() > 20) {
                        var9 = var9 + " (" + Potion.getDurationString(var8) + ")";
                    }
                    if (var10.isBadEffect()) {
                        p_77624_3_.add(EnumChatFormatting.RED + var9);
                    }
                    else {
                        p_77624_3_.add(EnumChatFormatting.GRAY + var9);
                    }
                }
            }
            else {
                final String var16 = StatCollector.translateToLocal("potion.empty").trim();
                p_77624_3_.add(EnumChatFormatting.GRAY + var16);
            }
            if (!var6.isEmpty()) {
                p_77624_3_.add("");
                p_77624_3_.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));
                for (final Map.Entry var17 : var6.entries()) {
                    final AttributeModifier var18 = var17.getValue();
                    final double var19 = var18.getAmount();
                    double var20;
                    if (var18.getOperation() != 1 && var18.getOperation() != 2) {
                        var20 = var18.getAmount();
                    }
                    else {
                        var20 = var18.getAmount() * 100.0;
                    }
                    if (var19 > 0.0) {
                        p_77624_3_.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var18.getOperation(), ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + var17.getKey())));
                    }
                    else {
                        if (var19 >= 0.0) {
                            continue;
                        }
                        var20 *= -1.0;
                        p_77624_3_.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var18.getOperation(), ItemStack.field_111284_a.format(var20), StatCollector.translateToLocal("attribute.name." + var17.getKey())));
                    }
                }
            }
        }
    }
    
    @Override
    public boolean hasEffect(final ItemStack p_77636_1_) {
        final List var2 = this.getEffects(p_77636_1_);
        return var2 != null && !var2.isEmpty();
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        super.getSubItems(p_150895_1_, p_150895_2_, p_150895_3_);
        if (ItemPotion.field_77835_b.isEmpty()) {
            for (int var4 = 0; var4 <= 15; ++var4) {
                for (int var5 = 0; var5 <= 1; ++var5) {
                    int var6;
                    if (var5 == 0) {
                        var6 = (var4 | 0x2000);
                    }
                    else {
                        var6 = (var4 | 0x4000);
                    }
                    for (int var7 = 0; var7 <= 2; ++var7) {
                        int var8 = var6;
                        if (var7 != 0) {
                            if (var7 == 1) {
                                var8 = (var6 | 0x20);
                            }
                            else if (var7 == 2) {
                                var8 = (var6 | 0x40);
                            }
                        }
                        final List var9 = PotionHelper.getPotionEffects(var8, false);
                        if (var9 != null && !var9.isEmpty()) {
                            ItemPotion.field_77835_b.put(var9, var8);
                        }
                    }
                }
            }
        }
        for (final int var5 : ItemPotion.field_77835_b.values()) {
            p_150895_3_.add(new ItemStack(p_150895_1_, 1, var5));
        }
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        this.field_94590_d = p_94581_1_.registerIcon(this.getIconString() + "_bottle_drinkable");
        this.field_94591_c = p_94581_1_.registerIcon(this.getIconString() + "_bottle_splash");
        this.field_94592_ct = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
    }
    
    public static IIcon func_94589_d(final String p_94589_0_) {
        return p_94589_0_.equals("bottle_drinkable") ? Items.potionitem.field_94590_d : (p_94589_0_.equals("bottle_splash") ? Items.potionitem.field_94591_c : (p_94589_0_.equals("overlay") ? Items.potionitem.field_94592_ct : null));
    }
    
    static {
        field_77835_b = new LinkedHashMap();
    }
}
