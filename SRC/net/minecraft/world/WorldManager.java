package net.minecraft.world;

import net.minecraft.server.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import java.util.*;

public class WorldManager implements IWorldAccess
{
    private MinecraftServer mcServer;
    private WorldServer theWorldServer;
    private static final String __OBFID = "CL_00001433";
    
    public WorldManager(final MinecraftServer p_i1517_1_, final WorldServer p_i1517_2_) {
        this.mcServer = p_i1517_1_;
        this.theWorldServer = p_i1517_2_;
    }
    
    @Override
    public void spawnParticle(final String p_72708_1_, final double p_72708_2_, final double p_72708_4_, final double p_72708_6_, final double p_72708_8_, final double p_72708_10_, final double p_72708_12_) {
    }
    
    @Override
    public void onEntityCreate(final Entity p_72703_1_) {
        this.theWorldServer.getEntityTracker().addEntityToTracker(p_72703_1_);
    }
    
    @Override
    public void onEntityDestroy(final Entity p_72709_1_) {
        this.theWorldServer.getEntityTracker().removeEntityFromAllTrackingPlayers(p_72709_1_);
    }
    
    @Override
    public void playSound(final String p_72704_1_, final double p_72704_2_, final double p_72704_4_, final double p_72704_6_, final float p_72704_8_, final float p_72704_9_) {
        this.mcServer.getConfigurationManager().func_148541_a(p_72704_2_, p_72704_4_, p_72704_6_, (p_72704_8_ > 1.0f) ? ((double)(16.0f * p_72704_8_)) : 16.0, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(p_72704_1_, p_72704_2_, p_72704_4_, p_72704_6_, p_72704_8_, p_72704_9_));
    }
    
    @Override
    public void playSoundToNearExcept(final EntityPlayer p_85102_1_, final String p_85102_2_, final double p_85102_3_, final double p_85102_5_, final double p_85102_7_, final float p_85102_9_, final float p_85102_10_) {
        this.mcServer.getConfigurationManager().func_148543_a(p_85102_1_, p_85102_3_, p_85102_5_, p_85102_7_, (p_85102_9_ > 1.0f) ? ((double)(16.0f * p_85102_9_)) : 16.0, this.theWorldServer.provider.dimensionId, new S29PacketSoundEffect(p_85102_2_, p_85102_3_, p_85102_5_, p_85102_7_, p_85102_9_, p_85102_10_));
    }
    
    @Override
    public void markBlockRangeForRenderUpdate(final int p_147585_1_, final int p_147585_2_, final int p_147585_3_, final int p_147585_4_, final int p_147585_5_, final int p_147585_6_) {
    }
    
    @Override
    public void markBlockForUpdate(final int p_147586_1_, final int p_147586_2_, final int p_147586_3_) {
        this.theWorldServer.getPlayerManager().func_151250_a(p_147586_1_, p_147586_2_, p_147586_3_);
    }
    
    @Override
    public void markBlockForRenderUpdate(final int p_147588_1_, final int p_147588_2_, final int p_147588_3_) {
    }
    
    @Override
    public void playRecord(final String p_72702_1_, final int p_72702_2_, final int p_72702_3_, final int p_72702_4_) {
    }
    
    @Override
    public void playAuxSFX(final EntityPlayer p_72706_1_, final int p_72706_2_, final int p_72706_3_, final int p_72706_4_, final int p_72706_5_, final int p_72706_6_) {
        this.mcServer.getConfigurationManager().func_148543_a(p_72706_1_, p_72706_3_, p_72706_4_, p_72706_5_, 64.0, this.theWorldServer.provider.dimensionId, new S28PacketEffect(p_72706_2_, p_72706_3_, p_72706_4_, p_72706_5_, p_72706_6_, false));
    }
    
    @Override
    public void broadcastSound(final int p_82746_1_, final int p_82746_2_, final int p_82746_3_, final int p_82746_4_, final int p_82746_5_) {
        this.mcServer.getConfigurationManager().func_148540_a(new S28PacketEffect(p_82746_1_, p_82746_2_, p_82746_3_, p_82746_4_, p_82746_5_, true));
    }
    
    @Override
    public void destroyBlockPartially(final int p_147587_1_, final int p_147587_2_, final int p_147587_3_, final int p_147587_4_, final int p_147587_5_) {
        for (final EntityPlayerMP var7 : this.mcServer.getConfigurationManager().playerEntityList) {
            if (var7 != null && var7.worldObj == this.theWorldServer && var7.getEntityId() != p_147587_1_) {
                final double var8 = p_147587_2_ - var7.posX;
                final double var9 = p_147587_3_ - var7.posY;
                final double var10 = p_147587_4_ - var7.posZ;
                if (var8 * var8 + var9 * var9 + var10 * var10 >= 1024.0) {
                    continue;
                }
                var7.playerNetServerHandler.sendPacket(new S25PacketBlockBreakAnim(p_147587_1_, p_147587_2_, p_147587_3_, p_147587_4_, p_147587_5_));
            }
        }
    }
    
    @Override
    public void onStaticEntitiesChanged() {
    }
}
