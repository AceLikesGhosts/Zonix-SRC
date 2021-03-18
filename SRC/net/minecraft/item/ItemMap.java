package net.minecraft.item;

import net.minecraft.world.storage.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.init.*;
import java.util.*;

public class ItemMap extends ItemMapBase
{
    private static final String __OBFID = "CL_00000047";
    
    protected ItemMap() {
        this.setHasSubtypes(true);
    }
    
    public static MapData func_150912_a(final int p_150912_0_, final World p_150912_1_) {
        final String var2 = "map_" + p_150912_0_;
        MapData var3 = (MapData)p_150912_1_.loadItemData(MapData.class, var2);
        if (var3 == null) {
            var3 = new MapData(var2);
            p_150912_1_.setItemData(var2, var3);
        }
        return var3;
    }
    
    public MapData getMapData(final ItemStack p_77873_1_, final World p_77873_2_) {
        String var3 = "map_" + p_77873_1_.getItemDamage();
        MapData var4 = (MapData)p_77873_2_.loadItemData(MapData.class, var3);
        if (var4 == null && !p_77873_2_.isClient) {
            p_77873_1_.setItemDamage(p_77873_2_.getUniqueDataId("map"));
            var3 = "map_" + p_77873_1_.getItemDamage();
            var4 = new MapData(var3);
            var4.scale = 3;
            final int var5 = 128 * (1 << var4.scale);
            var4.xCenter = Math.round(p_77873_2_.getWorldInfo().getSpawnX() / (float)var5) * var5;
            var4.zCenter = Math.round((float)(p_77873_2_.getWorldInfo().getSpawnZ() / var5)) * var5;
            var4.dimension = (byte)p_77873_2_.provider.dimensionId;
            var4.markDirty();
            p_77873_2_.setItemData(var3, var4);
        }
        return var4;
    }
    
    public void updateMapData(final World p_77872_1_, final Entity p_77872_2_, final MapData p_77872_3_) {
        if (p_77872_1_.provider.dimensionId == p_77872_3_.dimension && p_77872_2_ instanceof EntityPlayer) {
            final int var4 = 1 << p_77872_3_.scale;
            final int var5 = p_77872_3_.xCenter;
            final int var6 = p_77872_3_.zCenter;
            final int var7 = MathHelper.floor_double(p_77872_2_.posX - var5) / var4 + 64;
            final int var8 = MathHelper.floor_double(p_77872_2_.posZ - var6) / var4 + 64;
            int var9 = 128 / var4;
            if (p_77872_1_.provider.hasNoSky) {
                var9 /= 2;
            }
            final MapData.MapInfo func_82568_a;
            final MapData.MapInfo var10 = func_82568_a = p_77872_3_.func_82568_a((EntityPlayer)p_77872_2_);
            ++func_82568_a.field_82569_d;
            for (int var11 = var7 - var9 + 1; var11 < var7 + var9; ++var11) {
                if ((var11 & 0xF) == (var10.field_82569_d & 0xF)) {
                    int var12 = 255;
                    int var13 = 0;
                    double var14 = 0.0;
                    for (int var15 = var8 - var9 - 1; var15 < var8 + var9; ++var15) {
                        if (var11 >= 0 && var15 >= -1 && var11 < 128 && var15 < 128) {
                            final int var16 = var11 - var7;
                            final int var17 = var15 - var8;
                            final boolean var18 = var16 * var16 + var17 * var17 > (var9 - 2) * (var9 - 2);
                            final int var19 = (var5 / var4 + var11 - 64) * var4;
                            final int var20 = (var6 / var4 + var15 - 64) * var4;
                            final HashMultiset var21 = HashMultiset.create();
                            final Chunk var22 = p_77872_1_.getChunkFromBlockCoords(var19, var20);
                            if (!var22.isEmpty()) {
                                final int var23 = var19 & 0xF;
                                final int var24 = var20 & 0xF;
                                int var25 = 0;
                                double var26 = 0.0;
                                if (p_77872_1_.provider.hasNoSky) {
                                    int var27 = var19 + var20 * 231871;
                                    var27 = var27 * var27 * 31287121 + var27 * 11;
                                    if ((var27 >> 20 & 0x1) == 0x0) {
                                        var21.add((Object)Blocks.dirt.getMapColor(0), 10);
                                    }
                                    else {
                                        var21.add((Object)Blocks.stone.getMapColor(0), 100);
                                    }
                                    var26 = 100.0;
                                }
                                else {
                                    for (int var27 = 0; var27 < var4; ++var27) {
                                        for (int var28 = 0; var28 < var4; ++var28) {
                                            int var29 = var22.getHeightValue(var27 + var23, var28 + var24) + 1;
                                            Block var30 = Blocks.air;
                                            int var31 = 0;
                                            if (var29 > 1) {
                                                do {
                                                    --var29;
                                                    var30 = var22.func_150810_a(var27 + var23, var29, var28 + var24);
                                                    var31 = var22.getBlockMetadata(var27 + var23, var29, var28 + var24);
                                                } while (var30.getMapColor(var31) == MapColor.field_151660_b && var29 > 0);
                                                if (var29 > 0 && var30.getMaterial().isLiquid()) {
                                                    int var32 = var29 - 1;
                                                    Block var33;
                                                    do {
                                                        var33 = var22.func_150810_a(var27 + var23, var32--, var28 + var24);
                                                        ++var25;
                                                    } while (var32 > 0 && var33.getMaterial().isLiquid());
                                                }
                                            }
                                            var26 += var29 / (double)(var4 * var4);
                                            var21.add((Object)var30.getMapColor(var31));
                                        }
                                    }
                                }
                                var25 /= var4 * var4;
                                double var34 = (var26 - var14) * 4.0 / (var4 + 4) + ((var11 + var15 & 0x1) - 0.5) * 0.4;
                                byte var35 = 1;
                                if (var34 > 0.6) {
                                    var35 = 2;
                                }
                                if (var34 < -0.6) {
                                    var35 = 0;
                                }
                                final MapColor var36 = (MapColor)Iterables.getFirst((Iterable)Multisets.copyHighestCountFirst((Multiset)var21), (Object)MapColor.field_151660_b);
                                if (var36 == MapColor.field_151662_n) {
                                    var34 = var25 * 0.1 + (var11 + var15 & 0x1) * 0.2;
                                    var35 = 1;
                                    if (var34 < 0.5) {
                                        var35 = 2;
                                    }
                                    if (var34 > 0.9) {
                                        var35 = 0;
                                    }
                                }
                                var14 = var26;
                                if (var15 >= 0 && var16 * var16 + var17 * var17 < var9 * var9 && (!var18 || (var11 + var15 & 0x1) != 0x0)) {
                                    final byte var37 = p_77872_3_.colors[var11 + var15 * 128];
                                    final byte var38 = (byte)(var36.colorIndex * 4 + var35);
                                    if (var37 != var38) {
                                        if (var12 > var15) {
                                            var12 = var15;
                                        }
                                        if (var13 < var15) {
                                            var13 = var15;
                                        }
                                        p_77872_3_.colors[var11 + var15 * 128] = var38;
                                    }
                                }
                            }
                        }
                    }
                    if (var12 <= var13) {
                        p_77872_3_.setColumnDirty(var11, var12, var13);
                    }
                }
            }
        }
    }
    
