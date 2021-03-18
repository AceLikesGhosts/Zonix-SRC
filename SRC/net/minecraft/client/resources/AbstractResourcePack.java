package net.minecraft.client.resources;

import net.minecraft.util.*;
import net.minecraft.client.resources.data.*;
import com.google.common.base.*;
import java.io.*;
import org.apache.commons.io.*;
import com.google.gson.*;
import java.awt.image.*;
import javax.imageio.*;
import org.apache.logging.log4j.*;

public abstract class AbstractResourcePack implements IResourcePack
{
    private static final Logger resourceLog;
    public final File resourcePackFile;
    private static final String __OBFID = "CL_00001072";
    
    public AbstractResourcePack(final File p_i1287_1_) {
        this.resourcePackFile = p_i1287_1_;
    }
    
    private static String locationToName(final ResourceLocation p_110592_0_) {
        return String.format("%s/%s/%s", "assets", p_110592_0_.getResourceDomain(), p_110592_0_.getResourcePath());
    }
    
    protected static String getRelativeName(final File p_110595_0_, final File p_110595_1_) {
        return p_110595_0_.toURI().relativize(p_110595_1_.toURI()).getPath();
    }
    
    @Override
    public InputStream getInputStream(final ResourceLocation p_110590_1_) throws IOException {
        return this.getInputStreamByName(locationToName(p_110590_1_));
    }
    
    @Override
    public boolean resourceExists(final ResourceLocation p_110589_1_) {
        return this.hasResourceName(locationToName(p_110589_1_));
    }
    
    protected abstract InputStream getInputStreamByName(final String p0) throws IOException;
    
    protected abstract boolean hasResourceName(final String p0);
    
    protected void logNameNotLowercase(final String p_110594_1_) {
        AbstractResourcePack.resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[] { p_110594_1_, this.resourcePackFile });
    }
    
    @Override
    public IMetadataSection getPackMetadata(final IMetadataSerializer p_135058_1_, final String p_135058_2_) throws IOException {
        return readMetadata(p_135058_1_, this.getInputStreamByName("pack.mcmeta"), p_135058_2_);
    }
    
    static IMetadataSection readMetadata(final IMetadataSerializer p_110596_0_, final InputStream p_110596_1_, final String p_110596_2_) {
        JsonObject var3 = null;
        BufferedReader var4 = null;
        try {
            var4 = new BufferedReader(new InputStreamReader(p_110596_1_, Charsets.UTF_8));
            var3 = new JsonParser().parse((Reader)var4).getAsJsonObject();
        }
        catch (RuntimeException var5) {
            throw new JsonParseException((Throwable)var5);
        }
        finally {
            IOUtils.closeQuietly((Reader)var4);
        }
        return p_110596_0_.parseMetadataSection(p_110596_2_, var3);
    }
    
    @Override
    public BufferedImage getPackImage() throws IOException {
        return ImageIO.read(this.getInputStreamByName("pack.png"));
    }
    
    @Override
    public String getPackName() {
        return this.resourcePackFile.getName();
    }
    
    static {
        resourceLog = LogManager.getLogger();
    }
}
