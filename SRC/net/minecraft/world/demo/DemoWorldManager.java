package net.minecraft.world.demo;

import net.minecraft.server.management.*;
import net.minecraft.world.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean field_73105_c;
    private boolean demoTimeExpired;
    private int field_73104_e;
    private int field_73102_f;
    private static final String __OBFID = "CL_00001429";
    
    public DemoWorldManager(final World p_i1513_1_) {
        super(p_i1513_1_);
    }
    
    @Override
    public void updateBlockRemoving() {
        super.updateBlockRemoving();
        ++this.field_73102_f;
        final long var1 = this.theWorld.getTotalWorldTime();
        final long var2 = var1 / 24000L + 1L;
        if (!this.field_73105_c && this.field_73102_f > 20) {
            this.field_73105_c = true;
            this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 0.0f));
        }
        this.demoTimeExpired = (var1 > 120500L);
        if (this.demoTimeExpired) {
            ++this.field_73104_e;
        }
        if (var1 % 24000L == 500L) {
            if (var2 <= 6L) {
                this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day." + var2, new Object[0]));
            }
        }
        else if (var2 == 1L) {
            if (var1 == 100L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 101.0f));
            }
            else if (var1 == 175L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 102.0f));
            }
            else if (var1 == 250L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(5, 103.0f));
            }
        }
        else if (var2 == 5L && var1 % 24000L == 22000L) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.day.warning", new Object[0]));
        }
    }
    
    private void sendDemoReminder() {
        if (this.field_73104_e > 100) {
            this.thisPlayerMP.addChatMessage(new ChatComponentTranslation("demo.reminder", new Object[0]));
            this.field_73104_e = 0;
        }
    }
    
    @Override
    public void onBlockClicked(final int p_73074_1_, final int p_73074_2_, final int p_73074_3_, final int p_73074_4_) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
        }
        else {
            super.onBlockClicked(p_73074_1_, p_73074_2_, p_73074_3_, p_73074_4_);
        }
    }
    
    @Override
    public void uncheckedTryHarvestBlock(final int p_73082_1_, final int p_73082_2_, final int p_73082_3_) {
        if (!this.demoTimeExpired) {
            super.uncheckedTryHarvestBlock(p_73082_1_, p_73082_2_, p_73082_3_);
        }
    }
    
    @Override
    public boolean tryHarvestBlock(final int p_73084_1_, final int p_73084_2_, final int p_73084_3_) {
        return !this.demoTimeExpired && super.tryHarvestBlock(p_73084_1_, p_73084_2_, p_73084_3_);
    }
    
    @Override
    public boolean tryUseItem(final EntityPlayer p_73085_1_, final World p_73085_2_, final ItemStack p_73085_3_) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.tryUseItem(p_73085_1_, p_73085_2_, p_73085_3_);
    }
    
    @Override
    public boolean activateBlockOrUseItem(final EntityPlayer p_73078_1_, final World p_73078_2_, final ItemStack p_73078_3_, final int p_73078_4_, final int p_73078_5_, final int p_73078_6_, final int p_73078_7_, final float p_73078_8_, final float p_73078_9_, final float p_73078_10_) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        }
        return super.activateBlockOrUseItem(p_73078_1_, p_73078_2_, p_73078_3_, p_73078_4_, p_73078_5_, p_73078_6_, p_73078_7_, p_73078_8_, p_73078_9_, p_73078_10_);
    }
}
