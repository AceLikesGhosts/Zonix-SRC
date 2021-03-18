package net.minecraft.block;

import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.texture.*;

public class BlockMelon extends Block
{
    private IIcon field_150201_a;
    private static final String __OBFID = "CL_00000267";
    
    protected BlockMelon() {
        super(Material.field_151572_C);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @Override
    public IIcon getIcon(final int p_149691_1_, final int p_149691_2_) {
        return (p_149691_1_ != 1 && p_149691_1_ != 0) ? this.blockIcon : this.field_150201_a;
    }
    
    @Override
    public Item getItemDropped(final int p_149650_1_, final Random p_149650_2_, final int p_149650_3_) {
        return Items.melon;
    }
    
    @Override
    public int quantityDropped(final Random p_149745_1_) {
        return 3 + p_149745_1_.nextInt(5);
    }
    
    @Override
    public int quantityDroppedWithBonus(final int p_149679_1_, final Random p_149679_2_) {
        int var3 = this.quantityDropped(p_149679_2_) + p_149679_2_.nextInt(1 + p_149679_1_);
        if (var3 > 9) {
            var3 = 9;
        }
        return var3;
    }
    
    @Override
    public void registerBlockIcons(final IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.field_150201_a = p_149651_1_.registerIcon(this.getTextureName() + "_top");
    }
}