    @Override
    public void onUpdate(final ItemStack p_77663_1_, final World p_77663_2_, final Entity p_77663_3_, final int p_77663_4_, final boolean p_77663_5_) {
        if (!p_77663_2_.isClient) {
            final MapData var6 = this.getMapData(p_77663_1_, p_77663_2_);
            if (p_77663_3_ instanceof EntityPlayer) {
                final EntityPlayer var7 = (EntityPlayer)p_77663_3_;
                var6.updateVisiblePlayers(var7, p_77663_1_);
            }
            if (p_77663_5_) {
                this.updateMapData(p_77663_2_, p_77663_3_, var6);
            }
        }
    }
    
    @Override
    public Packet func_150911_c(final ItemStack p_150911_1_, final World p_150911_2_, final EntityPlayer p_150911_3_) {
        final byte[] var4 = this.getMapData(p_150911_1_, p_150911_2_).getUpdatePacketData(p_150911_1_, p_150911_2_, p_150911_3_);
        return (var4 == null) ? null : new S34PacketMaps(p_150911_1_.getItemDamage(), var4);
    }
    
    @Override
    public void onCreated(final ItemStack p_77622_1_, final World p_77622_2_, final EntityPlayer p_77622_3_) {
        if (p_77622_1_.hasTagCompound() && p_77622_1_.getTagCompound().getBoolean("map_is_scaling")) {
            final MapData var4 = Items.filled_map.getMapData(p_77622_1_, p_77622_2_);
            p_77622_1_.setItemDamage(p_77622_2_.getUniqueDataId("map"));
            final MapData var5 = new MapData("map_" + p_77622_1_.getItemDamage());
            var5.scale = (byte)(var4.scale + 1);
            if (var5.scale > 4) {
                var5.scale = 4;
            }
            var5.xCenter = var4.xCenter;
            var5.zCenter = var4.zCenter;
            var5.dimension = var4.dimension;
            var5.markDirty();
            p_77622_2_.setItemData("map_" + p_77622_1_.getItemDamage(), var5);
        }
    }
    
    @Override
    public void addInformation(final ItemStack p_77624_1_, final EntityPlayer p_77624_2_, final List p_77624_3_, final boolean p_77624_4_) {
        final MapData var5 = this.getMapData(p_77624_1_, p_77624_2_.worldObj);
        if (p_77624_4_) {
            if (var5 == null) {
                p_77624_3_.add("Unknown map");
            }
            else {
                p_77624_3_.add("Scaling at 1:" + (1 << var5.scale));
                p_77624_3_.add("(Level " + var5.scale + "/" + 4 + ")");
            }
        }
    }
}
