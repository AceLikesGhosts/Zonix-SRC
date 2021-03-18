package net.minecraft.src;

import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;

public class ClearWater
{
    public static void updateWaterOpacity(final GameSettings settings, final World world) {
        if (settings != null) {
            byte cp = 3;
            if (settings.ofClearWater) {
                cp = 1;
            }
            BlockUtils.setLightOpacity(Blocks.water, cp);
            BlockUtils.setLightOpacity(Blocks.flowing_water, cp);
        }
        if (world != null) {
            final IChunkProvider var23 = world.getChunkProvider();
            if (var23 != null) {
                final EntityLivingBase rve = Config.getMinecraft().renderViewEntity;
                if (rve != null) {
                    final int cViewX = (int)rve.posX / 16;
                    final int cViewZ = (int)rve.posZ / 16;
                    final int cXMin = cViewX - 512;
                    final int cXMax = cViewX + 512;
                    final int cZMin = cViewZ - 512;
                    final int cZMax = cViewZ + 512;
                    int countUpdated = 0;
                    for (int threadName = cXMin; threadName < cXMax; ++threadName) {
                        for (int cz = cZMin; cz < cZMax; ++cz) {
                            if (var23.chunkExists(threadName, cz)) {
                                final Chunk c = var23.provideChunk(threadName, cz);
                                if (c != null && !(c instanceof EmptyChunk)) {
                                    final int x0 = threadName << 4;
                                    final int z0 = cz << 4;
                                    final int x2 = x0 + 16;
                                    final int z2 = z0 + 16;
                                    for (int x3 = x0; x3 < x2; ++x3) {
                                        for (int z3 = z0; z3 < z2; ++z3) {
                                            for (int posH = world.getPrecipitationHeight(x3, z3), y = 0; y < posH; ++y) {
                                                final Block block = world.getBlock(x3, y, z3);
                                                if (block.getMaterial() == Material.water) {
                                                    world.markBlocksDirtyVertical(x3, z3, y, posH);
                                                    ++countUpdated;
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (countUpdated > 0) {
                        String var24 = "server";
                        if (Config.isMinecraftThread()) {
                            var24 = "client";
                        }
                        Config.dbg("ClearWater (" + var24 + ") relighted " + countUpdated + " chunks");
                    }
                }
            }
        }
    }
}
