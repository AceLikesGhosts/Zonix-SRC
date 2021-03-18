package net.minecraft.client.renderer.entity;

import net.minecraft.entity.ai.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.tileentity.*;

public class RenderMinecartMobSpawner extends RenderMinecart
{
    private static final String __OBFID = "CL_00001014";
    
    protected void func_147910_a(final EntityMinecartMobSpawner p_147910_1_, final float p_147910_2_, final Block p_147910_3_, final int p_147910_4_) {
        super.func_147910_a(p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
        if (p_147910_3_ == Blocks.mob_spawner) {
            TileEntityMobSpawnerRenderer.func_147517_a(p_147910_1_.func_98039_d(), p_147910_1_.posX, p_147910_1_.posY, p_147910_1_.posZ, p_147910_2_);
        }
    }
    
    @Override
    protected void func_147910_a(final EntityMinecart p_147910_1_, final float p_147910_2_, final Block p_147910_3_, final int p_147910_4_) {
        this.func_147910_a((EntityMinecartMobSpawner)p_147910_1_, p_147910_2_, p_147910_3_, p_147910_4_);
    }
}
