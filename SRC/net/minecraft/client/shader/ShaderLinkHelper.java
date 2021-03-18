package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;
import org.apache.logging.log4j.*;

public class ShaderLinkHelper
{
    private static final Logger logger;
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static final String __OBFID = "CL_00001045";
    
    public static void setNewStaticShaderLinkHelper() {
        ShaderLinkHelper.staticShaderLinkHelper = new ShaderLinkHelper();
    }
    
    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return ShaderLinkHelper.staticShaderLinkHelper;
    }
    
    public void func_148077_a(final ShaderManager p_148077_1_) {
        p_148077_1_.func_147994_f().func_148054_b(p_148077_1_);
        p_148077_1_.func_147989_e().func_148054_b(p_148077_1_);
        OpenGlHelper.func_153187_e(p_148077_1_.func_147986_h());
    }
    
    public int func_148078_c() throws JsonException {
        final int var1 = OpenGlHelper.func_153183_d();
        if (var1 <= 0) {
            throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
        }
        return var1;
    }
    
    public void func_148075_b(final ShaderManager p_148075_1_) {
        p_148075_1_.func_147994_f().func_148056_a(p_148075_1_);
        p_148075_1_.func_147989_e().func_148056_a(p_148075_1_);
        OpenGlHelper.func_153179_f(p_148075_1_.func_147986_h());
        final int var2 = OpenGlHelper.func_153175_a(p_148075_1_.func_147986_h(), OpenGlHelper.field_153207_o);
        if (var2 == 0) {
            ShaderLinkHelper.logger.warn("Error encountered when linking program containing VS " + p_148075_1_.func_147989_e().func_148055_a() + " and FS " + p_148075_1_.func_147994_f().func_148055_a() + ". Log output:");
            ShaderLinkHelper.logger.warn(OpenGlHelper.func_153166_e(p_148075_1_.func_147986_h(), 32768));
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
