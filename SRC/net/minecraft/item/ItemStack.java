package net.minecraft.item;

import java.text.*;
import net.minecraft.entity.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.event.*;

public final class ItemStack
{
    public static final DecimalFormat field_111284_a;
    public int stackSize;
    public int animationsToGo;
    private Item field_151002_e;
    public NBTTagCompound stackTagCompound;
    private int itemDamage;
    private EntityItemFrame itemFrame;
    private static final String __OBFID = "CL_00000043";
    
    public ItemStack(final Block p_i1876_1_) {
        this(p_i1876_1_, 1);
    }
    
    public ItemStack(final Block p_i1877_1_, final int p_i1877_2_) {
        this(p_i1877_1_, p_i1877_2_, 0);
    }
    
    public ItemStack(final Block p_i1878_1_, final int p_i1878_2_, final int p_i1878_3_) {
        this(Item.getItemFromBlock(p_i1878_1_), p_i1878_2_, p_i1878_3_);
    }
    
    public ItemStack(final Item p_i1879_1_) {
        this(p_i1879_1_, 1);
    }
    
    public ItemStack(final Item p_i1880_1_, final int p_i1880_2_) {
        this(p_i1880_1_, p_i1880_2_, 0);
    }
    
    public ItemStack(final Item p_i1881_1_, final int p_i1881_2_, final int p_i1881_3_) {
        this.field_151002_e = p_i1881_1_;
        this.stackSize = p_i1881_2_;
        this.itemDamage = p_i1881_3_;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public static ItemStack loadItemStackFromNBT(final NBTTagCompound p_77949_0_) {
        final ItemStack var1 = new ItemStack();
        var1.readFromNBT(p_77949_0_);
        return (var1.getItem() != null) ? var1 : null;
    }
    
    private ItemStack() {
    }
    
    public ItemStack splitStack(final int p_77979_1_) {
        final ItemStack var2 = new ItemStack(this.field_151002_e, p_77979_1_, this.itemDamage);
        if (this.stackTagCompound != null) {
            var2.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        this.stackSize -= p_77979_1_;
        return var2;
    }
    
    public Item getItem() {
        return this.field_151002_e;
    }
    
    public IIcon getIconIndex() {
        return this.getItem().getIconIndex(this);
    }
    
    public int getItemSpriteNumber() {
        return this.getItem().getSpriteNumber();
    }
    
    public boolean tryPlaceItemIntoWorld(final EntityPlayer p_77943_1_, final World p_77943_2_, final int p_77943_3_, final int p_77943_4_, final int p_77943_5_, final int p_77943_6_, final float p_77943_7_, final float p_77943_8_, final float p_77943_9_) {
        final boolean var10 = this.getItem().onItemUse(this, p_77943_1_, p_77943_2_, p_77943_3_, p_77943_4_, p_77943_5_, p_77943_6_, p_77943_7_, p_77943_8_, p_77943_9_);
        if (var10) {
            p_77943_1_.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }
        return var10;
    }
    
    public float func_150997_a(final Block p_150997_1_) {
        return this.getItem().func_150893_a(this, p_150997_1_);
    }
    
    public ItemStack useItemRightClick(final World p_77957_1_, final EntityPlayer p_77957_2_) {
        return this.getItem().onItemRightClick(this, p_77957_1_, p_77957_2_);
    }
    
    public ItemStack onFoodEaten(final World p_77950_1_, final EntityPlayer p_77950_2_) {
        return this.getItem().onEaten(this, p_77950_1_, p_77950_2_);
    }
    
    public NBTTagCompound writeToNBT(final NBTTagCompound p_77955_1_) {
        p_77955_1_.setShort("id", (short)Item.getIdFromItem(this.field_151002_e));
        p_77955_1_.setByte("Count", (byte)this.stackSize);
        p_77955_1_.setShort("Damage", (short)this.itemDamage);
        if (this.stackTagCompound != null) {
            p_77955_1_.setTag("tag", this.stackTagCompound);
        }
        return p_77955_1_;
    }
    
    public void readFromNBT(final NBTTagCompound p_77963_1_) {
        this.field_151002_e = Item.getItemById(p_77963_1_.getShort("id"));
        this.stackSize = p_77963_1_.getByte("Count");
        this.itemDamage = p_77963_1_.getShort("Damage");
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
        if (p_77963_1_.func_150297_b("tag", 10)) {
            this.stackTagCompound = p_77963_1_.getCompoundTag("tag");
        }
    }
    
    public int getMaxStackSize() {
        return this.getItem().getItemStackLimit();
    }
    
    public boolean isStackable() {
        return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
    }
    
    public boolean isItemStackDamageable() {
        return this.field_151002_e.getMaxDamage() > 0 && (!this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable"));
    }
    
    public boolean getHasSubtypes() {
        return this.field_151002_e.getHasSubtypes();
    }
    
    public boolean isItemDamaged() {
        return this.isItemStackDamageable() && this.itemDamage > 0;
    }
    
    public int getItemDamageForDisplay() {
        return this.itemDamage;
    }
    
    public int getItemDamage() {
        return this.itemDamage;
    }
    
    public void setItemDamage(final int p_77964_1_) {
        this.itemDamage = p_77964_1_;
        if (this.itemDamage < 0) {
            this.itemDamage = 0;
        }
    }
    
    public int getMaxDamage() {
        return this.field_151002_e.getMaxDamage();
    }
    
    public boolean attemptDamageItem(int p_96631_1_, final Random p_96631_2_) {
        if (!this.isItemStackDamageable()) {
            return false;
        }
        if (p_96631_1_ > 0) {
            final int var3 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
            int var4 = 0;
            for (int var5 = 0; var3 > 0 && var5 < p_96631_1_; ++var5) {
                if (EnchantmentDurability.negateDamage(this, var3, p_96631_2_)) {
                    ++var4;
                }
            }
            p_96631_1_ -= var4;
            if (p_96631_1_ <= 0) {
                return false;
            }
        }
        this.itemDamage += p_96631_1_;
        return this.itemDamage > this.getMaxDamage();
    }
    
    public void damageItem(final int p_77972_1_, final EntityLivingBase p_77972_2_) {
        if ((!(p_77972_2_ instanceof EntityPlayer) || !((EntityPlayer)p_77972_2_).capabilities.isCreativeMode) && this.isItemStackDamageable() && this.attemptDamageItem(p_77972_1_, p_77972_2_.getRNG())) {
            p_77972_2_.renderBrokenItemStack(this);
            --this.stackSize;
            if (p_77972_2_ instanceof EntityPlayer) {
                final EntityPlayer var3 = (EntityPlayer)p_77972_2_;
                var3.addStat(StatList.objectBreakStats[Item.getIdFromItem(this.field_151002_e)], 1);
                if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
                    var3.destroyCurrentEquippedItem();
                }
            }
            if (this.stackSize < 0) {
                this.stackSize = 0;
            }
            this.itemDamage = 0;
        }
    }
    
    public void hitEntity(final EntityLivingBase p_77961_1_, final EntityPlayer p_77961_2_) {
        final boolean var3 = this.field_151002_e.hitEntity(this, p_77961_1_, p_77961_2_);
        if (var3) {
            p_77961_2_.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }
    }
    
    public void func_150999_a(final World p_150999_1_, final Block p_150999_2_, final int p_150999_3_, final int p_150999_4_, final int p_150999_5_, final EntityPlayer p_150999_6_) {
        final boolean var7 = this.field_151002_e.onBlockDestroyed(this, p_150999_1_, p_150999_2_, p_150999_3_, p_150999_4_, p_150999_5_, p_150999_6_);
        if (var7) {
            p_150999_6_.addStat(StatList.objectUseStats[Item.getIdFromItem(this.field_151002_e)], 1);
        }
    }
    
    public boolean func_150998_b(final Block p_150998_1_) {
        return this.field_151002_e.func_150897_b(p_150998_1_);
    }
    
    public boolean interactWithEntity(final EntityPlayer p_111282_1_, final EntityLivingBase p_111282_2_) {
        return this.field_151002_e.itemInteractionForEntity(this, p_111282_1_, p_111282_2_);
    }
    
    public ItemStack copy() {
        final ItemStack var1 = new ItemStack(this.field_151002_e, this.stackSize, this.itemDamage);
        if (this.stackTagCompound != null) {
            var1.stackTagCompound = (NBTTagCompound)this.stackTagCompound.copy();
        }
        return var1;
    }
    
    public static boolean areItemStackTagsEqual(final ItemStack p_77970_0_, final ItemStack p_77970_1_) {
        return (p_77970_0_ == null && p_77970_1_ == null) || (p_77970_0_ != null && p_77970_1_ != null && (p_77970_0_.stackTagCompound != null || p_77970_1_.stackTagCompound == null) && (p_77970_0_.stackTagCompound == null || p_77970_0_.stackTagCompound.equals(p_77970_1_.stackTagCompound)));
    }
    
    public static boolean areItemStacksEqual(final ItemStack p_77989_0_, final ItemStack p_77989_1_) {
        return (p_77989_0_ == null && p_77989_1_ == null) || (p_77989_0_ != null && p_77989_1_ != null && p_77989_0_.isItemStackEqual(p_77989_1_));
    }
    
    private boolean isItemStackEqual(final ItemStack p_77959_1_) {
        return this.stackSize == p_77959_1_.stackSize && this.field_151002_e == p_77959_1_.field_151002_e && this.itemDamage == p_77959_1_.itemDamage && (this.stackTagCompound != null || p_77959_1_.stackTagCompound == null) && (this.stackTagCompound == null || this.stackTagCompound.equals(p_77959_1_.stackTagCompound));
    }
    
    public boolean isItemEqual(final ItemStack p_77969_1_) {
        return this.field_151002_e == p_77969_1_.field_151002_e && this.itemDamage == p_77969_1_.itemDamage;
    }
    
    public String getUnlocalizedName() {
        return this.field_151002_e.getUnlocalizedName(this);
    }
    
    public static ItemStack copyItemStack(final ItemStack p_77944_0_) {
        return (p_77944_0_ == null) ? null : p_77944_0_.copy();
    }
    
    @Override
    public String toString() {
        return this.stackSize + "x" + this.field_151002_e.getUnlocalizedName() + "@" + this.itemDamage;
    }
    
    public void updateAnimation(final World p_77945_1_, final Entity p_77945_2_, final int p_77945_3_, final boolean p_77945_4_) {
        if (this.animationsToGo > 0) {
            --this.animationsToGo;
        }
        this.field_151002_e.onUpdate(this, p_77945_1_, p_77945_2_, p_77945_3_, p_77945_4_);
    }
    
    public void onCrafting(final World p_77980_1_, final EntityPlayer p_77980_2_, final int p_77980_3_) {
        p_77980_2_.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.field_151002_e)], p_77980_3_);
        this.field_151002_e.onCreated(this, p_77980_1_, p_77980_2_);
    }
    
    public int getMaxItemUseDuration() {
        return this.getItem().getMaxItemUseDuration(this);
    }
    
    public EnumAction getItemUseAction() {
        return this.getItem().getItemUseAction(this);
    }
    
    public void onPlayerStoppedUsing(final World p_77974_1_, final EntityPlayer p_77974_2_, final int p_77974_3_) {
        this.getItem().onPlayerStoppedUsing(this, p_77974_1_, p_77974_2_, p_77974_3_);
    }
    
    public boolean hasTagCompound() {
        return this.stackTagCompound != null;
    }
    
    public NBTTagCompound getTagCompound() {
        return this.stackTagCompound;
    }
    
    public NBTTagList getEnchantmentTagList() {
        return (this.stackTagCompound == null) ? null : this.stackTagCompound.getTagList("ench", 10);
    }
    
    public void setTagCompound(final NBTTagCompound p_77982_1_) {
        this.stackTagCompound = p_77982_1_;
    }
    
    public String getDisplayName() {
        String var1 = this.getItem().getItemStackDisplayName(this);
        if (this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10)) {
            final NBTTagCompound var2 = this.stackTagCompound.getCompoundTag("display");
            if (var2.func_150297_b("Name", 8)) {
                var1 = var2.getString("Name");
            }
        }
        return var1;
    }
    
    public ItemStack setStackDisplayName(final String p_151001_1_) {
        if (this.stackTagCompound == null) {
            this.stackTagCompound = new NBTTagCompound();
        }
        if (!this.stackTagCompound.func_150297_b("display", 10)) {
            this.stackTagCompound.setTag("display", new NBTTagCompound());
        }
        this.stackTagCompound.getCompoundTag("display").setString("Name", p_151001_1_);
        return this;
    }
    
    public void func_135074_t() {
        if (this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10)) {
            final NBTTagCompound var1 = this.stackTagCompound.getCompoundTag("display");
            var1.removeTag("Name");
            if (var1.hasNoTags()) {
                this.stackTagCompound.removeTag("display");
                if (this.stackTagCompound.hasNoTags()) {
                    this.setTagCompound(null);
                }
            }
        }
    }
    
    public boolean hasDisplayName() {
        return this.stackTagCompound != null && this.stackTagCompound.func_150297_b("display", 10) && this.stackTagCompound.getCompoundTag("display").func_150297_b("Name", 8);
    }
    
    public List getTooltip(final EntityPlayer p_82840_1_, final boolean p_82840_2_) {
        final ArrayList var3 = new ArrayList();
        String var4 = this.getDisplayName();
        if (this.hasDisplayName()) {
            var4 = EnumChatFormatting.ITALIC + var4 + EnumChatFormatting.RESET;
        }
        if (p_82840_2_) {
            String var5 = "";
            if (var4.length() > 0) {
                var4 += " (";
                var5 = ")";
            }
            final int var6 = Item.getIdFromItem(this.field_151002_e);
            if (this.getHasSubtypes()) {
                var4 += String.format("#%04d/%d%s", var6, this.itemDamage, var5);
            }
            else {
                var4 += String.format("#%04d%s", var6, var5);
            }
        }
        else if (!this.hasDisplayName() && this.field_151002_e == Items.filled_map) {
            var4 = var4 + " #" + this.itemDamage;
        }
        var3.add(var4);
        this.field_151002_e.addInformation(this, p_82840_1_, var3, p_82840_2_);
        if (this.hasTagCompound()) {
            final NBTTagList var7 = this.getEnchantmentTagList();
            if (var7 != null) {
                for (int var6 = 0; var6 < var7.tagCount(); ++var6) {
                    final short var8 = var7.getCompoundTagAt(var6).getShort("id");
                    final short var9 = var7.getCompoundTagAt(var6).getShort("lvl");
                    if (Enchantment.enchantmentsList[var8] != null) {
                        var3.add(Enchantment.enchantmentsList[var8].getTranslatedName(var9));
                    }
                }
            }
            if (this.stackTagCompound.func_150297_b("display", 10)) {
                final NBTTagCompound var10 = this.stackTagCompound.getCompoundTag("display");
                if (var10.func_150297_b("color", 3)) {
                    if (p_82840_2_) {
                        var3.add("Color: #" + Integer.toHexString(var10.getInteger("color")).toUpperCase());
                    }
                    else {
                        var3.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
                    }
                }
                if (var10.func_150299_b("Lore") == 9) {
                    final NBTTagList var11 = var10.getTagList("Lore", 8);
                    if (var11.tagCount() > 0) {
                        for (int var12 = 0; var12 < var11.tagCount(); ++var12) {
                            var3.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC + var11.getStringTagAt(var12));
                        }
                    }
                }
            }
        }
        final Multimap var13 = this.getAttributeModifiers();
        if (!var13.isEmpty()) {
            var3.add("");
            for (final Map.Entry var15 : var13.entries()) {
                final AttributeModifier var16 = var15.getValue();
                double var17 = var16.getAmount();
                if (var16.getID() == Item.field_111210_e) {
                    var17 += EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
                }
                double var18;
                if (var16.getOperation() != 1 && var16.getOperation() != 2) {
                    var18 = var17;
                }
                else {
                    var18 = var17 * 100.0;
                }
                if (var17 > 0.0) {
                    var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted("attribute.modifier.plus." + var16.getOperation(), ItemStack.field_111284_a.format(var18), StatCollector.translateToLocal("attribute.name." + var15.getKey())));
                }
                else {
                    if (var17 >= 0.0) {
                        continue;
                    }
                    var18 *= -1.0;
                    var3.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted("attribute.modifier.take." + var16.getOperation(), ItemStack.field_111284_a.format(var18), StatCollector.translateToLocal("attribute.name." + var15.getKey())));
                }
            }
        }
        if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable")) {
            var3.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
        }
        if (p_82840_2_ && this.isItemDamaged()) {
            var3.add("Durability: " + (this.getMaxDamage() - this.getItemDamageForDisplay()) + " / " + this.getMaxDamage());
        }
        return var3;
    }
    
    public boolean hasEffect() {
        return this.getItem().hasEffect(this);
    }
    
    public EnumRarity getRarity() {
        return this.getItem().getRarity(this);
    }
    
    public boolean isItemEnchantable() {
        return this.getItem().isItemTool(this) && !this.isItemEnchanted();
    }
    
    public void addEnchantment(final Enchantment p_77966_1_, final int p_77966_2_) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        if (!this.stackTagCompound.func_150297_b("ench", 9)) {
            this.stackTagCompound.setTag("ench", new NBTTagList());
        }
        final NBTTagList var3 = this.stackTagCompound.getTagList("ench", 10);
        final NBTTagCompound var4 = new NBTTagCompound();
        var4.setShort("id", (short)p_77966_1_.effectId);
        var4.setShort("lvl", (byte)p_77966_2_);
        var3.appendTag(var4);
    }
    
    public boolean isItemEnchanted() {
        return this.stackTagCompound != null && this.stackTagCompound.func_150297_b("ench", 9);
    }
    
    public void setTagInfo(final String p_77983_1_, final NBTBase p_77983_2_) {
        if (this.stackTagCompound == null) {
            this.setTagCompound(new NBTTagCompound());
        }
        this.stackTagCompound.setTag(p_77983_1_, p_77983_2_);
    }
    
    public boolean canEditBlocks() {
        return this.getItem().canItemEditBlocks();
    }
    
    public boolean isOnItemFrame() {
        return this.itemFrame != null;
    }
    
    public void setItemFrame(final EntityItemFrame p_82842_1_) {
        this.itemFrame = p_82842_1_;
    }
    
    public EntityItemFrame getItemFrame() {
        return this.itemFrame;
    }
    
    public int getRepairCost() {
        return (this.hasTagCompound() && this.stackTagCompound.func_150297_b("RepairCost", 3)) ? this.stackTagCompound.getInteger("RepairCost") : 0;
    }
    
    public void setRepairCost(final int p_82841_1_) {
        if (!this.hasTagCompound()) {
            this.stackTagCompound = new NBTTagCompound();
        }
        this.stackTagCompound.setInteger("RepairCost", p_82841_1_);
    }
    
    public Multimap getAttributeModifiers() {
        Object var1;
        if (this.hasTagCompound() && this.stackTagCompound.func_150297_b("AttributeModifiers", 9)) {
            var1 = HashMultimap.create();
            final NBTTagList var2 = this.stackTagCompound.getTagList("AttributeModifiers", 10);
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                final NBTTagCompound var4 = var2.getCompoundTagAt(var3);
                final AttributeModifier var5 = SharedMonsterAttributes.readAttributeModifierFromNBT(var4);
                if (var5.getID().getLeastSignificantBits() != 0L && var5.getID().getMostSignificantBits() != 0L) {
                    ((Multimap)var1).put((Object)var4.getString("AttributeName"), (Object)var5);
                }
            }
        }
        else {
            var1 = this.getItem().getItemAttributeModifiers();
        }
        return (Multimap)var1;
    }
    
    public void func_150996_a(final Item p_150996_1_) {
        this.field_151002_e = p_150996_1_;
    }
    
    public IChatComponent func_151000_E() {
        final IChatComponent var1 = new ChatComponentText("[").appendText(this.getDisplayName()).appendText("]");
        if (this.field_151002_e != null) {
            final NBTTagCompound var2 = new NBTTagCompound();
            this.writeToNBT(var2);
            var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(var2.toString())));
            var1.getChatStyle().setColor(this.getRarity().rarityColor);
        }
        return var1;
    }
    
    static {
        field_111284_a = new DecimalFormat("#.###");
    }
}
