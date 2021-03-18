package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class ItemFirework extends Item
{
    private static final String __OBFID = "CL_00000031";
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, final int p_77648_4_, final int p_77648_5_, final int p_77648_6_, final int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        if (!p_77648_3_.isClient) {
            final EntityFireworkRocket var11 = new EntityFireworkRocket(p_77648_3_, p_77648_4_ + p_77648_8_, p_77648_5_ + p_77648_9_, p_77648_6_ + p_77648_10_, p_77648_1_);
            p_77648_3_.spawnEntityInWorld(var11);
            if (!p_77648_2_.capabilities.isCreativeMode) {
                --p_77648_1_.stackSize;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        if (p_77624_1_.hasTagCompound()) {
            final NBTTagCompound var5 = p_77624_1_.getTagCompound().getCompoundTag("Fireworks");
            if (var5 != null) {
                if (var5.func_150297_b("Flight", 99)) {
                    p_77624_3_.add(StatCollector.translateToLocal("item.fireworks.flight") + " " + var5.getByte("Flight"));
                }
                final NBTTagList var6 = var5.getTagList("Explosions", 10);
                if (var6 != null && var6.tagCount() > 0) {
                    for (int var7 = 0; var7 < var6.tagCount(); ++var7) {
                        final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
                        final ArrayList var9 = new ArrayList();
                        ItemFireworkCharge.func_150902_a(var8, var9);
                        if (var9.size() > 0) {
                            for (int var10 = 1; var10 < var9.size(); ++var10) {
                                var9.set(var10, "  " + var9.get(var10));
                            }
                            p_77624_3_.addAll(var9);
                        }
                    }
                }
            }
        }
    }
}
