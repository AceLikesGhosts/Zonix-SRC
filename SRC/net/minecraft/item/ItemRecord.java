package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;

public class ItemRecord extends Item
{
    private static final Map field_150928_b;
    public final String field_150929_a;
    private static final String __OBFID = "CL_00000057";
    
    protected ItemRecord(final String p_i45350_1_) {
        this.field_150929_a = p_i45350_1_;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        ItemRecord.field_150928_b.put(p_i45350_1_, this);
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return this.itemIcon;
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) != Blocks.jukebox || p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) != 0) {
            return false;
        }
        if (p_77648_3_.isClient) {
            return true;
        }
        ((BlockJukebox)Blocks.jukebox).func_149926_b(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_1_);
        p_77648_3_.playAuxSFXAtEntity(null, 1005, p_77648_4_, p_77648_5_, p_77648_6_, Item.getIdFromItem(this));
        --p_77648_1_.stackSize;
        return true;
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        p_77624_3_.add(this.func_150927_i());
    }
    
    public String func_150927_i() {
        return StatCollector.translateToLocal("item.record." + this.field_150929_a + ".desc");
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack p_77613_1_) {
        return EnumRarity.rare;
    }
    
    public static ItemRecord func_150926_b(final String p_150926_0_) {
        return ItemRecord.field_150928_b.get(p_150926_0_);
    }
    
    static {
        field_150928_b = new HashMap();
    }
}
