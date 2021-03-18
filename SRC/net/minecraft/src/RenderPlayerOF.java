package net.minecraft.src;

import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.player.*;
import java.lang.reflect.*;
import java.util.*;

public class RenderPlayerOF extends RenderPlayer
{
    @Override
    protected void renderEquippedItems(final EntityLivingBase entityLiving, final float partialTicks) {
        super.renderEquippedItems(entityLiving, partialTicks);
        this.renderEquippedItems(entityLiving, 0.0625f, partialTicks);
    }
    
    private void renderEquippedItems(final EntityLivingBase entityLiving, final float scale, final float partialTicks) {
        if (Config.isShowCapes() && entityLiving instanceof AbstractClientPlayer) {
            final AbstractClientPlayer player = (AbstractClientPlayer)entityLiving;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glDisable(32826);
            final ModelBiped modelBipedMain = (ModelBiped)this.mainModel;
            PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
        }
    }
    
    public static void register() {
        final RenderManager rm = RenderManager.instance;
        final Map mapRenderTypes = getMapRenderTypes(rm);
        if (mapRenderTypes == null) {
            Config.warn("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
        }
        else {
            final RenderPlayerOF rpof = new RenderPlayerOF();
            rpof.setRenderManager(rm);
            mapRenderTypes.put(EntityPlayer.class, rpof);
        }
    }
    
    public static Field[] getFields(final Class cls, final Class fieldType) {
        final ArrayList list = new ArrayList();
        try {
            final Field[] e = cls.getDeclaredFields();
            for (int fields = 0; fields < e.length; ++fields) {
                final Field field = e[fields];
                if (field.getType() == fieldType) {
                    field.setAccessible(true);
                    list.add(field);
                }
            }
            final Field[] var7 = list.toArray(new Field[list.size()]);
            return var7;
        }
        catch (Exception var8) {
            return null;
        }
    }
    
    private static Map getMapRenderTypes(final RenderManager rm) {
        try {
            final Field[] e = getFields(RenderManager.class, Map.class);
            for (int i = 0; i < e.length; ++i) {
                final Field field = e[i];
                final Map map = (Map)field.get(rm);
                if (map != null) {
                    final Object renderSteve = map.get(EntityPlayer.class);
                    if (renderSteve instanceof RenderPlayer) {
                        return map;
                    }
                }
            }
            return null;
        }
        catch (Exception var6) {
            Config.warn("Error getting RenderManager.mapRenderTypes");
            Config.warn(var6.getClass().getName() + ": " + var6.getMessage());
            return null;
        }
    }
}
