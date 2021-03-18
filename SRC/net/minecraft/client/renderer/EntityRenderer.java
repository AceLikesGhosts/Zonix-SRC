package net.minecraft.client.renderer;

import net.minecraft.client.*;
import net.minecraft.client.resources.*;
import java.nio.*;
import com.google.gson.*;
import java.io.*;
import net.minecraft.client.shader.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.src.*;
import org.lwjgl.input.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import us.zonix.client.module.impl.*;
import java.util.concurrent.*;
import net.minecraft.crash.*;
import net.minecraft.client.gui.*;
import net.minecraft.server.integrated.*;
import org.lwjgl.util.glu.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.particle.*;
import net.minecraft.world.biome.*;
import net.minecraft.enchantment.*;
import org.lwjgl.opengl.*;
import org.apache.logging.log4j.*;

public class EntityRenderer implements IResourceManagerReloadListener
{
    private static final Logger logger;
    private static final ResourceLocation locationRainPng;
    private static final ResourceLocation locationSnowPng;
    public static boolean anaglyphEnable;
    public static int anaglyphField;
    private Minecraft mc;
    private float farPlaneDistance;
    public final ItemRenderer itemRenderer;
    private final MapItemRenderer theMapItemRenderer;
    private int rendererUpdateCount;
    private Entity pointedEntity;
    private MouseFilter mouseFilterXAxis;
    private MouseFilter mouseFilterYAxis;
    private MouseFilter mouseFilterDummy1;
    private MouseFilter mouseFilterDummy2;
    private MouseFilter mouseFilterDummy3;
    private MouseFilter mouseFilterDummy4;
    private float thirdPersonDistance;
    private float thirdPersonDistanceTemp;
    private float debugCamYaw;
    private float prevDebugCamYaw;
    private float debugCamPitch;
    private float prevDebugCamPitch;
    private float smoothCamYaw;
    private float smoothCamPitch;
    private float smoothCamFilterX;
    private float smoothCamFilterY;
    private float smoothCamPartialTicks;
    private float debugCamFOV;
    private float prevDebugCamFOV;
    private float camRoll;
    private float prevCamRoll;
    private final DynamicTexture lightmapTexture;
    private final int[] lightmapColors;
    private final ResourceLocation locationLightMap;
    private float fovModifierHand;
    private float fovModifierHandPrev;
    private float fovMultiplierTemp;
    private float bossColorModifier;
    private float bossColorModifierPrev;
    private boolean cloudFog;
    private final IResourceManager resourceManager;
    public ShaderGroup theShaderGroup;
    private static final ResourceLocation[] shaderResourceLocations;
    public static final int shaderCount;
    private int shaderIndex;
    private double cameraZoom;
    private double cameraYaw;
    private double cameraPitch;
    private long prevFrameTime;
    private long renderEndNanoTime;
    private boolean lightmapUpdateNeeded;
    float torchFlickerX;
    float torchFlickerDX;
    float torchFlickerY;
    float torchFlickerDY;
    private Random random;
    private int rainSoundCounter;
    float[] rainXCoords;
    float[] rainYCoords;
    FloatBuffer fogColorBuffer;
    float fogColorRed;
    float fogColorGreen;
    float fogColorBlue;
    private float fogColor2;
    private float fogColor1;
    public int debugViewDirection;
    public boolean fogStandard;
    private static final String __OBFID = "CL_00000947";
    public double lastRange;
    public long lastAttackTime;
    public boolean altPerspectiveToggled;
    public boolean altPerspective;
    public float altYaw;
    public float altPitch;
    private long lastErrorCheckTimeMs;
    private long lastServerTime;
    private int lastServerTicks;
    private int serverWaitTime;
    private int serverWaitTimeCurrent;
    private int chunksRendered;
    
