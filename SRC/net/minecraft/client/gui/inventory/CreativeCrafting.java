package net.minecraft.client.gui.inventory;

import net.minecraft.client.*;
import net.minecraft.inventory.*;
import java.util.*;
import net.minecraft.item.*;

public class CreativeCrafting implements ICrafting
{
    private final Minecraft field_146109_a;
    private static final String __OBFID = "CL_00000751";
    
    public CreativeCrafting(final Minecraft p_i46314_1_) {
        this.field_146109_a = p_i46314_1_;
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container p_71110_1_, final List p_71110_2_) {
    }
    
    @Override
    public void sendSlotContents(final Container p_71111_1_, final int p_71111_2_, final ItemStack p_71111_3_) {
        this.field_146109_a.playerController.sendSlotPacket(p_71111_3_, p_71111_2_);
    }
    
    @Override
    public void sendProgressBarUpdate(final Container p_71112_1_, final int p_71112_2_, final int p_71112_3_) {
    }
}
