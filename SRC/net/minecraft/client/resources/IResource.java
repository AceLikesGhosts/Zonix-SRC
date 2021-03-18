package net.minecraft.client.resources;

import java.io.*;
import net.minecraft.client.resources.data.*;

public interface IResource
{
    InputStream getInputStream();
    
    boolean hasMetadata();
    
    IMetadataSection getMetadata(final String p0);
}