    public EntityRenderer(final Minecraft p_i45076_1_, final IResourceManager p_i45076_2_) {
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.mouseFilterDummy1 = new MouseFilter();
        this.mouseFilterDummy2 = new MouseFilter();
        this.mouseFilterDummy3 = new MouseFilter();
        this.mouseFilterDummy4 = new MouseFilter();
        this.thirdPersonDistance = 4.0f;
        this.thirdPersonDistanceTemp = 4.0f;
        this.fogStandard = false;
        this.lastErrorCheckTimeMs = 0L;
        this.lastServerTime = 0L;
        this.lastServerTicks = 0;
        this.serverWaitTime = 0;
        this.serverWaitTimeCurrent = 0;
        this.shaderIndex = EntityRenderer.shaderCount;
        this.cameraZoom = 1.0;
        this.prevFrameTime = Minecraft.getSystemTime();
        this.random = new Random();
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.mc = p_i45076_1_;
        this.resourceManager = p_i45076_2_;
        this.theMapItemRenderer = new MapItemRenderer(p_i45076_1_.getTextureManager());
        this.itemRenderer = new ItemRenderer(p_i45076_1_);
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap = p_i45076_1_.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
    }
    
    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }
    
    public void deactivateShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        this.theShaderGroup = null;
        this.shaderIndex = EntityRenderer.shaderCount;
    }
    
    public void setBlur(final boolean blur) {
        if (!blur) {
            this.deactivateShader();
        }
        else {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = 18;
            try {
                (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), EntityRenderer.shaderResourceLocations[this.shaderIndex])).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            }
            catch (JsonSyntaxException | IOException ex2) {
                final Exception ex;
                final Exception var3 = ex;
                EntityRenderer.logger.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[this.shaderIndex], (Throwable)var3);
                this.shaderIndex = EntityRenderer.shaderCount;
            }
        }
    }
    
    public void activateNextShader() {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }
            this.shaderIndex = (this.shaderIndex + 1) % (EntityRenderer.shaderResourceLocations.length + 1);
            if (this.shaderIndex != EntityRenderer.shaderCount) {
                try {
                    EntityRenderer.logger.info("Selecting effect " + EntityRenderer.shaderResourceLocations[this.shaderIndex]);
                    (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), EntityRenderer.shaderResourceLocations[this.shaderIndex])).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
                }
                catch (IOException var2) {
                    EntityRenderer.logger.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[this.shaderIndex], (Throwable)var2);
                    this.shaderIndex = EntityRenderer.shaderCount;
                }
                catch (JsonSyntaxException var3) {
                    EntityRenderer.logger.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[this.shaderIndex], (Throwable)var3);
                    this.shaderIndex = EntityRenderer.shaderCount;
                }
            }
            else {
                this.theShaderGroup = null;
                EntityRenderer.logger.info("No effect selected");
            }
        }
    }
    
    @Override
    public void onResourceManagerReload(final IResourceManager p_110549_1_) {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }
        if (this.shaderIndex != EntityRenderer.shaderCount) {
            try {
                (this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), p_110549_1_, this.mc.getFramebuffer(), EntityRenderer.shaderResourceLocations[this.shaderIndex])).createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            }
            catch (IOException var3) {
                EntityRenderer.logger.warn("Failed to load shader: " + EntityRenderer.shaderResourceLocations[this.shaderIndex], (Throwable)var3);
                this.shaderIndex = EntityRenderer.shaderCount;
            }
        }
    }
    
    public void updateRenderer() {
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }
        this.updateFovModifierHand();
        this.updateTorchFlicker();
        this.fogColor2 = this.fogColor1;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        this.prevDebugCamYaw = this.debugCamYaw;
        this.prevDebugCamPitch = this.debugCamPitch;
        this.prevDebugCamFOV = this.debugCamFOV;
        this.prevCamRoll = this.camRoll;
        if (this.mc.gameSettings.smoothCamera) {
            final float var1 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float var2 = var1 * var1 * var1 * 8.0f;
            this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05f * var2);
            this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05f * var2);
            this.smoothCamPartialTicks = 0.0f;
            this.smoothCamYaw = 0.0f;
            this.smoothCamPitch = 0.0f;
        }
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        final float var1 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(this.mc.renderViewEntity.posX), MathHelper.floor_double(this.mc.renderViewEntity.posY), MathHelper.floor_double(this.mc.renderViewEntity.posZ));
        final float var2 = this.mc.gameSettings.renderDistanceChunks / 16.0f;
        final float var3 = var1 * (1.0f - var2) + var2;
        this.fogColor1 += (var3 - this.fogColor1) * 0.1f;
        ++this.rendererUpdateCount;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();
        this.bossColorModifierPrev = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier += 0.05f;
            if (this.bossColorModifier > 1.0f) {
                this.bossColorModifier = 1.0f;
            }
            BossStatus.hasColorModifier = false;
        }
        else if (this.bossColorModifier > 0.0f) {
            this.bossColorModifier -= 0.0125f;
        }
    }
    
    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }
    
    public void updateShaderGroupSize(final int p_147704_1_, final int p_147704_2_) {
        if (OpenGlHelper.shadersSupported && this.theShaderGroup != null) {
            this.theShaderGroup.createBindFramebuffers(p_147704_1_, p_147704_2_);
        }
    }
    
    public void getMouseOver(final float p_78473_1_) {
        if (this.mc.renderViewEntity != null && this.mc.theWorld != null) {
            this.mc.pointedEntity = null;
            double var2 = this.mc.playerController.getBlockReachDistance();
            this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(var2, p_78473_1_);
            double var3 = var2;
            final Vec3 var4 = this.mc.renderViewEntity.getPosition(p_78473_1_);
            if (this.mc.playerController.extendedReach()) {
                var2 = 6.0;
                var3 = 6.0;
            }
            else {
                if (var2 > 3.0) {
                    var3 = 3.0;
                }
                var2 = var3;
            }
            if (this.mc.objectMouseOver != null) {
                var3 = this.mc.objectMouseOver.hitVec.distanceTo(var4);
            }
            final Vec3 var5 = this.mc.renderViewEntity.getLook(p_78473_1_);
            final Vec3 var6 = var4.addVector(var5.xCoord * var2, var5.yCoord * var2, var5.zCoord * var2);
            this.pointedEntity = null;
            Vec3 var7 = null;
            final float var8 = 1.0f;
            final List var9 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var5.xCoord * var2, var5.yCoord * var2, var5.zCoord * var2).expand(var8, var8, var8));
            double var10 = var3;
            for (int var11 = 0; var11 < var9.size(); ++var11) {
                final Entity var12 = var9.get(var11);
                if (var12.canBeCollidedWith()) {
                    final float var13 = var12.getCollisionBorderSize();
                    final AxisAlignedBB var14 = var12.boundingBox.expand(var13, var13, var13);
                    final MovingObjectPosition var15 = var14.calculateIntercept(var4, var6);
                    if (var14.isVecInside(var4)) {
                        if (0.0 < var10 || var10 == 0.0) {
                            this.pointedEntity = var12;
                            var7 = ((var15 == null) ? var4 : var15.hitVec);
                            var10 = 0.0;
                        }
                    }
                    else if (var15 != null) {
                        final double var16 = var4.distanceTo(var15.hitVec);
                        if (var16 < var10 || var10 == 0.0) {
                            if (var12 == this.mc.renderViewEntity.ridingEntity) {
                                if (var10 == 0.0) {
                                    this.pointedEntity = var12;
                                    var7 = var15.hitVec;
                                }
                            }
                            else {
                                this.pointedEntity = var12;
                                var7 = var15.hitVec;
                                var10 = var16;
                            }
                        }
                    }
                }
            }
            if (this.pointedEntity != null && (var10 < var3 || this.mc.objectMouseOver == null)) {
                this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var7);
                if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                    this.mc.pointedEntity = this.pointedEntity;
                }
            }
        }
    }
    
    private void updateFovModifierHand() {
        final EntityPlayerSP var1 = (EntityPlayerSP)this.mc.renderViewEntity;
        this.fovMultiplierTemp = var1.getFOVMultiplier();
        this.fovModifierHandPrev = this.fovModifierHand;
        this.fovModifierHand += (this.fovMultiplierTemp - this.fovModifierHand) * 0.5f;
        if (this.fovModifierHand > 1.5f) {
            this.fovModifierHand = 1.5f;
        }
        if (this.fovModifierHand < 0.1f) {
            this.fovModifierHand = 0.1f;
        }
    }
    
    private float getFOVModifier(final float par1, final boolean par2) {
        if (this.debugViewDirection > 0) {
            return 90.0f;
        }
        final EntityLivingBase var3 = this.mc.renderViewEntity;
        float var4 = 70.0f;
        if (par2) {
            var4 = this.mc.gameSettings.fovSetting;
            if (Config.isDynamicFov()) {
                var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * par1;
            }
        }
        boolean zoomActive = false;
        if (this.mc.currentScreen == null) {
            if (this.mc.gameSettings.ofKeyBindZoom.getKeyCode() < 0) {
                zoomActive = Mouse.isButtonDown(this.mc.gameSettings.ofKeyBindZoom.getKeyCode() + 100);
            }
            else {
                zoomActive = Keyboard.isKeyDown(this.mc.gameSettings.ofKeyBindZoom.getKeyCode());
            }
        }
        if (zoomActive) {
            if (!Config.zoomMode) {
                Config.zoomMode = true;
                this.mc.gameSettings.smoothCamera = true;
            }
            if (Config.zoomMode) {
                var4 /= 4.0f;
            }
        }
        else if (Config.zoomMode) {
            Config.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
        }
        if (var3.getHealth() <= 0.0f) {
            final float var5 = var3.deathTime + par1;
            var4 /= (1.0f - 500.0f / (var5 + 500.0f)) * 2.0f + 1.0f;
        }
        final Block var6 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, par1);
        if (var6.getMaterial() == Material.water) {
            var4 = var4 * 60.0f / 70.0f;
        }
        return var4 + this.prevDebugCamFOV + (this.debugCamFOV - this.prevDebugCamFOV) * par1;
    }
    
    private void hurtCameraEffect(final float p_78482_1_) {
        final EntityLivingBase var2 = this.mc.renderViewEntity;
        float var3 = var2.hurtTime - p_78482_1_;
        if (var2.getHealth() <= 0.0f) {
            final float var4 = var2.deathTime + p_78482_1_;
            GL11.glRotatef(40.0f - 8000.0f / (var4 + 200.0f), 0.0f, 0.0f, 1.0f);
        }
        if (var3 >= 0.0f) {
            var3 /= var2.maxHurtTime;
            var3 = MathHelper.sin(var3 * var3 * var3 * var3 * 3.1415927f);
            final float var4 = var2.attackedAtYaw;
            GL11.glRotatef(-var4, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var3 * 14.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(var4, 0.0f, 1.0f, 0.0f);
        }
    }
    
    private void setupViewBobbing(final float p_78475_1_) {
        if (this.mc.renderViewEntity instanceof EntityPlayer) {
            final EntityPlayer var2 = (EntityPlayer)this.mc.renderViewEntity;
            final float var3 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
            final float var4 = -(var2.distanceWalkedModified + var3 * p_78475_1_);
            final float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * p_78475_1_;
            final float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * p_78475_1_;
            GL11.glTranslatef(MathHelper.sin(var4 * 3.1415927f) * var5 * 0.5f, -Math.abs(MathHelper.cos(var4 * 3.1415927f) * var5), 0.0f);
            GL11.glRotatef(MathHelper.sin(var4 * 3.1415927f) * var5 * 3.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(Math.abs(MathHelper.cos(var4 * 3.1415927f - 0.2f) * var5) * 5.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(var6, 1.0f, 0.0f, 0.0f);
        }
    }
    
    private void orientCamera(final float p_78467_1_) {
        final EntityLivingBase var2 = this.mc.renderViewEntity;
        float var3 = var2.yOffset - 1.62f;
        double var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
        double var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ - var3;
        double var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
        GL11.glRotatef(this.prevCamRoll + (this.camRoll - this.prevCamRoll) * p_78467_1_, 0.0f, 0.0f, 1.0f);
        if (var2.isPlayerSleeping()) {
            ++var3;
            GL11.glTranslatef(0.0f, 0.3f, 0.0f);
            if (!this.mc.gameSettings.debugCamEnable) {
                final Block var7 = this.mc.theWorld.getBlock(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                if (var7 == Blocks.bed) {
                    final int var8 = this.mc.theWorld.getBlockMetadata(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posY), MathHelper.floor_double(var2.posZ));
                    final int var9 = var8 & 0x3;
                    GL11.glRotatef((float)(var9 * 90), 0.0f, 1.0f, 0.0f);
                }
                GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0f, 0.0f, -1.0f, 0.0f);
                GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, -1.0f, 0.0f, 0.0f);
            }
        }
        else if (this.mc.gameSettings.thirdPersonView > 0) {
            double var10 = this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * p_78467_1_;
            if (this.mc.gameSettings.debugCamEnable) {
                final float var11 = this.prevDebugCamYaw + (this.debugCamYaw - this.prevDebugCamYaw) * p_78467_1_;
                final float var12 = this.prevDebugCamPitch + (this.debugCamPitch - this.prevDebugCamPitch) * p_78467_1_;
                GL11.glTranslatef(0.0f, 0.0f, (float)(-var10));
                GL11.glRotatef(var12, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var11, 0.0f, 1.0f, 0.0f);
            }
            else {
                float var11 = var2.rotationYaw;
                float var12 = var2.rotationPitch;
                if (this.altPerspective) {
                    var11 = this.altYaw;
                    var12 = this.altPitch;
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    var12 += 180.0f;
                }
                final double var13 = -MathHelper.sin(var11 / 180.0f * 3.1415927f) * MathHelper.cos(var12 / 180.0f * 3.1415927f) * var10;
                final double var14 = MathHelper.cos(var11 / 180.0f * 3.1415927f) * MathHelper.cos(var12 / 180.0f * 3.1415927f) * var10;
                final double var15 = -MathHelper.sin(var12 / 180.0f * 3.1415927f) * var10;
                for (int var16 = 0; var16 < 8; ++var16) {
                    float var17 = (float)((var16 & 0x1) * 2 - 1);
                    float var18 = (float)((var16 >> 1 & 0x1) * 2 - 1);
                    float var19 = (float)((var16 >> 2 & 0x1) * 2 - 1);
                    var17 *= 0.1f;
                    var18 *= 0.1f;
                    var19 *= 0.1f;
                    final MovingObjectPosition var20 = this.mc.theWorld.rayTraceBlocks(Vec3.createVectorHelper(var4 + var17, var5 + var18, var6 + var19), Vec3.createVectorHelper(var4 - var13 + var17 + var19, var5 - var15 + var18, var6 - var14 + var19));
                    if (var20 != null) {
                        final double var21 = var20.hitVec.distanceTo(Vec3.createVectorHelper(var4, var5, var6));
                        if (var21 < var10) {
                            var10 = var21;
                        }
                    }
                }
                if (this.mc.gameSettings.thirdPersonView == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (this.altPerspective) {
                    GL11.glRotatef(this.altPitch - var12, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(this.altYaw - var11, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.0f, (float)(-var10));
                    GL11.glRotatef(var11 - this.altYaw, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var12 - this.altPitch, 1.0f, 0.0f, 0.0f);
                }
                else {
                    GL11.glRotatef(var2.rotationPitch - var12, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(var2.rotationYaw - var11, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.0f, (float)(-var10));
                    GL11.glRotatef(var11 - var2.rotationYaw, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var12 - var2.rotationPitch, 1.0f, 0.0f, 0.0f);
                }
            }
        }
        else {
            GL11.glTranslatef(0.0f, 0.0f, -0.1f);
        }
        if (!this.mc.gameSettings.debugCamEnable) {
            if (this.altPerspective) {
                GL11.glRotatef(this.altPitch, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(this.altYaw + 180.0f, 0.0f, 1.0f, 0.0f);
            }
            else {
                GL11.glRotatef(var2.prevRotationPitch + (var2.rotationPitch - var2.prevRotationPitch) * p_78467_1_, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(var2.prevRotationYaw + (var2.rotationYaw - var2.prevRotationYaw) * p_78467_1_ + 180.0f, 0.0f, 1.0f, 0.0f);
            }
        }
        GL11.glTranslatef(0.0f, var3, 0.0f);
        var4 = var2.prevPosX + (var2.posX - var2.prevPosX) * p_78467_1_;
        var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * p_78467_1_ - var3;
        var6 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * p_78467_1_;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(var4, var5, var6, p_78467_1_);
    }
    
    private void setupCameraTransform(final float p_78479_1_, final int p_78479_2_) {
        this.farPlaneDistance = (float)(this.mc.gameSettings.renderDistanceChunks * 16);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        final float var3 = 0.07f;
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef(-(p_78479_2_ * 2 - 1) * var3, 0.0f, 0.0f);
        }
        if (this.cameraZoom != 1.0) {
            GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
            GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0);
        }
        Project.gluPerspective(this.getFOVModifier(p_78479_1_, true), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
        if (this.mc.playerController.enableEverythingIsScrewedUpMode()) {
            final float var4 = 0.6666667f;
            GL11.glScalef(1.0f, var4, 1.0f);
        }
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        if (this.mc.gameSettings.anaglyph) {
            GL11.glTranslatef((p_78479_2_ * 2 - 1) * 0.1f, 0.0f, 0.0f);
        }
        this.hurtCameraEffect(p_78479_1_);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(p_78479_1_);
        }
        final float var4 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * p_78479_1_;
        if (var4 > 0.0f) {
            byte var5 = 20;
            if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
                var5 = 7;
            }
            float var6 = 5.0f / (var4 * var4 + 5.0f) - var4 * 0.04f;
            var6 *= var6;
            GL11.glRotatef((this.rendererUpdateCount + p_78479_1_) * var5, 0.0f, 1.0f, 1.0f);
            GL11.glScalef(1.0f / var6, 1.0f, 1.0f);
            GL11.glRotatef(-(this.rendererUpdateCount + p_78479_1_) * var5, 0.0f, 1.0f, 1.0f);
        }
        this.orientCamera(p_78479_1_);
        if (this.debugViewDirection > 0) {
            final int var7 = this.debugViewDirection - 1;
            if (var7 == 1) {
                GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var7 == 2) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var7 == 3) {
                GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
            }
            if (var7 == 4) {
                GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            }
            if (var7 == 5) {
                GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
            }
        }
    }
    
    private void renderHand(final float p_78476_1_, final int p_78476_2_) {
        if (this.debugViewDirection <= 0) {
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            final float var3 = 0.07f;
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef(-(p_78476_2_ * 2 - 1) * var3, 0.0f, 0.0f);
            }
            if (this.cameraZoom != 1.0) {
                GL11.glTranslatef((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0f);
                GL11.glScaled(this.cameraZoom, this.cameraZoom, 1.0);
            }
            Project.gluPerspective(this.getFOVModifier(p_78476_1_, false), this.mc.displayWidth / (float)this.mc.displayHeight, 0.05f, this.farPlaneDistance * 2.0f);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            if (this.mc.gameSettings.anaglyph) {
                GL11.glTranslatef((p_78476_2_ * 2 - 1) * 0.1f, 0.0f, 0.0f);
            }
            GL11.glPushMatrix();
            this.hurtCameraEffect(p_78476_1_);
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(p_78476_1_);
            }
            if (this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping() && !this.mc.playerController.enableEverythingIsScrewedUpMode() && (this.mc.gameSettings.cinematicMode || !this.mc.gameSettings.hideGUI)) {
                this.enableLightmap(p_78476_1_);
                this.itemRenderer.renderItemInFirstPerson(p_78476_1_);
                this.disableLightmap(p_78476_1_);
            }
            GL11.glPopMatrix();
            if (this.mc.gameSettings.thirdPersonView == 0 && !this.mc.renderViewEntity.isPlayerSleeping()) {
                this.itemRenderer.renderOverlays(p_78476_1_);
                this.hurtCameraEffect(p_78476_1_);
            }
            if (this.mc.gameSettings.viewBobbing) {
                this.setupViewBobbing(p_78476_1_);
            }
        }
    }
    
    public void disableLightmap(final double p_78483_1_) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    public void enableLightmap(final double p_78463_1_) {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glMatrixMode(5890);
        GL11.glLoadIdentity();
        final float var3 = 0.00390625f;
        GL11.glScalef(var3, var3, var3);
        GL11.glTranslatef(8.0f, 8.0f, 8.0f);
        GL11.glMatrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
    
    private void updateTorchFlicker() {
        this.torchFlickerDX += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDY += (float)((Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerDX *= (float)0.9;
        this.torchFlickerDY *= (float)0.9;
        this.torchFlickerX += (this.torchFlickerDX - this.torchFlickerX) * 1.0f;
        this.torchFlickerY += (this.torchFlickerDY - this.torchFlickerY) * 1.0f;
        this.lightmapUpdateNeeded = true;
    }
    
    private void updateLightmap(final float p_78472_1_) {
        final WorldClient var2 = this.mc.theWorld;
        if (var2 != null) {
            for (int var3 = 0; var3 < 256; ++var3) {
                final float var4 = var2.getSunBrightness(1.0f) * 0.95f + 0.05f;
                float var5 = var2.provider.lightBrightnessTable[var3 / 16] * var4;
                final float var6 = var2.provider.lightBrightnessTable[var3 % 16] * (this.torchFlickerX * 0.1f + 1.5f);
                if (var2.lastLightningBolt > 0) {
                    var5 = var2.provider.lightBrightnessTable[var3 / 16];
                }
                final float var7 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                final float var8 = var5 * (var2.getSunBrightness(1.0f) * 0.65f + 0.35f);
                final float var9 = var6 * ((var6 * 0.6f + 0.4f) * 0.6f + 0.4f);
                final float var10 = var6 * (var6 * var6 * 0.6f + 0.4f);
                float var11 = var7 + var6;
                float var12 = var8 + var9;
                float var13 = var5 + var10;
                var11 = var11 * 0.96f + 0.03f;
                var12 = var12 * 0.96f + 0.03f;
                var13 = var13 * 0.96f + 0.03f;
                if (this.bossColorModifier > 0.0f) {
                    final float var14 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * p_78472_1_;
                    var11 = var11 * (1.0f - var14) + var11 * 0.7f * var14;
                    var12 = var12 * (1.0f - var14) + var12 * 0.6f * var14;
                    var13 = var13 * (1.0f - var14) + var13 * 0.6f * var14;
                }
                if (var2.provider.dimensionId == 1) {
                    var11 = 0.22f + var6 * 0.75f;
                    var12 = 0.28f + var9 * 0.75f;
                    var13 = 0.25f + var10 * 0.75f;
                }
                if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                    final float var14 = this.getNightVisionBrightness(this.mc.thePlayer, p_78472_1_);
                    float var15 = 1.0f / var11;
                    if (var15 > 1.0f / var12) {
                        var15 = 1.0f / var12;
                    }
                    if (var15 > 1.0f / var13) {
                        var15 = 1.0f / var13;
                    }
                    var11 = var11 * (1.0f - var14) + var11 * var15 * var14;
                    var12 = var12 * (1.0f - var14) + var12 * var15 * var14;
                    var13 = var13 * (1.0f - var14) + var13 * var15 * var14;
                }
                if (var11 > 1.0f) {
                    var11 = 1.0f;
                }
                if (var12 > 1.0f) {
                    var12 = 1.0f;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                final float var14 = this.mc.gameSettings.getGammaSetting();
                float var15 = 1.0f - var11;
                float var16 = 1.0f - var12;
                float var17 = 1.0f - var13;
                var15 = 1.0f - var15 * var15 * var15 * var15;
                var16 = 1.0f - var16 * var16 * var16 * var16;
                var17 = 1.0f - var17 * var17 * var17 * var17;
                var11 = var11 * (1.0f - var14) + var15 * var14;
                var12 = var12 * (1.0f - var14) + var16 * var14;
                var13 = var13 * (1.0f - var14) + var17 * var14;
                var11 = var11 * 0.96f + 0.03f;
                var12 = var12 * 0.96f + 0.03f;
                var13 = var13 * 0.96f + 0.03f;
                if (var11 > 1.0f) {
                    var11 = 1.0f;
                }
                if (var12 > 1.0f) {
                    var12 = 1.0f;
                }
                if (var13 > 1.0f) {
                    var13 = 1.0f;
                }
                if (var11 < 0.0f) {
                    var11 = 0.0f;
                }
                if (var12 < 0.0f) {
                    var12 = 0.0f;
                }
                if (var13 < 0.0f) {
                    var13 = 0.0f;
                }
                final short var18 = 255;
                final int var19 = (int)(var11 * 255.0f);
                final int var20 = (int)(var12 * 255.0f);
                final int var21 = (int)(var13 * 255.0f);
                this.lightmapColors[var3] = (var18 << 24 | var19 << 16 | var20 << 8 | var21);
            }
            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
        }
    }
    
    private float getNightVisionBrightness(final EntityPlayer p_82830_1_, final float p_82830_2_) {
        final int var3 = p_82830_1_.getActivePotionEffect(Potion.nightVision).getDuration();
        return (var3 > 200) ? 1.0f : (0.7f + MathHelper.sin((var3 - p_82830_2_) * 3.1415927f * 0.2f) * 0.3f);
    }
    
    public void updateCameraAndRender(final float p_78480_1_) {
        final long sysTime = Minecraft.getSystemTime();
        this.mc.mcProfiler.startSection("lightTex");
        if (FPSBoost.LIGHTING.getValue() && this.lightmapUpdateNeeded) {
            this.updateLightmap(p_78480_1_);
        }
        this.mc.mcProfiler.endSection();
        final boolean var2 = this.mc.displayActive;
        if (!var2 && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
            if (sysTime - this.prevFrameTime > 500L) {
                this.mc.displayInGameMenu();
            }
        }
        else {
            this.prevFrameTime = sysTime;
        }
        this.mc.mcProfiler.startSection("mouse");
        if (this.mc.inGameHasFocus && var2) {
            if (this.altPerspective && this.mc.gameSettings.thirdPersonView != 1) {
                this.altPerspective = false;
            }
            this.mc.mouseHelper.mouseXYChange();
            final float var3 = this.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            final float var4 = var3 * var3 * var3 * 8.0f;
            float var5 = this.mc.mouseHelper.deltaX * var4;
            float var6 = this.mc.mouseHelper.deltaY * var4;
            byte var7 = 1;
            if (this.mc.gameSettings.invertMouse) {
                var7 = -1;
            }
            if (this.mc.gameSettings.smoothCamera) {
                this.smoothCamYaw += var5;
                this.smoothCamPitch += var6;
                final float var8 = p_78480_1_ - this.smoothCamPartialTicks;
                this.smoothCamPartialTicks = p_78480_1_;
                var5 = this.smoothCamFilterX * var8;
                var6 = this.smoothCamFilterY * var8;
                if (this.altPerspective) {
                    this.altYaw += var5 / 8.0f;
                    this.altPitch += var6 / 8.0f;
                    if (Math.abs(this.altPitch) > 90.0f) {
                        this.altPitch = ((this.altPitch > 0.0f) ? 90.0f : -90.0f);
                    }
                }
                else {
                    this.mc.thePlayer.setAngles(var5, var6 * var7);
                }
            }
            if (this.altPerspective) {
                this.altYaw += var5 / 8.0f;
                this.altPitch += var6 / 8.0f;
                if (Math.abs(this.altPitch) > 90.0f) {
                    this.altPitch = ((this.altPitch > 0.0f) ? 90.0f : -90.0f);
                }
            }
            else {
                this.mc.thePlayer.setAngles(var5, var6 * var7);
            }
        }
        this.mc.mcProfiler.endSection();
        if (!this.mc.skipRenderWorld) {
            EntityRenderer.anaglyphEnable = this.mc.gameSettings.anaglyph;
            final ScaledResolution var9 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            final int var10 = var9.getScaledWidth();
            final int var11 = var9.getScaledHeight();
            final int var12 = Mouse.getX() * var10 / this.mc.displayWidth;
            final int var13 = var11 - Mouse.getY() * var11 / this.mc.displayHeight - 1;
            final int var14 = this.mc.gameSettings.limitFramerate;
            if (this.mc.theWorld != null) {
                this.mc.mcProfiler.startSection("level");
                if (this.mc.isFramerateLimitBelowMax()) {
                    this.renderWorld(p_78480_1_, this.renderEndNanoTime + 1000000000 / var14);
                }
                else {
                    this.renderWorld(p_78480_1_, 0L);
                }
                if (OpenGlHelper.shadersSupported && FPSBoost.SMART_PERFORMANCE.getValue() >= 60.0f) {
                    if (this.theShaderGroup != null) {
                        GL11.glMatrixMode(5890);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        this.theShaderGroup.loadShaderGroup(p_78480_1_);
                        GL11.glPopMatrix();
                    }
                    this.mc.getFramebuffer().bindFramebuffer(true);
                }
                this.renderEndNanoTime = System.nanoTime();
                this.mc.mcProfiler.endStartSection("gui");
                if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
                    GL11.glAlphaFunc(516, 0.1f);
                    this.mc.ingameGUI.renderGameOverlay(p_78480_1_, this.mc.currentScreen != null, var12, var13);
                }
                this.mc.mcProfiler.endSection();
            }
            else {
                GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
                GL11.glMatrixMode(5889);
                GL11.glLoadIdentity();
                GL11.glMatrixMode(5888);
                GL11.glLoadIdentity();
                this.setupOverlayRendering();
                this.renderEndNanoTime = System.nanoTime();
            }
            if (this.mc.currentScreen != null) {
                GL11.glClear(256);
                try {
                    if (this.mc.currentScreen.initialised) {
                        this.mc.currentScreen.drawScreen(var12, var13, p_78480_1_);
                    }
                }
                catch (Throwable var16) {
                    final CrashReport var15 = CrashReport.makeCrashReport(var16, "Rendering screen");
                    final CrashReportCategory var17 = var15.makeCategory("Screen render details");
                    var17.addCrashSectionCallable("Screen name", new Callable() {
                        private static final String __OBFID = "CL_00000948";
                        
                        @Override
                        public String call() {
                            return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
                        }
                    });
                    var17.addCrashSectionCallable("Mouse location", new Callable() {
                        private static final String __OBFID = "CL_00000950";
                        
                        @Override
                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var12, var13, Mouse.getX(), Mouse.getY());
                        }
                    });
                    var17.addCrashSectionCallable("Screen size", new Callable() {
                        private static final String __OBFID = "CL_00000951";
                        
                        @Override
                        public String call() {
                            return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", var9.getScaledWidth(), var9.getScaledHeight(), EntityRenderer.this.mc.displayWidth, EntityRenderer.this.mc.displayHeight, var9.getScaleFactor());
                        }
                    });
                    throw new ReportedException(var15);
                }
            }
        }
        this.frameFinish();
        this.waitForServerThread();
    }
    
    private void waitForServerThread() {
        this.serverWaitTimeCurrent = 0;
        if (!Config.isSmoothWorld()) {
            this.lastServerTime = 0L;
            this.lastServerTicks = 0;
        }
        else if (this.mc.getIntegratedServer() != null) {
            final IntegratedServer srv = this.mc.getIntegratedServer();
            final boolean paused = this.mc.func_147113_T();
            if (!paused && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.serverWaitTime > 0) {
                    Config.sleep(this.serverWaitTime);
                    this.serverWaitTimeCurrent = this.serverWaitTime;
                }
                final long timeNow = System.nanoTime() / 1000000L;
                if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                    long timeDiff = timeNow - this.lastServerTime;
                    if (timeDiff < 0L) {
                        this.lastServerTime = timeNow;
                        timeDiff = 0L;
                    }
                    if (timeDiff >= 50L) {
                        this.lastServerTime = timeNow;
                        final int ticks = srv.getTickCounter();
                        int tickDiff = ticks - this.lastServerTicks;
                        if (tickDiff < 0) {
                            this.lastServerTicks = ticks;
                            tickDiff = 0;
                        }
                        if (tickDiff < 1 && this.serverWaitTime < 100) {
                            this.serverWaitTime += 2;
                        }
                        if (tickDiff > 1 && this.serverWaitTime > 0) {
                            --this.serverWaitTime;
                        }
                        this.lastServerTicks = ticks;
                    }
                }
                else {
                    this.lastServerTime = timeNow;
                    this.lastServerTicks = srv.getTickCounter();
                }
            }
            else {
                if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                    Config.sleep(20L);
                }
                this.lastServerTime = 0L;
                this.lastServerTicks = 0;
            }
        }
    }
    
    private void frameFinish() {
        if (this.mc.theWorld != null) {
            final long now = System.currentTimeMillis();
            if (now > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = now;
                final int err = GL11.glGetError();
                if (err != 0) {
                    final String text = GLU.gluErrorString(err);
                    final ChatComponentText msg = new ChatComponentText("§eOpenGL Error§f: " + err + " (" + text + ")");
                    this.mc.ingameGUI.getChatGUI().func_146227_a(msg);
                }
            }
        }
    }
    
    public void func_152430_c(final float p_152430_1_) {
        this.setupOverlayRendering();
        final ScaledResolution var2 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        final int var3 = var2.getScaledWidth();
        final int var4 = var2.getScaledHeight();
        this.mc.ingameGUI.func_152126_a((float)var3, (float)var4);
    }
    
    public void renderWorld(final float p_78471_1_, final long p_78471_2_) {
        final float chunkAmount = FPSBoost.CHUNK_LOADING.getValue();
        if (chunkAmount == 100.0f) {
            this.chunksRendered = 0;
        }
        this.mc.mcProfiler.startSection("lightTex");
        if (this.lightmapUpdateNeeded) {
            this.updateLightmap(p_78471_1_);
        }
        GL11.glEnable(2884);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glAlphaFunc(516, 0.5f);
        if (this.mc.renderViewEntity == null) {
            this.mc.renderViewEntity = this.mc.thePlayer;
        }
        this.mc.mcProfiler.endStartSection("pick");
        final EntityLivingBase var4 = this.mc.renderViewEntity;
        final RenderGlobal var5 = this.mc.renderGlobal;
        final EffectRenderer var6 = this.mc.effectRenderer;
        final double var7 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * p_78471_1_;
        final double var8 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * p_78471_1_;
        final double var9 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * p_78471_1_;
        this.mc.mcProfiler.endStartSection("center");
        for (int var10 = 0; var10 < 2; ++var10) {
            if (this.mc.gameSettings.anaglyph) {
                EntityRenderer.anaglyphField = var10;
                if (EntityRenderer.anaglyphField == 0) {
                    GL11.glColorMask(false, true, true, false);
                }
                else {
                    GL11.glColorMask(true, false, false, false);
                }
            }
            this.mc.mcProfiler.endStartSection("clear");
            GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            this.updateFogColor(p_78471_1_);
            GL11.glClear(16640);
            GL11.glEnable(2884);
            this.mc.mcProfiler.endStartSection("camera");
            this.setupCameraTransform(p_78471_1_, var10);
            ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
            this.mc.mcProfiler.endStartSection("frustrum");
            ClippingHelperImpl.getInstance();
            if (!Config.isSkyEnabled() && !Config.isSunMoonEnabled() && !Config.isStarsEnabled()) {
                GL11.glDisable(3042);
            }
            else {
                this.setupFog(-1, p_78471_1_);
                this.mc.mcProfiler.endStartSection("sky");
                var5.renderSky(p_78471_1_);
            }
            GL11.glEnable(2912);
            this.setupFog(1, p_78471_1_);
            if (this.mc.gameSettings.ambientOcclusion != 0) {
                GL11.glShadeModel(7425);
            }
            this.mc.mcProfiler.endStartSection("culling");
            final Frustrum var11 = new Frustrum();
            var11.setPosition(var7, var8, var9);
            this.mc.renderGlobal.clipRenderersByFrustum(var11, p_78471_1_);
            if (var10 == 0) {
                this.mc.mcProfiler.endStartSection("updatechunks");
                if (this.chunksRendered == 0) {
                    while (!this.mc.renderGlobal.updateRenderers(var4, false) && p_78471_2_ != 0L) {
                        final long var12 = p_78471_2_ - System.nanoTime();
                        if (var12 < 0L) {
                            break;
                        }
                        if (var12 > 1000000000L) {
                            break;
                        }
                    }
                }
                ++this.chunksRendered;
                if (this.chunksRendered >= 100.0f / chunkAmount) {
                    this.chunksRendered = 0;
                }
            }
            if (var4.posY < 128.0) {
                this.renderCloudsCheck(var5, p_78471_1_);
            }
            this.mc.mcProfiler.endStartSection("prepareterrain");
            this.setupFog(0, p_78471_1_);
            GL11.glEnable(2912);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            RenderHelper.disableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("terrain");
            GL11.glMatrixMode(5888);
            GL11.glPushMatrix();
            var5.sortAndRender(var4, 0, p_78471_1_);
            GL11.glShadeModel(7424);
            GL11.glAlphaFunc(516, 0.1f);
            if (this.debugViewDirection == 0) {
                GL11.glMatrixMode(5888);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                RenderHelper.enableStandardItemLighting();
                this.mc.mcProfiler.endStartSection("entities");
                var5.renderEntities(var4, var11, p_78471_1_);
                RenderHelper.disableStandardItemLighting();
                this.disableLightmap(p_78471_1_);
                GL11.glMatrixMode(5888);
                GL11.glPopMatrix();
                GL11.glPushMatrix();
                if (this.mc.objectMouseOver != null && this.mc.gameSettings.hideGUI && var4.isInsideOfMaterial(Material.water) && var4 instanceof EntityPlayer) {
                    final EntityPlayer var13 = (EntityPlayer)var4;
                    GL11.glDisable(3008);
                    this.mc.mcProfiler.endStartSection("outline");
                    var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, p_78471_1_);
                    GL11.glEnable(3008);
                }
            }
            GL11.glMatrixMode(5888);
            GL11.glPopMatrix();
            if (this.cameraZoom == 1.0 && var4 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI && this.mc.objectMouseOver != null && !var4.isInsideOfMaterial(Material.water)) {
                final EntityPlayer var13 = (EntityPlayer)var4;
                GL11.glDisable(3008);
                this.mc.mcProfiler.endStartSection("outline");
                var5.drawSelectionBox(var13, this.mc.objectMouseOver, 0, p_78471_1_);
                GL11.glEnable(3008);
            }
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 1, 1, 0);
            var5.drawBlockDamageTexture(Tessellator.instance, (EntityPlayer)var4, p_78471_1_);
            GL11.glDisable(3042);
            if (this.debugViewDirection == 0) {
                this.enableLightmap(p_78471_1_);
                this.mc.mcProfiler.endStartSection("litParticles");
                var6.renderLitParticles(var4, p_78471_1_);
                RenderHelper.disableStandardItemLighting();
                this.setupFog(0, p_78471_1_);
                this.mc.mcProfiler.endStartSection("particles");
                var6.renderParticles(var4, p_78471_1_);
                this.disableLightmap(p_78471_1_);
            }
            GL11.glDepthMask(false);
            GL11.glEnable(2884);
            this.mc.mcProfiler.endStartSection("weather");
            this.renderRainSnow(p_78471_1_);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glEnable(2884);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(516, 0.1f);
            this.setupFog(0, p_78471_1_);
            GL11.glEnable(3042);
            GL11.glDepthMask(false);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            if (this.mc.gameSettings.fancyGraphics) {
                this.mc.mcProfiler.endStartSection("water");
                if (this.mc.gameSettings.ambientOcclusion != 0) {
                    GL11.glShadeModel(7425);
                }
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                if (this.mc.gameSettings.anaglyph) {
                    if (EntityRenderer.anaglyphField == 0) {
                        GL11.glColorMask(false, true, true, true);
                    }
                    else {
                        GL11.glColorMask(true, false, false, true);
                    }
                    var5.sortAndRender(var4, 1, p_78471_1_);
                }
                else {
                    var5.sortAndRender(var4, 1, p_78471_1_);
                }
                GL11.glDisable(3042);
                GL11.glShadeModel(7424);
            }
            else {
                this.mc.mcProfiler.endStartSection("water");
                var5.sortAndRender(var4, 1, p_78471_1_);
            }
            GL11.glDepthMask(true);
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glDisable(2912);
            if (var4.posY >= 128.0) {
                this.mc.mcProfiler.endStartSection("aboveClouds");
                this.renderCloudsCheck(var5, p_78471_1_);
            }
            this.mc.mcProfiler.endStartSection("hand");
            if (this.cameraZoom == 1.0) {
                GL11.glClear(256);
                this.renderHand(p_78471_1_, var10);
            }
            if (!this.mc.gameSettings.anaglyph) {
                this.mc.mcProfiler.endSection();
                return;
            }
        }
        GL11.glColorMask(true, true, true, false);
        this.mc.mcProfiler.endSection();
    }
    
    private void renderCloudsCheck(final RenderGlobal p_82829_1_, final float p_82829_2_) {
        if (this.mc.gameSettings.shouldRenderClouds()) {
            this.mc.mcProfiler.endStartSection("clouds");
            GL11.glPushMatrix();
            this.setupFog(0, p_82829_2_);
            GL11.glEnable(2912);
            p_82829_1_.renderClouds(p_82829_2_);
            GL11.glDisable(2912);
            this.setupFog(1, p_82829_2_);
            GL11.glPopMatrix();
        }
    }
    
    private void addRainParticles() {
        float var1 = this.mc.theWorld.getRainStrength(1.0f);
        if (!this.mc.gameSettings.fancyGraphics) {
            var1 /= 2.0f;
        }
        if (var1 != 0.0f) {
            this.random.setSeed(this.rendererUpdateCount * 312987231L);
            final EntityLivingBase var2 = this.mc.renderViewEntity;
            final WorldClient var3 = this.mc.theWorld;
            final int var4 = MathHelper.floor_double(var2.posX);
            final int var5 = MathHelper.floor_double(var2.posY);
            final int var6 = MathHelper.floor_double(var2.posZ);
            final byte var7 = 10;
            double var8 = 0.0;
            double var9 = 0.0;
            double var10 = 0.0;
            int var11 = 0;
            int var12 = (int)(100.0f * var1 * var1);
            if (this.mc.gameSettings.particleSetting == 1) {
                var12 >>= 1;
            }
            else if (this.mc.gameSettings.particleSetting == 2) {
                var12 = 0;
            }
            for (int var13 = 0; var13 < var12; ++var13) {
                final int var14 = var4 + this.random.nextInt(var7) - this.random.nextInt(var7);
                final int var15 = var6 + this.random.nextInt(var7) - this.random.nextInt(var7);
                final int var16 = var3.getPrecipitationHeight(var14, var15);
                final Block var17 = var3.getBlock(var14, var16 - 1, var15);
                final BiomeGenBase var18 = var3.getBiomeGenForCoords(var14, var15);
                if (var16 <= var5 + var7 && var16 >= var5 - var7 && var18.canSpawnLightningBolt() && var18.getFloatTemperature(var14, var16, var15) >= 0.15f) {
                    final float var19 = this.random.nextFloat();
                    final float var20 = this.random.nextFloat();
                    if (var17.getMaterial() == Material.lava) {
                        this.mc.effectRenderer.addEffect(new EntitySmokeFX(var3, var14 + var19, var16 + 0.1f - var17.getBlockBoundsMinY(), var15 + var20, 0.0, 0.0, 0.0));
                    }
                    else if (var17.getMaterial() != Material.air) {
                        ++var11;
                        if (this.random.nextInt(var11) == 0) {
                            var8 = var14 + var19;
                            var9 = var16 + 0.1f - var17.getBlockBoundsMinY();
                            var10 = var15 + var20;
                        }
                        this.mc.effectRenderer.addEffect(new EntityRainFX(var3, var14 + var19, var16 + 0.1f - var17.getBlockBoundsMinY(), var15 + var20));
                    }
                }
            }
            if (var11 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
                this.rainSoundCounter = 0;
                if (var9 > var2.posY + 1.0 && var3.getPrecipitationHeight(MathHelper.floor_double(var2.posX), MathHelper.floor_double(var2.posZ)) > MathHelper.floor_double(var2.posY)) {
                    this.mc.theWorld.playSound(var8, var9, var10, "ambient.weather.rain", 0.1f, 0.5f, false);
                }
                else {
                    this.mc.theWorld.playSound(var8, var9, var10, "ambient.weather.rain", 0.2f, 1.0f, false);
                }
            }
        }
    }
    
    protected void renderRainSnow(final float p_78474_1_) {
        final float var2 = this.mc.theWorld.getRainStrength(p_78474_1_);
        if (var2 > 0.0f) {
            this.enableLightmap(p_78474_1_);
            if (this.rainXCoords == null) {
                this.rainXCoords = new float[1024];
                this.rainYCoords = new float[1024];
                for (int var3 = 0; var3 < 32; ++var3) {
                    for (int var4 = 0; var4 < 32; ++var4) {
                        final float var5 = (float)(var4 - 16);
                        final float var6 = (float)(var3 - 16);
                        final float var7 = MathHelper.sqrt_float(var5 * var5 + var6 * var6);
                        this.rainXCoords[var3 << 5 | var4] = -var6 / var7;
                        this.rainYCoords[var3 << 5 | var4] = var5 / var7;
                    }
                }
            }
            final EntityLivingBase var8 = this.mc.renderViewEntity;
            final WorldClient var9 = this.mc.theWorld;
            final int var10 = MathHelper.floor_double(var8.posX);
            final int var11 = MathHelper.floor_double(var8.posY);
            final int var12 = MathHelper.floor_double(var8.posZ);
            final Tessellator var13 = Tessellator.instance;
            GL11.glDisable(2884);
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glAlphaFunc(516, 0.1f);
            final double var14 = var8.lastTickPosX + (var8.posX - var8.lastTickPosX) * p_78474_1_;
            final double var15 = var8.lastTickPosY + (var8.posY - var8.lastTickPosY) * p_78474_1_;
            final double var16 = var8.lastTickPosZ + (var8.posZ - var8.lastTickPosZ) * p_78474_1_;
            final int var17 = MathHelper.floor_double(var15);
            byte var18 = 5;
            if (this.mc.gameSettings.fancyGraphics) {
                var18 = 10;
            }
            boolean var19 = false;
            byte var20 = -1;
            final float var21 = this.rendererUpdateCount + p_78474_1_;
            if (this.mc.gameSettings.fancyGraphics) {
                var18 = 10;
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            var19 = false;
            for (int var22 = var12 - var18; var22 <= var12 + var18; ++var22) {
                for (int var23 = var10 - var18; var23 <= var10 + var18; ++var23) {
                    final int var24 = (var22 - var12 + 16) * 32 + var23 - var10 + 16;
                    final float var25 = this.rainXCoords[var24] * 0.5f;
                    final float var26 = this.rainYCoords[var24] * 0.5f;
                    final BiomeGenBase var27 = var9.getBiomeGenForCoords(var23, var22);
                    if (var27.canSpawnLightningBolt() || var27.getEnableSnow()) {
                        final int var28 = var9.getPrecipitationHeight(var23, var22);
                        int var29 = var11 - var18;
                        int var30 = var11 + var18;
                        if (var29 < var28) {
                            var29 = var28;
                        }
                        if (var30 < var28) {
                            var30 = var28;
                        }
                        final float var31 = 1.0f;
                        int var32;
                        if ((var32 = var28) < var17) {
                            var32 = var17;
                        }
                        if (var29 != var30) {
                            this.random.setSeed(var23 * var23 * 3121 + var23 * 45238971 ^ var22 * var22 * 418711 + var22 * 13761);
                            final float var33 = var27.getFloatTemperature(var23, var29, var22);
                            if (var9.getWorldChunkManager().getTemperatureAtHeight(var33, var28) >= 0.15f) {
                                if (var20 != 0) {
                                    if (var20 >= 0) {
                                        var13.draw();
                                    }
                                    var20 = 0;
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationRainPng);
                                    var13.startDrawingQuads();
                                }
                                final float var34 = ((this.rendererUpdateCount + var23 * var23 * 3121 + var23 * 45238971 + var22 * var22 * 418711 + var22 * 13761 & 0x1F) + p_78474_1_) / 32.0f * (3.0f + this.random.nextFloat());
                                final double var35 = var23 + 0.5f - var8.posX;
                                final double var36 = var22 + 0.5f - var8.posZ;
                                final float var37 = MathHelper.sqrt_double(var35 * var35 + var36 * var36) / var18;
                                final float var38 = 1.0f;
                                var13.setBrightness(var9.getLightBrightnessForSkyBlocks(var23, var32, var22, 0));
                                var13.setColorRGBA_F(var38, var38, var38, ((1.0f - var37 * var37) * 0.5f + 0.5f) * var2);
                                var13.setTranslation(-var14 * 1.0, -var15 * 1.0, -var16 * 1.0);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var29, var22 - var26 + 0.5, 0.0f * var31, var29 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var29, var22 + var26 + 0.5, 1.0f * var31, var29 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var30, var22 + var26 + 0.5, 1.0f * var31, var30 * var31 / 4.0f + var34 * var31);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var30, var22 - var26 + 0.5, 0.0f * var31, var30 * var31 / 4.0f + var34 * var31);
                                var13.setTranslation(0.0, 0.0, 0.0);
                            }
                            else {
                                if (var20 != 1) {
                                    if (var20 >= 0) {
                                        var13.draw();
                                    }
                                    var20 = 1;
                                    this.mc.getTextureManager().bindTexture(EntityRenderer.locationSnowPng);
                                    var13.startDrawingQuads();
                                }
                                final float var34 = ((this.rendererUpdateCount & 0x1FF) + p_78474_1_) / 512.0f;
                                final float var39 = this.random.nextFloat() + var21 * 0.01f * (float)this.random.nextGaussian();
                                final float var40 = this.random.nextFloat() + var21 * (float)this.random.nextGaussian() * 0.001f;
                                final double var36 = var23 + 0.5f - var8.posX;
                                final double var41 = var22 + 0.5f - var8.posZ;
                                final float var42 = MathHelper.sqrt_double(var36 * var36 + var41 * var41) / var18;
                                final float var43 = 1.0f;
                                var13.setBrightness((var9.getLightBrightnessForSkyBlocks(var23, var32, var22, 0) * 3 + 15728880) / 4);
                                var13.setColorRGBA_F(var43, var43, var43, ((1.0f - var42 * var42) * 0.3f + 0.5f) * var2);
                                var13.setTranslation(-var14 * 1.0, -var15 * 1.0, -var16 * 1.0);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var29, var22 - var26 + 0.5, 0.0f * var31 + var39, var29 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var29, var22 + var26 + 0.5, 1.0f * var31 + var39, var29 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 + var25 + 0.5, var30, var22 + var26 + 0.5, 1.0f * var31 + var39, var30 * var31 / 4.0f + var34 * var31 + var40);
                                var13.addVertexWithUV(var23 - var25 + 0.5, var30, var22 - var26 + 0.5, 0.0f * var31 + var39, var30 * var31 / 4.0f + var34 * var31 + var40);
                                var13.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }
                }
            }
            if (var20 >= 0) {
                var13.draw();
            }
            GL11.glEnable(2884);
            GL11.glDisable(3042);
            GL11.glAlphaFunc(516, 0.1f);
            this.disableLightmap(p_78474_1_);
        }
    }
    
    public void setupOverlayRendering() {
        final ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GL11.glClear(256);
        GL11.glMatrixMode(5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, var1.getScaledWidth_double(), var1.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode(5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
    }
    
    private void updateFogColor(final float p_78466_1_) {
        final WorldClient var2 = this.mc.theWorld;
        final EntityLivingBase var3 = this.mc.renderViewEntity;
        float var4 = 0.25f + 0.75f * this.mc.gameSettings.renderDistanceChunks / 16.0f;
        var4 = 1.0f - (float)Math.pow(var4, 0.25);
        final Vec3 var5 = var2.getSkyColor(this.mc.renderViewEntity, p_78466_1_);
        final float var6 = (float)var5.xCoord;
        final float var7 = (float)var5.yCoord;
        final float var8 = (float)var5.zCoord;
        final Vec3 var9 = var2.getFogColor(p_78466_1_);
        this.fogColorRed = (float)var9.xCoord;
        this.fogColorGreen = (float)var9.yCoord;
        this.fogColorBlue = (float)var9.zCoord;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            final Vec3 var10 = (MathHelper.sin(var2.getCelestialAngleRadians(p_78466_1_)) > 0.0f) ? Vec3.createVectorHelper(-1.0, 0.0, 0.0) : Vec3.createVectorHelper(1.0, 0.0, 0.0);
            float var11 = (float)var3.getLook(p_78466_1_).dotProduct(var10);
            if (var11 < 0.0f) {
                var11 = 0.0f;
            }
            if (var11 > 0.0f) {
                final float[] var12 = var2.provider.calcSunriseSunsetColors(var2.getCelestialAngle(p_78466_1_), p_78466_1_);
                if (var12 != null) {
                    var11 *= var12[3];
                    this.fogColorRed = this.fogColorRed * (1.0f - var11) + var12[0] * var11;
                    this.fogColorGreen = this.fogColorGreen * (1.0f - var11) + var12[1] * var11;
                    this.fogColorBlue = this.fogColorBlue * (1.0f - var11) + var12[2] * var11;
                }
            }
        }
        this.fogColorRed += (var6 - this.fogColorRed) * var4;
        this.fogColorGreen += (var7 - this.fogColorGreen) * var4;
        this.fogColorBlue += (var8 - this.fogColorBlue) * var4;
        final float var13 = var2.getRainStrength(p_78466_1_);
        if (var13 > 0.0f) {
            final float var11 = 1.0f - var13 * 0.5f;
            final float var14 = 1.0f - var13 * 0.4f;
            this.fogColorRed *= var11;
            this.fogColorGreen *= var11;
            this.fogColorBlue *= var14;
        }
        float var11 = var2.getWeightedThunderStrength(p_78466_1_);
        if (var11 > 0.0f) {
            final float var14 = 1.0f - var11 * 0.5f;
            this.fogColorRed *= var14;
            this.fogColorGreen *= var14;
            this.fogColorBlue *= var14;
        }
        final Block var15 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, p_78466_1_);
        if (this.cloudFog) {
            final Vec3 var16 = var2.getCloudColour(p_78466_1_);
            this.fogColorRed = (float)var16.xCoord;
            this.fogColorGreen = (float)var16.yCoord;
            this.fogColorBlue = (float)var16.zCoord;
        }
        else if (var15.getMaterial() == Material.water) {
            final float var17 = EnchantmentHelper.getRespiration(var3) * 0.2f;
            this.fogColorRed = 0.02f + var17;
            this.fogColorGreen = 0.02f + var17;
            this.fogColorBlue = 0.2f + var17;
        }
        else if (var15.getMaterial() == Material.lava) {
            this.fogColorRed = 0.6f;
            this.fogColorGreen = 0.1f;
            this.fogColorBlue = 0.0f;
        }
        final float var17 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * p_78466_1_;
        this.fogColorRed *= var17;
        this.fogColorGreen *= var17;
        this.fogColorBlue *= var17;
        double var18 = (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * p_78466_1_) * var2.provider.getVoidFogYFactor();
        if (var3.isPotionActive(Potion.blindness)) {
            final int var19 = var3.getActivePotionEffect(Potion.blindness).getDuration();
            if (var19 < 20) {
                var18 *= 1.0f - var19 / 20.0f;
            }
            else {
                var18 = 0.0;
            }
        }
        if (var18 < 1.0) {
            if (var18 < 0.0) {
                var18 = 0.0;
            }
            var18 *= var18;
            this.fogColorRed *= (float)var18;
            this.fogColorGreen *= (float)var18;
            this.fogColorBlue *= (float)var18;
        }
        if (this.bossColorModifier > 0.0f) {
            final float var20 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * p_78466_1_;
            this.fogColorRed = this.fogColorRed * (1.0f - var20) + this.fogColorRed * 0.7f * var20;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var20) + this.fogColorGreen * 0.6f * var20;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var20) + this.fogColorBlue * 0.6f * var20;
        }
        if (var3.isPotionActive(Potion.nightVision)) {
            final float var20 = this.getNightVisionBrightness(this.mc.thePlayer, p_78466_1_);
            float var21 = 1.0f / this.fogColorRed;
            if (var21 > 1.0f / this.fogColorGreen) {
                var21 = 1.0f / this.fogColorGreen;
            }
            if (var21 > 1.0f / this.fogColorBlue) {
                var21 = 1.0f / this.fogColorBlue;
            }
            this.fogColorRed = this.fogColorRed * (1.0f - var20) + this.fogColorRed * var21 * var20;
            this.fogColorGreen = this.fogColorGreen * (1.0f - var20) + this.fogColorGreen * var21 * var20;
            this.fogColorBlue = this.fogColorBlue * (1.0f - var20) + this.fogColorBlue * var21 * var20;
        }
        if (this.mc.gameSettings.anaglyph) {
            final float var20 = (this.fogColorRed * 30.0f + this.fogColorGreen * 59.0f + this.fogColorBlue * 11.0f) / 100.0f;
            final float var21 = (this.fogColorRed * 30.0f + this.fogColorGreen * 70.0f) / 100.0f;
            final float var22 = (this.fogColorRed * 30.0f + this.fogColorBlue * 70.0f) / 100.0f;
            this.fogColorRed = var20;
            this.fogColorGreen = var21;
            this.fogColorBlue = var22;
        }
        GL11.glClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0f);
    }
    
    private void setupFog(final int p_78468_1_, final float p_78468_2_) {
        final EntityLivingBase var3 = this.mc.renderViewEntity;
        boolean var4 = false;
        this.fogStandard = true;
        if (var3 instanceof EntityPlayer) {
            var4 = ((EntityPlayer)var3).capabilities.isCreativeMode;
        }
        if (p_78468_1_ == 999) {
            GL11.glFog(2918, this.setFogColorBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glFogi(2917, 9729);
            GL11.glFogf(2915, 0.0f);
            GL11.glFogf(2916, 8.0f);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(34138, 34139);
            }
            GL11.glFogf(2915, 0.0f);
        }
        else {
            GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0f));
            GL11.glNormal3f(0.0f, -1.0f, 0.0f);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            final Block var5 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, p_78468_2_);
            if (var3.isPotionActive(Potion.blindness)) {
                float var6 = 5.0f;
                final int var7 = var3.getActivePotionEffect(Potion.blindness).getDuration();
                if (var7 < 20) {
                    var6 = 5.0f + (this.farPlaneDistance - 5.0f) * (1.0f - var7 / 20.0f);
                }
                GL11.glFogi(2917, 9729);
                if (p_78468_1_ < 0) {
                    GL11.glFogf(2915, 0.0f);
                    GL11.glFogf(2916, var6 * 0.8f);
                }
                else {
                    GL11.glFogf(2915, var6 * 0.25f);
                    GL11.glFogf(2916, var6);
                }
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    GL11.glFogi(34138, 34139);
                }
            }
            else if (this.cloudFog) {
                GL11.glFogi(2917, 2048);
                GL11.glFogf(2914, 0.1f);
            }
            else if (var5.getMaterial() == Material.water) {
                GL11.glFogi(2917, 2048);
                if (var3.isPotionActive(Potion.waterBreathing)) {
                    GL11.glFogf(2914, 0.05f);
                }
                else {
                    GL11.glFogf(2914, 0.1f - EnchantmentHelper.getRespiration(var3) * 0.03f);
                }
                if (Config.isClearWater()) {
                    GL11.glFogf(2914, 0.02f);
                }
            }
            else if (var5.getMaterial() == Material.lava) {
                GL11.glFogi(2917, 2048);
                GL11.glFogf(2914, 2.0f);
            }
            else {
                float var6 = this.farPlaneDistance;
                this.fogStandard = true;
                if (Config.isDepthFog() && this.mc.theWorld.provider.getWorldHasVoidParticles() && !var4) {
                    double var8 = ((var3.getBrightnessForRender(p_78468_2_) & 0xF00000) >> 20) / 16.0 + (var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * p_78468_2_ + 4.0) / 32.0;
                    if (var8 < 1.0) {
                        if (var8 < 0.0) {
                            var8 = 0.0;
                        }
                        var8 *= var8;
                        float var9 = 100.0f * (float)var8;
                        if (var9 < 5.0f) {
                            var9 = 5.0f;
                        }
                        if (var6 > var9) {
                            var6 = var9;
                        }
                    }
                }
                GL11.glFogi(2917, 9729);
                if (p_78468_1_ < 0) {
                    GL11.glFogf(2915, 0.0f);
                    GL11.glFogf(2916, var6);
                }
                else {
                    GL11.glFogf(2915, var6 * 0.75f);
                    GL11.glFogf(2916, var6);
                }
                if (GLContext.getCapabilities().GL_NV_fog_distance) {
                    if (Config.isFogFancy()) {
                        GL11.glFogi(34138, 34139);
                    }
                    if (Config.isFogFast()) {
                        GL11.glFogi(34138, 34140);
                    }
                }
                if (this.mc.theWorld.provider.doesXZShowFog((int)var3.posX, (int)var3.posZ)) {
                    GL11.glFogf(2915, var6 * 0.05f);
                    GL11.glFogf(2916, Math.min(var6, 192.0f) * 0.5f);
                }
            }
            GL11.glEnable(2903);
            GL11.glColorMaterial(1028, 4608);
        }
    }
    
    private FloatBuffer setFogColorBuffer(final float p_78469_1_, final float p_78469_2_, final float p_78469_3_, final float p_78469_4_) {
        this.fogColorBuffer.clear();
        this.fogColorBuffer.put(p_78469_1_).put(p_78469_2_).put(p_78469_3_).put(p_78469_4_);
        this.fogColorBuffer.flip();
        return this.fogColorBuffer;
    }
    
    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }
    
    static {
        logger = LogManager.getLogger();
        locationRainPng = new ResourceLocation("textures/environment/rain.png");
        locationSnowPng = new ResourceLocation("textures/environment/snow.png");
        shaderResourceLocations = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json") };
        shaderCount = EntityRenderer.shaderResourceLocations.length;
    }
}
