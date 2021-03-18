package net.minecraft.world.storage;

import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import java.util.*;

public class MapData extends WorldSavedData
{
    public int xCenter;
    public int zCenter;
    public byte dimension;
    public byte scale;
    public byte[] colors;
    public List playersArrayList;
    private Map playersHashMap;
    public Map playersVisibleOnMap;
    private static final String __OBFID = "CL_00000577";
    
    public MapData(final String p_i2140_1_) {
        super(p_i2140_1_);
        this.colors = new byte[16384];
        this.playersArrayList = new ArrayList();
        this.playersHashMap = new HashMap();
        this.playersVisibleOnMap = new LinkedHashMap();
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_76184_1_) {
        this.dimension = p_76184_1_.getByte("dimension");
        this.xCenter = p_76184_1_.getInteger("xCenter");
        this.zCenter = p_76184_1_.getInteger("zCenter");
        this.scale = p_76184_1_.getByte("scale");
        if (this.scale < 0) {
            this.scale = 0;
        }
        if (this.scale > 4) {
            this.scale = 4;
        }
        final short var2 = p_76184_1_.getShort("width");
        final short var3 = p_76184_1_.getShort("height");
        if (var2 == 128 && var3 == 128) {
            this.colors = p_76184_1_.getByteArray("colors");
        }
        else {
            final byte[] var4 = p_76184_1_.getByteArray("colors");
            this.colors = new byte[16384];
            final int var5 = (128 - var2) / 2;
            final int var6 = (128 - var3) / 2;
            for (int var7 = 0; var7 < var3; ++var7) {
                final int var8 = var7 + var6;
                if (var8 >= 0 || var8 < 128) {
                    for (int var9 = 0; var9 < var2; ++var9) {
                        final int var10 = var9 + var5;
                        if (var10 >= 0 || var10 < 128) {
                            this.colors[var10 + var8 * 128] = var4[var9 + var7 * var2];
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_76187_1_) {
        p_76187_1_.setByte("dimension", this.dimension);
        p_76187_1_.setInteger("xCenter", this.xCenter);
        p_76187_1_.setInteger("zCenter", this.zCenter);
        p_76187_1_.setByte("scale", this.scale);
        p_76187_1_.setShort("width", (short)128);
        p_76187_1_.setShort("height", (short)128);
        p_76187_1_.setByteArray("colors", this.colors);
    }
    
    public void updateVisiblePlayers(final EntityPlayer p_76191_1_, final ItemStack p_76191_2_) {
        if (!this.playersHashMap.containsKey(p_76191_1_)) {
            final MapInfo var3 = new MapInfo(p_76191_1_);
            this.playersHashMap.put(p_76191_1_, var3);
            this.playersArrayList.add(var3);
        }
        if (!p_76191_1_.inventory.hasItemStack(p_76191_2_)) {
            this.playersVisibleOnMap.remove(p_76191_1_.getCommandSenderName());
        }
        for (int var4 = 0; var4 < this.playersArrayList.size(); ++var4) {
            final MapInfo var5 = this.playersArrayList.get(var4);
            if (!var5.entityplayerObj.isDead && (var5.entityplayerObj.inventory.hasItemStack(p_76191_2_) || p_76191_2_.isOnItemFrame())) {
                if (!p_76191_2_.isOnItemFrame() && var5.entityplayerObj.dimension == this.dimension) {
                    this.func_82567_a(0, var5.entityplayerObj.worldObj, var5.entityplayerObj.getCommandSenderName(), var5.entityplayerObj.posX, var5.entityplayerObj.posZ, var5.entityplayerObj.rotationYaw);
                }
            }
            else {
                this.playersHashMap.remove(var5.entityplayerObj);
                this.playersArrayList.remove(var5);
            }
        }
        if (p_76191_2_.isOnItemFrame()) {
            this.func_82567_a(1, p_76191_1_.worldObj, "frame-" + p_76191_2_.getItemFrame().getEntityId(), p_76191_2_.getItemFrame().field_146063_b, p_76191_2_.getItemFrame().field_146062_d, p_76191_2_.getItemFrame().hangingDirection * 90);
        }
    }
    
    private void func_82567_a(int p_82567_1_, final World p_82567_2_, final String p_82567_3_, final double p_82567_4_, final double p_82567_6_, double p_82567_8_) {
        final int var10 = 1 << this.scale;
        final float var11 = (float)(p_82567_4_ - this.xCenter) / var10;
        final float var12 = (float)(p_82567_6_ - this.zCenter) / var10;
        byte var13 = (byte)(var11 * 2.0f + 0.5);
        byte var14 = (byte)(var12 * 2.0f + 0.5);
        final byte var15 = 63;
        byte var16;
        if (var11 >= -var15 && var12 >= -var15 && var11 <= var15 && var12 <= var15) {
            p_82567_8_ += ((p_82567_8_ < 0.0) ? -8.0 : 8.0);
            var16 = (byte)(p_82567_8_ * 16.0 / 360.0);
            if (this.dimension < 0) {
                final int var17 = (int)(p_82567_2_.getWorldInfo().getWorldTime() / 10L);
                var16 = (byte)(var17 * var17 * 34187121 + var17 * 121 >> 15 & 0xF);
            }
        }
        else {
            if (Math.abs(var11) >= 320.0f || Math.abs(var12) >= 320.0f) {
                this.playersVisibleOnMap.remove(p_82567_3_);
                return;
            }
            p_82567_1_ = 6;
            var16 = 0;
            if (var11 <= -var15) {
                var13 = (byte)(var15 * 2 + 2.5);
            }
            if (var12 <= -var15) {
                var14 = (byte)(var15 * 2 + 2.5);
            }
            if (var11 >= var15) {
                var13 = (byte)(var15 * 2 + 1);
            }
            if (var12 >= var15) {
                var14 = (byte)(var15 * 2 + 1);
            }
        }
        this.playersVisibleOnMap.put(p_82567_3_, new MapCoord((byte)p_82567_1_, var13, var14, var16));
    }
    
    public byte[] getUpdatePacketData(final ItemStack p_76193_1_, final World p_76193_2_, final EntityPlayer p_76193_3_) {
        final MapInfo var4 = this.playersHashMap.get(p_76193_3_);
        return (byte[])((var4 == null) ? null : var4.getPlayersOnMap(p_76193_1_));
    }
    
    public void setColumnDirty(final int p_76194_1_, final int p_76194_2_, final int p_76194_3_) {
        super.markDirty();
        for (int var4 = 0; var4 < this.playersArrayList.size(); ++var4) {
            final MapInfo var5 = this.playersArrayList.get(var4);
            if (var5.field_76209_b[p_76194_1_] < 0 || var5.field_76209_b[p_76194_1_] > p_76194_2_) {
                var5.field_76209_b[p_76194_1_] = p_76194_2_;
            }
            if (var5.field_76210_c[p_76194_1_] < 0 || var5.field_76210_c[p_76194_1_] < p_76194_3_) {
                var5.field_76210_c[p_76194_1_] = p_76194_3_;
            }
        }
    }
    
    public void updateMPMapData(final byte[] p_76192_1_) {
        if (p_76192_1_[0] == 0) {
            final int var2 = p_76192_1_[1] & 0xFF;
            final int var3 = p_76192_1_[2] & 0xFF;
            for (int var4 = 0; var4 < p_76192_1_.length - 3; ++var4) {
                this.colors[(var4 + var3) * 128 + var2] = p_76192_1_[var4 + 3];
            }
            this.markDirty();
        }
        else if (p_76192_1_[0] == 1) {
            this.playersVisibleOnMap.clear();
            for (int var2 = 0; var2 < (p_76192_1_.length - 1) / 3; ++var2) {
                final byte var5 = (byte)(p_76192_1_[var2 * 3 + 1] >> 4);
                final byte var6 = p_76192_1_[var2 * 3 + 2];
                final byte var7 = p_76192_1_[var2 * 3 + 3];
                final byte var8 = (byte)(p_76192_1_[var2 * 3 + 1] & 0xF);
                this.playersVisibleOnMap.put("icon-" + var2, new MapCoord(var5, var6, var7, var8));
            }
        }
        else if (p_76192_1_[0] == 2) {
            this.scale = p_76192_1_[1];
        }
    }
    
    public MapInfo func_82568_a(final EntityPlayer p_82568_1_) {
        MapInfo var2 = this.playersHashMap.get(p_82568_1_);
        if (var2 == null) {
            var2 = new MapInfo(p_82568_1_);
            this.playersHashMap.put(p_82568_1_, var2);
            this.playersArrayList.add(var2);
        }
        return var2;
    }
    
    public class MapCoord
    {
        public byte iconSize;
        public byte centerX;
        public byte centerZ;
        public byte iconRotation;
        private static final String __OBFID = "CL_00000579";
        
        public MapCoord(final byte p_i2139_2_, final byte p_i2139_3_, final byte p_i2139_4_, final byte p_i2139_5_) {
            this.iconSize = p_i2139_2_;
            this.centerX = p_i2139_3_;
            this.centerZ = p_i2139_4_;
            this.iconRotation = p_i2139_5_;
        }
    }
    
    public class MapInfo
    {
        public final EntityPlayer entityplayerObj;
        public int[] field_76209_b;
        public int[] field_76210_c;
        private int currentRandomNumber;
        private int ticksUntilPlayerLocationMapUpdate;
        private byte[] lastPlayerLocationOnMap;
        public int field_82569_d;
        private boolean field_82570_i;
        private static final String __OBFID = "CL_00000578";
        
        public MapInfo(final EntityPlayer p_i2138_2_) {
            this.field_76209_b = new int[128];
            this.field_76210_c = new int[128];
            this.entityplayerObj = p_i2138_2_;
            for (int var3 = 0; var3 < this.field_76209_b.length; ++var3) {
                this.field_76209_b[var3] = 0;
                this.field_76210_c[var3] = 127;
            }
        }
        
        public byte[] getPlayersOnMap(final ItemStack p_76204_1_) {
            if (!this.field_82570_i) {
                final byte[] var2 = { 2, MapData.this.scale };
                this.field_82570_i = true;
                return var2;
            }
            if (--this.ticksUntilPlayerLocationMapUpdate < 0) {
                this.ticksUntilPlayerLocationMapUpdate = 4;
                final byte[] var2 = new byte[MapData.this.playersVisibleOnMap.size() * 3 + 1];
                var2[0] = 1;
                int var3 = 0;
                for (final MapCoord var5 : MapData.this.playersVisibleOnMap.values()) {
                    var2[var3 * 3 + 1] = (byte)(var5.iconSize << 4 | (var5.iconRotation & 0xF));
                    var2[var3 * 3 + 2] = var5.centerX;
                    var2[var3 * 3 + 3] = var5.centerZ;
                    ++var3;
                }
                boolean var6 = !p_76204_1_.isOnItemFrame();
                if (this.lastPlayerLocationOnMap != null && this.lastPlayerLocationOnMap.length == var2.length) {
                    for (int var7 = 0; var7 < var2.length; ++var7) {
                        if (var2[var7] != this.lastPlayerLocationOnMap[var7]) {
                            var6 = false;
                            break;
                        }
                    }
                }
                else {
                    var6 = false;
                }
                if (!var6) {
                    return this.lastPlayerLocationOnMap = var2;
                }
            }
            for (int var8 = 0; var8 < 1; ++var8) {
                final int var3 = this.currentRandomNumber++ * 11 % 128;
                if (this.field_76209_b[var3] >= 0) {
                    final int var9 = this.field_76210_c[var3] - this.field_76209_b[var3] + 1;
                    final int var7 = this.field_76209_b[var3];
                    final byte[] var10 = new byte[var9 + 3];
                    var10[0] = 0;
                    var10[1] = (byte)var3;
                    var10[2] = (byte)var7;
                    for (int var11 = 0; var11 < var10.length - 3; ++var11) {
                        var10[var11 + 3] = MapData.this.colors[(var11 + var7) * 128 + var3];
                    }
                    this.field_76210_c[var3] = -1;
                    this.field_76209_b[var3] = -1;
                    return var10;
                }
            }
            return null;
        }
    }
}
