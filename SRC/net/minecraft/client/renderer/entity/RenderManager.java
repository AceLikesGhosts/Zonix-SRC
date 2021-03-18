package net.minecraft.client.renderer.entity;

import net.minecraft.client.gui.*;
import net.minecraft.world.*;
import net.minecraft.client.settings.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.tileentity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.effect.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import org.lwjgl.opengl.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;

public class RenderManager
{
    private Map entityRenderMap;
    public static RenderManager instance;
    private FontRenderer fontRenderer;
    public static double renderPosX;
    public static double renderPosY;
    public static double renderPosZ;
    public TextureManager renderEngine;
    public ItemRenderer itemRenderer;
    public World worldObj;
    public EntityLivingBase livingPlayer;
    public Entity field_147941_i;
    public float playerViewY;
    public float playerViewX;
    public GameSettings options;
    public double viewerPosX;
    public double viewerPosY;
    public double viewerPosZ;
    public static boolean field_85095_o;
    private static final String __OBFID = "CL_00000991";
    
    private RenderManager() {
        (this.entityRenderMap = new HashMap()).put(EntityCaveSpider.class, new RenderCaveSpider());
        this.entityRenderMap.put(EntitySpider.class, new RenderSpider());
        this.entityRenderMap.put(EntityPig.class, new RenderPig(new ModelPig(), new ModelPig(0.5f), 0.7f));
        this.entityRenderMap.put(EntitySheep.class, new RenderSheep(new ModelSheep2(), new ModelSheep1(), 0.7f));
        this.entityRenderMap.put(EntityCow.class, new RenderCow(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityMooshroom.class, new RenderMooshroom(new ModelCow(), 0.7f));
        this.entityRenderMap.put(EntityWolf.class, new RenderWolf(new ModelWolf(), new ModelWolf(), 0.5f));
        this.entityRenderMap.put(EntityChicken.class, new RenderChicken(new ModelChicken(), 0.3f));
        this.entityRenderMap.put(EntityOcelot.class, new RenderOcelot(new ModelOcelot(), 0.4f));
        this.entityRenderMap.put(EntitySilverfish.class, new RenderSilverfish());
        this.entityRenderMap.put(EntityCreeper.class, new RenderCreeper());
        this.entityRenderMap.put(EntityEnderman.class, new RenderEnderman());
        this.entityRenderMap.put(EntitySnowman.class, new RenderSnowMan());
        this.entityRenderMap.put(EntitySkeleton.class, new RenderSkeleton());
        this.entityRenderMap.put(EntityWitch.class, new RenderWitch());
        this.entityRenderMap.put(EntityBlaze.class, new RenderBlaze());
        this.entityRenderMap.put(EntityZombie.class, new RenderZombie());
        this.entityRenderMap.put(EntitySlime.class, new RenderSlime(new ModelSlime(16), new ModelSlime(0), 0.25f));
        this.entityRenderMap.put(EntityMagmaCube.class, new RenderMagmaCube());
        this.entityRenderMap.put(EntityPlayer.class, new RenderPlayer());
        this.entityRenderMap.put(EntityGiantZombie.class, new RenderGiantZombie(new ModelZombie(), 0.5f, 6.0f));
        this.entityRenderMap.put(EntityGhast.class, new RenderGhast());
        this.entityRenderMap.put(EntitySquid.class, new RenderSquid(new ModelSquid(), 0.7f));
        this.entityRenderMap.put(EntityVillager.class, new RenderVillager());
        this.entityRenderMap.put(EntityIronGolem.class, new RenderIronGolem());
        this.entityRenderMap.put(EntityBat.class, new RenderBat());
        this.entityRenderMap.put(EntityDragon.class, new RenderDragon());
        this.entityRenderMap.put(EntityEnderCrystal.class, new RenderEnderCrystal());
        this.entityRenderMap.put(EntityWither.class, new RenderWither());
        this.entityRenderMap.put(Entity.class, new RenderEntity());
        this.entityRenderMap.put(EntityPainting.class, new RenderPainting());
        this.entityRenderMap.put(EntityItemFrame.class, new RenderItemFrame());
        this.entityRenderMap.put(EntityLeashKnot.class, new RenderLeashKnot());
        this.entityRenderMap.put(EntityArrow.class, new RenderArrow());
        this.entityRenderMap.put(EntitySnowball.class, new RenderSnowball(Items.snowball));
        this.entityRenderMap.put(EntityEnderPearl.class, new RenderSnowball(Items.ender_pearl));
        this.entityRenderMap.put(EntityEnderEye.class, new RenderSnowball(Items.ender_eye));
        this.entityRenderMap.put(EntityEgg.class, new RenderSnowball(Items.egg));
        this.entityRenderMap.put(EntityPotion.class, new RenderSnowball(Items.potionitem, 16384));
        this.entityRenderMap.put(EntityExpBottle.class, new RenderSnowball(Items.experience_bottle));
        this.entityRenderMap.put(EntityFireworkRocket.class, new RenderSnowball(Items.fireworks));
        this.entityRenderMap.put(EntityLargeFireball.class, new RenderFireball(2.0f));
        this.entityRenderMap.put(EntitySmallFireball.class, new RenderFireball(0.5f));
        this.entityRenderMap.put(EntityWitherSkull.class, new RenderWitherSkull());
        this.entityRenderMap.put(EntityItem.class, new RenderItem());
        this.entityRenderMap.put(EntityXPOrb.class, new RenderXPOrb());
        this.entityRenderMap.put(EntityTNTPrimed.class, new RenderTNTPrimed());
        this.entityRenderMap.put(EntityFallingBlock.class, new RenderFallingBlock());
        this.entityRenderMap.put(EntityMinecartTNT.class, new RenderTntMinecart());
        this.entityRenderMap.put(EntityMinecartMobSpawner.class, new RenderMinecartMobSpawner());
        this.entityRenderMap.put(EntityMinecart.class, new RenderMinecart());
        this.entityRenderMap.put(EntityBoat.class, new RenderBoat());
        this.entityRenderMap.put(EntityFishHook.class, new RenderFish());
        this.entityRenderMap.put(EntityHorse.class, new RenderHorse(new ModelHorse(), 0.75f));
        this.entityRenderMap.put(EntityLightningBolt.class, new RenderLightningBolt());
        for (final Render var2 : this.entityRenderMap.values()) {
            var2.setRenderManager(this);
        }
    }
    
    public Render getEntityClassRenderObject(final Class p_78715_1_) {
        Render var2 = (Render)this.entityRenderMap.get(p_78715_1_);
        if (var2 == null && p_78715_1_ != Entity.class) {
            var2 = this.getEntityClassRenderObject(p_78715_1_.getSuperclass());
            this.entityRenderMap.put(p_78715_1_, var2);
        }
        return var2;
    }
    
    public Render getEntityRenderObject(final Entity p_78713_1_) {
        return this.getEntityClassRenderObject(p_78713_1_.getClass());
    }
    
    public void func_147938_a(final World p_147938_1_, final TextureManager p_147938_2_, final FontRenderer p_147938_3_, final EntityLivingBase p_147938_4_, final Entity p_147938_5_, final GameSettings p_147938_6_, final float p_147938_7_) {
        this.worldObj = p_147938_1_;
        this.renderEngine = p_147938_2_;
        this.options = p_147938_6_;
        this.livingPlayer = p_147938_4_;
        this.field_147941_i = p_147938_5_;
        this.fontRenderer = p_147938_3_;
        if (p_147938_4_.isPlayerSleeping()) {
            final Block var8 = p_147938_1_.getBlock(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
            if (var8 == Blocks.bed) {
                final int var9 = p_147938_1_.getBlockMetadata(MathHelper.floor_double(p_147938_4_.posX), MathHelper.floor_double(p_147938_4_.posY), MathHelper.floor_double(p_147938_4_.posZ));
                final int var10 = var9 & 0x3;
                this.playerViewY = (float)(var10 * 90 + 180);
                this.playerViewX = 0.0f;
            }
        }
        else {
            this.playerViewY = p_147938_4_.prevRotationYaw + (p_147938_4_.rotationYaw - p_147938_4_.prevRotationYaw) * p_147938_7_;
            this.playerViewX = p_147938_4_.prevRotationPitch + (p_147938_4_.rotationPitch - p_147938_4_.prevRotationPitch) * p_147938_7_;
        }
        if (p_147938_6_.thirdPersonView == 2) {
            this.playerViewY += 180.0f;
        }
        this.viewerPosX = p_147938_4_.lastTickPosX + (p_147938_4_.posX - p_147938_4_.lastTickPosX) * p_147938_7_;
        this.viewerPosY = p_147938_4_.lastTickPosY + (p_147938_4_.posY - p_147938_4_.lastTickPosY) * p_147938_7_;
        this.viewerPosZ = p_147938_4_.lastTickPosZ + (p_147938_4_.posZ - p_147938_4_.lastTickPosZ) * p_147938_7_;
    }
    
    public boolean func_147937_a(final Entity p_147937_1_, final float p_147937_2_) {
        return this.func_147936_a(p_147937_1_, p_147937_2_, false);
    }
    
    public boolean func_147936_a(final Entity p_147936_1_, final float p_147936_2_, final boolean p_147936_3_) {
        if (p_147936_1_.ticksExisted == 0) {
            p_147936_1_.lastTickPosX = p_147936_1_.posX;
            p_147936_1_.lastTickPosY = p_147936_1_.posY;
            p_147936_1_.lastTickPosZ = p_147936_1_.posZ;
        }
        final double var4 = p_147936_1_.lastTickPosX + (p_147936_1_.posX - p_147936_1_.lastTickPosX) * p_147936_2_;
        final double var5 = p_147936_1_.lastTickPosY + (p_147936_1_.posY - p_147936_1_.lastTickPosY) * p_147936_2_;
        final double var6 = p_147936_1_.lastTickPosZ + (p_147936_1_.posZ - p_147936_1_.lastTickPosZ) * p_147936_2_;
        final float var7 = p_147936_1_.prevRotationYaw + (p_147936_1_.rotationYaw - p_147936_1_.prevRotationYaw) * p_147936_2_;
        int var8 = p_147936_1_.getBrightnessForRender(p_147936_2_);
        if (p_147936_1_.isBurning()) {
            var8 = 15728880;
        }
        final int var9 = var8 % 65536;
        final int var10 = var8 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var9 / 1.0f, var10 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        return this.func_147939_a(p_147936_1_, var4 - RenderManager.renderPosX, var5 - RenderManager.renderPosY, var6 - RenderManager.renderPosZ, var7, p_147936_2_, p_147936_3_);
    }
    
    public boolean func_147940_a(final Entity p_147940_1_, final double p_147940_2_, final double p_147940_4_, final double p_147940_6_, final float p_147940_8_, final float p_147940_9_) {
        return this.func_147939_a(p_147940_1_, p_147940_2_, p_147940_4_, p_147940_6_, p_147940_8_, p_147940_9_, false);
    }
    
    public boolean func_147939_a(final Entity p_147939_1_, final double p_147939_2_, final double p_147939_4_, final double p_147939_6_, final float p_147939_8_, final float p_147939_9_, final boolean p_147939_10_) {
        Render var11 = null;
        try {
            var11 = this.getEntityRenderObject(p_147939_1_);
            if (var11 != null && this.renderEngine != null) {
                if (var11.func_147905_a()) {
                    if (!p_147939_10_) {
                        return true;
                    }
                }
                try {
                    var11.doRender(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                }
                catch (Throwable var12) {
                    throw new ReportedException(CrashReport.makeCrashReport(var12, "Rendering entity in world"));
                }
                try {
                    var11.doRenderShadowAndFire(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                }
                catch (Throwable var13) {
                    throw new ReportedException(CrashReport.makeCrashReport(var13, "Post-rendering entity in world"));
                }
                if (!RenderManager.field_85095_o || p_147939_1_.isInvisible() || p_147939_10_) {
                    return true;
                }
                try {
                    this.func_85094_b(p_147939_1_, p_147939_2_, p_147939_4_, p_147939_6_, p_147939_8_, p_147939_9_);
                    return true;
                }
                catch (Throwable var14) {
                    throw new ReportedException(CrashReport.makeCrashReport(var14, "Rendering entity hitbox in world"));
                }
            }
            if (this.renderEngine != null) {
                return false;
            }
            return true;
        }
        catch (Throwable var16) {
            final CrashReport var15 = CrashReport.makeCrashReport(var16, "Rendering entity in world");
            final CrashReportCategory var17 = var15.makeCategory("Entity being rendered");
            p_147939_1_.addEntityCrashInfo(var17);
            final CrashReportCategory var18 = var15.makeCategory("Renderer details");
            var18.addCrashSection("Assigned renderer", var11);
            var18.addCrashSection("Location", CrashReportCategory.func_85074_a(p_147939_2_, p_147939_4_, p_147939_6_));
            var18.addCrashSection("Rotation", p_147939_8_);
            var18.addCrashSection("Delta", p_147939_9_);
            throw new ReportedException(var15);
        }
    }
    
    private void func_85094_b(final Entity p_85094_1_, final double p_85094_2_, final double p_85094_4_, final double p_85094_6_, final float p_85094_8_, final float p_85094_9_) {
        GL11.glDepthMask(false);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDisable(2884);
        GL11.glDisable(3042);
        final float var10 = p_85094_1_.width / 2.0f;
        final AxisAlignedBB var11 = AxisAlignedBB.getBoundingBox(p_85094_2_ - var10, p_85094_4_, p_85094_6_ - var10, p_85094_2_ + var10, p_85094_4_ + p_85094_1_.height, p_85094_6_ + var10);
        RenderGlobal.drawOutlinedBoundingBox(var11, 16777215);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2884);
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
    }
    
    public void set(final World p_78717_1_) {
        this.worldObj = p_78717_1_;
    }
    
    public double getDistanceToCamera(final double p_78714_1_, final double p_78714_3_, final double p_78714_5_) {
        final double var7 = p_78714_1_ - this.viewerPosX;
        final double var8 = p_78714_3_ - this.viewerPosY;
        final double var9 = p_78714_5_ - this.viewerPosZ;
        return var7 * var7 + var8 * var8 + var9 * var9;
    }
    
    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    public void updateIcons(final IIconRegister p_94178_1_) {
        for (final Render var3 : this.entityRenderMap.values()) {
            var3.updateIcons(p_94178_1_);
        }
    }
    
    static {
        RenderManager.instance = new RenderManager();
    }
}
