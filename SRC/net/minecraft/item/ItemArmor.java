package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.dispenser.*;
import net.minecraft.entity.*;
import net.minecraft.command.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemArmor extends Item
{
    private static final int[] maxDamageArray;
    private static final String[] CLOTH_OVERLAY_NAMES;
    public static final String[] EMPTY_SLOT_NAMES;
    private static final IBehaviorDispenseItem dispenserBehavior;
    public final int armorType;
    public final int damageReduceAmount;
    public final int renderIndex;
    private final ArmorMaterial material;
    private IIcon overlayIcon;
    private IIcon emptySlotIcon;
    private static final String __OBFID = "CL_00001766";
    
    public ItemArmor(final ArmorMaterial p_i45325_1_, final int p_i45325_2_, final int p_i45325_3_) {
        this.material = p_i45325_1_;
        this.armorType = p_i45325_3_;
        this.renderIndex = p_i45325_2_;
        this.damageReduceAmount = p_i45325_1_.getDamageReductionAmount(p_i45325_3_);
        this.setMaxDamage(p_i45325_1_.getDurability(p_i45325_3_));
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemArmor.dispenserBehavior);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack p_82790_1_, final int p_82790_2_) {
        if (p_82790_2_ > 0) {
            return 16777215;
        }
        int var3 = this.getColor(p_82790_1_);
        if (var3 < 0) {
            var3 = 16777215;
        }
        return var3;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return this.material == ArmorMaterial.CLOTH;
    }
    
    @Override
    public int getItemEnchantability() {
        return this.material.getEnchantability();
    }
    
    public ArmorMaterial getArmorMaterial() {
        return this.material;
    }
    
    public boolean hasColor(final ItemStack p_82816_1_) {
        return this.material == ArmorMaterial.CLOTH && p_82816_1_.hasTagCompound() && p_82816_1_.getTagCompound().func_150297_b("display", 10) && p_82816_1_.getTagCompound().getCompoundTag("display").func_150297_b("color", 3);
    }
    
    public int getColor(final ItemStack p_82814_1_) {
        if (this.material != ArmorMaterial.CLOTH) {
            return -1;
        }
        final NBTTagCompound var2 = p_82814_1_.getTagCompound();
        if (var2 == null) {
            return 10511680;
        }
        final NBTTagCompound var3 = var2.getCompoundTag("display");
        return (var3 == null) ? 10511680 : (var3.func_150297_b("color", 3) ? var3.getInteger("color") : 10511680);
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(final int p_77618_1_, final int p_77618_2_) {
        return (p_77618_2_ == 1) ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }
    
    public void removeColor(final ItemStack p_82815_1_) {
        if (this.material == ArmorMaterial.CLOTH) {
            final NBTTagCompound var2 = p_82815_1_.getTagCompound();
            if (var2 != null) {
                final NBTTagCompound var3 = var2.getCompoundTag("display");
                if (var3.hasKey("color")) {
                    var3.removeTag("color");
                }
            }
        }
    }
    
    public void func_82813_b(final ItemStack p_82813_1_, final int p_82813_2_) {
        if (this.material != ArmorMaterial.CLOTH) {
            throw new UnsupportedOperationException("Can't dye non-leather!");
        }
        NBTTagCompound var3 = p_82813_1_.getTagCompound();
        if (var3 == null) {
            var3 = new NBTTagCompound();
            p_82813_1_.setTagCompound(var3);
        }
        final NBTTagCompound var4 = var3.getCompoundTag("display");
        if (!var3.func_150297_b("display", 10)) {
            var3.setTag("display", var4);
        }
        var4.setInteger("color", p_82813_2_);
    }
    
    @Override
    public boolean getIsRepairable(final ItemStack p_82789_1_, final ItemStack p_82789_2_) {
        return this.material.func_151685_b() == p_82789_2_.getItem() || super.getIsRepairable(p_82789_1_, p_82789_2_);
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        super.registerIcons(p_94581_1_);
        if (this.material == ArmorMaterial.CLOTH) {
            this.overlayIcon = p_94581_1_.registerIcon(ItemArmor.CLOTH_OVERLAY_NAMES[this.armorType]);
        }
        this.emptySlotIcon = p_94581_1_.registerIcon(ItemArmor.EMPTY_SLOT_NAMES[this.armorType]);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack p_77659_1_, final World p_77659_2_, final EntityPlayer p_77659_3_) {
        final int var4 = EntityLiving.getArmorPosition(p_77659_1_) - 1;
        final ItemStack var5 = p_77659_3_.getCurrentArmor(var4);
        if (var5 == null) {
            p_77659_3_.setCurrentItemOrArmor(var4, p_77659_1_.copy());
            p_77659_1_.stackSize = 0;
        }
        return p_77659_1_;
    }
    
    public static IIcon func_94602_b(final int p_94602_0_) {
        switch (p_94602_0_) {
            case 0: {
                return Items.diamond_helmet.emptySlotIcon;
            }
            case 1: {
                return Items.diamond_chestplate.emptySlotIcon;
            }
            case 2: {
                return Items.diamond_leggings.emptySlotIcon;
            }
            case 3: {
                return Items.diamond_boots.emptySlotIcon;
            }
            default: {
                return null;
            }
        }
    }
    
    static {
        maxDamageArray = new int[] { 11, 16, 15, 13 };
        CLOTH_OVERLAY_NAMES = new String[] { "leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay" };
        EMPTY_SLOT_NAMES = new String[] { "empty_armor_slot_helmet", "empty_armor_slot_chestplate", "empty_armor_slot_leggings", "empty_armor_slot_boots" };
        dispenserBehavior = new BehaviorDefaultDispenseItem() {
            private static final String __OBFID = "CL_00001767";
            
            @Override
            protected ItemStack dispenseStack(final IBlockSource p_82487_1_, final ItemStack p_82487_2_) {
                final EnumFacing var3 = BlockDispenser.func_149937_b(p_82487_1_.getBlockMetadata());
                final int var4 = p_82487_1_.getXInt() + var3.getFrontOffsetX();
                final int var5 = p_82487_1_.getYInt() + var3.getFrontOffsetY();
                final int var6 = p_82487_1_.getZInt() + var3.getFrontOffsetZ();
                final AxisAlignedBB var7 = AxisAlignedBB.getBoundingBox(var4, var5, var6, var4 + 1, var5 + 1, var6 + 1);
                final List var8 = p_82487_1_.getWorld().selectEntitiesWithinAABB(EntityLivingBase.class, var7, new IEntitySelector.ArmoredMob(p_82487_2_));
                if (var8.size() > 0) {
                    final EntityLivingBase var9 = var8.get(0);
                    final int var10 = (var9 instanceof EntityPlayer) ? 1 : 0;
                    final int var11 = EntityLiving.getArmorPosition(p_82487_2_);
                    final ItemStack var12 = p_82487_2_.copy();
                    var12.stackSize = 1;
                    var9.setCurrentItemOrArmor(var11 - var10, var12);
                    if (var9 instanceof EntityLiving) {
                        ((EntityLiving)var9).setEquipmentDropChance(var11, 2.0f);
                    }
                    --p_82487_2_.stackSize;
                    return p_82487_2_;
                }
                return super.dispenseStack(p_82487_1_, p_82487_2_);
            }
        };
    }
    
    public enum ArmorMaterial
    {
        CLOTH("CLOTH", 0, 5, new int[] { 1, 3, 2, 1 }, 15), 
        CHAIN("CHAIN", 1, 15, new int[] { 2, 5, 4, 1 }, 12), 
        IRON("IRON", 2, 15, new int[] { 2, 6, 5, 2 }, 9), 
        GOLD("GOLD", 3, 7, new int[] { 2, 5, 3, 1 }, 25), 
        DIAMOND("DIAMOND", 4, 33, new int[] { 3, 8, 6, 3 }, 10);
        
        private int maxDamageFactor;
        private int[] damageReductionAmountArray;
        private int enchantability;
        private static final ArmorMaterial[] $VALUES;
        private static final String __OBFID = "CL_00001768";
        
        private ArmorMaterial(final String p_i1827_1_, final int p_i1827_2_, final int p_i1827_3_, final int[] p_i1827_4_, final int p_i1827_5_) {
            this.maxDamageFactor = p_i1827_3_;
            this.damageReductionAmountArray = p_i1827_4_;
            this.enchantability = p_i1827_5_;
        }
        
        public int getDurability(final int p_78046_1_) {
            return ItemArmor.maxDamageArray[p_78046_1_] * this.maxDamageFactor;
        }
        
        public int getDamageReductionAmount(final int p_78044_1_) {
            return this.damageReductionAmountArray[p_78044_1_];
        }
        
        public int getEnchantability() {
            return this.enchantability;
        }
        
        public Item func_151685_b() {
            return (this == ArmorMaterial.CLOTH) ? Items.leather : ((this == ArmorMaterial.CHAIN) ? Items.iron_ingot : ((this == ArmorMaterial.GOLD) ? Items.gold_ingot : ((this == ArmorMaterial.IRON) ? Items.iron_ingot : ((this == ArmorMaterial.DIAMOND) ? Items.diamond : null))));
        }
        
        static {
            $VALUES = new ArmorMaterial[] { ArmorMaterial.CLOTH, ArmorMaterial.CHAIN, ArmorMaterial.IRON, ArmorMaterial.GOLD, ArmorMaterial.DIAMOND };
        }
    }
}
