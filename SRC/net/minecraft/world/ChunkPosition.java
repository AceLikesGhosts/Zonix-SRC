package net.minecraft.world;

import net.minecraft.util.*;

public class ChunkPosition
{
    public final int field_151329_a;
    public final int field_151327_b;
    public final int field_151328_c;
    private static final String __OBFID = "CL_00000132";
    
    public ChunkPosition(final int p_i45363_1_, final int p_i45363_2_, final int p_i45363_3_) {
        this.field_151329_a = p_i45363_1_;
        this.field_151327_b = p_i45363_2_;
        this.field_151328_c = p_i45363_3_;
    }
    
    public ChunkPosition(final Vec3 p_i45364_1_) {
        this(MathHelper.floor_double(p_i45364_1_.xCoord), MathHelper.floor_double(p_i45364_1_.yCoord), MathHelper.floor_double(p_i45364_1_.zCoord));
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (!(p_equals_1_ instanceof ChunkPosition)) {
            return false;
        }
        final ChunkPosition var2 = (ChunkPosition)p_equals_1_;
        return var2.field_151329_a == this.field_151329_a && var2.field_151327_b == this.field_151327_b && var2.field_151328_c == this.field_151328_c;
    }
    
    @Override
    public int hashCode() {
        return this.field_151329_a * 8976890 + this.field_151327_b * 981131 + this.field_151328_c;
    }
}
