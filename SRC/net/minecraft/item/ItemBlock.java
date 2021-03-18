package net.minecraft.item;

import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;

public class ItemBlock extends Item
{
    protected final Block field_150939_a;
    private IIcon field_150938_b;
    private static final String __OBFID = "CL_00001772";
    
    public ItemBlock(final Block p_i45328_1_) {
        this.field_150939_a = p_i45328_1_;
    }
    
    @Override
    public ItemBlock setUnlocalizedName(final String p_77655_1_) {
        super.setUnlocalizedName(p_77655_1_);
        return this;
    }
    
    @Override
    public int getSpriteNumber() {
        return (this.field_150939_a.getItemIconName() != null) ? 1 : 0;
    }
    
    @Override
    public IIcon getIconFromDamage(final int p_77617_1_) {
        return (this.field_150938_b != null) ? this.field_150938_b : this.field_150939_a.getBlockTextureFromSide(1);
    }
    
    @Override
    public boolean onItemUse(final ItemStack p_77648_1_, final EntityPlayer p_77648_2_, final World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, final float p_77648_8_, final float p_77648_9_, final float p_77648_10_) {
        final Block var11 = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (var11 == Blocks.snow_layer && (p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_) & 0x7) < 1) {
            p_77648_7_ = 1;
        }
        else if (var11 != Blocks.vine && var11 != Blocks.tallgrass && var11 != Blocks.deadbush) {
            if (p_77648_7_ == 0) {
                --p_77648_5_;
            }
            if (p_77648_7_ == 1) {
                ++p_77648_5_;
            }
            if (p_77648_7_ == 2) {
                --p_77648_6_;
            }
            if (p_77648_7_ == 3) {
                ++p_77648_6_;
            }
            if (p_77648_7_ == 4) {
                --p_77648_4_;
            }
            if (p_77648_7_ == 5) {
                ++p_77648_4_;
            }
        }
        if (p_77648_1_.stackSize == 0) {
            return false;
        }
        if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        }
        if (p_77648_5_ == 255 && this.field_150939_a.getMaterial().isSolid()) {
            return false;
        }
        if (p_77648_3_.canPlaceEntityOnSide(this.field_150939_a, p_77648_4_, p_77648_5_, p_77648_6_, false, p_77648_7_, p_77648_2_, p_77648_1_)) {
            final int var12 = this.getMetadata(p_77648_1_.getItemDamage());
            final int var13 = this.field_150939_a.onBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_, var12);
            if (p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, this.field_150939_a, var13, 3)) {
                if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == this.field_150939_a) {
                    this.field_150939_a.onBlockPlacedBy(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_2_, p_77648_1_);
                    this.field_150939_a.onPostBlockPlaced(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, var13);
                }
                p_77648_3_.playSoundEffect(p_77648_4_ + 0.5f, p_77648_5_ + 0.5f, p_77648_6_ + 0.5f, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.func_150497_c() + 1.0f) / 2.0f, this.field_150939_a.stepSound.func_150494_d() * 0.8f);
                --p_77648_1_.stackSize;
            }
            return true;
        }
        return false;
    }
    
    public boolean func_150936_a(final World p_150936_1_, int p_150936_2_, int p_150936_3_, int p_150936_4_, int p_150936_5_, final EntityPlayer p_150936_6_, final ItemStack p_150936_7_) {
        final Block var8 = p_150936_1_.getBlock(p_150936_2_, p_150936_3_, p_150936_4_);
        if (var8 == Blocks.snow_layer) {
            p_150936_5_ = 1;
        }
        else if (var8 != Blocks.vine && var8 != Blocks.tallgrass && var8 != Blocks.deadbush) {
            if (p_150936_5_ == 0) {
                --p_150936_3_;
            }
            if (p_150936_5_ == 1) {
                ++p_150936_3_;
            }
            if (p_150936_5_ == 2) {
                --p_150936_4_;
            }
            if (p_150936_5_ == 3) {
                ++p_150936_4_;
            }
            if (p_150936_5_ == 4) {
                --p_150936_2_;
            }
            if (p_150936_5_ == 5) {
                ++p_150936_2_;
            }
        }
        return p_150936_1_.canPlaceEntityOnSide(this.field_150939_a, p_150936_2_, p_150936_3_, p_150936_4_, false, p_150936_5_, null, p_150936_7_);
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack p_77667_1_) {
        return this.field_150939_a.getUnlocalizedName();
    }
    
    @Override
    public String getUnlocalizedName() {
        return this.field_150939_a.getUnlocalizedName();
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return this.field_150939_a.getCreativeTabToDisplayOn();
    }
    
    @Override
    public void getSubItems(final Item p_150895_1_, final CreativeTabs p_150895_2_, final List p_150895_3_) {
        this.field_150939_a.getSubBlocks(p_150895_1_, p_150895_2_, p_150895_3_);
    }
    
    @Override
    public void registerIcons(final IIconRegister p_94581_1_) {
        final String var2 = this.field_150939_a.getItemIconName();
        if (var2 != null) {
            this.field_150938_b = p_94581_1_.registerIcon(var2);
        }
    }
}
