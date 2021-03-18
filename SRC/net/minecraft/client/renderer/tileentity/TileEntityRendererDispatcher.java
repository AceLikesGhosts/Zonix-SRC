package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.client.renderer.entity.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.crash.*;

public class TileEntityRendererDispatcher
{
    private Map mapSpecialRenderers;
    public static TileEntityRendererDispatcher instance;
    private FontRenderer field_147557_n;
    public static double staticPlayerX;
    public static double staticPlayerY;
    public static double staticPlayerZ;
    public TextureManager field_147553_e;
    public World field_147550_f;
    public EntityLivingBase field_147551_g;
    public float field_147562_h;
    public float field_147563_i;
    public double field_147560_j;
    public double field_147561_k;
    public double field_147558_l;
    private static final String __OBFID = "CL_00000963";
    
    private TileEntityRendererDispatcher() {
        (this.mapSpecialRenderers = new HashMap()).put(TileEntitySign.class, new TileEntitySignRenderer());
        this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityRendererPiston());
        this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new RenderEnchantmentTable());
        this.mapSpecialRenderers.put(TileEntityEndPortal.class, new RenderEndPortal());
        this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        for (final TileEntitySpecialRenderer var2 : this.mapSpecialRenderers.values()) {
            var2.func_147497_a(this);
        }
    }
    
    public TileEntitySpecialRenderer getSpecialRendererByClass(final Class p_147546_1_) {
        TileEntitySpecialRenderer var2 = (TileEntitySpecialRenderer)this.mapSpecialRenderers.get(p_147546_1_);
        if (var2 == null && p_147546_1_ != TileEntity.class) {
            var2 = this.getSpecialRendererByClass(p_147546_1_.getSuperclass());
            this.mapSpecialRenderers.put(p_147546_1_, var2);
        }
        return var2;
    }
    
    public boolean hasSpecialRenderer(final TileEntity p_147545_1_) {
        return this.getSpecialRenderer(p_147545_1_) != null;
    }
    
    public TileEntitySpecialRenderer getSpecialRenderer(final TileEntity p_147547_1_) {
        return (p_147547_1_ == null) ? null : this.getSpecialRendererByClass(p_147547_1_.getClass());
    }
    
    public void func_147542_a(final World p_147542_1_, final TextureManager p_147542_2_, final FontRenderer p_147542_3_, final EntityLivingBase p_147542_4_, final float p_147542_5_) {
        if (this.field_147550_f != p_147542_1_) {
            this.func_147543_a(p_147542_1_);
        }
        this.field_147553_e = p_147542_2_;
        this.field_147551_g = p_147542_4_;
        this.field_147557_n = p_147542_3_;
        this.field_147562_h = p_147542_4_.prevRotationYaw + (p_147542_4_.rotationYaw - p_147542_4_.prevRotationYaw) * p_147542_5_;
        this.field_147563_i = p_147542_4_.prevRotationPitch + (p_147542_4_.rotationPitch - p_147542_4_.prevRotationPitch) * p_147542_5_;
        this.field_147560_j = p_147542_4_.lastTickPosX + (p_147542_4_.posX - p_147542_4_.lastTickPosX) * p_147542_5_;
        this.field_147561_k = p_147542_4_.lastTickPosY + (p_147542_4_.posY - p_147542_4_.lastTickPosY) * p_147542_5_;
        this.field_147558_l = p_147542_4_.lastTickPosZ + (p_147542_4_.posZ - p_147542_4_.lastTickPosZ) * p_147542_5_;
    }
    
    public void func_147544_a(final TileEntity p_147544_1_, final float p_147544_2_) {
        if (p_147544_1_.getDistanceFrom(this.field_147560_j, this.field_147561_k, this.field_147558_l) < p_147544_1_.getMaxRenderDistanceSquared()) {
            final int var3 = this.field_147550_f.getLightBrightnessForSkyBlocks(p_147544_1_.field_145851_c, p_147544_1_.field_145848_d, p_147544_1_.field_145849_e, 0);
            final int var4 = var3 % 65536;
            final int var5 = var3 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var4 / 1.0f, var5 / 1.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.func_147549_a(p_147544_1_, p_147544_1_.field_145851_c - TileEntityRendererDispatcher.staticPlayerX, p_147544_1_.field_145848_d - TileEntityRendererDispatcher.staticPlayerY, p_147544_1_.field_145849_e - TileEntityRendererDispatcher.staticPlayerZ, p_147544_2_);
        }
    }
    
    public void func_147549_a(final TileEntity p_147549_1_, final double p_147549_2_, final double p_147549_4_, final double p_147549_6_, final float p_147549_8_) {
        final TileEntitySpecialRenderer var9 = this.getSpecialRenderer(p_147549_1_);
        if (var9 != null) {
            try {
                var9.renderTileEntityAt(p_147549_1_, p_147549_2_, p_147549_4_, p_147549_6_, p_147549_8_);
            }
            catch (Throwable var11) {
                final CrashReport var10 = CrashReport.makeCrashReport(var11, "Rendering Block Entity");
                final CrashReportCategory var12 = var10.makeCategory("Block Entity Details");
                p_147549_1_.func_145828_a(var12);
                throw new ReportedException(var10);
            }
        }
    }
    
    public void func_147543_a(final World p_147543_1_) {
        this.field_147550_f = p_147543_1_;
        for (final TileEntitySpecialRenderer var3 : this.mapSpecialRenderers.values()) {
            if (var3 != null) {
                var3.func_147496_a(p_147543_1_);
            }
        }
    }
    
    public FontRenderer func_147548_a() {
        return this.field_147557_n;
    }
    
    static {
        TileEntityRendererDispatcher.instance = new TileEntityRendererDispatcher();
    }
}
