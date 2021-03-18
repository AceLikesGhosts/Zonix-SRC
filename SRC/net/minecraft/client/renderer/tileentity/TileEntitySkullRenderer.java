package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import com.mojang.authlib.minecraft.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer
{
    private static final ResourceLocation field_147537_c;
    private static final ResourceLocation field_147534_d;
    private static final ResourceLocation field_147535_e;
    private static final ResourceLocation field_147532_f;
    public static TileEntitySkullRenderer field_147536_b;
    private ModelSkeletonHead field_147533_g;
    private ModelSkeletonHead field_147538_h;
    private static final String __OBFID = "CL_00000971";
    
    public TileEntitySkullRenderer() {
        this.field_147533_g = new ModelSkeletonHead(0, 0, 64, 32);
        this.field_147538_h = new ModelSkeletonHead(0, 0, 64, 64);
    }
    
    public void renderTileEntityAt(final TileEntitySkull p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.func_152674_a((float)p_147500_2_, (float)p_147500_4_, (float)p_147500_6_, p_147500_1_.getBlockMetadata() & 0x7, p_147500_1_.func_145906_b() * 360 / 16.0f, p_147500_1_.func_145904_a(), p_147500_1_.func_152108_a());
    }
    
    @Override
    public void func_147497_a(final TileEntityRendererDispatcher p_147497_1_) {
        super.func_147497_a(p_147497_1_);
        TileEntitySkullRenderer.field_147536_b = this;
    }
    
    public void func_152674_a(final float p_152674_1_, final float p_152674_2_, final float p_152674_3_, final int p_152674_4_, float p_152674_5_, final int p_152674_6_, final GameProfile p_152674_7_) {
        ModelSkeletonHead var8 = this.field_147533_g;
        switch (p_152674_6_) {
            default: {
                this.bindTexture(TileEntitySkullRenderer.field_147537_c);
                break;
            }
            case 1: {
                this.bindTexture(TileEntitySkullRenderer.field_147534_d);
                break;
            }
            case 2: {
                this.bindTexture(TileEntitySkullRenderer.field_147535_e);
                var8 = this.field_147538_h;
                break;
            }
            case 3: {
                ResourceLocation var9 = AbstractClientPlayer.locationStevePng;
                if (p_152674_7_ != null) {
                    final Minecraft var10 = Minecraft.getMinecraft();
                    final Map var11 = var10.func_152342_ad().func_152788_a(p_152674_7_);
                    if (var11.containsKey(MinecraftProfileTexture.Type.SKIN)) {
                        var9 = var10.func_152342_ad().func_152792_a(var11.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
                    }
                }
                this.bindTexture(var9);
                break;
            }
            case 4: {
                this.bindTexture(TileEntitySkullRenderer.field_147532_f);
                break;
            }
        }
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        if (p_152674_4_ != 1) {
            switch (p_152674_4_) {
                case 2: {
                    GL11.glTranslatef(p_152674_1_ + 0.5f, p_152674_2_ + 0.25f, p_152674_3_ + 0.74f);
                    break;
                }
                case 3: {
                    GL11.glTranslatef(p_152674_1_ + 0.5f, p_152674_2_ + 0.25f, p_152674_3_ + 0.26f);
                    p_152674_5_ = 180.0f;
                    break;
                }
                case 4: {
                    GL11.glTranslatef(p_152674_1_ + 0.74f, p_152674_2_ + 0.25f, p_152674_3_ + 0.5f);
                    p_152674_5_ = 270.0f;
                    break;
                }
                default: {
                    GL11.glTranslatef(p_152674_1_ + 0.26f, p_152674_2_ + 0.25f, p_152674_3_ + 0.5f);
                    p_152674_5_ = 90.0f;
                    break;
                }
            }
        }
        else {
            GL11.glTranslatef(p_152674_1_ + 0.5f, p_152674_2_, p_152674_3_ + 0.5f);
        }
        final float var12 = 0.0625f;
        GL11.glEnable(32826);
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        GL11.glEnable(3008);
        var8.render(null, 0.0f, 0.0f, 0.0f, p_152674_5_, 0.0f, var12);
        GL11.glPopMatrix();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity p_147500_1_, final double p_147500_2_, final double p_147500_4_, final double p_147500_6_, final float p_147500_8_) {
        this.renderTileEntityAt((TileEntitySkull)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
    
    static {
        field_147537_c = new ResourceLocation("textures/entity/skeleton/skeleton.png");
        field_147534_d = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
        field_147535_e = new ResourceLocation("textures/entity/zombie/zombie.png");
        field_147532_f = new ResourceLocation("textures/entity/creeper/creeper.png");
    }
}
