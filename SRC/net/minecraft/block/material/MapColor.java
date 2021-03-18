package net.minecraft.block.material;

import net.minecraft.block.*;

public class MapColor
{
    public static final MapColor[] mapColorArray;
    public static final MapColor field_151660_b;
    public static final MapColor field_151661_c;
    public static final MapColor field_151658_d;
    public static final MapColor field_151659_e;
    public static final MapColor field_151656_f;
    public static final MapColor field_151657_g;
    public static final MapColor field_151668_h;
    public static final MapColor field_151669_i;
    public static final MapColor field_151666_j;
    public static final MapColor field_151667_k;
    public static final MapColor field_151664_l;
    public static final MapColor field_151665_m;
    public static final MapColor field_151662_n;
    public static final MapColor field_151663_o;
    public static final MapColor field_151677_p;
    public static final MapColor field_151676_q;
    public static final MapColor field_151675_r;
    public static final MapColor field_151674_s;
    public static final MapColor field_151673_t;
    public static final MapColor field_151672_u;
    public static final MapColor field_151671_v;
    public static final MapColor field_151670_w;
    public static final MapColor field_151680_x;
    public static final MapColor field_151679_y;
    public static final MapColor field_151678_z;
    public static final MapColor field_151649_A;
    public static final MapColor field_151650_B;
    public static final MapColor field_151651_C;
    public static final MapColor field_151645_D;
    public static final MapColor field_151646_E;
    public static final MapColor field_151647_F;
    public static final MapColor field_151648_G;
    public static final MapColor field_151652_H;
    public static final MapColor field_151653_I;
    public static final MapColor field_151654_J;
    public static final MapColor field_151655_K;
    public final int colorValue;
    public final int colorIndex;
    private static final String __OBFID = "CL_00000544";
    
    private MapColor(final int p_i2117_1_, final int p_i2117_2_) {
        if (p_i2117_1_ >= 0 && p_i2117_1_ <= 63) {
            this.colorIndex = p_i2117_1_;
            this.colorValue = p_i2117_2_;
            MapColor.mapColorArray[p_i2117_1_] = this;
            return;
        }
        throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
    }
    
    public static MapColor func_151644_a(final int p_151644_0_) {
        switch (BlockColored.func_150031_c(p_151644_0_)) {
            case 0: {
                return MapColor.field_151646_E;
            }
            case 1: {
                return MapColor.field_151645_D;
            }
            case 2: {
                return MapColor.field_151651_C;
            }
            case 3: {
                return MapColor.field_151650_B;
            }
            case 4: {
                return MapColor.field_151649_A;
            }
            case 5: {
                return MapColor.field_151678_z;
            }
            case 6: {
                return MapColor.field_151679_y;
            }
            case 7: {
                return MapColor.field_151680_x;
            }
            case 8: {
                return MapColor.field_151670_w;
            }
            case 9: {
                return MapColor.field_151671_v;
            }
            case 10: {
                return MapColor.field_151672_u;
            }
            case 11: {
                return MapColor.field_151673_t;
            }
            case 12: {
                return MapColor.field_151674_s;
            }
            case 13: {
                return MapColor.field_151675_r;
            }
            case 14: {
                return MapColor.field_151676_q;
            }
            case 15: {
                return MapColor.field_151666_j;
            }
            default: {
                return MapColor.field_151660_b;
            }
        }
    }
    
    public int func_151643_b(final int p_151643_1_) {
        short var2 = 220;
        if (p_151643_1_ == 3) {
            var2 = 135;
        }
        if (p_151643_1_ == 2) {
            var2 = 255;
        }
        if (p_151643_1_ == 1) {
            var2 = 220;
        }
        if (p_151643_1_ == 0) {
            var2 = 180;
        }
        final int var3 = (this.colorValue >> 16 & 0xFF) * var2 / 255;
        final int var4 = (this.colorValue >> 8 & 0xFF) * var2 / 255;
        final int var5 = (this.colorValue & 0xFF) * var2 / 255;
        return 0xFF000000 | var3 << 16 | var4 << 8 | var5;
    }
    
    static {
        mapColorArray = new MapColor[64];
        field_151660_b = new MapColor(0, 0);
        field_151661_c = new MapColor(1, 8368696);
        field_151658_d = new MapColor(2, 16247203);
        field_151659_e = new MapColor(3, 10987431);
        field_151656_f = new MapColor(4, 16711680);
        field_151657_g = new MapColor(5, 10526975);
        field_151668_h = new MapColor(6, 10987431);
        field_151669_i = new MapColor(7, 31744);
        field_151666_j = new MapColor(8, 16777215);
        field_151667_k = new MapColor(9, 10791096);
        field_151664_l = new MapColor(10, 12020271);
        field_151665_m = new MapColor(11, 7368816);
        field_151662_n = new MapColor(12, 4210943);
        field_151663_o = new MapColor(13, 6837042);
        field_151677_p = new MapColor(14, 16776437);
        field_151676_q = new MapColor(15, 14188339);
        field_151675_r = new MapColor(16, 11685080);
        field_151674_s = new MapColor(17, 6724056);
        field_151673_t = new MapColor(18, 15066419);
        field_151672_u = new MapColor(19, 8375321);
        field_151671_v = new MapColor(20, 15892389);
        field_151670_w = new MapColor(21, 5000268);
        field_151680_x = new MapColor(22, 10066329);
        field_151679_y = new MapColor(23, 5013401);
        field_151678_z = new MapColor(24, 8339378);
        field_151649_A = new MapColor(25, 3361970);
        field_151650_B = new MapColor(26, 6704179);
        field_151651_C = new MapColor(27, 6717235);
        field_151645_D = new MapColor(28, 10040115);
        field_151646_E = new MapColor(29, 1644825);
        field_151647_F = new MapColor(30, 16445005);
        field_151648_G = new MapColor(31, 6085589);
        field_151652_H = new MapColor(32, 4882687);
        field_151653_I = new MapColor(33, 55610);
        field_151654_J = new MapColor(34, 1381407);
        field_151655_K = new MapColor(35, 7340544);
    }
}
