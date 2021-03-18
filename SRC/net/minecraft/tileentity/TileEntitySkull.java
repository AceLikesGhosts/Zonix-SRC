package net.minecraft.tileentity;

import com.mojang.authlib.*;
import net.minecraft.nbt.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.server.*;
import com.mojang.authlib.properties.*;
import com.google.common.collect.*;

public class TileEntitySkull extends TileEntity
{
    private int field_145908_a;
    private int field_145910_i;
    private GameProfile field_152110_j;
    private static final String __OBFID = "CL_00000364";
    
    public TileEntitySkull() {
        this.field_152110_j = null;
    }
    
    @Override
    public void writeToNBT(final NBTTagCompound p_145841_1_) {
        super.writeToNBT(p_145841_1_);
        p_145841_1_.setByte("SkullType", (byte)(this.field_145908_a & 0xFF));
        p_145841_1_.setByte("Rot", (byte)(this.field_145910_i & 0xFF));
        if (this.field_152110_j != null) {
            final NBTTagCompound var2 = new NBTTagCompound();
            NBTUtil.func_152460_a(var2, this.field_152110_j);
            p_145841_1_.setTag("Owner", var2);
        }
    }
    
    @Override
    public void readFromNBT(final NBTTagCompound p_145839_1_) {
        super.readFromNBT(p_145839_1_);
        this.field_145908_a = p_145839_1_.getByte("SkullType");
        this.field_145910_i = p_145839_1_.getByte("Rot");
        if (this.field_145908_a == 3) {
            if (p_145839_1_.func_150297_b("Owner", 10)) {
                this.field_152110_j = NBTUtil.func_152459_a(p_145839_1_.getCompoundTag("Owner"));
            }
            else if (p_145839_1_.func_150297_b("ExtraType", 8) && !StringUtils.isNullOrEmpty(p_145839_1_.getString("ExtraType"))) {
                this.field_152110_j = new GameProfile((UUID)null, p_145839_1_.getString("ExtraType"));
                this.func_152109_d();
            }
        }
    }
    
    public GameProfile func_152108_a() {
        return this.field_152110_j;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        final NBTTagCompound var1 = new NBTTagCompound();
        this.writeToNBT(var1);
        return new S35PacketUpdateTileEntity(this.field_145851_c, this.field_145848_d, this.field_145849_e, 4, var1);
    }
    
    public void func_152107_a(final int p_152107_1_) {
        this.field_145908_a = p_152107_1_;
        this.field_152110_j = null;
    }
    
    public void func_152106_a(final GameProfile p_152106_1_) {
        this.field_145908_a = 3;
        this.field_152110_j = p_152106_1_;
        this.func_152109_d();
    }
    
    private void func_152109_d() {
        if (this.field_152110_j != null && !StringUtils.isNullOrEmpty(this.field_152110_j.getName()) && (!this.field_152110_j.isComplete() || !this.field_152110_j.getProperties().containsKey((Object)"textures"))) {
            GameProfile var1 = MinecraftServer.getServer().func_152358_ax().func_152655_a(this.field_152110_j.getName());
            if (var1 != null) {
                final Property var2 = (Property)Iterables.getFirst((Iterable)var1.getProperties().get((Object)"textures"), (Object)null);
                if (var2 == null) {
                    var1 = MinecraftServer.getServer().func_147130_as().fillProfileProperties(var1, true);
                }
                this.field_152110_j = var1;
                this.onInventoryChanged();
            }
        }
    }
    
    public int func_145904_a() {
        return this.field_145908_a;
    }
    
    public int func_145906_b() {
        return this.field_145910_i;
    }
    
    public void func_145903_a(final int p_145903_1_) {
        this.field_145910_i = p_145903_1_;
    }
}
