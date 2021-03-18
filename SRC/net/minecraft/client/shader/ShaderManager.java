package net.minecraft.client.shader;

import net.minecraft.client.resources.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import org.apache.commons.io.*;
import net.minecraft.util.*;
import net.minecraft.client.util.*;
import com.google.gson.*;
import net.minecraft.client.renderer.*;
import java.io.*;
import java.util.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import org.apache.logging.log4j.*;

public class ShaderManager
{
    private static final Logger logger;
    private static final ShaderDefault defaultShaderUniform;
    private static ShaderManager staticShaderManager;
    private static int field_147999_d;
    private static boolean field_148000_e;
    private final Map field_147997_f;
    private final List field_147998_g;
    private final List field_148010_h;
    private final List field_148011_i;
    private final List field_148008_j;
    private final Map field_148009_k;
    private final int field_148006_l;
    private final String field_148007_m;
    private final boolean field_148004_n;
    private boolean field_148005_o;
    private final JsonBlendingMode field_148016_p;
    private final List field_148015_q;
    private final List field_148014_r;
    private final ShaderLoader field_148013_s;
    private final ShaderLoader field_148012_t;
    private static final String __OBFID = "CL_00001040";
    
    public ShaderManager(final IResourceManager p_i45087_1_, final String p_i45087_2_) throws JsonException {
        this.field_147997_f = Maps.newHashMap();
        this.field_147998_g = Lists.newArrayList();
        this.field_148010_h = Lists.newArrayList();
        this.field_148011_i = Lists.newArrayList();
        this.field_148008_j = Lists.newArrayList();
        this.field_148009_k = Maps.newHashMap();
        final JsonParser var3 = new JsonParser();
        final ResourceLocation var4 = new ResourceLocation("shaders/program/" + p_i45087_2_ + ".json");
        this.field_148007_m = p_i45087_2_;
        InputStream var5 = null;
        try {
            var5 = p_i45087_1_.getResource(var4).getInputStream();
            final JsonObject var6 = var3.parse(IOUtils.toString(var5, Charsets.UTF_8)).getAsJsonObject();
            final String var7 = JsonUtils.getJsonObjectStringFieldValue(var6, "vertex");
            final String var8 = JsonUtils.getJsonObjectStringFieldValue(var6, "fragment");
            final JsonArray var9 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "samplers", null);
            if (var9 != null) {
                int var10 = 0;
                for (final JsonElement var12 : var9) {
                    try {
                        this.func_147996_a(var12);
                    }
                    catch (Exception var14) {
                        final JsonException var13 = JsonException.func_151379_a(var14);
                        var13.func_151380_a("samplers[" + var10 + "]");
                        throw var13;
                    }
                    ++var10;
                }
            }
            final JsonArray var15 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "attributes", null);
            if (var15 != null) {
                int var16 = 0;
                this.field_148015_q = Lists.newArrayListWithCapacity(var15.size());
                this.field_148014_r = Lists.newArrayListWithCapacity(var15.size());
                for (final JsonElement var18 : var15) {
                    try {
                        this.field_148014_r.add(JsonUtils.getJsonElementStringValue(var18, "attribute"));
                    }
                    catch (Exception var20) {
                        final JsonException var19 = JsonException.func_151379_a(var20);
                        var19.func_151380_a("attributes[" + var16 + "]");
                        throw var19;
                    }
                    ++var16;
                }
            }
            else {
                this.field_148015_q = null;
                this.field_148014_r = null;
            }
            final JsonArray var21 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var6, "uniforms", null);
            if (var21 != null) {
                int var22 = 0;
                for (final JsonElement var24 : var21) {
                    try {
                        this.func_147987_b(var24);
                    }
                    catch (Exception var26) {
                        final JsonException var25 = JsonException.func_151379_a(var26);
                        var25.func_151380_a("uniforms[" + var22 + "]");
                        throw var25;
                    }
                    ++var22;
                }
            }
            this.field_148016_p = JsonBlendingMode.func_148110_a(JsonUtils.getJsonObjectFieldOrDefault(var6, "blend", null));
            this.field_148004_n = JsonUtils.getJsonObjectBooleanFieldValueOrDefault(var6, "cull", true);
            this.field_148013_s = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.VERTEX, var7);
            this.field_148012_t = ShaderLoader.func_148057_a(p_i45087_1_, ShaderLoader.ShaderType.FRAGMENT, var8);
            this.field_148006_l = ShaderLinkHelper.getStaticShaderLinkHelper().func_148078_c();
            ShaderLinkHelper.getStaticShaderLinkHelper().func_148075_b(this);
            this.func_147990_i();
            if (this.field_148014_r != null) {
                for (final String var27 : this.field_148014_r) {
                    final int var28 = OpenGlHelper.func_153164_b(this.field_148006_l, var27);
                    this.field_148015_q.add(var28);
                }
            }
        }
        catch (Exception var30) {
            final JsonException var29 = JsonException.func_151379_a(var30);
            var29.func_151381_b(var4.getResourcePath());
            throw var29;
        }
        finally {
            IOUtils.closeQuietly(var5);
        }
        this.func_147985_d();
    }
    
    public void func_147988_a() {
        ShaderLinkHelper.getStaticShaderLinkHelper().func_148077_a(this);
    }
    
    public void func_147993_b() {
        OpenGlHelper.func_153161_d(0);
        ShaderManager.field_147999_d = -1;
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_148000_e = true;
        for (int var1 = 0; var1 < this.field_148010_h.size(); ++var1) {
            if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null) {
                GL13.glActiveTexture(33984 + var1);
                GL11.glBindTexture(3553, 0);
            }
        }
    }
    
    public void func_147995_c() {
        this.field_148005_o = false;
        ShaderManager.staticShaderManager = this;
        this.field_148016_p.func_148109_a();
        if (this.field_148006_l != ShaderManager.field_147999_d) {
            OpenGlHelper.func_153161_d(this.field_148006_l);
            ShaderManager.field_147999_d = this.field_148006_l;
        }
        if (ShaderManager.field_148000_e != this.field_148004_n) {
            ShaderManager.field_148000_e = this.field_148004_n;
            if (this.field_148004_n) {
                GL11.glEnable(2884);
            }
            else {
                GL11.glDisable(2884);
            }
        }
        for (int var1 = 0; var1 < this.field_148010_h.size(); ++var1) {
            if (this.field_147997_f.get(this.field_147998_g.get(var1)) != null) {
                GL13.glActiveTexture(33984 + var1);
                GL11.glEnable(3553);
                final Object var2 = this.field_147997_f.get(this.field_147998_g.get(var1));
                int var3 = -1;
                if (var2 instanceof Framebuffer) {
                    var3 = ((Framebuffer)var2).framebufferTexture;
                }
                else if (var2 instanceof ITextureObject) {
                    var3 = ((ITextureObject)var2).getGlTextureId();
                }
                else if (var2 instanceof Integer) {
                    var3 = (int)var2;
                }
                if (var3 != -1) {
                    GL11.glBindTexture(3553, var3);
                    OpenGlHelper.func_153163_f(OpenGlHelper.func_153194_a(this.field_148006_l, this.field_147998_g.get(var1)), var1);
                }
            }
        }
        for (final ShaderUniform var5 : this.field_148011_i) {
            var5.func_148093_b();
        }
    }
    
    public void func_147985_d() {
        this.field_148005_o = true;
    }
    
    public ShaderUniform func_147991_a(final String p_147991_1_) {
        return this.field_148009_k.containsKey(p_147991_1_) ? this.field_148009_k.get(p_147991_1_) : null;
    }
    
    public ShaderUniform func_147984_b(final String p_147984_1_) {
        return this.field_148009_k.containsKey(p_147984_1_) ? this.field_148009_k.get(p_147984_1_) : ShaderManager.defaultShaderUniform;
    }
    
    private void func_147990_i() {
        for (int var1 = 0, var2 = 0; var1 < this.field_147998_g.size(); ++var1, ++var2) {
            final String var3 = this.field_147998_g.get(var1);
            final int var4 = OpenGlHelper.func_153194_a(this.field_148006_l, var3);
            if (var4 == -1) {
                ShaderManager.logger.warn("Shader " + this.field_148007_m + "could not find sampler named " + var3 + " in the specified shader program.");
                this.field_147997_f.remove(var3);
                this.field_147998_g.remove(var2);
                --var2;
            }
            else {
                this.field_148010_h.add(var4);
            }
        }
        for (final ShaderUniform var6 : this.field_148011_i) {
            final String var3 = var6.func_148086_a();
            final int var4 = OpenGlHelper.func_153194_a(this.field_148006_l, var3);
            if (var4 == -1) {
                ShaderManager.logger.warn("Could not find uniform named " + var3 + " in the specified shader program.");
            }
            else {
                this.field_148008_j.add(var4);
                var6.func_148084_b(var4);
                this.field_148009_k.put(var3, var6);
            }
        }
    }
    
    private void func_147996_a(final JsonElement p_147996_1_) {
        final JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147996_1_, "sampler");
        final String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        if (!JsonUtils.jsonObjectFieldTypeIsString(var2, "file")) {
            this.field_147997_f.put(var3, null);
            this.field_147998_g.add(var3);
        }
        else {
            this.field_147998_g.add(var3);
        }
    }
    
    public void func_147992_a(final String p_147992_1_, final Object p_147992_2_) {
        if (this.field_147997_f.containsKey(p_147992_1_)) {
            this.field_147997_f.remove(p_147992_1_);
        }
        this.field_147997_f.put(p_147992_1_, p_147992_2_);
        this.func_147985_d();
    }
    
    private void func_147987_b(final JsonElement p_147987_1_) throws JsonException {
        final JsonObject var2 = JsonUtils.getJsonElementAsJsonObject(p_147987_1_, "uniform");
        final String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
        final int var4 = ShaderUniform.func_148085_a(JsonUtils.getJsonObjectStringFieldValue(var2, "type"));
        final int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var2, "count");
        final float[] var6 = new float[Math.max(var5, 16)];
        final JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
        if (var7.size() != var5 && var7.size() > 1) {
            throw new JsonException("Invalid amount of values specified (expected " + var5 + ", found " + var7.size() + ")");
        }
        int var8 = 0;
        for (final JsonElement var10 : var7) {
            try {
                var6[var8] = JsonUtils.getJsonElementFloatValue(var10, "value");
            }
            catch (Exception var12) {
                final JsonException var11 = JsonException.func_151379_a(var12);
                var11.func_151380_a("values[" + var8 + "]");
                throw var11;
            }
            ++var8;
        }
        if (var5 > 1 && var7.size() == 1) {
            while (var8 < var5) {
                var6[var8] = var6[0];
                ++var8;
            }
        }
        final int var13 = (var5 > 1 && var5 <= 4 && var4 < 8) ? (var5 - 1) : 0;
        final ShaderUniform var14 = new ShaderUniform(var3, var4 + var13, var5, this);
        if (var4 <= 3) {
            var14.func_148083_a((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
        }
        else if (var4 <= 7) {
            var14.func_148092_b(var6[0], var6[1], var6[2], var6[3]);
        }
        else {
            var14.func_148097_a(var6);
        }
        this.field_148011_i.add(var14);
    }
    
    public ShaderLoader func_147989_e() {
        return this.field_148013_s;
    }
    
    public ShaderLoader func_147994_f() {
        return this.field_148012_t;
    }
    
    public int func_147986_h() {
        return this.field_148006_l;
    }
    
    static {
        logger = LogManager.getLogger();
        defaultShaderUniform = new ShaderDefault();
        ShaderManager.staticShaderManager = null;
        ShaderManager.field_147999_d = -1;
        ShaderManager.field_148000_e = true;
    }
}
